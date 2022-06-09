package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tutu_intern_test_cherniak.databinding.ItemStateLoadingBinding

class PokemonLoadStateAdapter : LoadStateAdapter<LoadStateItemViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (loadState is LoadState.Loading) LoadingStateItemViewHolder(
            ItemStateLoadingBinding.inflate(inflater, parent, false)
        ) else error("Not support")
    }
}


abstract class LoadStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(loadState: LoadState)
}


class LoadingStateItemViewHolder(
    binding: ItemStateLoadingBinding
) : LoadStateItemViewHolder(binding.root) {

    override fun bind(loadState: LoadState) {
    }
}