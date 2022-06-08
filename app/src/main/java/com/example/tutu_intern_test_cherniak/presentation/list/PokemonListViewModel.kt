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
import com.example.domain.models.model_vo.PokemonListData
import com.example.domain.models.model_vo.PokemonListItemModelVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonListViewModel @Inject constructor(
    private val pokemonsListInteractor: PokemonsListInteractor
) : ViewModel() {

    private var mPokemonsList = MutableLiveData<PokemonListData>()
    val pokemonsList: LiveData<PokemonListData> = mPokemonsList

    // TODO mb Dagger
    private fun createPager() : Pager<Int, PokemonListItemModelVo>{
        return Pager(config = PagingConfig(
            pageSize = LIMIT_SIZE,
            prefetchDistance = LIMIT_SIZE / 2,
            enablePlaceholders = true,
            initialLoadSize = LIMIT_SIZE,
            maxSize = 3 * LIMIT_SIZE
        )) {PokemonPagingSource(pokemonsListInteractor)}
    }

    fun getPokemons(): LiveData<PagingData<PokemonListItemModelVo>>{
        return createPager().flow.asLiveData().cachedIn(viewModelScope)
    }

    //  Pager(PagingConfig(20, enablePlaceholders = false)) { MoviesPagingSource() }.flow
    //
        // VM:
    //     fun getMovies(): Flow<PagingData<Movie>> {
    //        return repository.getMovies().cachedIn(viewModelScope)
    //    }

/**    fun getPokemons(offset: String, limit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mPokemonsList.postValue(pokemonsListInteractor.getPokemons(offset, limit))
        }
    }*/

    fun getUiState(): LiveData<UIStateEnum> {
        return pokemonsListInteractor.uiStateFlow.asLiveData()
    }

}