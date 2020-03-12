package com.bm.main.pos.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.bm.sc.bebasbayar.social.di.UserComponent

interface AppComponentProvider {
    var appComponent: AppComponent
    fun getUserComponent(): UserComponent?
}

val Activity.appComponent get() = (application as AppComponentProvider).appComponent
val Fragment.appComponent: AppComponent? get() = (activity?.application as AppComponentProvider).appComponent

val Activity.userComponent get() = (application as AppComponentProvider).getUserComponent()
val Fragment.userComponent get() = (activity!!.application as AppComponentProvider).getUserComponent()