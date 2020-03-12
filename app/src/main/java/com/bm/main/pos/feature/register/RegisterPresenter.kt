package com.bm.main.pos.feature.register

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.constraintlayout.solver.widgets.Helper
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.user.User

class RegisterPresenter(val context: Context, val view: RegisterContract.View) : BasePresenter<RegisterContract.View>(),
    RegisterContract.Presenter, RegisterContract.InteractorOutput {

    private var interactor: RegisterInteractor = RegisterInteractor(this)

    override fun onViewCreated() {

    }



}