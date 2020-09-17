package com.bm.main.scm.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier

@Qualifier
annotation class QrisImage

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {
    @Provides
    fun providesGson(): Gson = Gson()
}