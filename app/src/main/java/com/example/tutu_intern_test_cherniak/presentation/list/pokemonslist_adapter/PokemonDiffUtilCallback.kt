package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.model_vo.PokemonListItemModelVo


class PokemonDiffUtilItemCallback : DiffUtil.ItemCallback<PokemonListItemModelVo>() {

    override fun areItemsTheSame(
        oldItem: PokemonListItemModelVo,
        newItem: PokemonListItemModelVo
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: PokemonListItemModelVo,
        newItem: PokemonListItemModelVo
    ): Boolean {
        return oldItem == newItem
    }
}