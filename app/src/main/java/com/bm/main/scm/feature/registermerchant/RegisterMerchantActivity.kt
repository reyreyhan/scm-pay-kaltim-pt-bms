package com.bm.main.scm.feature.registermerchant

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import com.bm.main.fpl.constants.ActionCode
import com.bm.main.fpl.utils.Device
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.dialog.SuccessDialog
import com.bm.main.scm.feature.support.ListKecamatanSCMActivity
import com.bm.main.scm.feature.support.ListKelurahanSCMActivity
import com.bm.main.scm.feature.support.ListKotaSCMActivity
import com.bm.main.scm.feature.support.ListProvinsiSCMActivity
import kotlinx.android.synthetic.main.activity_register_scm.*
import timber.log.Timber

class RegisterMerchantActivity : BaseActivity<RegisterMerchantPresenter, RegisterMerchantContract.View>(), RegisterMerchantContract.View {

    var provinceCode: String? = ""
    var cityCode: String? = ""
    var districtCode: String? = ""
    var subdistrictCode: String? = ""
    var postCode: String? = ""

    override fun createPresenter(): RegisterMerchantPresenter {
        return RegisterMerchantPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_register_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView(){
        initForm()
        initButton()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) finish()
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Registrasi"
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

    private fun initForm() {
        et_merchant_owner.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        et_merchant_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        et_merchant_num_hp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        et_merchant_pin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_merchant_pin_confirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_merchant_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_merchant_province.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val intent = Intent(this, ListProvinsiSCMActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivityForResult(intent, ActionCode.LIST_PROPINSI)
            }
        }
        et_merchant_province.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ListProvinsiSCMActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivityForResult(intent, ActionCode.LIST_PROPINSI)
        })

        et_merchant_city.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (et_merchant_province.editableText.toString().isEmpty()) {
                    et_merchant_province.animation = animShake
                    et_merchant_province.startAnimation(animShake)
                    et_merchant_province.error = "Provinsi Tidak Boleh Kosong"
                    et_merchant_province.requestFocus()
                    Device.vibrate(this)
                    return@OnFocusChangeListener
                }
                val intent = Intent(this, ListKotaSCMActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.putExtra("propCode", provinceCode)
                startActivityForResult(intent, ActionCode.LIST_KABUPATEN)
            }
        }
        et_merchant_city.setOnClickListener(View.OnClickListener {
            if (et_merchant_province.editableText.toString().isEmpty()) {
                et_merchant_province.animation = animShake
                et_merchant_province.startAnimation(animShake)
                et_merchant_province.error = "Propinsi Tidak Boleh Kosong"
                Device.vibrate(this)
                return@OnClickListener
            }
            val intent = Intent(this, ListKotaSCMActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("propCode", provinceCode)
            startActivityForResult(intent, ActionCode.LIST_KABUPATEN)
        })

        et_merchant_district.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (et_merchant_city.editableText.toString().isEmpty()) {
                    et_merchant_city.animation = animShake
                    et_merchant_city.startAnimation(animShake)
                    et_merchant_city.error = "Kabupaten/Kota Tidak Boleh Kosong"
                    et_merchant_city.requestFocus()
                    Device.vibrate(this)
                    return@OnFocusChangeListener
                }
                val intent = Intent(this, ListKecamatanSCMActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.putExtra("kabCode", cityCode)
                startActivityForResult(intent, ActionCode.LIST_KECAMATAN)
            }
        }
        et_merchant_district.setOnClickListener(View.OnClickListener {
            if (et_merchant_city.editableText.toString().isEmpty()) {
                et_merchant_city.animation = animShake
                et_merchant_city.startAnimation(animShake)
                et_merchant_city.error = "Kabupaten/Kota Tidak Boleh Kosong"
                Device.vibrate(this)
                return@OnClickListener
            }
            val intent = Intent(this, ListKecamatanSCMActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("kabCode", cityCode)
            startActivityForResult(intent, ActionCode.LIST_KECAMATAN)
        })

        et_merchant_sub_district.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (et_merchant_district.editableText.toString().isEmpty()) {
                    et_merchant_district.animation = animShake
                    et_merchant_district.startAnimation(animShake)
                    et_merchant_district.error = "Kecamatan Tidak Boleh Kosong"
                    et_merchant_district.requestFocus()
                    Device.vibrate(this)
                    return@OnFocusChangeListener
                }
                val intent = Intent(this, ListKelurahanSCMActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.putExtra("kecCode", districtCode)
                startActivityForResult(intent, ActionCode.LIST_KODEPOS)
            }
        }
        et_merchant_sub_district.setOnClickListener(View.OnClickListener {
            if (et_merchant_district.editableText.toString().isEmpty()) {
                et_merchant_district.animation = animShake
                with(et_merchant_district) {
                    et_merchant_district.startAnimation(animShake)
                    et_merchant_district.error = "Kecamatan Tidak Boleh Kosong"
                }
                Device.vibrate(this)
                return@OnClickListener
            }
            val intent = Intent(this, ListKelurahanSCMActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("kecCode", districtCode)
            startActivityForResult(intent, ActionCode.LIST_KODEPOS)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun initButton() {
        btn_sign_up.setOnClickListener {
            SuccessDialog.newInstance("Selamat Pendaftaran Merchant Berhasil!", "Merchant Anda akan mendapat QRIS setelah proses verifikasi selama 2x24 jam").show(supportFragmentManager, SuccessDialog.TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActionCode.LIST_PROPINSI -> {
                    provinceCode = data!!.getStringExtra("propCode")
                    Timber.d("PropCode: %s", provinceCode)
                    et_merchant_province.setText(data.getStringExtra("propName"))
                }
                ActionCode.LIST_KABUPATEN -> {
                    cityCode = data!!.getStringExtra("kabCode")
                    et_merchant_city.setText(data.getStringExtra("kabName"))
                }
                ActionCode.LIST_KECAMATAN -> {
                    districtCode = data!!.getStringExtra("kecCode")
                    et_merchant_district.setText(data.getStringExtra("kecName"))
                }
                ActionCode.LIST_KODEPOS -> {
                    subdistrictCode = data!!.getStringExtra("kelCode")
                    postCode = data.getStringExtra("posCode")
                    et_merchant_sub_district.setText(data.getStringExtra("kelName"))
                }
            }
        }
    }
}
