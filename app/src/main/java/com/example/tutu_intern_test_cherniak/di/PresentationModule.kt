package com.example.tutu_intern_test_cherniak.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactors.PokemonDetailsInteractor
import com.example.domain.interactors.PokemonsListInteractor
import com.example.tutu_intern_test_cherniak.presentation.details.PokemonDetailsViewModel
import com.example.tutu_intern_test_cherniak.presentation.factory.PokemonViwModelFactory
import com.example.tutu_intern_test_cherniak.presentation.list.PokemonListViewModel
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@ExperimentalSerializationApi
@Module
class PresentationModule {

    @Provides
    @FragmentScope
    fun providePokemonListViewModel(
        pokemonListFragment: Fragment,
        pokemonListInteractor: PokemonsListInteractor,
        pokemonDetailsInteractor: PokemonDetailsInteractor
    ): PokemonListViewModel {
        return ViewModelProvider(
            pokemonListFragment,
            PokemonViwModelFactory(
                pokemonListInteractor,
                pokemonDetailsInteractor
            )
        )[PokemonListViewModel::class.java]
    }

    @Provides
    @FragmentScope
    fun providePokemonDetailsViewModel(
        pokemonDetailsFragment: Fragment,
        pokemonListInteractor: PokemonsListInteractor,
        pokemonDetailsInteractor: PokemonDetailsInteractor
    ): PokemonDetailsViewModel {
        return ViewModelProvider(
            pokemonDetailsFragment,
            PokemonViwModelFactory(
                pokemonListInteractor,
                pokemonDetailsInteractor
            )
        )[PokemonDetailsViewModel::class.java]
    }
}