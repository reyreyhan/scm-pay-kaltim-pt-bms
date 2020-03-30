package com.bm.main.pos.feature.manage.product.list

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel


interface ProductListContract {

    interface View : BaseViewImpl {
        fun setProducts(list:List<Product>)
        fun showErrorMessage(code: Int, msg: String)
        fun showSuccessMessage(msg: String?)
        fun reloadData()
        fun openAddPage()
        fun openEditPage(data: Product)
        fun openScanPage()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadProducts()
        fun deleteProduct(id:String)
        fun searchProduct(search:String)
        fun searchProductMaster(search:String)
        fun searchByBarcode(search:String)
        fun onCheckScan()
        fun onCheckSort()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetProductsAPI(context: Context, restModel:ProductRestModel)
        fun callSearchProductByNameAPI(context: Context, restModel: ProductRestModel, search: String)
        fun callDeleteProductAPI(context: Context, restModel:ProductRestModel, id:String)
        fun callSearchProductAPI(context: Context, restModel:ProductRestModel, search:String)
        fun callSearchByBarcodeAPI(context: Context, restModel:ProductRestModel, search:String)
        fun callSortProductsAPI(context: Context, restModel:ProductRestModel)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProducts(list:List<Product>)
        fun onSuccessDeleteProduct(msg: String?)
        fun onSuccessByBarcode(list: List<Product>)
        fun onSuccessSort(list: List<Product>)
        fun onFailedAPI(code:Int,msg:String)
    }


}