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

/*
class PokemonDiffUtilCallback(
    private val oldList: List<PokemonListItemModelVo>,
    private val newList: List<PokemonListItemModelVo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].name ==
                newList[newItemPosition].name
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}*/
