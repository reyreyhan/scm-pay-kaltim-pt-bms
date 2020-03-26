package com.bm.main.pos.feature.sell.main

import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.models.transaction.Order
import com.bm.main.pos.models.transaction.RequestTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel


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