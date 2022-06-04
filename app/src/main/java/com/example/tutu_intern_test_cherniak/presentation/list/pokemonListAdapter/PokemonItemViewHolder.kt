package com.example.tutu_intern_test_cherniak.presentation.list.pokemonListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.model_vo.PokemonListVo
import com.example.tutu_intern_test_cherniak.databinding.ItemPokemonBinding
import com.example.tutu_intern_test_cherniak.presentation.extensions.loadImage

class PokemonItemViewHolder(
    private val itemBinding: ItemPokemonBinding,
    pokemonClickListener: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemView.setOnClickListener { pokemonClickListener(bindingAdapterPosition) }
    }

    fun bind(pokemonItem: PokemonListVo) {
        with(itemBinding){
            itemNameTextView.text = pokemonItem.pokemonName
            itemAvatarImageView.loadImage(pokemonItem.pokemonAvatarUrl)
        }
    }
}