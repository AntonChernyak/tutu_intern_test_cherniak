package com.example.tutu_intern_test_cherniak.di

import android.app.Application
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Singleton
@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class])
interface AppComponent {
    fun inject(app: Application)
}