package com.example.tutu_intern_test_cherniak.presentation.list

import androidx.lifecycle.*
import com.example.domain.interactors.PokemonsListInteractor
import com.example.domain.interactors.UIStateEnum
import com.example.domain.models.model_vo.PokemonListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonListViewModel @Inject constructor(
    private val pokemonsListInteractor: PokemonsListInteractor
) : ViewModel() {

    private var mPokemonsList = MutableLiveData<PokemonListData>()

    val pokemonsList: LiveData<PokemonListData> = mPokemonsList

    fun getPokemons(offset: String, limit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mPokemonsList.postValue(pokemonsListInteractor.getPokemons(offset, limit))
        }
    }

    private fun uiStateLiveData(): LiveData<UIStateEnum> {
        return pokemonsListInteractor.changeUiState().asLiveData()
    }
}