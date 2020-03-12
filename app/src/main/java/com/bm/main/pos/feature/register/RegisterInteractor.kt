package com.bm.main.pos.feature.register;

import android.os.Handler
import com.bm.main.pos.models.user.User
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.AppSession

class RegisterInteractor(var output: RegisterContract.InteractorOutput?) : RegisterContract.Interactor {

    private val appSession = AppSession()

}