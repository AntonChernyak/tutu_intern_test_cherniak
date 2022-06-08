package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.tutu_intern_test_cherniak.R
import com.example.tutu_intern_test_cherniak.databinding.ItemPokemonBinding
import com.example.tutu_intern_test_cherniak.presentation.extensions.loadImage

class PokemonItemViewHolder(
    private val itemBinding: ItemPokemonBinding,
    pokemonClickListener: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemView.setOnClickListener { pokemonClickListener(bindingAdapterPosition) }
    }

    fun bind(pokemonItem: PokemonListItemModelVo?) {
        if (pokemonItem != null) {
            with(itemBinding) {
                itemNameTextView.text = pokemonItem.name
                itemAvatarImageView.loadImage(pokemonItem.avatarUrl)
            }
        } else {
            with(itemBinding) {
                itemNameTextView.text = ""
                itemAvatarImageView.loadImage(resources = R.drawable.error_drawable)
            }
        }
    }
}