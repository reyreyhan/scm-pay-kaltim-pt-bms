package com.bm.main.pos.feature.manage.hutangpiutang.detailPiutang

//import com.bm.main.pos.utils.glide.GlideApp
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.models.hutangpiutang.DetailPiutangNew
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.toast
import kotlinx.android.synthetic.main.activity_piutang_detail_new.*
import org.jetbrains.anko.toast
import timber.log.Timber


class DetailPiutangActivity : BaseActivity<DetailPiutangPresenter, DetailPiutangContract.View>(), DetailPiutangContract.View {

    val adapter = DetailPiutangAdapter()


    override fun createPresenter(): DetailPiutangPresenter {
        return DetailPiutangPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_piutang_detail_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView(){
        setupToolbar()
        sw_refresh.isRefreshing = true
        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

        et_bayar.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                btn_bayar.isEnabled = !s.isNullOrEmpty()
                val rmvRp = tv_jumlah_hutang.text.replace("\\bRp".toRegex(), "")
                val hutang = rmvRp.replace(".", "").trim()
                Timber.d("Hutang: $hutang")
                if (!s.isNullOrEmpty()){
                    if (s.toString().trim().toInt() > hutang.toInt()){
                        et_bayar.setText(hutang)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        btn_bayar.setOnClickListener {
            getPresenter()?.payHutang(et_bayar.editableText.toString().trim())
        }
    }

    private fun setupToolbar() {
//        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onClose(status: Int) {
        setResult(status,intent)
        finish()
    }

    override fun setCustomer(name: String?) {
        name?.let {
            tv_name.text = it
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun showMessage(code: Int, msg: String?) {
        sw_refresh.isRefreshing = false
        hideLoadingDialog()
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            msg?.let {
                toast(this,it)
            }
        }
    }

    override fun showSuccess(msg: String) {
        toast(msg)
        finish()
    }

    private fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadHutang()
    }

    override fun setPiutang(piutang: String?, tanggal: String?) {
        sw_refresh.isRefreshing = false
        tv_jumlah_hutang.text = piutang!!
        tv_tanggal_hutang.text = tanggal!!
    }

    override fun setList(list: List<DetailPiutangNew.Data>) {
        adapter.setItems(list)
    }

}
