package com.example.tutu_intern_test_cherniak.di

import androidx.fragment.app.Fragment
import com.example.tutu_intern_test_cherniak.presentation.details.PokemonDetailsFragment
import com.example.tutu_intern_test_cherniak.presentation.list.PokemonListFragment
import dagger.BindsInstance
import dagger.Subcomponent
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@FragmentScope
@Subcomponent(modules = [PresentationModule::class])
interface FragmentViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun fragment(fragment: Fragment): Builder
        fun build(): FragmentViewModelComponent
    }

    fun inject(pokemonListFragment: PokemonListFragment)
    fun inject(pokemonDetailsFragment: PokemonDetailsFragment)
}