package com.bm.main.scm.feature.qrisscm

import android.annotation.SuppressLint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.registerqrismerchantscm.HelpBottomSheetAdapter
import com.bm.main.scm.rabbit.QrisService
import com.bm.main.scm.utils.AppSession
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_qris_merchant_scm.*
import kotlinx.android.synthetic.main.bottom_sheet_help_rv_scm.*
import kotlinx.android.synthetic.main.bottom_sheet_help_rv_scm.view.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis.view.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis_2.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis_2.view.*
import kotlinx.android.synthetic.main.fragment_qris_product.*
import kotlinx.android.synthetic.main.fragment_qris_static.*
import javax.inject.Inject

@AndroidEntryPoint
class QrisSCMActivity : BaseActivity<QrisSCMPresenter, QrisSCMContract.View>(),
    QrisSCMContract.View {
    @Inject
    lateinit var qrisService: QrisService

    private val appSession: AppSession = AppSession()

    private var disposable: Disposable? = null

    private var merchantLogin = true
    private val QRIS_DYNAMIC = R.id.btn_qris_dynamic
    private val QRIS_STATIC = R.id.btn_qris_static
    private val QRIS_PRODUCT = R.id.btn_qris_product

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun createPresenter(): QrisSCMPresenter {
        return QrisSCMPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_qris_merchant_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        merchantLogin = intent.getBooleanExtra("IsMerchant", false)
        loadQrisStatic(appSession.getSharedPreferenceString("FASTPAY_ID")!!)
        renderView()
//        getPresenter()?.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "QRIS"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                backArrow.setTint(resources.getColor(android.R.color.black))
            }
            setHomeAsUpIndicator(backArrow)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu!!.clear()
        menu.add(0, 1, Menu.NONE, "QRIS Help").apply {
            setIcon(R.drawable.ic_baseline_help_30)
//            icon.setTint(resources.getColor(R.color.white))
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> {
                slideUpDownBottomSheet()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderView() {
        initViewTab()
        initButtonListener()
        initBottomSheet()
    }

    private fun initButtonListener() {
        toggle_button.addOnButtonCheckedListener { group, checkedId, isChecked ->
            changeTab(checkedId)
        }
        qrisDynamicView.btn_process.setOnClickListener {
            if (qrisDynamicView.et_payment_ammount.text.toString().isNotEmpty()) {
                qrisDynamicView.visibility = View.GONE
                qrisDynamic2View.visibility = View.VISIBLE
                setQrisDynamicLayoutView()
            }
        }
        qrisStaticView.btn_download.setOnClickListener {

        }
        qrisStaticView.btn_share.setOnClickListener {

        }
    }

    private fun initViewTab() {
        if (!merchantLogin) {
            btn_qris_product.visibility = View.GONE
        }
        changeTab(QRIS_DYNAMIC)
        toggle_button.check(QRIS_DYNAMIC)
    }

    private fun setQrisDynamicLayoutView() {
        val ammount = qrisDynamicView.et_payment_ammount.text.toString()
        qrisDynamic2View.tv_ammount.text = "Rp $ammount"
        loadQrisDynamic(appSession.getSharedPreferenceString("FASTPAY_ID")!!, ammount, ammount)
    }

    private fun setQrisStaticLayoutView() {
        loadQrisStatic(appSession.getSharedPreferenceString("FASTPAY_ID")!!)
    }

    private fun changeTab(selectedId: Int) {
        when (selectedId) {
            QRIS_DYNAMIC -> {
                qrisDynamicView.visibility = View.VISIBLE
                qrisDynamic2View.visibility = View.GONE
                qrisStaticView.visibility = View.GONE
                qrisProductView.visibility = View.GONE
            }
            QRIS_STATIC -> {
                qrisDynamicView.visibility = View.GONE
                qrisDynamic2View.visibility = View.GONE
                qrisStaticView.visibility = View.VISIBLE
                qrisProductView.visibility = View.GONE
                setQrisStaticLayoutView()
            }
            QRIS_PRODUCT -> {
                qrisDynamicView.visibility = View.GONE
                qrisDynamic2View.visibility = View.GONE
                qrisStaticView.visibility = View.GONE
                qrisProductView.visibility = View.VISIBLE
            }
        }
    }

    private fun loadQrisStatic(fastpay_id: String) {
        Glide.with(this)
            .load("https://mp.fastpay.co.id/qris/image_qris_receiver?sc_id=${fastpay_id}")
            .into(qrisStaticView.iv_qris)
    }

    private fun loadQrisDynamic(fastpay_id: String, bill: String, billNumber: String) {
        Glide.with(this)
            .load("https://mp.fastpay.co.id/qris/image_qris_dinamis_receiver?sc_id=$fastpay_id&nominal=$bill&bill_number=$billNumber")
            .into(qrisDynamic2View.iv_qris)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("ResourceType")
    private fun initBottomSheet() {
        bottom_sheet.lbl_title.text = "Jenis QRIS"
        val listOfHelp = mutableListOf(
            "<b>QRIS Dinamis</b> merupakan kode QR yang bisa dibuat setelah Anda menginputkan nominal tertentu sehingga pelanggan Anda tidak perlu menginputkan nominal setiap pembayaran.",
            "<b>QRIS Statis</b> merupakan kode QR yang tidak dapat berubah. Setiap transaksi pelanggan Anda harus menginputkan nominal untuk melakukan pembayaran."
        )
        if (merchantLogin){
            listOfHelp.add("<b>Qris Produk</b> merupakan kode QR yang tidak dapat berubah. Setiap transaksi pelanggan Anda harus menginputkan nominal untuk melakukan pembayaran.")
        }
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
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
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
