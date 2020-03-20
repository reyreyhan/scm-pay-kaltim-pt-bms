package com.bm.main.pos.feature.drawer

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bm.main.fpl.templates.showcaseview.GuideView
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.fpl.webview.FCMActivity
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.di.userComponent
import com.bm.main.pos.events.onHistoryChangedDate
import com.bm.main.pos.events.onHistoryChangedStatus
import com.bm.main.pos.events.onMenuClicked
import com.bm.main.pos.feature.dialog.*
import com.bm.main.pos.feature.home.HomeFragment
import com.bm.main.pos.feature.manage.main.ManageFragment
import com.bm.main.pos.feature.manage.product.ProductViewModel
import com.bm.main.pos.feature.qris.QrFragment
import com.bm.main.pos.feature.report.main.ReportFragment
import com.bm.main.pos.feature.sell.main.SellFragment
import com.bm.main.pos.feature.setting.main.SettingFragment
import com.bm.main.pos.feature.transaction.history.HistoryFragment
import com.bm.main.pos.feature.transaction.historyTransaction.TransactionFragment
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.rabbit.QrisViewModel
import com.bm.main.pos.rabbit.RabbitMqPrint
import com.bm.main.pos.rest.salesforce.SfViewModel
import com.bm.main.pos.utils.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.navigation.NavigationView
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.squareup.moshi.Types
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.content_drawer.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.threeten.bp.LocalDate
import timber.log.Timber

class DrawerActivity : BaseActivity<DrawerPresenter, DrawerContract.View>(), DrawerContract.View,
    NavigationView.OnNavigationItemSelectedListener,
