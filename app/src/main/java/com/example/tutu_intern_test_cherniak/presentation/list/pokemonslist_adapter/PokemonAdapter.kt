package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.tutu_intern_test_cherniak.databinding.ItemPokemonBinding

class PokemonAdapter(
    private val itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<PokemonItemViewHolder>() {

    var data: List<PokemonListItemModelVo> = emptyList()
        set(newValue) {
            val diffCallback = PokemonDiffUtilCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        val viewBinding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonItemViewHolder(viewBinding, itemClick)
    }

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}