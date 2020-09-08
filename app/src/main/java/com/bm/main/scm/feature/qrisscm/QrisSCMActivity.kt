package com.bm.main.scm.feature.qrisscm

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.rabbit.QrisService
import com.bm.main.scm.utils.AppSession
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_qris_merchant_scm.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis.view.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis_2.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis_2.view.*
import kotlinx.android.synthetic.main.fragment_qris_product.*
import kotlinx.android.synthetic.main.fragment_qris_static.*
import timber.log.Timber
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

    override fun createPresenter(): QrisSCMPresenter {
        return QrisSCMPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_qris_merchant_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        merchantLogin = intent.getBooleanExtra("IsMerchant", false)
        loadData()
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

    private fun renderView() {
        initViewTab()
        initButtonListener()
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
        if (!appSession.getSharedPreferenceString("URL_QRIS").isNullOrEmpty()){
            Glide.with(this)
                .load(appSession.getSharedPreferenceString("URL_QRIS"))
                .into(qrisDynamic2View.iv_qris)
            qrisDynamic2View.tv_ammount.text = "Rp ${qrisDynamicView.et_payment_ammount.text}"
        }
    }

    private fun setQrisStaticLayoutView() {
        if (!appSession.getSharedPreferenceString("URL_QRIS").isNullOrEmpty()){
            Glide.with(this)
                .load(appSession.getSharedPreferenceString("URL_QRIS"))
                .into(qrisStaticView.iv_qris)
        }
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

    private fun loadData() {
        disposable?.dispose()
        disposable = qrisService
            .check("FT1013")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
//                swipe.isRefreshing = false
                if (result.rc == "00" && !result.result.isNullOrEmpty()) {
                    val check = result.result[0]
                    appSession.setSharedPreferenceString("URL_QRIS", check.url_qr)
                }
            }, { error ->
                Timber.d("Error: %s", error.toString())
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
