package com.example.tutu_intern_test_cherniak.presentation.details.pokemondetails_adapter

import com.example.domain.models.model_vo.ItemDetails
import com.example.domain.models.model_vo.LowLevelItem
import com.example.domain.models.model_vo.TopLevelItem
import com.example.tutu_intern_test_cherniak.databinding.ItemDetailsLowLevelBinding
import com.example.tutu_intern_test_cherniak.databinding.ItemDetailsTopLevelBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object DetailsAdapterDelegates {

    fun lowLevelDetailsAdapterDelegate() =
        adapterDelegateViewBinding<LowLevelItem, ItemDetails, ItemDetailsLowLevelBinding>(
            { layoutInflater, root -> ItemDetailsLowLevelBinding.inflate(layoutInflater, root, false) }
        ) {

            bind {
                binding.lowLevelDetailsTextView.text = item.detailName
            }
        }

    fun topLevelDetailsAdapterDelegate(itemClickedListener : (String) -> Unit) = adapterDelegateViewBinding<TopLevelItem, ItemDetails, ItemDetailsTopLevelBinding>(
        { layoutInflater, root -> ItemDetailsTopLevelBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.expandableImageView.setOnClickListener {
            itemClickedListener(item.detailName)
        }

        bind {
            binding.detailsTopLevelNameTextView.text = item.detailName
        }
    }

}