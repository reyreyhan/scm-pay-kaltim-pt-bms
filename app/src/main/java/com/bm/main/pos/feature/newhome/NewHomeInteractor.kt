package com.bm.main.pos.feature.newhome;

import android.content.Context
import android.os.Handler
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.feature.drawer.DrawerActivity
import com.google.gson.Gson
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class NewHomeInteractor(var output: NewHomeContract.InteractorOutput?) : NewHomeContract.Interactor {

    override fun onDestroy() {
    }
}