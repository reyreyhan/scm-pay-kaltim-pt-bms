package com.bm.main.scm.feature.registerqrismerchant

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bm.main.fpl.constants.ActionCode
import com.bm.main.fpl.utils.Device
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.dialog.SuccessDialog
import com.bm.main.scm.feature.drawer.DrawerActivity
import com.bm.main.scm.feature.support.ListKecamatanSCMActivity
import com.bm.main.scm.feature.support.ListKelurahanSCMActivity
import com.bm.main.scm.feature.support.ListKotaSCMActivity
import com.bm.main.scm.feature.support.ListProvinsiSCMActivity
import com.bm.main.scm.ui.ext.htmlText
import com.example.samq.util.dp
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_register_merchant_scm.*
import kotlinx.android.synthetic.main.bottom_sheet_register_scm_help_layout.*
import kotlinx.android.synthetic.main.bottom_sheet_register_scm_help_layout.view.*
import java.io.File

class RegisterQRISMerchantActivity :
    BaseActivity<RegisterQRISMerchantPresenter, RegisterQRISMerchantContract.View>(),
    RegisterQRISMerchantContract.View, SuccessDialog.SuccessDialogListener {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    var provinceCode: String? = ""
    var cityCode: String? = ""
    var districtCode: String? = ""
    var subdistrictCode: String? = ""
    var postCode: String? = ""

    override fun createPresenter(): RegisterQRISMerchantPresenter {
        return RegisterQRISMerchantPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_register_merchant_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView() {
        initForm()
        initButton()
        initBottomSheet()
        initSpinner()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) finish()
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Pendaftaran Merchant QRIS"
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

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun initForm() {
        et_merchant_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        et_merchant_num_hp_owner.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        et_merchant_id_card.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_merchant_npwp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_merchant_address.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_merchant_province.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
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

        et_merchant_city.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
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

        et_merchant_district.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
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

        et_merchant_sub_district.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
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

        tv_lbl_photo_ktp.htmlText("Pastikan <b>foto KTP</b> beserta datanya <b>terlihat jelas</b>")
        tv_lbl_photo_selfie_ktp.htmlText("<b>Wajah, KTP</b>, dan <b>tanda tangan</b> berada dalam <b>1 foto</b>")

        iv_upload_photo_ktp.setOnClickListener {
            ImagePicker.with(this)
                .compress(2048)
                .start{
                    resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            val fileUri = data?.data
                            iv_photo_ktp.setImageURI(fileUri)
                            val file: File = ImagePicker.getFile(data)!!
//                            val filePath:String = ImagePicker.getFilePath(data)!!
                            iv_photo_ktp.visibility = View.VISIBLE
                            iv_upload_photo_ktp.visibility = View.GONE
                        }
                        ImagePicker.RESULT_ERROR -> {
                            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Upload dibatalkan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        iv_upload_photo_selfie_ktp.setOnClickListener {
            ImagePicker.with(this)
                .compress(2048)
                .start{
                        resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            val fileUri = data?.data
                            iv_photo_selfie_ktp.setImageURI(fileUri)
                            val file: File = ImagePicker.getFile(data)!!
//                            val filePath:String = ImagePicker.getFilePath(data)!!
                            iv_photo_selfie_ktp.visibility = View.VISIBLE
                            iv_upload_photo_selfie_ktp.visibility = View.GONE
                        }
                        ImagePicker.RESULT_ERROR -> {
                            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Upload dibatalkan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    private fun initButton() {
        btn_sign_up.setOnClickListener {
            SuccessDialog.newInstance(
                "Selamat Pendaftaran Merchant Berhasil!",
                "Merchant Anda akan mendapat QRIS setelah proses verifikasi selama 2x24 jam"
            ).show(supportFragmentManager, SuccessDialog.TAG)
        }
    }

    private fun initSpinner() {
        val listCriteria = mutableListOf(
            getString(R.string.activity_register_spinner_merchant_business_hint),
            "Usaha Mikro",
            "Usaha Kecil",
            "Usaha Menengah",
            "Usaha Besar"
        )

        val listBusiness = mutableListOf(
            getString(R.string.activity_register_spinner_merchant_criteria_hint),
            "1",
            "2",
            "3",
            "4"
        )

        val adapterBusiness: ArrayAdapter<String> = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listBusiness
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                view.setTextColor(Color.GRAY)
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                view.setPadding(35.dp, 20.dp, 35.dp, 20.dp)
                if (position == spinner_merchant_business.selectedItemPosition && position != 0) {
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }

        val adapterCriteria: ArrayAdapter<String> = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listCriteria
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                view.setTextColor(Color.GRAY)
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                view.setPadding(35.dp, 20.dp, 35.dp, 20.dp)
                // set selected item style
                if (position == spinner_merchant_criteria.selectedItemPosition && position != 0) {
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }

        spinner_merchant_business.adapter = adapterBusiness
        spinner_merchant_criteria.adapter = adapterCriteria
    }

    private fun initBottomSheet() {
        val listOfHelp = mutableListOf(
            "<b>Usaha Mikro</b> memiliki Omzet senilai <b>maksimal 300 Juta</b>",
            "<b>Usaha Kecil</b> memiliki Omzet senilai <b>maksimal 300 Juta sampai 2,5 Milyar</b>",
            "<b>Usaha Menengah</b> memiliki Omzet senilai <b>maksimal 2,5 Milyar sampai 10 Milyar</b>",
            "<b>Usaha Besar</b> memiliki Omzet senilai <b>di atas 10 Milyar</b>"
        )
        lbl_criteria.setOnClickListener {
            slideUpDownBottomSheet()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == ActionCode.LIST_PROPINSI) {
                provinceCode = data!!.getStringExtra("propCode")
                et_merchant_province.setText(data.getStringExtra("propName"))
            } else if (requestCode == ActionCode.LIST_KABUPATEN) {
                cityCode = data!!.getStringExtra("kabCode")
                et_merchant_city.setText(data.getStringExtra("kabName"))
            } else if (requestCode == ActionCode.LIST_KECAMATAN) {
                districtCode = data!!.getStringExtra("kecCode")
                et_merchant_district.setText(data.getStringExtra("kecName"))
            } else if (requestCode == ActionCode.LIST_KODEPOS) {
                subdistrictCode = data!!.getStringExtra("kelCode")
                postCode = data!!.getStringExtra("posCode")
                et_merchant_sub_district.setText(data.getStringExtra("kelName"))
            }
        }
    }

    override fun onPositiveButtonDialog() {
        val intent = Intent(this, DrawerActivity::class.java)
        intent.putExtra("IsMerchant", true)
        startActivity(intent)
    }
}