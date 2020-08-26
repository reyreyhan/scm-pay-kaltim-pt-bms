package com.bm.main.scm.feature.sell.main

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel
import com.bm.main.scm.models.transaction.Order
import com.bm.main.scm.models.transaction.RequestTransaction
import com.bm.main.scm.models.transaction.TransactionRestModel
import com.prolificinteractive.materialcalendarview.CalendarDay


interface SellContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun openScanPage()
        fun openChooseProduct()
        fun openAddManual(barcode:String)
        fun openEditManual(product: Product)
        fun showContentView()
        fun showErrorView(err:String)
        fun setCartText(count:String,nominal:String)
        fun addCart(data: Cart)
        fun getTotalValue():Double
        fun getPayValue():Double
        fun setCashback(value:Double)
        fun hideShowCashback(value:Int)
        fun hideContentView()
        fun enableBtnBuy(isEnable:Boolean)
        fun updateCart(cart:Cart,position: Int)
        fun deleteCart(position: Int)
        fun showTunaiView()
        fun showConfirmPayTunaiDialog(jumlah:String, cashback:String, jumlahBarang:Int)
        fun showConfirmPayHutangDialog(jumlah:String, cashback:String, jumlahBarang:Int, namaPelanggan:String)
        fun showPayNonTunai(jumlah:String)
        fun showTambahBarangDialog(code:String)
        fun showNonTunaiView()
        fun showPiutangView()
        fun setCustomerName(data:Customer?)
        fun openChooseCustomer()
        fun openAddCustomer()
        fun setSelectedDate(date:CalendarDay?)
        fun checkCarts()
        fun openSuccessPage(id:String)
        fun onNoteSaved(selected: Cart, pos: Int)
        fun onCountSaved(selected: Cart, pos: Int)
        fun hideContainerFragment()
        fun setDeselectButtonSearch()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheckScan()
        fun checkCart(data:Product, barcode: String?)
        fun addCart(data:Product)
        fun increaseCart(data:Cart,position:Int)
        fun decreaseCart(data: Cart,position:Int)
        fun deleteCart(data: Cart,position:Int)
        fun updateCart(data: Cart,position:Int)
        fun countCart()
        fun searchByBarcode(search:String)
        fun countCashback()
        fun updateCustomer(data:Customer?)
        fun setSelectedDate(date:CalendarDay?)
        fun getSelectedDate():CalendarDay?
        fun checkTunai()
        fun checkNonTunai()
        fun checkPiutang()
        fun getCartsSize():Int
        fun getCustomerName():String
        fun clearCart()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callSearchByBarcodeAPI(context: Context, restModel: ProductRestModel, search:String)
        fun callOrderAPI(context: Context, restModel: TransactionRestModel, req: RequestTransaction)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessByBarcode(list: List<Product>)
        fun onSuccessOrder(message: Order)
        fun onFailedBarcode(code:Int,msg:String)
        fun onFailedAPI(code:Int,msg:String)
    }


}