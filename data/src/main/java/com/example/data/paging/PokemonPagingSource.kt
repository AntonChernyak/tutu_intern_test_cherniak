package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.interactors.PokemonsListInteractor
import com.example.domain.models.model_vo.PokemonListItemModelVo
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val listInteractor: PokemonsListInteractor
) : PagingSource<Int, PokemonListItemModelVo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItemModelVo> {

        return try {
            val pageOffset = params.key ?: INITIAL_OFFSET_VALUE
            val pageLimit = params.loadSize.toString()
            val pokemonList = listInteractor.getPokemons(pageOffset.toString(), pageLimit)

            LoadResult.Page(
                data = pokemonList,
                prevKey = if (pageOffset == INITIAL_OFFSET_VALUE) null else pageOffset - LIMIT_SIZE,
                nextKey = if (pokemonList.size == params.loadSize) pageOffset + LIMIT_SIZE else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListItemModelVo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(LIMIT_SIZE) ?: anchorPage.nextKey?.minus(LIMIT_SIZE)
    }

    companion object{
        const val INITIAL_OFFSET_VALUE = 0
        const val LIMIT_SIZE = 10
    }

}