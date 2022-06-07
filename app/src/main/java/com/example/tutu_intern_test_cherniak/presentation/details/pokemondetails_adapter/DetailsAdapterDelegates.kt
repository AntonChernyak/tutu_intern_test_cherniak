package com.example.tutu_intern_test_cherniak.presentation.details.pokemondetails_adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.example.domain.models.model_vo.ItemDetails
import com.example.domain.models.model_vo.LowLevelItem
import com.example.domain.models.model_vo.TopLevelItem
import com.example.tutu_intern_test_cherniak.R
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

    fun topLevelDetailsAdapterDelegate(itemClickedListener : (String, View) -> Unit) = adapterDelegateViewBinding<TopLevelItem, ItemDetails, ItemDetailsTopLevelBinding>(
        { layoutInflater, root -> ItemDetailsTopLevelBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.expandableImageView.setOnClickListener { expandableImageView ->
            itemClickedListener(item.detailName, expandableImageView)
            if (item.isExpandable) {
                expandableImageView.background =
                    ContextCompat.getDrawable(expandableImageView.context, R.drawable.ic_baseline_arrow_up_24)
            } else  expandableImageView.background =
                ContextCompat.getDrawable(expandableImageView.context, R.drawable.ic_baseline_arrow_down_24)
        }

        bind {
            binding.detailsTopLevelNameTextView.text = item.detailName
        }
    }

}