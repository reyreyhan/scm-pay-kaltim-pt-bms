package com.bm.main.scm.feature.drawerscm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bm.main.fpl.webview.FCMActivity
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.events.onHistoryChangedDate
import com.bm.main.scm.events.onHistoryChangedStatus
import com.bm.main.scm.events.onMenuClicked
import com.bm.main.scm.feature.cashout.CashoutActivity
import com.bm.main.scm.feature.dialog.*
import com.bm.main.scm.feature.home.HomeActivity
import com.bm.main.scm.feature.homescm.cashier.HomeCashierFragment
import com.bm.main.scm.feature.homescm.merchant.HomeMerchantFragment
import com.bm.main.scm.feature.loginscm.LoginActivity
import com.bm.main.scm.feature.manage.cashier.list.CashierListActivity
import com.bm.main.scm.feature.notification.NotificationSCMActivity
import com.bm.main.scm.feature.profilescm.ProfileSCMActivity
import com.bm.main.scm.feature.registerqrismerchantscm.HelpBottomSheetAdapter
import com.bm.main.scm.feature.reportscm.mutation.ReportMutationActivity
import com.bm.main.scm.feature.sell.main.SellFragment
import com.bm.main.scm.feature.setting.main.SettingFragment
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.AppSession
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.bottom_sheet_help_rv_scm.*
import kotlinx.android.synthetic.main.bottom_sheet_help_rv_scm.view.*
import kotlinx.android.synthetic.main.content_drawer.*
import kotlinx.android.synthetic.main.nav_header_drawer.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.threeten.bp.LocalDate
import timber.log.Timber

