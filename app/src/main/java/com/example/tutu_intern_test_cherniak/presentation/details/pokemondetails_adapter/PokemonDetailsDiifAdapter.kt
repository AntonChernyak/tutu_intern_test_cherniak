package com.example.tutu_intern_test_cherniak.presentation.details.pokemondetails_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.model_vo.ItemDetails
import com.example.domain.models.model_vo.TopLevelItem
import com.example.tutu_intern_test_cherniak.presentation.details.pokemondetails_adapter.DetailsAdapterDelegates.lowLevelDetailsAdapterDelegate
import com.example.tutu_intern_test_cherniak.presentation.details.pokemondetails_adapter.DetailsAdapterDelegates.topLevelDetailsAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class PokemonDetailsDiffAdapter(expandableButtonOnClick: (String) -> Unit) :
    AsyncListDifferDelegationAdapter<ItemDetails>(DIFF_CALLBACK) {

    init {
        delegatesManager.addDelegate(topLevelDetailsAdapterDelegate(expandableButtonOnClick))
        delegatesManager.addDelegate(lowLevelDetailsAdapterDelegate())
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemDetails>() {
            override fun areItemsTheSame(oldItem: ItemDetails, newItem: ItemDetails): Boolean {
                return oldItem.uniqueName == newItem.uniqueName
            }

            override fun areContentsTheSame(oldItem: ItemDetails, newItem: ItemDetails): Boolean {
                return if (oldItem is TopLevelItem && newItem is TopLevelItem) {
                    oldItem == newItem
                } else oldItem.uniqueName == newItem.uniqueName

            }

        }
    }
}