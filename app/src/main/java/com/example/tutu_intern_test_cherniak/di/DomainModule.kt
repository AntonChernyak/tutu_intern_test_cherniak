package com.example.tutu_intern_test_cherniak.di

import com.example.domain.interactors.PokemonDetailsInteractor
import com.example.domain.interactors.PokemonsListInteractor
import com.example.domain.models.mapper.PokemonDetailsDtoToListItemVoMapper
import com.example.domain.repository.PokemonDetailsLocalRepositoryInterface
import com.example.domain.repository.PokemonDetailsRemoteRepositoryInterface
import com.example.domain.repository.PokemonListLocalRepositoryInterface
import com.example.domain.repository.PokemonListRemoteRepositoryInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providesDetailsDtoToListItemVoMapper(): PokemonDetailsDtoToListItemVoMapper {
        return PokemonDetailsDtoToListItemVoMapper()
    }

    @Provides
    @Singleton
    fun providesPokemonDetailsInteractor(
        localRepository: PokemonDetailsLocalRepositoryInterface,
        remoteRepository: PokemonDetailsRemoteRepositoryInterface
    ): PokemonDetailsInteractor {
        return PokemonDetailsInteractor(localRepository, remoteRepository)
    }

    @Provides
    @Singleton
    fun providesPokemonListInteractor(
        localRepository: PokemonListLocalRepositoryInterface,
        remoteRepository: PokemonListRemoteRepositoryInterface,
        listItemMapper: PokemonDetailsDtoToListItemVoMapper,
    ): PokemonsListInteractor {
        return PokemonsListInteractor(
            remoteRepository,
            localRepository,
            listItemMapper
        )
    }
}