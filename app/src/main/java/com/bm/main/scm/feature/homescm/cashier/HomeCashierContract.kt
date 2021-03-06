package com.bm.main.scm.feature.homescm.cashier

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.menu.Menu
import com.bm.main.scm.models.user.Profile
import com.bm.main.scm.models.user.UserRestModel

interface HomeCashierContract {

    interface View : BaseViewImpl {
        fun setMenu(list:List<Menu>)
        fun setProfile(name:String,address:String,city:String,phone:String,url:String,omset:String)

        fun reloadData()
        fun showErrorMessage(code: Int, msg: String)
        fun openAccountPage()
        fun openPPOBPage()
        fun openKulakanPage()
    }


    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun loadProfile()
     //  fun onLogin(nama:String,password:String)
        fun onDestroy()
       // fun onResume()


    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetProfileAPI(context: Context, restModel:UserRestModel)
      //  fun callLoginAPI(context: Context, restModel: UserRestModel, phone:String, password:String)
        fun saveUser(user: Profile)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProfile(list:List<Profile>)
       // fun onSuccessLogin(list:List<User>)
        fun onFailedAPI(code:Int,msg:String)

    }


}