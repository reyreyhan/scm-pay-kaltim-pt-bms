package com.bm.sc.bebasbayar.social.di

import com.bm.main.scm.feature.manage.product.ProductViewModel
import com.bm.main.scm.rabbit.QrisServiceModule
import com.bm.main.scm.rabbit.QrisViewModel
import com.bm.main.scm.rest.ApiRequestModule
import com.bm.main.scm.rest.salesforce.ApiSalesForceModule
import com.bm.main.scm.rest.salesforce.SfViewModel
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class UserScope

@UserScope
@Subcomponent(modules = [ApiRequestModule::class, QrisServiceModule::class, ApiSalesForceModule::class])
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("token") token: String,
            @BindsInstance @Named("version_name") versionName: String
        ): UserComponent
    }

    fun productComponentFactory(): ViewModelFactory<ProductViewModel>
    fun qrisComponentFactory(): ViewModelFactory<QrisViewModel>
    fun sfComponentFactory(): ViewModelFactory<SfViewModel>
}