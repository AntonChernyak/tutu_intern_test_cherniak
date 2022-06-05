package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.tutu_intern_test_cherniak.databinding.ItemPokemonBinding
import com.example.tutu_intern_test_cherniak.presentation.extensions.loadImage

class PokemonItemViewHolder(
    private val itemBinding: ItemPokemonBinding,
    pokemonClickListener: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemView.setOnClickListener { pokemonClickListener(bindingAdapterPosition) }
    }

    fun bind(pokemonItem: PokemonListItemModelVo) {
        with(itemBinding){
            itemNameTextView.text = pokemonItem.name
            itemAvatarImageView.loadImage(pokemonItem.avatarUrl)
        }
    }
}