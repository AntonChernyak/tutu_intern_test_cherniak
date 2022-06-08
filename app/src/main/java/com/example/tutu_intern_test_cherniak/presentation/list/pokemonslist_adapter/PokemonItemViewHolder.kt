package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.tutu_intern_test_cherniak.R
import com.example.tutu_intern_test_cherniak.databinding.ItemPokemonBinding
import com.example.tutu_intern_test_cherniak.presentation.extensions.loadImage

class PokemonItemViewHolder(
    private val itemBinding: ItemPokemonBinding,
    val pokemonClickListener: (pokemonItem: PokemonListItemModelVo?) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(pokemonItem: PokemonListItemModelVo?) {
        itemView.setOnClickListener { pokemonClickListener(pokemonItem) }

        if (pokemonItem != null) {
            with(itemBinding) {
                itemNameTextView.text = pokemonItem.name
                itemAvatarImageView.loadImage(imageUrl = pokemonItem.avatarUrl)
            }
        } else {
            with(itemBinding) {
                itemNameTextView.text = ""
                itemAvatarImageView.loadImage(resources = R.drawable.placeholder_pokemon_ball)
            }
        }
    }
}