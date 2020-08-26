package com.bm.main.scm.feature.manage.product.list

import android.content.Context
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel
import com.bm.main.scm.utils.PermissionUtil

class ProductListPresenter(val context: Context, val view: ProductListContract.View) : BasePresenter<ProductListContract.View>(),
    ProductListContract.Presenter, ProductListContract.InteractorOutput {

    private var interactor: ProductListInteractor = ProductListInteractor(this)
    private var restModel = ProductRestModel(context)
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var cameraPermission: PermissionCallback
    private var sort = false
    private var offset = 0

    fun setOffset(offset:Int){
        this.offset = offset
    }

    fun getOffset():Int{
        return offset
    }

    override fun onViewCreated() {
        cameraPermission = object : PermissionCallback{
            override fun onSuccess() {
                view.openScanPage()
            }

            override fun onFailed() {
                view.showErrorMessage(999,context.getString(R.string.reason_permission_camera))
            }
        }
    }
    override fun onCheckScan() {
        permissionUtil.checkCameraPermission(cameraPermission)
    }


    override fun loadProducts() {
        interactor.callGetProductsAPI(context,restModel)
    }

    override fun deleteProduct(id: String) {
        interactor.callDeleteProductAPI(context,restModel,id)
    }

    override fun searchProduct(search: String) {
        interactor.onRestartDisposable()
        if(search.isNullOrEmpty() || search.isNullOrBlank()){
            loadProducts()
        }
        else{
            interactor.callSearchProductAPI(context, restModel, search)
        }
    }

    override fun searchProductMaster(search: String, offset:Int) {
        interactor.onRestartDisposable()
        if(search.isNullOrEmpty() || search.isNullOrBlank()){
            this.offset = 0
            interactor.callSearchProductByNameAPI(context, restModel,"", 20, offset)
        }
        else{
            interactor.callSearchProductByNameAPI(context, restModel, search, 20, offset)
        }
    }

    override fun searchByBarcode(search: String) {
        interactor.callSearchByBarcodeAPI(context,restModel,search)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetProducts(list: List<Product>) {
        sort = false
        view.setProducts(list)
    }

    override fun onSuccessDeleteProduct(msg: String?) {
        view.showSuccessMessage(msg)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }

    override fun onSuccessByBarcode(list: List<Product>) {
        if(list.isEmpty()){
            view.showErrorMessage(999,"Barang tidak ditemukan")
            return
        }
        val data = list[0]
        view.openEditPage(data)
    }

    override fun onCheckSort() {
        interactor.onRestartDisposable()
        if(sort){
            interactor.callGetProductsAPI(context,restModel)
        }
        else{
            interactor.callSortProductsAPI(context,restModel)
        }
    }

    override fun onSuccessSort(list: List<Product>) {
        sort = true
        view.setProducts(list)
    }


}