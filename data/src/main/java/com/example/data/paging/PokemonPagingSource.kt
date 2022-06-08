package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.interactors.PokemonsListInteractor
import com.example.domain.models.mapper.PokemonDetailsDtoToListItemVoMapper
import com.example.domain.models.model_vo.PokemonListItemModelVo
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val listInteractor: PokemonsListInteractor
) : PagingSource<Int, PokemonListItemModelVo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItemModelVo> {

        return try {
            // получаем индекс загружаемой страницы (м.б. null, в этом случае загрузим первую страницу с индексом = 0)
            val pageOffset = params.key ?: INITIAL_OFFSET_VALUE
            val pageLimit = params.loadSize.toString()

            // получаем список покемонов
            val pokemonResponse = listInteractor.getPokemons(pageOffset.toString(), pageLimit)
            val namesList = pokemonResponse.results.map { it.name }
            val pokemonList = mutableListOf<PokemonListItemModelVo>()
                // сеть есть - идём за картинкой к покемону
                namesList.forEach {
                    val pokemonDetailsDto = listInteractor.getPokemonByNameOrID(it)
                    pokemonList.add(PokemonDetailsDtoToListItemVoMapper().toOutObject(pokemonDetailsDto))

                }


            // Получили успешно - возвращаем Page
            LoadResult.Page(
                data = pokemonList,
                prevKey = if (pageOffset == INITIAL_OFFSET_VALUE) null else pageOffset - LIMIT_SIZE,
                nextKey = if (pokemonList.size == params.loadSize) pageOffset + LIMIT_SIZE else null
            )
        } catch (e: Exception) {
            // произошла ошибка - оборачиваем её в LoadResult
            LoadResult.Error(
                throwable = e
            )
        }
    }

    /**
     * getRefreshKey() позволяет нам получить ключ, который будет использоваться для того, чтобы
     * получить данные при обновлении списка. Это происходит, если, например, у нас происходить прыжок, либо
     * обновляется состояние. Т.е. это какая-то позиция, которая уже есть в списке и нам надо смапить её в ключ,
     * чтобы загружать новую страницу, которая соответствует актуальной позиции списка
     *
     * Для этого у нас есть специальный объект State, который нам приходит на вход и у него есть параметр
     * anchorPosition - это последняя позция, которая была видна, от которой мы отталкиваемся. Она м.б.
     */
    override fun getRefreshKey(state: PagingState<Int, PokemonListItemModelVo>): Int? {
        // получаем самый последний доступный индекс в списке покемонов:
        val anchorPosition = state.anchorPosition ?: return null
        /* конвертируем индекс элемента в page индекс. Получаем ближайшую страницу, которая была
           загружена. Если будет null, то вернём null, и тогда загружаем весь список сначала:   */
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        // page не имеет свойства 'currentKey', поэтому его необходимо вычислить вручную:
        return anchorPage.prevKey?.plus(LIMIT_SIZE) ?: anchorPage.nextKey?.minus(LIMIT_SIZE)
    }

    companion object{
        const val INITIAL_OFFSET_VALUE = 0
        const val LIMIT_SIZE = 10
    }

}