package com.bm.main.scm.feature.manage.cashier.list

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.manage.cashier.add.CashierAddActivity
import kotlinx.android.synthetic.main.activity_qris_cashier_manage.*

class CashierListActivity : BaseActivity<CashierListPresenter, CashierListContract.View>(), CashierListContract.View {
    override fun createPresenter(): CashierListPresenter {
        return CashierListPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_qris_cashier_manage
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
//        getPresenter()?.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "QRIS Kasir"
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
        initRecyclerView()
        initButton()
    }

    private fun initButton() {
        btn_add_qris_cashier.setOnClickListener {
            startActivity(Intent(this, CashierAddActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        val arrayCashier = ArrayList<CashierObject>()
        arrayCashier.add(CashierObject(1, "Aisyah", true))
        arrayCashier.add(CashierObject(2, "Putra", false))
        val adapter = CashierListAdapter(arrayCashier)
        rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }
}

data class CashierObject(
    var id:Int? = null,
    var name:String? = null,
    var active:Boolean = false
)
