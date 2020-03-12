package com.bm.main.pos.feature.manage.supplier.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper

class EditSupplierPresenter(val context: Context, val view: EditSupplierContract.View) : BasePresenter<EditSupplierContract.View>(),
    EditSupplierContract.Presenter, EditSupplierContract.InteractorOutput {


    private var interactor = EditSupplierInteractor(this)
    private var restModel = SupplierRestModel(context)
    private var data : Supplier ?= null
    private var provinces:HashMap<String,List<String>>? = null
    private var selectedProvince = ""
    private var selectedCity = ""


    override fun onViewCreated(intent: Intent) {
        data = intent.getSerializableExtra(AppConstant.DATA) as Supplier
        if(data == null){
            view.onClose(null,Activity.RESULT_CANCELED)
            return
        }

        data?.let {
            selectedProvince = it.profinsi!!
            selectedCity = it.kota!!
            view.setSupplier(it.nama_supplier,it.email,it.telpon,it.alamat,it.profinsi,it.kota)
        }

        val json = Helper.getJsonStringFromAssets(context,"indonesia.json")
        json?.let {
            val type = object : TypeToken<HashMap<String, List<String>>>() {}.type
            provinces = Gson().fromJson(it,type)
            provinces?.let {p->
                Log.d("provinces", Gson().toJson(provinces))
            }
        }
    }

    override fun onCheck(name:String,email:String,phone:String,address:String,province:String,city:String) {
        if(name.isNullOrBlank() || name.isNullOrEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_name))
            return
        }

        if(email.isNotBlank() && email.isNotEmpty()){
            if(!Helper.isEmailValid(email)){
                view.showMessage(999,context.getString(R.string.err_email_format))
                return
            }
        }

        if(phone.isNullOrBlank() || phone.isNullOrEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_phone))
            return
        }

        if(!Helper.isPhoneValid(phone)){
            view.showMessage(999,context.getString(R.string.err_phone_format))
            return
        }

        if(address.isNullOrBlank() || address.isNullOrEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_address))
            return
        }

        if(province.isNullOrBlank() || province.isNullOrEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_province))
            return
        }

        if(city.isNullOrBlank() || city.isNullOrEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_city))
            return
        }

        interactor.callEditSupplierAPI(context,restModel,data?.id_supplier!!,name,email,phone,address,province,city)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessEditSupplier(msg: String?) {
        view.onClose(msg,Activity.RESULT_OK)
    }

    override fun onFailedEditSupplier(code: Int, msg: String) {
        view.showMessage(code,msg)
    }

    override fun onCheckProvince() {
        val list = provinces?.keys?.toList()
        list?.let {
            view.openPlace("Pilih Provinsi",AppConstant.Code.CODE_PROVINCE,list!!.sorted(),selectedProvince)
        }

    }

    override fun onCheckCity() {
        if(selectedProvince.isNullOrBlank() || selectedProvince.isNullOrEmpty()){
            view.showMessage(999,"Pilih provinsi terlebih dahulu")
            return
        }

        val list = provinces?.get(selectedProvince)
        list?.let {
            view.openPlace("Pilih Kota",AppConstant.Code.CODE_CITY,list!!.sorted(),selectedCity)
        }
    }

    override fun setProvince(value: String) {
        selectedProvince = value
    }

    override fun setCity(value: String) {
        selectedCity = value
    }

}