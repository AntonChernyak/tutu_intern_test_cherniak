package com.example.domain.interactors

import com.example.domain.model_dto.PokemonDto
import com.example.domain.model_dto.PokemonsResponse
import com.example.domain.repository.PokemonRemoteRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonsListInteractor(val remoteRepository: PokemonRemoteRepositoryInterface) {

    // mb Flow<VO> - mapper tut? Проверить какой поток тут
    // Всё гразим в БД в фоне, а из экрана с детали - запрос БД
    // Если список не пустой, то сохраняем в БД, если сети нет, то из него. Есть - чистим БД, сохраняем новые

    // Нужно тут сделать Enum - его в VM. Если список пустой и после БД, то через Enum отпарвить состояние, что
    // нужно отобразить плашку - добавить её и loader (shimmer). Также сразу добавить Toolbar

    // Использовать Dagger, сначала только там, где создаются зависимости, потом в Дата и Домейн

    // Unit на Usecase с Mockito, Espresso
    suspend fun getPokemons(offset: String, limit: String): Flow<PokemonsResponse> {
        return remoteRepository.getPokemons(offset, limit)
    }

    suspend fun getPokemonByNameOrID(nameOrId: String): Flow<PokemonDto>{
        return remoteRepository.getPokemonByNameOrId(nameOrId)
    }
}