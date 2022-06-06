package com.example.tutu_intern_test_cherniak.presentation.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.tutu_intern_test_cherniak.App
import com.example.tutu_intern_test_cherniak.R
import com.example.tutu_intern_test_cherniak.databinding.FragmentDetailsBinding
import com.example.tutu_intern_test_cherniak.presentation.extensions.loadImage
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class PokemonDetailsFragment : Fragment() {

    @Inject
    lateinit var pokemonDetailsViewModel: PokemonDetailsViewModel

    private val saveArguments: PokemonDetailsFragmentArgs by navArgs()
    private val binding: FragmentDetailsBinding by viewBinding()

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

        pokemonDetailsViewModel.getPokemonDetailsData(saveArguments.pokemonName)

        pokemonDetailsViewModel.pokemonsDetailsLiveData.observe(viewLifecycleOwner) { pokemonDetails ->
            setDataToUi(pokemonDetails)
        }
    }

    private fun setDataToUi(pokemonDetailsModelVo: PokemonDetailsModelVo) {
        with(binding) {
            pokemonNameTextView.text = pokemonDetailsModelVo.name
            pokemonAvatarImageView.loadImage(pokemonDetailsModelVo.avatarUrl)
            weightTextView.text = pokemonDetailsModelVo.weight.toString()
            heightTextView.text = pokemonDetailsModelVo.height.toString()
        }
        setDataToDetailsRecyclerView()
    }

    private fun setDataToDetailsRecyclerView(){

    }
}