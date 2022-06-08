package com.example.tutu_intern_test_cherniak.presentation.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.interactors.UIStateEnum
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.tutu_intern_test_cherniak.App
import com.example.tutu_intern_test_cherniak.R
import com.example.tutu_intern_test_cherniak.databinding.FragmentListBinding
import com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter.PokemonItemViewHolder
import com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter.PokemonLoadStateAdapter
import com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter.PokemonPagingAdapter
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class PokemonListFragment : Fragment() {

    @Inject
    lateinit var listViewModel: PokemonListViewModel
    private val binding: FragmentListBinding by viewBinding()

    private val pokemonsAdapter: PagingDataAdapter<PokemonListItemModelVo, PokemonItemViewHolder> by lazy {
        PokemonPagingAdapter { pokemon ->
            onPokemonItemClick(pokemon)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).component.fragmentViewModelComponentBuilder()
            .fragment(this)
            .build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewSettings()

        listViewModel.getPokemons().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                pokemonsAdapter.submitData(it)
            }
        }

/*        listViewModel.getUiState().observe(viewLifecycleOwner) { uiState ->
            updateUiState(uiState)
        }*/

    }

    private fun setRecyclerViewSettings() {
        binding.listRecyclerView.apply {
            adapter = pokemonsAdapter.withLoadStateHeaderAndFooter(
                header = PokemonLoadStateAdapter { refreshAdapterDataButtonOnClick(it) },
                footer = PokemonLoadStateAdapter { refreshAdapterDataButtonOnClick(it) }
            )
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        setLoadStateListener()
    }

    private fun refreshAdapterDataButtonOnClick(view: View) {
        pokemonsAdapter.refresh()
    }

    private fun setLoadStateListener() {
        pokemonsAdapter.addLoadStateListener { state: CombinedLoadStates ->
            val refreshState = state.refresh
            with(binding) {
                listRecyclerView.isVisible = refreshState != LoadState.Loading
                listProgressBar.isVisible = refreshState == LoadState.Loading
                if (refreshState is LoadState.Error) {
                    noDataTextView.text = resources.getString(
                        R.string.error_project_message,
                        refreshState.error.localizedMessage ?: ""
                    )
                }
            }
        }
    }

    private fun onPokemonItemClick(pokemon: PokemonListItemModelVo?) {
        val directions = PokemonListFragmentDirections.actionListFragmentToDetailsFragment(
            pokemon?.name ?: ""
        )
        findNavController().navigate(directions)
    }

    private fun updateUiState(uiStateEnum: UIStateEnum) {
        when (uiStateEnum) {
            UIStateEnum.NETWORK_ERROR -> binding.noNetworkTextView.visibility = View.VISIBLE
            UIStateEnum.START_LOADING -> binding.listProgressBar.visibility = View.VISIBLE
            UIStateEnum.END_LOADING -> binding.listProgressBar.visibility = View.GONE
            UIStateEnum.DATA_NOT_FOUND -> binding.noDataTextView.visibility = View.VISIBLE
            UIStateEnum.DEFAULT -> {
                binding.noNetworkTextView.visibility = View.GONE
                binding.listProgressBar.visibility = View.GONE
                binding.noDataTextView.visibility = View.GONE
            }
        }
    }

}