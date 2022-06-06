package com.example.tutu_intern_test_cherniak

import android.app.Application
import com.example.tutu_intern_test_cherniak.di.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class App: Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        instance = this
    }

    companion object {
        var instance: App? = null
            private set
    }
}