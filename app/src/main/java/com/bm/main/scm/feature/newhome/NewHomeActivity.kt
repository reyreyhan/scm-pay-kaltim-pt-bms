package com.bm.main.scm.feature.newhome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.bm.main.fpl.activities.HomeActivity
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.fpl.webview.FCMActivity
import com.bm.main.scm.R
import com.bm.main.scm.SBFApplication
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.dialog.NoteDialog
import com.bm.main.scm.feature.dialog.SingleDateDialog
import com.bm.main.scm.feature.merchant.MerchantActivity
import com.bm.main.scm.feature.newhome.adapter.NewHomeFragmentStateAdapter
import com.bm.main.scm.feature.newhome.adapter.PENJUALAN_FRAGMENT_INDEX
import com.bm.main.scm.feature.scan.ScanCodeFragment
import com.bm.main.scm.feature.sell.chooseProduct.ChooseProductFragment
import com.bm.main.scm.feature.sell.main.SellFragment
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.rabbit.RabbitMqThread
import com.google.android.material.tabs.TabLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home_new.*
import org.threeten.bp.LocalDate
import timber.log.Timber


class NewHomeActivity : BaseActivity<NewHomePresenter, NewHomeContract.View>(),
    NewHomeContract.View,
    SellFragment.ShowDate,
    ChooseProductFragment.OnProductSelectedListener,
    ScanCodeFragment.OnProductSelectedListener{

    private var currentPage = 0

    lateinit var fragmentAdapter:NewHomeFragmentStateAdapter

   /* private val productViewModel by lazy {
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

    private val qrisViewModel by lazy {
        ViewModelProvider(
            this,
            userComponent!!.qrisComponentFactory()
        ).get(QrisViewModel::class.java)
    }*/

    private val TAG = NewHomeActivity::class.java.simpleName

    private var ft: FragmentTransaction? = null

    private val disposables by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.hasExtra("url") && intent.getStringExtra("url").orEmpty().isNotEmpty()) {
            startActivity(Intent(this, FCMActivity::class.java).putExtras(intent))
        } else {
            super.onCreate(savedInstanceState)
        }

        if (PreferenceClass.isLoggedIn()) {
            if (SBFApplication.getInstance().rabbitThread == null) {
                SBFApplication.getInstance().rabbitThread = RabbitMqThread(this)
                SBFApplication.getInstance().rabbitThread!!.start()
            } else if (!SBFApplication.getInstance().rabbitThread!!.isAlive) {
                try {
                    SBFApplication.getInstance().rabbitThread!!.start()
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    override fun createPresenter(): NewHomePresenter {
        return NewHomePresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_home_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        //EventBus.getDefault().register(this)
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        setSupportActionBar(toolbarx)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = getColor(R.color.white)
        }
        /*toolbar_logo.setOnClickListener {
            RabbitMqPrint.printStrukRabbit(
                "aku adalah\nanak gembala\nselalu riang\ndan bergembira\n",
                this
            )
        }*/
        fragmentAdapter = NewHomeFragmentStateAdapter(supportFragmentManager)
        fragment_pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(fragment_pager)
        for (i in 0 until tab_layout.tabCount) {
            val tab: TabLayout.Tab = tab_layout.getTabAt(i)!!
            when(tab.position){
                0 -> tab.text = "Penjualan"
                1 -> tab.text = "Tokoku"
                2 -> tab.text = "PPOB"
            }
        }
        currentPage = fragment_pager.currentItem
        fragment_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    startActivityForResult(Intent(this@NewHomeActivity, HomeActivity::class.java), 999)
                } else if (currentPage != position){
                    currentPage = position;
                }
            }
        })

//        disposables.add(
//            qrisViewModel.service.check(PreferenceClass.getId().orEmpty())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe({ res ->
//                    //nav_view.menu.findItem(R.id.nav_qris).isEnabled = res.rc == "00"
//                    //homeFragment.enableQrMenu(res.rc == "00")
//                    res.result.firstOrNull()?.let {
//                        Timber.d("Outlet ID: ${PreferenceClass.getId()}")
//                        Timber.d("url_qr: ${it.url_qr}")
//                        Timber.d("nmid: ${it.nmid}")
//                        PreferenceClass.putString("url_qr", it.url_qr)
//                        PreferenceClass.putString("nmid", it.nmid)
//                        PreferenceClass.putString("id_speedcash", it.id_speedcash)
//                        PreferenceClass.putString("nama_toko", it.nama_toko)
////                        PreferenceClass.putString("nama_pemilik", it.nama_pemilik)
//                    }
//                }, { e ->
//                    Timber.e(e)
//                    //nav_view.menu.findItem(R.id.nav_qris).isEnabled = false
//                    //homeFragment.enableQrMenu(false)
//                })
//        )
    }

    override fun onResume() {
        super.onResume()

        if (currentPage < 2)
            fragment_pager.currentItem = currentPage
    }

    override fun onBackPressed() {
        val fragment = fragmentAdapter.getItem(fragment_pager.currentItem)
        if (fragment is SellFragment){
          fragment.onFragmentBackPressed()
        }
        super.onBackPressed()
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            PENJUALAN_FRAGMENT_INDEX -> "Penjualan"
            else -> null
        }
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

    override fun openNoteDialog(selected: Cart, pos: Int) {
        val noteDialog = NoteDialog.newInstance()
        noteDialog.setData(selected, pos)
        noteDialog.show(this.supportFragmentManager, NoteDialog.TAG)
    }

    override fun openCountDialog(selected: Cart, pos: Int) {

    }

    override fun onProductSelected(data: Product) {
        val fragment = fragmentAdapter.getItem(fragment_pager.currentItem)
        if (fragment is SellFragment) {
            fragment.getPresenter()?.checkCart(data, null)
            fragment.hideContainerFragment()
            fragment.setDeselectButtonSearch()
        }
    }

    override fun onProductSelected(data: String) {
        val fragment = fragmentAdapter.getItem(fragment_pager.currentItem)
        if (fragment is SellFragment) {
            fragment.getPresenter()?.searchByBarcode(data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_new, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_merchant -> {
                val intent = Intent(this, MerchantActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 999) {
            restartMainActivity()
        }
    }
}