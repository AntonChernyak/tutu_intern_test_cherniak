package com.example.tutu_intern_test_cherniak.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactors.PokemonDetailsInteractor
import com.example.domain.interactors.PokemonsListInteractor
import com.example.tutu_intern_test_cherniak.presentation.factory.PokemonViwModelFactory
import com.example.tutu_intern_test_cherniak.presentation.list.PokemonListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Module
class PresentationModule {

    @Provides
    @FragmentScope
    fun provideHabitListViewModel(
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
}