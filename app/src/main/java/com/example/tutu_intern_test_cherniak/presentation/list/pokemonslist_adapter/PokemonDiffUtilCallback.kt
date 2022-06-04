package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.model_vo.PokemonListVo


class PokemonDiffUtilCallback(
    private val oldList: List<PokemonListVo>,
    private val newList: List<PokemonListVo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].pokemonName ==
                newList[newItemPosition].pokemonName
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}