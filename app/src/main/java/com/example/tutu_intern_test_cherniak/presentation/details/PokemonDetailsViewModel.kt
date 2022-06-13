package com.example.tutu_intern_test_cherniak.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.PokemonDetailsInteractor
import com.example.domain.interactors.UIStateEnum
import com.example.domain.models.model_vo.ItemDetails
import com.example.domain.models.model_vo.LowLevelItem
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.domain.models.model_vo.TopLevelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.HashMap

class PokemonDetailsViewModel @Inject constructor(
    private val detailsInteractor: PokemonDetailsInteractor
) : ViewModel() {

    private var detailsRecyclerViewMap = HashMap<TopLevelItem, List<LowLevelItem>>()

    private val mPokemoDetailsLiveData = MutableLiveData<PokemonDetailsModelVo>()
    val pokemonsDetailsLiveData: LiveData<PokemonDetailsModelVo> = mPokemoDetailsLiveData

    private val mDetailsListLiveData = MutableLiveData<List<ItemDetails>>()
    val detailsListLiveData: LiveData<List<ItemDetails>> = mDetailsListLiveData

    private val mUiStateLiveData = MutableLiveData<UIStateEnum>()
    val uiStateLiveData: LiveData<UIStateEnum> = mUiStateLiveData

    private fun getItemDetailsList(detailsMap: Map<TopLevelItem, List<LowLevelItem>>) {
        viewModelScope.launch(Dispatchers.Default) {
            val detailsList = detailsMap.toSortedMap { p0, p1 ->
                p1?.detailName?.let { p0?.detailName?.compareTo(it) } ?: 0
            }.flatMap { map ->
                if (map.key.isExpandable) {
                    listOf(map.key) + map.value
                } else listOf(map.key)
            }
            mDetailsListLiveData.postValue(detailsList)
        }
    }

    fun getPokemonDetailsData(pokemonName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mUiStateLiveData.postValue(UIStateEnum.START_LOADING)
                val pokemonDetails = detailsInteractor.getPokemonByNameOrId(pokemonName)
                mPokemoDetailsLiveData.postValue(pokemonDetails)
                detailsRecyclerViewMap = pokemonDetails.detailsRecyclerViewMap
                getItemDetailsList(detailsRecyclerViewMap)
                mUiStateLiveData.postValue(UIStateEnum.END_LOADING)
                if (pokemonDetails.name.isBlank()) {
                    mUiStateLiveData.postValue(UIStateEnum.DATA_NOT_FOUND)
                } else mUiStateLiveData.postValue(UIStateEnum.DATA_FOUND)
            } catch (e: Exception) {
                mUiStateLiveData.postValue(UIStateEnum.DATA_NOT_FOUND)
                error("Database Exception: ${e.localizedMessage}")
            }
        }
    }

    fun expandableButtonOnClick(topLevelDetailsName: String) {
        val key = detailsRecyclerViewMap.keys.firstOrNull { it.detailName == topLevelDetailsName }

        if (key != null) {
            val lowList = detailsRecyclerViewMap[key]
            detailsRecyclerViewMap.remove(key)
            val newKey = key.copy(isExpandable = !key.isExpandable)
            detailsRecyclerViewMap[newKey] = lowList ?: listOf()

            getItemDetailsList(detailsRecyclerViewMap)
        }
    }

}