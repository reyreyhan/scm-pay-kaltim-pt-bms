package com.bm.main.pos.feature.drawer

import android.content.Context
import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.cart.Cart
import org.threeten.bp.LocalDate
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel

interface DrawerContract {

    interface View : BaseViewImpl {
        fun selectMenu(resId: Int)
        fun setProfile(name: String, address: String, city: String, phone: String, url: String)
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
        fun loadProfile()
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetProfileAPI(context: Context, restModel: UserRestModel)
        fun saveUser(user: User)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProfile(list: List<User>)
        fun onFailedAPI(code: Int, msg: String)
    }
}