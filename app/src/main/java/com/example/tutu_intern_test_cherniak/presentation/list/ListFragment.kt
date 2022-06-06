package com.example.tutu_intern_test_cherniak.presentation.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.interactors.UIStateEnum
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.tutu_intern_test_cherniak.App
import com.example.tutu_intern_test_cherniak.R
import com.example.tutu_intern_test_cherniak.databinding.FragmentListBinding
import com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter.PokemonAdapter
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class ListFragment : Fragment() {

    @Inject
    lateinit var listViewModel: PokemonListViewModel

    private var previousOffset: String? = "0"
    private var nextOffset: String? = "0"
    private val binding: FragmentListBinding by viewBinding()
    private val pokemonItems: MutableList<PokemonListItemModelVo> = mutableListOf()
    private val pokemonsAdapter: PokemonAdapter by lazy {
        PokemonAdapter { position ->
            onPokemonItemClick(position)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).component.inject(this)
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
        listViewModel.getPokemons("0", REQUEST_ITEMS_LIMIT)

        listViewModel.pokemonsList.observe(viewLifecycleOwner) { pokemonData ->
            pokemonsAdapter.data = pokemonData.itemListVo
            previousOffset = pokemonData.previousOffset
            nextOffset = pokemonData.nextOffset
            pokemonItems.addAll(pokemonData.itemListVo)
        }

        listViewModel.getUiState().observe(viewLifecycleOwner) { uiState ->
            updateUiState(uiState)
        }

    }

    private fun setRecyclerViewSettings() {
        binding.listRecyclerView.apply {
            adapter = pokemonsAdapter
            layoutManager = GridLayoutManager(requireActivity(), GRID_LM_SPAN_CONT)
        }
    }

    private fun onPokemonItemClick(position: Int) {
        val bundle = Bundle().apply {
            putString(BUNDLE_NAME_KEY, pokemonItems[position].name)
        }
        findNavController().navigate(
            R.id.action_listFragment_to_detailsFragment,
            bundle
        )
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

    companion object {
        private const val GRID_LM_SPAN_CONT = 2
        private const val BUNDLE_NAME_KEY = "name_key"
        private const val REQUEST_ITEMS_LIMIT = "16"
    }

}