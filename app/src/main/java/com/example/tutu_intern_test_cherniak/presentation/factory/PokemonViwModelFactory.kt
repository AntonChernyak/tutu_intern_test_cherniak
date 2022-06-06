package com.example.tutu_intern_test_cherniak.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactors.PokemonDetailsInteractor
import com.example.domain.interactors.PokemonsListInteractor
import com.example.tutu_intern_test_cherniak.presentation.details.PokemonDetailsViewModel
import com.example.tutu_intern_test_cherniak.presentation.list.PokemonListViewModel

class PokemonViwModelFactory(
    private val pokemonListInteractor: PokemonsListInteractor,
    private val pokemonDetailsInteractor: PokemonDetailsInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            PokemonListViewModel::class.java -> {
                PokemonListViewModel(pokemonListInteractor)
            }
            PokemonDetailsViewModel::class.java -> {
                PokemonDetailsViewModel(pokemonDetailsInteractor)
            }
            else -> {
                throw IllegalStateException("Unknown ViewModel class")
            }
        }
        return viewModel as T
    }
}