package com.bm.main.pos.feature.home

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.menu.Menu
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel

interface HomeContract {

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
        fun saveUser(user: User)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProfile(list:List<User>)
       // fun onSuccessLogin(list:List<User>)
        fun onFailedAPI(code:Int,msg:String)

    }


}