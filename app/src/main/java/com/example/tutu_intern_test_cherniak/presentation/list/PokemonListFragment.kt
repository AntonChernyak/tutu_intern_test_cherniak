package com.example.tutu_intern_test_cherniak.presentation.list

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import com.example.tutu_intern_test_cherniak.presentation.extensions.checkNetworkConnect
import com.example.tutu_intern_test_cherniak.presentation.extensions.registerNetworkCallback
import com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter.PokemonItemViewHolder
import com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter.PokemonLoadStateAdapter
import com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter.PokemonPagingAdapter
import kotlinx.coroutines.flow.collectLatest
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null && this@PokemonListFragment.checkNetworkConnect()) {
            listViewModel.clearDatabase()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewSettings()
        setOnRefreshListener()
        setPokemonListLiveDataObserver()
        setUiStateLiveDataObserver()
        setNetworkListener()
    }

    private fun setRecyclerViewSettings() {
        binding.listRecyclerView.apply {
            adapter = pokemonsAdapter.withLoadStateHeaderAndFooter(
                header = PokemonLoadStateAdapter(),
                footer = PokemonLoadStateAdapter()
            )
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        setLoadStateListener()
    }

    private fun setLoadStateListener() {
        pokemonsAdapter.addLoadStateListener { state: CombinedLoadStates ->
            val refreshState = state.refresh
            with(binding) {
                listRecyclerView.isVisible = refreshState != LoadState.Loading
                listProgressBar.isVisible = refreshState == LoadState.Loading
            }
        }
    }

    private fun onPokemonItemClick(pokemon: PokemonListItemModelVo?) {
        val directions = PokemonListFragmentDirections.actionListFragmentToDetailsFragment(
            pokemon?.name ?: ""
        )
        findNavController().navigate(directions)
    }

    private fun setOnRefreshListener() {
        binding.listSwipeToRefreshLayout.setOnRefreshListener {
            binding.listSwipeToRefreshLayout.isRefreshing = false
            pokemonsAdapter.refresh()
        }
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun setPokemonListLiveDataObserver() {
        listViewModel.pokemonsLiveData.observe(viewLifecycleOwner) { flow ->
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    flow.collectLatest { pagingData ->
                        pokemonsAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

    private fun setUiStateLiveDataObserver() {
        listViewModel.getUiState().observe(viewLifecycleOwner) { uiState ->
            updateUiState(uiState)
        }
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun updateUiState(uiStateEnum: UIStateEnum) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (uiStateEnum == UIStateEnum.NETWORK_ERROR) {
                    binding.noNetworkTextView.visibility = View.VISIBLE
                } else if (uiStateEnum == UIStateEnum.NETWORK_AVAILABLE) {
                    binding.noNetworkTextView.visibility = View.GONE
                }
            }
        }
    }

    private fun setNetworkListener() {
        if (this@PokemonListFragment.checkNetworkConnect()) {
            updateUiState(UIStateEnum.NETWORK_AVAILABLE)
        } else updateUiState(UIStateEnum.NETWORK_ERROR)

        this@PokemonListFragment.registerNetworkCallback({
            updateUiState(UIStateEnum.NETWORK_AVAILABLE)
        }, {
            updateUiState(UIStateEnum.NETWORK_ERROR)
        })
    }
}