package com.bm.main.scm.feature.transaction.success

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.constants.EventParam
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.callback.BluetoothCallback
import com.bm.main.scm.feature.printer.PrinterActivity
import com.bm.main.scm.feature.transaction.detail.DetailSuccessActivity
import com.bm.main.scm.models.transaction.DetailTransaction
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.*
import com.bm.main.scm.utils.print.PrinterUtil
import kotlinx.android.synthetic.main.activity_transaction_success_new.*
import java.lang.Integer.parseInt
import java.net.URLEncoder

class SuccessActivity : BaseActivity<SuccessPresenter, SuccessContract.View>(),
    SuccessContract.View {

    private var fileStruk:String = ""

    override fun setProducts(list: List<DetailTransaction.Data>) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        adapter.setItems(list)
    }

    val adapter = SuccessAdapter()

    override fun createPresenter(): SuccessPresenter {
        return SuccessPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_transaction_success_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        sw_refresh.isRefreshing = false
        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list_barang.layoutManager = layoutManager
        rv_list_barang.adapter = adapter
        //rv_list.addItemDecoration(LinearItemDecoration(space = resources.getDimensionPixelSize(R.dimen._4sdp)))

        btn_cetak_struk.setOnClickListener {
            getPresenter()?.onCheckBluetooth()
        }

        btn_kirim_email.setOnClickListener {
            getPresenter()?.sendStruk(et_email.editableText.toString().trim())
        }

        et_email.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!s.isNullOrEmpty()){
                    btn_kirim_email.isEnabled = true
                }else{
                    btn_kirim_email.isEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        et_no_wa.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!s.isNullOrEmpty()){
                    btn_kirim_no_wa.isEnabled = true
                }else{
                    btn_kirim_no_wa.isEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        btn_kirim_no_wa.setOnClickListener {
//            if (et_no_wa.editableText.toString().isNotEmpty() && fileStruk.isNotEmpty()){
//                shareToWhatsapp(et_no_wa.editableText.toString(), fileStruk)
//
//            }
            val packageManager: PackageManager = getPackageManager()
            val i = Intent(Intent.ACTION_VIEW)
            val noWa = "62${et_no_wa.editableText.toString().trim().substring(1)}"
            try {
                val url =
                    "https://api.whatsapp.com/send?phone=" + noWa + "&text=" + URLEncoder.encode(
                        "Terima kasih sudah membeli.\nStruk Anda: ${getPresenter()?.getDataStruk()?.url_struk}",
                        "UTF-8"
                    )
                i.setPackage("com.whatsapp")
                i.data = Uri.parse(url)
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        tv_nanti.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Cetak Struk"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }

    }

    private val filterWhite by lazy { PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN) }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_struk, menu)
        menu?.findItem(R.id.action_share)?.apply { icon = icon?.apply { colorFilter = filterWhite } }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.action_share -> getPresenter()?.onCheckShare()
            R.id.action_download -> getPresenter()?.onCheckDownload()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun takeScreenshot(filename: String, isShare:Boolean) {
        container_action.visibility = View.GONE
        if (isShare){
            ImageHelper.takeScreenshot(this, ns_content, filename) {
                //container_action.visibility = View.VISIBLE
                Helper.shareBitmapToApps(this, Uri.parse(it))
                //fileStruk = it
            }
        }else{
            ImageHelper.takeScreenshot(this, ns_content, filename)
            showToast("Berhasil mengunduh Struk")
        }
        container_action.visibility = View.VISIBLE
    }

    override fun shareToWhatsapp(contact:String, filename: String) {
        var mContact = contact.substring(1)
        mContact = "62$mContact"
//        try {
//            val sendIntent = Intent("android.intent.action.MAIN")
//            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
//            sendIntent.action = Intent.ACTION_SEND
//            sendIntent.type = "image/*"
//            sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filename))
//            sendIntent.putExtra("jid", mContact + "@s.whatsapp.net")
//            sendIntent.setPackage("com.whatsapp")
//            startActivity(sendIntent)
//        } catch (e: Exception) {
//            toast(this, "Error/n$e")
//        }

        val uri = Uri.parse("smsto:$mContact")
        val i = Intent(Intent.ACTION_SENDTO, uri)
        i.setPackage("com.whatsapp")
        i.type = "image/*"
        i.putExtra(Intent.EXTRA_STREAM, Uri.parse(filename))
        startActivity(Intent.createChooser(i, ""))
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            onErrorView(msg.toString())
        }
    }

    override fun showSuccessMessage(msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        showToast(msg!!)
    }

    private fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadDetail()
    }

    override fun onClose() {
        restartMainActivity()
    }

    @SuppressLint("SetTextI18n")
    var totQty: Int = 0

    override fun onSuccessCash(detail: DetailTransaction, id: String) {
        logEventFireBase(
            "Penjualan",
            "Tunai " + id,
            EventParam.EVENT_ACTION_SELL_PRODUCT,

            EventParam.EVENT_SUCCESS,
            SuccessActivity::class.java.simpleName
        )

        tv_nama_toko.text = detail.struk?.nama_toko
        tv_alamat_toko.text = detail.struk?.alamat
        tv_no_telepon_toko.text=  detail.struk?.nohp
        //htmlText("${detail.struk?.nama_toko} / ${detail.struk?.nohp}<br>${detail.struk?.alamat}")

        val kembalianString = detail.struk?.kembalian
        var kembalian = 0.0
        var kembalianValue: String? = null
        if (!kembalianString.isNullOrEmpty() && !kembalianString.isNullOrBlank()) {
            kembalian = kembalianString.toDouble()
            kembalianValue = "Rp ${Helper.convertToCurrency(kembalian)}"
        }
        tv_id_transaksi.text = id
//        if(!detail.struk?.status.equals("tunai")) {
//            ll_pay.visibility = View.GONE
//            ll_bayar.visibility=View.GONE
//            tv_pay.text = "tunai"
//        }else{
//        ll_pay.visibility = View.GONE
//        ll_bayar.visibility = View.VISIBLE
//        tv_pay.text = detail.struk?.status
        // tv_pay.text = pay
//        }
//        for (i in 0 until detail.data.size){
//            totQty.plus(Int)= detail.data[i].jumlah
//        }

//        for (i in 0 until detail.data.sumBy { data: DetailTransaction.Data ->  }) {
//            totQty += detail.data[i].jumlah.toInt()
//        }
//
//        detail.data.forEach {
//            totQty += parseInt(values)
//        }
        totQty = detail.data?.sumBy { parseInt(it.jumlah!!) } ?: 0
        tv_status.text = "Tunai"
        //tv_total_qty.text = totQty.toString()
        tv_total_item.text = "Rp ${Helper.convertToCurrency(detail.struk?.totalorder!!)}"
        tv_pay.text = "Rp ${Helper.convertToCurrency(detail.struk?.totalbayar!!)}"
//        tv_date.text =
//            Helper.getDateFormat(this, detail.struk?.tanggal!!, "yyyy-MM-dd", "EEE, dd MMMM yyyy")
        tv_kembalian.visibility = View.GONE
        kembalian?.let {
            tv_kembalian.text = kembalianValue
            tv_kembalian.visibility = View.VISIBLE
        }
        onSuccessView()
    }

    override fun onSuccessCash(
        total: String,
        pay: String,
        kembalian: String?,
        date: String,
        id: String
    ) {
//        tv_id_transaksi.text = id
        // tv_order.text = total
        //tv_date.text = date
//        ll_info.visibility = View.VISIBLE
//        if (!pay.equals("tunai")) {
////            ll_pay.visibility = View.VISIBLE
//            tv_pay.text = pay
//        } else {
//            ll_pay.visibility = View.GONE
//            tv_pay.text = pay
//        }
//        ll_piutang.visibility = View.GONE
//        ll_kembalian.visibility = View.GONE
//        kembalian?.let {
//            tv_kembalian.text = kembalian
//            ll_kembalian.visibility = View.VISIBLE
//        }
        onSuccessView()
    }

    override fun onSuccessPiutang(total: String, date: String, id: String) {
//        println("hutang")
        tv_id_transaksi.text = id
        tv_total_item.text = total
        //tv_date.text = date
        //ll_info.visibility = View.VISIBLE
        //ll_piutang.visibility = View.VISIBLE
        layout_kembalian.visibility = View.GONE
        //ll_kembalian.visibility = View.GONE
        tv_status.text = "Hutang"
        tv_pay.text = total
        onSuccessView()
    }

    override fun onSuccessPiutang(detail: DetailTransaction, id: String) {
        logEventFireBase(
            "Penjualan",
            "Hutang " + id,
            EventParam.EVENT_ACTION_SELL_PRODUCT,

            EventParam.EVENT_SUCCESS,
            SuccessActivity::class.java.getSimpleName()
        )
        tv_id_transaksi.text = id
        tv_nama_toko.text = detail.struk?.nama_toko
        tv_alamat_toko.text = detail.struk?.alamat
        tv_no_telepon_toko.text=  detail.struk?.nohp
//        ll_pay.visibility = View.VISIBLE
//        ll_bayar.visibility = View.GONE
        tv_status.text = "Hutang"
        tv_total_item.text = "Rp ${Helper.convertToCurrency(detail.struk?.totalorder!!)}"
        tv_pay.text = "Rp ${Helper.convertToCurrency(detail.struk?.totalorder!!)}"
        layout_kembalian.visibility = View.GONE
//        tv_jatuh_tempo.text =
//            Helper.getDateFormat(this, detail.struk?.jatuh_tempo!!, "yyyy-MM-dd", "dd-MM-yyyy")
//        ll_jatuh_tempo.visibility = View.VISIBLE
//        totQty = detail.data?.sumBy { parseInt(it.jumlah!!) } ?: 0
//
//        tv_total_qty.text = totQty.toString()
//        tv_total.text = "Rp ${Helper.convertToCurrency(detail.struk?.totalorder!!)}"
//        tv_bayar.text="Rp ${Helper.convertToCurrency(detail.struk?.totalbayar!!)}"
//        tv_date.text =
//            Helper.getDateFormat(this, detail.struk?.tanggal!!, "yyyy-MM-dd", "EEE, dd MMMM yyyy")
//        ll_kembalian.visibility = View.GONE
        onSuccessView()
    }

    override fun onSuccess(total: String, date: String, id: String) {
        tv_id_transaksi.text = id
//        tv_order.text = total
//        tv_date.text = date
//        ll_info.visibility = View.GONE
        onSuccessView()
    }



    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onBackPressed() {
        onClose()
    }

    override fun onErrorView(msg: String) {
        sw_refresh.isRefreshing = false
        checkout_view.visibility = View.GONE
        container_action.visibility = View.GONE
        ll_error.visibility = View.VISIBLE
        tv_error.text = msg
        supportActionBar!!.title = "Pembayaran Gagal!"
    }

    override fun onSuccessView() {
        sw_refresh.isRefreshing = false
        checkout_view.visibility = View.VISIBLE
        container_action.visibility = View.VISIBLE
        ll_error.visibility = View.GONE
    }

    private fun getLogo(): Drawable = Drawable.createFromStream(assets.open("logo_profit.bmp"), null)

    private val listDevice by lazy { BluetoothUtil.getPairedDevices() }
    override fun openPrinterPage() {
        showLoadingDialog()
        listDevice.firstOrNull()?.let {
            BluetoothConnectTask(object : BluetoothCallback {
                override fun onConnected(socket: BluetoothSocket?,
                                         taskType: Int,
                                         device: BluetoothDevice?) {
                    PrinterUtil.print(socket,
                        getPresenter()?.getDataStruk(),
                        null,
                        getString(R.string.app_name),
                        getLogo(),
                        device?.name.orEmpty())
                    hideLoadingDialog()
                    showToast("Sedang mencetak struk")
                    listDevice.union(BluetoothUtil.getPairedDevices())
                }

                override fun onError(msg: String?) {
                    listDevice.remove(it)
                    openPrinterPage()
                }

                override fun onPowerOn(intent: Intent?) {}
                override fun onPowerOff(intent: Intent?) {}
            }, 1, "").execute(it)
        } ?: run {
            dialogSelectPrinter()
        }
    }

    fun dialogSelectPrinter() {
        hideLoadingDialog()
        DialogUtils.showDialog(this,
            getString(R.string.btn_print),
            "Gagal menghubungkan printer, buka halaman pilih printer?",
            false,
            buttonOkLabel = "Ya",
            buttonOkAction = {
                startActivity(Intent(this,
                    PrinterActivity::class.java).putExtra(AppConstant.DATA,
                    getPresenter()?.getDataStruk()))
            })
    }

    override fun openDetailPage() {
        val intent = Intent(this, DetailSuccessActivity::class.java)
        intent.putExtra(AppConstant.DATA, getPresenter()?.getDataStruk()?.struk?.no_invoice)
        startActivity(intent)
    }

    override fun getParentLayout(): NestedScrollView {
        return ns_content
    }
}
