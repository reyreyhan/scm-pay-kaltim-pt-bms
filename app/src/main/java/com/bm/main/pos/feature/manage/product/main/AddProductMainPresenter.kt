package com.bm.main.pos.feature.manage.product.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.category.CategoryRestModel
import android.util.Log
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.feature.scan.ScanCodeFragment
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.PermissionUtil
import timber.log.Timber

class AddProductMainPresenter(val context: Context, val view: AddProductMainContract.View) :
    BasePresenter<AddProductMainContract.View>(),
    AddProductMainContract.Presenter,
    AddProductMainContract.InteractorOutput{

    private var interactor =
        AddProductMainInteractor(this)

    override fun onViewCreated(intent: Intent) {
        val barcode = intent.getStringExtra(AppConstant.DATA)
        if (!barcode.isNullOrEmpty()){
            view.openAddProductFragmentFromScan(barcode)
        }else{
            view.openScanFragment()
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}