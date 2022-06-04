package com.example.tutu_intern_test_cherniak.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.models.model_vo.PokemonListVo
import com.example.tutu_intern_test_cherniak.R
import com.example.tutu_intern_test_cherniak.databinding.FragmentListBinding
import com.example.tutu_intern_test_cherniak.presentation.list.pokemonslist_adapter.PokemonAdapter

class ListFragment : Fragment() {

    private val binding: FragmentListBinding by viewBinding()
    private val pokemonItems: List<PokemonListVo> = mutableListOf()
    private val pokemonsAdapter: PokemonAdapter by lazy {
        PokemonAdapter { position ->
            onPokemonItemClick(position)
        }
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
    }

    private fun setRecyclerViewSettings() {
        binding.listRecyclerView.apply {
            adapter = pokemonsAdapter
            layoutManager = GridLayoutManager(requireActivity(), GRID_LM_SPAN_CONT)
        }
    }

    private fun onPokemonItemClick(position: Int) {
        val bundle = Bundle().apply {
            putString(BUNDLE_NAME_KEY, pokemonItems[position].pokemonName)
        }
        findNavController().navigate(
            R.id.action_listFragment_to_detailsFragment,
            bundle
        )
    }

    companion object {
        private const val GRID_LM_SPAN_CONT = 2
        private const val BUNDLE_NAME_KEY = "name_key"
    }

}