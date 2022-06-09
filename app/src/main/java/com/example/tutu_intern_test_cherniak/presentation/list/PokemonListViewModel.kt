package com.example.tutu_intern_test_cherniak.presentation.list

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.paging.PokemonPagingSource
import com.example.data.paging.PokemonPagingSource.Companion.LIMIT_SIZE
import com.example.domain.interactors.PokemonsListInteractor
import com.example.domain.interactors.UIStateEnum
import com.example.domain.models.model_vo.PokemonListItemModelVo
import javax.inject.Inject

class PokemonListViewModel @Inject constructor(
    private val pokemonsListInteractor: PokemonsListInteractor
) : ViewModel() {

   private fun createPager() : Pager<Int, PokemonListItemModelVo>{
        return Pager(config = PagingConfig(
            pageSize = LIMIT_SIZE,
            prefetchDistance = LIMIT_SIZE - 4 ,
            enablePlaceholders = true,
            initialLoadSize = LIMIT_SIZE,
            maxSize = 5 * LIMIT_SIZE
        )) {PokemonPagingSource(pokemonsListInteractor)}
    }

    fun getPokemons(): LiveData<PagingData<PokemonListItemModelVo>> {
        return createPager().flow.cachedIn(viewModelScope).asLiveData()
    }

    fun getUiState(): LiveData<UIStateEnum> {
        return pokemonsListInteractor.uiStateFlow.asLiveData()
    }

}