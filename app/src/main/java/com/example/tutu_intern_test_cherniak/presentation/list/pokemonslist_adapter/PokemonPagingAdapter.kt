package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.tutu_intern_test_cherniak.databinding.ItemPokemonBinding

class PokemonPagingAdapter(
    private val itemClick: (PokemonListItemModelVo?) -> Unit
): PagingDataAdapter<PokemonListItemModelVo, PokemonItemViewHolder>(PokemonDiffUtilItemCallback()) {

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        val viewBinding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonItemViewHolder(viewBinding, itemClick)
    }
}