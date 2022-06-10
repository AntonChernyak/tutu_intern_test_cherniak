package com.example.tutu_intern_test_cherniak.presentation.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.interactors.UIStateEnum
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.tutu_intern_test_cherniak.App
import com.example.tutu_intern_test_cherniak.R
import com.example.tutu_intern_test_cherniak.databinding.FragmentDetailsBinding
import com.example.tutu_intern_test_cherniak.presentation.details.pokemondetails_adapter.PokemonDetailsDiffAdapter
import com.example.tutu_intern_test_cherniak.presentation.extensions.checkNetworkConnect
import com.example.tutu_intern_test_cherniak.presentation.extensions.loadImage
import com.example.tutu_intern_test_cherniak.presentation.extensions.registerNetworkCallback
import com.example.tutu_intern_test_cherniak.presentation.extensions.setNavigateUpButtonToToolbar
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class PokemonDetailsFragment : Fragment() {

    @Inject
    lateinit var pokemonDetailsViewModel: PokemonDetailsViewModel

    private val saveArguments: PokemonDetailsFragmentArgs by navArgs()
    private val binding: FragmentDetailsBinding by viewBinding()

    private val detailsRecyclerViewAdapter = PokemonDetailsDiffAdapter { name ->
        pokemonDetailsViewModel.expandableButtonOnClick(name)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).component.fragmentViewModelComponentBuilder()
            .fragment(this)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailsFragmentToolbar.title =resources.getString(R.string.pokemon, saveArguments.pokemonName)
        setNavigateUpButtonToToolbar(binding.detailsFragmentToolbar)
        setNetworkCallback()

        pokemonDetailsViewModel.getPokemonDetailsData(saveArguments.pokemonName)
        setLoadDataObserver()
        setDataToDetailsRecyclerView()
        setUiStateObserver()
    }

    private fun setDataToUi(pokemonDetailsModelVo: PokemonDetailsModelVo) {
        with(binding) {
            pokemonNameTextView.text = pokemonDetailsModelVo.name
            pokemonAvatarImageView.loadImage(pokemonDetailsModelVo.avatarUrl)
            weightTextView.text = pokemonDetailsModelVo.weight.toString()
            heightTextView.text = pokemonDetailsModelVo.height.toString()

            if (this@PokemonDetailsFragment.checkNetworkConnect()) {
                updateUiState(UIStateEnum.NETWORK_AVAILABLE)
            } else updateUiState(UIStateEnum.NETWORK_ERROR)
        }
    }

    private fun setDataToDetailsRecyclerView() {
        binding.detailsRecyclerView.adapter = detailsRecyclerViewAdapter
        pokemonDetailsViewModel.detailsListLiveData.observe(viewLifecycleOwner) {
            detailsRecyclerViewAdapter.items = it
        }
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun updateUiState(uiStateEnum: UIStateEnum) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(binding) {
                    when (uiStateEnum) {
                        UIStateEnum.NETWORK_AVAILABLE -> detailsNoNetworkTextView.visibility =
                            View.GONE
                        UIStateEnum.NETWORK_ERROR -> detailsNoNetworkTextView.visibility =
                            View.VISIBLE
                        UIStateEnum.START_LOADING -> detailsProgressBar.visibility =
                            View.VISIBLE
                        UIStateEnum.END_LOADING -> detailsProgressBar.visibility = View.GONE
                        UIStateEnum.DATA_NOT_FOUND -> detailsNoDataTextView.visibility =
                            View.VISIBLE
                        UIStateEnum.DATA_FOUND -> detailsNoDataTextView.visibility =
                            View.GONE
                        UIStateEnum.DEFAULT_STATE -> {
                            detailsNoNetworkTextView.visibility = View.GONE
                            detailsProgressBar.visibility = View.GONE
                            detailsNoDataTextView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setNetworkCallback() {
        this@PokemonDetailsFragment.registerNetworkCallback({
            updateUiState(UIStateEnum.NETWORK_AVAILABLE)
        }, {
            updateUiState(UIStateEnum.NETWORK_ERROR)
        })
    }

    private fun setLoadDataObserver(){
        pokemonDetailsViewModel.pokemonsDetailsLiveData.observe(viewLifecycleOwner) { pokemonDetails ->
            setDataToUi(pokemonDetails)
        }
    }

    private fun setUiStateObserver(){
        pokemonDetailsViewModel.uiStateLiveData.observe(viewLifecycleOwner) { uiState ->
            updateUiState(uiState)
        }
    }
}