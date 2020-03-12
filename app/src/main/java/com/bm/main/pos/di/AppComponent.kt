package com.bm.main.pos.di

import android.content.Context
import com.bm.main.pos.SBFApplication
import com.bm.sc.bebasbayar.social.di.UserComponent
import com.squareup.moshi.Moshi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MoshiModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appContext: Context,
            @BindsInstance appInstance: SBFApplication
        ): AppComponent
    }

    fun userComponentFactory(): UserComponent.Factory
    fun moshi(): Moshi
}