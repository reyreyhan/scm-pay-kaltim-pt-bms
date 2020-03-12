package com.bm.main.pos.feature.printer

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import kotlinx.android.synthetic.main.activity_print.*


class PrinterActivity : BaseActivity<PrinterPresenter, PrinterContract.View>(),
    PrinterContract.View {

    lateinit var adapter: PrinterAdapter
    private val CODE_OPEN_SETTING = 10001

    override fun createPresenter(): PrinterPresenter {
        return PrinterPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_print
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        adapter = PrinterAdapter(this).apply {
            callback = PrinterAdapter.Callback { device -> getPresenter()?.onPrint(device) }
        }

        listview.adapter = adapter

        btn_setting.setOnClickListener {
            openBluetoothSetting()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) finish()
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Printer"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            val backArrow = ResourcesCompat.getDrawable(resources, R.drawable.ic_back_pos, null)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onClose() {
        finish()
    }

    override fun openBluetoothSetting() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        startActivityForResult(intent, CODE_OPEN_SETTING)
    }

    override fun showEmpty() {
        listview.visibility = View.GONE
        ll_empty.visibility = View.VISIBLE
    }

    override fun showContent() {
        listview.visibility = View.VISIBLE
        ll_empty.visibility = View.GONE
    }

    override fun addAll(data: List<BluetoothDevice>) {
        adapter.addAll(data)
    }

    override fun clearList() {
        adapter.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_OPEN_SETTING) {
            getPresenter()?.checkDevice()
        }
    }
}