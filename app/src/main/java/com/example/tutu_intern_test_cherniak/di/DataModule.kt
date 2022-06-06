package com.example.tutu_intern_test_cherniak.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.PokemonDao
import com.example.data.database.PokemonDatabase
import com.example.data.mapper.PokemonDetailsDbToDetailsVoMapper
import com.example.data.mapper.PokemonDetailsDtoToDetailsDbMapper
import com.example.data.mapper.PokemonListItemModelDbToListItemModelVoMapper
import com.example.data.network.PokemonApiInterface
import com.example.data.repository.PokemonDetailsLocalRepository
import com.example.data.repository.PokemonListLocalRepository
import com.example.data.repository.PokemonRemoteRepository
import com.example.domain.repository.PokemonDetailsLocalRepositoryInterface
import com.example.domain.repository.PokemonListLocalRepositoryInterface
import com.example.domain.repository.PokemonListRemoteRepositoryInterface
import com.example.tutu_intern_test_cherniak.App
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module(subcomponents = [FragmentViewModelComponent::class])
class DataModule {
    private val baseUrl = "https://pokeapi.co/api/v2/"
    private val contentType = "application/json".toMediaType()
    private val pokemonDbName = "pokemons_db"

    @Singleton
    @Provides
    fun provideJson() : Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    // Network
    @Provides
    @Singleton
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()


    @Singleton
    @Provides
    fun provideApiClient(client: OkHttpClient, json: Json): PokemonApiInterface =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(PokemonApiInterface::class.java)

    @Singleton
    @Provides
    fun provideRemoteRepository(api: PokemonApiInterface): PokemonListRemoteRepositoryInterface {
        return PokemonRemoteRepository(api)
    }

    // Database
    @Singleton
    @Provides
    fun provideDb(context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            pokemonDbName
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonDetailsDbToDetailsVoMapper(): PokemonDetailsDbToDetailsVoMapper {
        return PokemonDetailsDbToDetailsVoMapper()
    }

    @Singleton
    @Provides
    fun providePokemonDetailsDtoToDetailsDbMapper(): PokemonDetailsDtoToDetailsDbMapper {
        return PokemonDetailsDtoToDetailsDbMapper()
    }

    @Singleton
    @Provides
    fun providePokemonListItemModelDbToListItemModelVoMapper(): PokemonListItemModelDbToListItemModelVoMapper {
        return PokemonListItemModelDbToListItemModelVoMapper()
    }


    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDatabase): PokemonDao {
        return db.getDao()
    }

    @Singleton
    @Provides
    fun provideLocalListRepository(
        dao: PokemonDao,
        detailsDtoToDetailsDbMapper: PokemonDetailsDtoToDetailsDbMapper,
        listItemDbToListItemVoMapper: PokemonListItemModelDbToListItemModelVoMapper
    ): PokemonListLocalRepositoryInterface {
        return PokemonListLocalRepository(dao, detailsDtoToDetailsDbMapper, listItemDbToListItemVoMapper)
    }

    @Singleton
    @Provides
    fun provideLocalDetailsRepository(
        dao: PokemonDao,
        pokemonDetailsDbToDetailsVoMapper: PokemonDetailsDbToDetailsVoMapper
    ): PokemonDetailsLocalRepositoryInterface {
        return PokemonDetailsLocalRepository(dao, pokemonDetailsDbToDetailsVoMapper)
    }

}