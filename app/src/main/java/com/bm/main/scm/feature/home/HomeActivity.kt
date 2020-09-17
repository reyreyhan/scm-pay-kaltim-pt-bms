package com.bm.main.scm.feature.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.bm.main.fpl.activities.HomeActivity
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.dialog.NoteDialog
import com.bm.main.scm.feature.dialog.SingleDateDialog
import com.bm.main.scm.feature.home.adapter.PENJUALAN_FRAGMENT_INDEX
import com.bm.main.scm.feature.home.adapter.ProfitHomeFragmentStateAdapter
import com.bm.main.scm.feature.home.adapter.TOKOKU_FRAGMENT_INDEX
import com.bm.main.scm.feature.merchant.MerchantActivity
import com.bm.main.scm.feature.scan.ScanCodeFragment
import com.bm.main.scm.feature.sell.chooseProduct.ChooseProductFragment
import com.bm.main.scm.feature.sell.main.SellFragment
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.models.product.Product
import com.google.android.material.tabs.TabLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home_new.*
import org.threeten.bp.LocalDate
import timber.log.Timber


class HomeActivity : BaseActivity<HomePresenter, HomeContract.View>(),
    HomeContract.View,
    SellFragment.ShowDate,
    ChooseProductFragment.OnProductSelectedListener,
    ScanCodeFragment.OnProductSelectedListener {

    private var currentPage = 0
    private var isTokoku = false

    lateinit var fragmentAdapter: ProfitHomeFragmentStateAdapter

    private val TAG = HomeActivity::class.java.simpleName

    private var ft: FragmentTransaction? = null

    private val disposables by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
//        if (intent.hasExtra("url") && intent.getStringExtra("url").orEmpty().isNotEmpty()) {
//            startActivity(Intent(this, FCMActivity::class.java).putExtras(intent))
//        } else {
//            super.onCreate(savedInstanceState)
//        }
        super.onCreate(savedInstanceState)
//        if (PreferenceClass.isLoggedIn()) {
//            if (SBFApplication.getInstance().rabbitThread == null) {
//                SBFApplication.getInstance().rabbitThread = RabbitMqThread(this)
//                SBFApplication.getInstance().rabbitThread!!.start()
//            } else if (!SBFApplication.getInstance().rabbitThread!!.isAlive) {
//                try {
//                    SBFApplication.getInstance().rabbitThread!!.start()
//                } catch (e: Exception) {
//                    Timber.e(e)
//                }
//            }
//        }
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
        }
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_home_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        //EventBus.getDefault().register(this)
        isTokoku = intent.getBooleanExtra("IS_TOKOKU", false)
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
        fragmentAdapter = ProfitHomeFragmentStateAdapter(supportFragmentManager)
        fragment_pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(fragment_pager)
        for (i in 0 until tab_layout.tabCount) {
            val tab: TabLayout.Tab = tab_layout.getTabAt(i)!!
            when (tab.position) {
                0 -> tab.text = "Penjualan"
                1 -> tab.text = "Tokoku"
                2 -> tab.text = "PPOB"
            }
        }
        fragment_pager.currentItem = if (isTokoku) 1 else 0
        currentPage = fragment_pager.currentItem
        fragment_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                Timber.d("Fragment Pager Current Item: %s", fragment_pager.currentItem)
                if (position == 2) {
                    startActivityForResult(Intent(this@HomeActivity, HomeActivity::class.java), 999)
                } else if (currentPage != position) {
                    currentPage = position;
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (currentPage < 2)
            fragment_pager.currentItem = currentPage
    }

    override fun onBackPressed() {
        val fragment = fragmentAdapter.getItem(fragment_pager.currentItem)
        if (fragment is SellFragment) {
            fragment.onFragmentBackPressed()
        }
        super.onBackPressed()
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            PENJUALAN_FRAGMENT_INDEX -> "Penjualan"
            TOKOKU_FRAGMENT_INDEX -> "Tokoku"
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