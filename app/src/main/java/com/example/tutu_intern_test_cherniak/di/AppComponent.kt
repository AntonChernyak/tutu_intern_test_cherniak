package com.example.tutu_intern_test_cherniak.di

import android.app.Application
import com.example.tutu_intern_test_cherniak.presentation.details.DetailsFragment
import com.example.tutu_intern_test_cherniak.presentation.list.ListFragment
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Singleton
@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class])
interface AppComponent {
    fun inject(app: Application)

    fun inject(listFragment: ListFragment)
    fun inject(detailFragment: DetailsFragment)
}