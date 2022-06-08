package com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tutu_intern_test_cherniak.databinding.ItemStateErrorBinding
import com.example.tutu_intern_test_cherniak.databinding.ItemStateLoadingBinding

class PokemonLoadStateAdapter(private val refreshButtonClick: (View) -> Unit) :
    LoadStateAdapter<LoadStateItemViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (loadState) {
            is LoadState.Loading -> LoadingStateItemViewHolder(
                ItemStateLoadingBinding.inflate(inflater, parent, false)
            )
            is LoadState.Error -> ErrorStateItemViewHolder(
                ItemStateErrorBinding.inflate(inflater, parent, false),
                refreshButtonClick
            )
            is LoadState.NotLoading -> error("Not supported")
        }
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


class ErrorStateItemViewHolder(
    private val binding: ItemStateErrorBinding,
    private val refreshButtonClick: (View) -> Unit
) : LoadStateItemViewHolder(binding.root) {

    init {
        binding.refreshButton.setOnClickListener { refreshButtonClick }
    }

    override fun bind(loadState: LoadState) {
        require(loadState is LoadState.Error)
        binding.errorMessageTextView.text = loadState.error.localizedMessage
    }
}