@AndroidEntryPoint
class DrawerActivity : BaseActivity<DrawerPresenter, DrawerContract.View>(), DrawerContract.View,
    NavigationView.OnNavigationItemSelectedListener,
    SellFragment.ShowDate,
    SettingFragment.Listener, SingleDateDialog.Listener, NoteDialog.Listener,
    CartCountDialog.Listener, RangeDateDialog.Listener,
    BottomDialog.Listener {

    private val MENU_NOTIFICATION = 1
    private var isMerchant = false

    private val TAG = DrawerActivity::class.java.simpleName

    private val homeCashierFragment: HomeCashierFragment = HomeCashierFragment()
    private val homeMerchantFragment: HomeMerchantFragment = HomeMerchantFragment()

    private var ft: FragmentTransaction? = null

    private val disposables by lazy { CompositeDisposable() }

    private val appSession = AppSession()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.hasExtra("url") && intent.getStringExtra("url").orEmpty().isNotEmpty()) {
            startActivity(Intent(this, FCMActivity::class.java).putExtras(intent))
        } else {
            isMerchant = appSession.getSharedPreferenceBoolean("IS_MERCHANT")
            super.onCreate(savedInstanceState)
        }
    }

    override fun createPresenter(): DrawerPresenter {
        return DrawerPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_drawer
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (isMerchant) {
            menu!!.clear()
            menu.add(0, MENU_NOTIFICATION, Menu.NONE, "Notification")
                .setIcon(R.drawable.ic_notif_scm_white)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_NOTIFICATION -> startActivity(Intent(this, NotificationSCMActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderView() {
        setSupportActionBar(toolbarx)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbarx,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_drawer)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setBackgroundDrawable(ColorDrawable(getColor(R.color.colorAccent)))
            } else {
                setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
            }
        }

        nav_view.setNavigationItemSelectedListener(this)

        val headerView = nav_view.getHeaderView(0)

        if (!isMerchant) {
            nav_view.menu.removeItem(R.id.nav_cashout_balance)
            nav_view.menu.removeItem(R.id.nav_mutation)
            nav_view.menu.removeItem(R.id.nav_qris_cashier)
            nav_view.menu.removeItem(R.id.nav_my_store)
            nav_view.menu.removeItem(R.id.nav_POS)
            headerView.tv_merchant_profile.visibility = View.GONE
        } else {
            headerView.tv_merchant_profile.setOnClickListener {
                startActivity(Intent(this, ProfileSCMActivity::class.java))
            }
        }

        ll_nav_exit.setOnClickListener {
            getPresenter()?.logOut()
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
        initBottomSheet()
        headerView.tv_help.setOnClickListener {
            slideUpDownBottomSheet()
        }
        headerView.btn_close.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        ft = supportFragmentManager.beginTransaction()

        if (!isMerchant) {
            if (homeCashierFragment.isAdded) {
                ft!!.show(homeCashierFragment)
            } else {
                ft!!.add(R.id.fragment_container, homeCashierFragment)
            }
        } else {
            if (homeMerchantFragment.isAdded) {
                ft!!.show(homeMerchantFragment)
            } else {
                ft!!.add(R.id.fragment_container, homeMerchantFragment)
            }
        }
        ft!!.commit()
    }

    private fun hideFragment(ft: FragmentTransaction, fragment: Fragment) {
        if (fragment.isAdded) {
            ft.hide(fragment)
        }
    }

    override fun selectMenu(resId: Int) {
        Log.d("drawer", "selectMenu")
        nav_view.setCheckedItem(resId)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
            return
        }
        if (R.id.nav_home == getPresenter()?.getSelectedIdMenu()) {
            finishAffinity()
        } else {
            selectMenu(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        Timber.e("onNavigationItemSelected: $item")
//        replaceContent(item.itemId)
        when (item.itemId) {
            R.id.nav_cashout_balance -> startActivity(Intent(this, CashoutActivity::class.java))
            R.id.nav_qris_cashier -> startActivity(Intent(this, CashierListActivity::class.java))
            R.id.nav_mutation -> startActivity(Intent(this, ReportMutationActivity::class.java))
            R.id.nav_my_store -> startActivity(Intent(this, HomeActivity::class.java).putExtra("IS_TOKOKU", true))
            R.id.nav_POS -> startActivity(Intent(this, HomeActivity::class.java).putExtra("IS_TOKOKU", false))
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun setProfile(
        name: String,
        id: String
    ) {
        val headerView = nav_view.getHeaderView(0)
        headerView.tv_name.text = name
        headerView.tv_id.text = id
    }

    override fun setProfilePict(url: String) {
        val headerView = nav_view.getHeaderView(0)
        Glide.with(this).load(url).error(R.drawable.logo).placeholder(R.drawable.logo)
            .transform(CenterCrop(), CircleCrop()).into(headerView.iv_photo)
    }

    @Subscribe
    fun onEvent(event: onMenuClicked) {
        event.id.let {
            selectMenu(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        EventBus.getDefault().unregister(this)
        getPresenter()?.onDestroy()
    }

    override fun openHelpPage() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(AppConstant.URL.HELP))
        startActivity(browserIntent)
    }

    override fun openSingleDatePickerDialog(
        selected: CalendarDay?,
        minDate: LocalDate?,
        maxDate: LocalDate?,
        type: Int
    ) {
        val dateDialog = SingleDateDialog.newInstance()
        dateDialog.setData(selected, minDate, maxDate, type)
        dateDialog.show(this.supportFragmentManager, SingleDateDialog.TAG)
    }

    override fun onDateClicked(selected: CalendarDay?, type: Int) {
        if (AppConstant.Code.CODE_FILTER_DATE_SELL == type) {
//            sellFragment.setSelectedDate(selected)
        }
    }

    override fun openNoteDialog(selected: Cart, pos: Int) {
        val noteDialog = NoteDialog.newInstance()
        noteDialog.setData(selected, pos)
        noteDialog.show(this.supportFragmentManager, NoteDialog.TAG)
    }

    override fun openCountDialog(selected: Cart, pos: Int) {
        val dialog = CartCountDialog.newInstance()
        dialog.setData(selected, pos, true)
        dialog.show(this.supportFragmentManager, CartCountDialog.TAG)
    }

    override fun onNoteSaved(selected: Cart, pos: Int) {
//        sellFragment.onNoteSaved(selected, pos)
    }

    override fun onCountSaved(selected: Cart, pos: Int) {
//        sellFragment.onCountSaved(selected, pos)
    }

    override fun openFilterDateDialog(
        minDate: LocalDate?,
        maxDate: LocalDate?,
        firstDate: CalendarDay?,
        lastDate: CalendarDay?,
        type: Int
    ) {
        val dateDialog = RangeDateDialog.newInstance()
        dateDialog.setType(type)
        dateDialog.setData(minDate, maxDate, firstDate, lastDate)
        dateDialog.show(this.supportFragmentManager, RangeDateDialog.TAG)
    }

    override fun onDateRangeClicked(firstDate: CalendarDay?, lastDate: CalendarDay?, type: Int) {
        if (AppConstant.Code.CODE_FILTER_DATE_HISTORY == type) {
            EventBus.getDefault().post(onHistoryChangedDate(firstDate, lastDate))
        }
    }

//    override fun openFilterByStatusDialog(
//        title: String,
//        list: List<DialogModel>,
//        selected: DialogModel?,
//        type: Int
//    ) {
//        val dialog = BottomDialog.newInstance()
//        dialog.setData(title, type, list, selected)
//        dialog.show(this.supportFragmentManager, BottomDialog.TAG)
//    }

    override fun onReloadProfile() {
//        homeFragment.reloadData()
    }

    override fun onItemClicked(data: DialogModel, type: Int) {
        if (AppConstant.Code.CODE_FILTER_DATE_HISTORY == type) {
            EventBus.getDefault().post(onHistoryChangedStatus(data))
        }
    }

    override fun openShowCaseHomeFragment(context: Context) {
//        if (PreferenceClass.getInt(TAG, 0) != 1) {
//            showCaseFirstHomePos(
//                context,
//                "",
//                "Klik disini untuk melakukan transaksi PPOB dan pemesanan Tiket Pesawat, Kereta, dan Kapal",
//                homeFragment.btn_ppob
//            )
//            mGbuilder.setGuideListener(GuideView.GuideListener { view ->
//                when (view.id) {
//                    R.id.btn_ppob -> mGbuilder.setTitle("")
//                        .setBackgroundColor(R.color.black_overlay).setContentText(
//                            "Klik disini untuk melakukan pembelian barang kebutuhan Toko Anda"
//                        ).setTargetView(
//                            homeFragment.btn_grocery
//                        ).build()
//                    R.id.btn_grocery -> {
//                        PreferenceClass.putInt(TAG, 1)
//                        return@GuideListener
//                    }
//                }
//                mGuideView = mGbuilder.build()
//                mGuideView.show()
//            })
//            mGuideView = mGbuilder.build()
//            mGuideView.show()
//        }
    }

    private fun showCaseFirstHomePos(
        context: Context?,
        title: String?,
        description: String?,
        viewFirst: View?
    ) {
//        mGbuilder = GuideView.Builder(context).setTitle(title)
//            .setBackgroundColor(R.color.black_overlay).setContentText(description)
//            .setGravity(GuideView.Gravity.center).setDismissType(GuideView.DismissType.anywhere)
//            .setTargetView(viewFirst)
    }

    @SuppressLint("ResourceType")
    private fun initBottomSheet() {
        bottom_sheet.lbl_title.text = "Informasi Saldo"
        val listOfHelp = mutableListOf(
            "<span color:${resources.getString(R.color.colorAccent)}><b>Saldo QRIS</b></span> merupakan saldo yang Anda terima saat transaksi" +
                    "berhasil. Saldo ini <b>belum dapat Anda gunakan</b> karena menunggu proses <b>pelimpahan dana</b> sesuai regulasi Bank Indonesia" +
                    ", akan otomatis berpindah ke <b>Saldo Bisnis</b> maksimal 1x24 jam.",
            "<span color:${resources.getString(R.color.colorAccent)}><b>Saldo Bisnis</b></span> merupakan saldo yang dapat <b>Anda tarik ke rekening</b>" +
                    "maupun digunakan untuk <b>keperluan lainnya</b>, misalnya untuk <b>Pembayaran</b>."
        )
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                }
            }
        })
        bottom_sheet.rv_help.adapter = HelpBottomSheetAdapter(listOfHelp.toList())
        bottom_sheet.rv_help.layoutManager = LinearLayoutManager(this)
    }

    private fun slideUpDownBottomSheet() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
//            coordinatorLayout.visibility = View.VISIBLE
            drawer_layout.closeDrawer(GravityCompat.START)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
//            coordinatorLayout.visibility = View.GONE
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.state === BottomSheetBehavior.STATE_EXPANDED) {
                val outRect = Rect()
                bottom_sheet.getGlobalVisibleRect(outRect)
                if (!outRect.contains(
                        event.rawX.toInt(),
                        event.rawY.toInt()
                    )
                ) bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        return super.dispatchTouchEvent(event)
    }
}