//    HomeFragment.MenuClick,
    SellFragment.ShowDate,
    SettingFragment.Listener, SingleDateDialog.Listener, NoteDialog.Listener,
    CartCountDialog.Listener, TransactionFragment.Listener, RangeDateDialog.Listener,
    BottomDialog.Listener {

    private val productViewModel by lazy {
        ViewModelProvider(
            this,
            userComponent!!.productComponentFactory()
        ).get(ProductViewModel::class.java)
    }

    private val sfViewModel by lazy {
        ViewModelProvider(
            this,
            userComponent!!.sfComponentFactory()
        ).get(SfViewModel::class.java)
    }

    private val TAG = DrawerActivity::class.java.simpleName
    private var homeFragment: HomeFragment = HomeFragment.newInstance()
    private var sellFragment: SellFragment = SellFragment.newInstance()
    private var historyFragment: Fragment = HistoryFragment.newInstance()
    private var reportFragment: ReportFragment = ReportFragment.newInstance()
    private var managementFragment: ManageFragment = ManageFragment.newInstance()
    private var settingFragment: SettingFragment = SettingFragment.newInstance()
    private val qrFragment by lazy { QrFragment() }
    private var helpFragment: Fragment = Fragment()

    //private lateinit var toolbar:Toolbar
    private lateinit var tvName: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvCity: TextView
    private lateinit var tvPhone: TextView
    private lateinit var ivPhoto: ImageView
    private lateinit var btnLogout: ImageView
    private lateinit var btnDrawer: ImageView

    private var ft: FragmentTransaction? = null

    private val disposables by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.hasExtra("url") && intent.getStringExtra("url").orEmpty().isNotEmpty()) {
            startActivity(Intent(this, FCMActivity::class.java).putExtras(intent))
        } else {
            super.onCreate(savedInstanceState)
        }
    }

    private val qrisViewModel by lazy {
        ViewModelProvider(
            this,
            userComponent!!.qrisComponentFactory()
        ).get(QrisViewModel::class.java)
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

    private fun renderView() {
        setSupportActionBar(toolbarx)

        toolbar_logo.setOnClickListener {
            RabbitMqPrint.printStrukRabbit(
                "aku adalah\nanak gembala\nselalu riang\ndan bergembira\n",
                this
            )
        }

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
        }

        nav_view.setNavigationItemSelectedListener(this)

        val headerView = nav_view.getHeaderView(0)
        tvName = headerView.findViewById(R.id.tv_name) as TextView
        tvAddress = headerView.findViewById(R.id.tv_address) as TextView
        tvPhone = headerView.findViewById(R.id.tv_phone) as TextView
        tvCity = headerView.findViewById(R.id.tv_city) as TextView
        ivPhoto = headerView.findViewById(R.id.iv_photo) as ImageView
        btnLogout = headerView.findViewById(R.id.btn_logout) as ImageView
        btnDrawer = headerView.findViewById(R.id.btn_drawer) as ImageView

        btnLogout.setOnClickListener {
            restartLoginActivity()
        }

        btnDrawer.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        productViewModel.moshi.adapter<List<CustomMenu>>(
                Types.newParameterizedType(
                    List::class.java,
                    CustomMenu::class.java
                )
            )
            .fromJson(FirebaseRemoteConfig.getInstance().getString("customMenu"))
            ?.filter { it.isActive }?.sortedBy { it.order }?.forEach {
                nav_view.menu.add(0, it.code.hashCode(), it.order, it.label).apply {
                    Glide.with(this@DrawerActivity).asDrawable().load(it.icon)
                        .into(object : CustomTarget<Drawable>() {
                            override fun onResourceReady(
                                resource: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                icon = resource
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {}
                        })
                }
            }

        disposables.add(
            qrisViewModel.service.check(PreferenceClass.getId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ res ->
                    nav_view.menu.findItem(R.id.nav_qris).isEnabled = res.rc == "00"
                    homeFragment.enableQrMenu(res.rc == "00")

                    res.result.firstOrNull()?.let {
                        PreferenceClass.putString("url_qr", it.url_qr)
                        PreferenceClass.putString("nmid", it.nmid)
                        PreferenceClass.putString("id_speedcash", it.id_speedcash)
                        PreferenceClass.putString("nama_toko", it.nama_toko)
                        PreferenceClass.putString("nama_pemilik", it.nama_pemilik)
                    }
                }, { e ->
                    Timber.e(e)
                    nav_view.menu.findItem(R.id.nav_qris).isEnabled = false
                    homeFragment.enableQrMenu(false)
                })
        )

        disposables.add(
            sfViewModel.apiService.checkStatus(PreferenceClass.getId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ res ->
                    homeFragment.setLengkapiButtonVisibility(if (res.status == 0) View.VISIBLE else View.GONE)
                }, { e ->
                    Timber.e(e)
                    homeFragment.setLengkapiButtonVisibility(View.GONE)
                })
        )
    }

    private fun replaceContent(resId: Int) {
        ft = supportFragmentManager.beginTransaction()
        when (resId) {
            R.id.nav_home -> {
                getPresenter()?.setSelectedIdMenu(resId)
                toolbar_title.visibility = View.GONE
                toolbar_logo.visibility = View.VISIBLE
                if (homeFragment.isAdded) {
                    ft!!.show(homeFragment)
                } else {
                    ft!!.add(R.id.fragment_container, homeFragment)
                }
                ft!!.commit()
                hideFragment(ft!!, sellFragment)
                hideFragment(ft!!, historyFragment)
                hideFragment(ft!!, reportFragment)
                hideFragment(ft!!, managementFragment)
                hideFragment(ft!!, settingFragment)
                hideFragment(ft!!, helpFragment)
                hideFragment(ft!!, qrFragment)
            }
            R.id.nav_sell -> {
                toolbar_title.visibility = View.VISIBLE
                toolbar_logo.visibility = View.GONE
                getPresenter()?.setSelectedIdMenu(resId)
                toolbar_title.text = getString(R.string.menu_sell)
                if (sellFragment.isAdded) {
                    ft!!.show(sellFragment)
                    sellFragment.checkCarts()
                } else {
                    ft!!.add(R.id.fragment_container, sellFragment)
                }
                ft!!.commit()
                hideFragment(ft!!, homeFragment)
                hideFragment(ft!!, historyFragment)
                hideFragment(ft!!, reportFragment)
                hideFragment(ft!!, managementFragment)
                hideFragment(ft!!, settingFragment)
                hideFragment(ft!!, helpFragment)
                hideFragment(ft!!, qrFragment)
            }
            R.id.nav_history -> {
                toolbar_title.visibility = View.VISIBLE
                toolbar_logo.visibility = View.GONE
                getPresenter()?.setSelectedIdMenu(resId)
                toolbar_title.text = getString(R.string.menu_history)
                if (historyFragment.isAdded) {
                    ft!!.show(historyFragment)

                } else {
                    ft!!.add(R.id.fragment_container, historyFragment)
                }
                ft!!.commit()
                hideFragment(ft!!, homeFragment)
                hideFragment(ft!!, sellFragment)
                hideFragment(ft!!, reportFragment)
                hideFragment(ft!!, managementFragment)
                hideFragment(ft!!, settingFragment)
                hideFragment(ft!!, helpFragment)
                hideFragment(ft!!, qrFragment)
            }
            R.id.nav_report -> {
                toolbar_title.visibility = View.VISIBLE
                toolbar_logo.visibility = View.GONE
                getPresenter()?.setSelectedIdMenu(resId)
                toolbar_title.text = getString(R.string.menu_report)
                if (reportFragment.isAdded) {
                    ft!!.show(reportFragment)
                } else {
                    ft!!.add(R.id.fragment_container, reportFragment)
                }
                ft!!.commit()
                hideFragment(ft!!, homeFragment)
                hideFragment(ft!!, historyFragment)
                hideFragment(ft!!, sellFragment)
                hideFragment(ft!!, managementFragment)
                hideFragment(ft!!, settingFragment)
                hideFragment(ft!!, helpFragment)
                hideFragment(ft!!, qrFragment)
            }
            R.id.nav_management -> {
                toolbar_title.visibility = View.VISIBLE
                toolbar_logo.visibility = View.GONE
                getPresenter()?.setSelectedIdMenu(resId)
                toolbar_title.text = getString(R.string.menu_management)
                if (managementFragment.isAdded) {
                    ft!!.show(managementFragment)
                } else {
                    ft!!.add(R.id.fragment_container, managementFragment)
                }
                ft!!.commit()
                hideFragment(ft!!, homeFragment)
                hideFragment(ft!!, historyFragment)
                hideFragment(ft!!, reportFragment)
                hideFragment(ft!!, sellFragment)
                hideFragment(ft!!, settingFragment)
                hideFragment(ft!!, helpFragment)
                hideFragment(ft!!, qrFragment)
            }
            R.id.nav_setting -> {
                toolbar_title.visibility = View.VISIBLE
                toolbar_logo.visibility = View.GONE
                getPresenter()?.setSelectedIdMenu(resId)
                toolbar_title.text = getString(R.string.menu_setting)
                if (settingFragment.isAdded) {
                    ft!!.show(settingFragment)
                } else {
                    ft!!.add(R.id.fragment_container, settingFragment)
                }
                ft!!.commit()
                hideFragment(ft!!, homeFragment)
                hideFragment(ft!!, historyFragment)
                hideFragment(ft!!, reportFragment)
                hideFragment(ft!!, managementFragment)
                hideFragment(ft!!, sellFragment)
                hideFragment(ft!!, helpFragment)
                hideFragment(ft!!, qrFragment)
            }
            R.id.nav_help -> {
                val id = getPresenter()?.getSelectedIdMenu()
                nav_view.setCheckedItem(id!!)
                openHelpPage()
            }
            R.id.nav_qris -> {
                toolbar_title.visibility = View.VISIBLE
                toolbar_logo.visibility = View.GONE
                getPresenter()?.setSelectedIdMenu(resId)
                toolbar_title.text = getString(R.string.menu_qris)
                if (qrFragment.isAdded) {
                    ft!!.show(qrFragment)
                } else {
                    ft!!.add(R.id.fragment_container, qrFragment)
                }
                ft!!.commit()
                hideFragment(ft!!, homeFragment)
                hideFragment(ft!!, historyFragment)
                hideFragment(ft!!, reportFragment)
                hideFragment(ft!!, managementFragment)
                hideFragment(ft!!, sellFragment)
                hideFragment(ft!!, helpFragment)
                hideFragment(ft!!, settingFragment)
            }
            else -> {
                toolbar_title.visibility = View.GONE
                toolbar_logo.visibility = View.VISIBLE
                getPresenter()?.setSelectedIdMenu(resId)
                if (homeFragment.isAdded) {
                    ft!!.show(homeFragment)
                } else {
                    ft!!.add(R.id.fragment_container, homeFragment)
                }
                ft!!.commit()
                hideFragment(ft!!, sellFragment)
                hideFragment(ft!!, historyFragment)
                hideFragment(ft!!, reportFragment)
                hideFragment(ft!!, managementFragment)
                hideFragment(ft!!, settingFragment)
                hideFragment(ft!!, helpFragment)
            }
        }
    }

    private fun hideFragment(ft: FragmentTransaction, fragment: Fragment) {
        if (fragment.isAdded) {
            ft.hide(fragment)
        }
    }

    override fun selectMenu(resId: Int) {
        Log.d("drawer", "selectMenu")
        nav_view.setCheckedItem(resId)
        replaceContent(resId)
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
        replaceContent(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun setProfile(
        name: String,
        address: String,
        city: String,
        phone: String,
        url: String
    ) {
        tvName.text = name
        tvAddress.text = address

        if (city.isNotBlank()) {
            tvCity.visibility = View.VISIBLE
            tvCity.text = city
        } else {
            tvCity.visibility = View.GONE
        }

        tvPhone.text = phone
        Glide.with(this).load(url).error(R.drawable.logo).placeholder(R.drawable.logo)
            .transform(CenterCrop(), CircleCrop()).into(ivPhoto)
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
            sellFragment.setSelectedDate(selected)
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
        sellFragment.onNoteSaved(selected, pos)
    }

    override fun onCountSaved(selected: Cart, pos: Int) {
        sellFragment.onCountSaved(selected, pos)
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

    override fun openFilterByStatusDialog(
        title: String,
        list: List<DialogModel>,
        selected: DialogModel?,
        type: Int
    ) {
        val dialog = BottomDialog.newInstance()
        dialog.setData(title, type, list, selected)
        dialog.show(this.supportFragmentManager, BottomDialog.TAG)
    }

    override fun onReloadProfile() {
        homeFragment.reloadData()
    }

    override fun onItemClicked(data: DialogModel, type: Int) {
        if (AppConstant.Code.CODE_FILTER_DATE_HISTORY == type) {
            EventBus.getDefault().post(onHistoryChangedStatus(data))
        }
    }

    override fun openShowCaseHomeFragment(context: Context) {
        if (PreferenceClass.getInt(TAG, 0) != 1) {
            showCaseFirstHomePos(
                context,
                "",
                "Klik disini untuk melakukan transaksi PPOB dan pemesanan Tiket Pesawat, Kereta, dan Kapal",
                homeFragment.btn_ppob
            )
            mGbuilder.setGuideListener(GuideView.GuideListener { view ->
                when (view.id) {
                    R.id.btn_ppob -> mGbuilder.setTitle("")
                        .setBackgroundColor(R.color.black_overlay).setContentText(
                            "Klik disini untuk melakukan pembelian barang kebutuhan Toko Anda"
                        ).setTargetView(
                            homeFragment.btn_grocery
                        ).build()
                    R.id.btn_grocery -> {
                        PreferenceClass.putInt(TAG, 1)
                        return@GuideListener
                    }
                }
                mGuideView = mGbuilder.build()
                mGuideView.show()
            })
            mGuideView = mGbuilder.build()
            mGuideView.show()
        }
    }

    fun showCaseFirstHomePos(
        context: Context?,
        title: String?,
        description: String?,
        viewFirst: View?
    ) {
        mGbuilder = GuideView.Builder(context).setTitle(title)
            .setBackgroundColor(R.color.black_overlay).setContentText(description)
            .setGravity(GuideView.Gravity.center).setDismissType(GuideView.DismissType.anywhere)
            .setTargetView(viewFirst)
    }
}