package com.example.tutu_intern_test_cherniak.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Component(modules = [DataModule::class, DomainModule::class])
@Singleton
interface AppComponent {
    fun inject(app: Application)

    fun fragmentViewModelComponentBuilder():
            FragmentViewModelComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}