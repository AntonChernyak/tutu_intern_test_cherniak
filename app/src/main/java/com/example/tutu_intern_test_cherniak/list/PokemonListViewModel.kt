package com.example.tutu_intern_test_cherniak.list

import androidx.lifecycle.*
import com.example.domain.interactors.PokemonsListInteractor
import com.example.domain.interactors.UIStateEnum
import com.example.domain.models.model_vo.PokemonListVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

class PokemonListViewModel(private val pokemonsListInteractor: PokemonsListInteractor) :
    ViewModel() {

    private var mPokemonsList = MutableLiveData<List<PokemonListVo>>()

    val pokemonsList: LiveData<List<PokemonListVo>> = mPokemonsList

    private fun getPokemons(offset: String, limit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mPokemonsList.postValue(pokemonsListInteractor.getPokemons(offset, limit))
        }
    }

    private fun uiStateLiveData(): LiveData<UIStateEnum> {
        return pokemonsListInteractor.changeUiState().asLiveData()
    }
}