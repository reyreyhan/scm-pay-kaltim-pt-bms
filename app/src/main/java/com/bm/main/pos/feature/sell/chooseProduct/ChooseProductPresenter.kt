package com.bm.main.pos.feature.sell.chooseProduct

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bm.main.fpl.activities.NewHomeActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.SBFApplication
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.utils.AppConstant
import com.google.firebase.analytics.FirebaseAnalytics

class ChooseProductPresenter(val context: Context, val view: ChooseProductContract.View) : BasePresenter<ChooseProductContract.View>(),
    ChooseProductContract.Presenter, ChooseProductContract.InteractorOutput {

    private var interactor  = ChooseProductInteractor(this)
    private var restModel = ProductRestModel(context)

    override fun onFragmentViewCreated(intent: Intent) {
        logEventFireBaseVIEW_ITEM_LIST(
            "Pilih Barang",
            "Show All Product",
            EventParam.EVENT_ACTION_VISIT,
            EventParam.EVENT_SUCCESS,
            NewHomeActivity::class.java.simpleName
        )
        val check = intent.getBooleanExtra(AppConstant.DATA,true)
        view.checkStockProducts(check)
        loadProducts()
    }

    override fun onViewCreated(intent: Intent) {
        logEventFireBaseVIEW_ITEM_LIST(
            "Pilih Barang",
            "Show All Product",
            EventParam.EVENT_ACTION_VISIT,
            EventParam.EVENT_SUCCESS,
            ChooseProductActivity::class.java.simpleName
        )
        val check = intent.getBooleanExtra(AppConstant.DATA,true)
        view.checkStockProducts(check)
        loadProducts()
    }

    override fun loadProducts() {
        interactor.callGetProductsAPI(context,restModel)
    }

    override fun searchProduct(search: String) {
        interactor.onRestartDisposable()
        if(search.isNullOrEmpty() || search.isNullOrBlank()){
            interactor.callGetProductsAPI(context,restModel)
        }
        else{
            interactor.callSearchProductAPI(context,restModel,search)
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetProducts(list: List<Product>) {
        view.setProducts(list)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }

    fun logEventFireBaseVIEW_ITEM_LIST(
        itemCategory: String?,
        itemName: String?,
        action: String?,
        success: String?,
        tag: String?
    ) {
        val bundle = Bundle()
        if (PreferenceClass.getUser() != null) {
            bundle.putString(
                FirebaseAnalytics.Param.ITEM_ID,
                PreferenceClass.getUser().id_outlet
            )
        } else {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getId())
        }
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName)
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory)
        bundle.putString(FirebaseAnalytics.Param.SOURCE, tag)
        bundle.putString(FirebaseAnalytics.Param.SUCCESS, success)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action)
        SBFApplication.sendEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle)
        SBFApplication.sendToAnalylic(itemCategory, itemName, action, tag)
    }
}