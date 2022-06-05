package com.example.tutu_intern_test_cherniak.di

import com.example.domain.interactors.PokemonDetailsInteractor
import com.example.domain.interactors.PokemonsListInteractor
import com.example.domain.mapper.PokemonDetailsDtoToVoMapper
import com.example.domain.mapper.PokemonListItemMapper
import com.example.domain.repository.PokemonDetailsLocalRepositoryInterface
import com.example.domain.repository.PokemonListLocalRepositoryInterface
import com.example.domain.repository.PokemonListRemoteRepositoryInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providesDetailsVoToItemListVoMapper(): PokemonListItemMapper {
        return PokemonListItemMapper()
    }

    @Provides
    @Singleton
    fun providesDetailsDtoToDetailsVoMapper(): PokemonDetailsDtoToVoMapper {
        return PokemonDetailsDtoToVoMapper()
    }

    @Provides
    @Singleton
    fun providesPokemonDetailsInteractor(
        localRepository: PokemonDetailsLocalRepositoryInterface
    ): PokemonDetailsInteractor {
        return PokemonDetailsInteractor(localRepository)
    }

    @Provides
    @Singleton
    fun providesPokemonListInteractor(
        localRepository: PokemonListLocalRepositoryInterface,
        remoteRepository: PokemonListRemoteRepositoryInterface,
        listItemMapper: PokemonListItemMapper,
        detailsMapper: PokemonDetailsDtoToVoMapper
    ): PokemonsListInteractor {
        return PokemonsListInteractor(
            remoteRepository,
            localRepository,
            listItemMapper,
            detailsMapper
        )
    }
}