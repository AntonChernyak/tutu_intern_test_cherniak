package com.example.domain.interactors

import com.example.domain.models.mapper.PokemonDetailsDtoToListItemVoMapper
import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_dto.PokemonResult
import com.example.domain.models.model_dto.PokemonsResponse
import com.example.domain.models.model_dto.SpriteDto
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.domain.repository.PokemonListLocalRepositoryInterface
import com.example.domain.repository.PokemonListRemoteRepositoryInterface
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.mockito.Mockito


@ExperimentalCoroutinesApi
class PokemonsListInteractorTest {

    companion object {
        private var detailsDtoToListItemVoMapper: PokemonDetailsDtoToListItemVoMapper? = null

        @JvmStatic
        @BeforeAll
        fun initBeforeAll() {
            detailsDtoToListItemVoMapper = PokemonDetailsDtoToListItemVoMapper()
        }

        @JvmStatic
        @AfterAll
        fun tearDownAfterAll() {
            detailsDtoToListItemVoMapper = null
        }

        private const val POKEMON_DTO_NAME = "PokemonName for Test"
        private const val POKEMON_DTO_AVATAR_URL = "PokemonAvatar url for Test"

        private const val FIRST_POKEMON_RESULT_NAME = "Pokemon 1"
        private const val SECOND_POKEMON_RESULT_NAME = "Pokemon 2"
        private const val THIRD_POKEMON_RESULT_NAME = "Pokemon 3"

        private const val OFFSET_TEST_VALUE = "0"
        private const val LIMIT_TEST_VALUE = "10"
    }

    private val remotePokemonRepository = mock<PokemonListRemoteRepositoryInterface>()
    private val localPokemonRepository = mock<PokemonListLocalRepositoryInterface>()
    private var listInteractor: PokemonsListInteractor? = null

    @BeforeEach
    fun initBefore() {
        listInteractor = detailsDtoToListItemVoMapper?.let {
            PokemonsListInteractor(
                remoteRepository = remotePokemonRepository,
                pokemonLocalRepository = localPokemonRepository,
                pokemonDetailDtoToListItemVoMapper = it
            )
        }
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(remotePokemonRepository)
        Mockito.reset(localPokemonRepository)
        listInteractor = null
    }

    @Test
    fun `Test should map DetailsDTO to ListItemVO object`() {
        val detailsDtoObject = PokemonDto(
            id = 1,
            name = POKEMON_DTO_NAME,
            height = 10,
            weight = 10,
            abilities = mutableListOf(),
            forms = mutableListOf(),
            stats = mutableListOf(),
            sprites = SpriteDto(POKEMON_DTO_AVATAR_URL)
        )
        val actulal = listInteractor?.mapDetailsDtoObjectToListItemVo(detailsDtoObject)
        val expected = PokemonListItemModelVo(
            name = POKEMON_DTO_NAME, avatarUrl = POKEMON_DTO_AVATAR_URL
        )
        Assertions.assertEquals(expected, actulal)
    }

    @Test
    fun `Test should return list of pokemonNames from pokemonResponse`() {
        runTest {
            Mockito.`when`(remotePokemonRepository.getPokemons(OFFSET_TEST_VALUE, LIMIT_TEST_VALUE))
                .thenReturn(
                    PokemonsResponse(
                        results =
                        listOf(
                            PokemonResult(FIRST_POKEMON_RESULT_NAME),
                            PokemonResult(SECOND_POKEMON_RESULT_NAME),
                            PokemonResult(THIRD_POKEMON_RESULT_NAME)
                        )
                    )
                )

            val actual = listInteractor?.getPokemonNamesList(OFFSET_TEST_VALUE, LIMIT_TEST_VALUE)
            val expected = listOf(
                FIRST_POKEMON_RESULT_NAME,
                SECOND_POKEMON_RESULT_NAME,
                THIRD_POKEMON_RESULT_NAME
            )
            Assertions.assertEquals(expected, actual)
        }

    }

    @Test
    fun `Test should get network error and get data from db`() {
        runTest {
            Mockito.`when`(remotePokemonRepository.getPokemons(OFFSET_TEST_VALUE, LIMIT_TEST_VALUE))
                .thenReturn(
                    null
                )

            Mockito.`when`(
                localPokemonRepository.getPokemonsListWithNamesAndAvatarUrls(
                    OFFSET_TEST_VALUE, LIMIT_TEST_VALUE
                )
            ).thenReturn(
                listOf(
                    PokemonListItemModelVo(FIRST_POKEMON_RESULT_NAME, "a_url"),
                    PokemonListItemModelVo(SECOND_POKEMON_RESULT_NAME, "b_url"),
                    PokemonListItemModelVo(THIRD_POKEMON_RESULT_NAME, "c_url"),
                )
            )

            val actual = listInteractor?.getPokemons(OFFSET_TEST_VALUE, LIMIT_TEST_VALUE)
            val expected = listOf(
                PokemonListItemModelVo(FIRST_POKEMON_RESULT_NAME, "a_url"),
                PokemonListItemModelVo(SECOND_POKEMON_RESULT_NAME, "b_url"),
                PokemonListItemModelVo(THIRD_POKEMON_RESULT_NAME, "c_url"),
            )

            Mockito.verify(localPokemonRepository, Mockito.never()).addPokemon(any())
            Mockito.verify(localPokemonRepository, Mockito.times(1))
                .getPokemonsListWithNamesAndAvatarUrls(
                    OFFSET_TEST_VALUE, LIMIT_TEST_VALUE
                )
            Assertions.assertEquals(expected, actual)
        }
    }

    @Test
    fun `Test should get data from the network and shouldn't call the method of getting data from the database`() {
        runTest {
            Mockito.`when`(remotePokemonRepository.getPokemons(OFFSET_TEST_VALUE, LIMIT_TEST_VALUE))
                .thenReturn(
                    PokemonsResponse(
                        results =
                        listOf(
                            PokemonResult(FIRST_POKEMON_RESULT_NAME),
                            PokemonResult(SECOND_POKEMON_RESULT_NAME)
                        )
                    )
                )

            Mockito.`when`(remotePokemonRepository.getPokemonByNameOrId(FIRST_POKEMON_RESULT_NAME)).thenReturn(
                PokemonDto(id = 1, name = FIRST_POKEMON_RESULT_NAME, height = 10, weight = 10,
                    abilities = mutableListOf(), forms = mutableListOf(), stats = mutableListOf(), sprites = SpriteDto("1_url")
                )
            )
            Mockito.`when`(remotePokemonRepository.getPokemonByNameOrId(SECOND_POKEMON_RESULT_NAME)).thenReturn(
                PokemonDto(id = 2, name = SECOND_POKEMON_RESULT_NAME, height = 20, weight = 20,
                    abilities = mutableListOf(), forms = mutableListOf(), stats = mutableListOf(), sprites = SpriteDto("2_url"))
            )

            Mockito.`when`(localPokemonRepository.addPokemon(any())).then {  }

            val actual = listInteractor?.getPokemons(OFFSET_TEST_VALUE, LIMIT_TEST_VALUE)
            val expected = listOf(
                PokemonListItemModelVo(name = FIRST_POKEMON_RESULT_NAME, avatarUrl = "1_url"),
                PokemonListItemModelVo(name = SECOND_POKEMON_RESULT_NAME, avatarUrl = "2_url")
            )
            Assertions.assertEquals(expected, actual)
            Mockito.verify(localPokemonRepository, Mockito.never()).getPokemonsListWithNamesAndAvatarUrls(
                OFFSET_TEST_VALUE, LIMIT_TEST_VALUE
            )
        }
    }

}