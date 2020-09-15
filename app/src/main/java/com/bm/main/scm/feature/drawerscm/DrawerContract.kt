package com.bm.main.scm.feature.drawerscm

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.models.user.merchant.MerchantToko
import com.bm.main.scm.models.user.merchant.MerchantUser
import com.bm.main.scm.models.user.merchant.MerchantUserRestModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate

interface DrawerContract {

    interface View : BaseViewImpl {
        fun selectMenu(resId: Int)
        fun setProfile(name: String, id:String)
        fun setProfilePict(url: String)
        fun openHelpPage()
        fun openSingleDatePickerDialog(
            selected: CalendarDay?,
            minDate: LocalDate?,
            maxDate: LocalDate?,
            type: Int
        )

        fun openFilterDateDialog(
            minDate: LocalDate?,
            maxDate: LocalDate?,
            firstDate: CalendarDay?,
            lastDate: CalendarDay?,
            type: Int
        )

        fun openNoteDialog(selected: Cart, pos: Int)
        fun openCountDialog(selected: Cart, pos: Int)
        fun openShowCaseHomeFragment(context: Context)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun setSelectedIdMenu(id: Int)
        fun getSelectedIdMenu(): Int
        fun setSelectedPosition(position: Int)
        fun getSelectedPosition(): Int
        fun loadProfileRemote()
        fun loadProfileLocal()
        fun logOut()
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetProfileAPI(context: Context, restModel: MerchantUserRestModel)
        fun callGetTokoAPI(context: Context, restModel: MerchantUserRestModel)
        fun saveUser(user: MerchantUser)
        fun saveToko(toko: MerchantToko)
        fun checkProfileToko()
        fun getLocalProfileToko()
        fun resetAppSession()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProfile(list: List<MerchantUser>)
        fun onSuccessGetToko(list: List<MerchantToko>)
        fun onProfileTokoExisting(isExisting:Boolean)
        fun onSuccessGetProfileTokoLocal(user:MerchantUser, toko:MerchantToko)
        fun onFailedAPI(code: Int, msg: String)
    }
}