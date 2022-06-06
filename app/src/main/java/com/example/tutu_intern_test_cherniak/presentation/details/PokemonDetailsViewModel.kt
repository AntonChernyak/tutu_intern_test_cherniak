package com.example.tutu_intern_test_cherniak.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.PokemonDetailsInteractor
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonDetailsViewModel @Inject constructor(
    private val detailsInteractor: PokemonDetailsInteractor
) : ViewModel() {

    private val mPokemoDetailsLiveData = MutableLiveData<PokemonDetailsModelVo>()
    val pokemonsDetailsLiveData: LiveData<PokemonDetailsModelVo> = mPokemoDetailsLiveData

    fun getPokemonDetailsData(pokemonName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mPokemoDetailsLiveData.postValue(detailsInteractor.getPokemonByNameOrId(pokemonName))
        }
    }
}