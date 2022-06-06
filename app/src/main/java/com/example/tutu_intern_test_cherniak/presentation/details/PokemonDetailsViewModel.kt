package com.example.tutu_intern_test_cherniak.presentation.details

import androidx.lifecycle.ViewModel
import com.example.domain.interactors.PokemonDetailsInteractor
import javax.inject.Inject

class PokemonDetailsViewModel @Inject constructor(
    private val detailsInteractor: PokemonDetailsInteractor
) : ViewModel() {
}