package com.example.rickandmorty.ui.pages.popup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.constants.Constants
import com.example.rickandmorty.databinding.FragmentBottomSheetBinding
import com.example.rickandmorty.response.LocationItem
import com.example.rickandmorty.ui.pages.locations.LocationViewModel
import com.example.rickandmorty.ui.pages.popup.adapter.PopUpAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetBinding


    private val bottomSheetViewModel: BottomSheetViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private var popUpAdapter = PopUpAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun getTheme(): Int {
        return com.example.rickandmorty.R.style.AppBottomSheetDialogTheme
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getId = requireArguments().getInt("id")
        val getSeasonNum = requireArguments().getString("season")


        when (requireArguments().getInt( "type")){
            Constants.typeEpisode -> {

                bottomSheetViewModel.fetchEpisodeById(getId)

                lifecycleScope.launchWhenStarted {
                    bottomSheetViewModel._episodeByIdStateFlow.collectLatest {
                        it?.let {
                            binding.popUpStaticCharacters.text = getString(R.string.characters)
                            binding.popUpEpisodeName.text = it.name
                            binding.popUpSeasonTxt.text = it.airDate
                            binding.popUpDate.text = getSeasonNum
                            val characterList = it.characters?.map { char ->
                                char.substring(startIndex = char.lastIndexOf("/") + 1)
                            }
                            if (characterList != null) {
                                locationViewModel.fetchGetMoreCharactersThanOne(characterList)
                            }
                            Log.d("msg", characterList.toString())
                        }
                    }
                }
                printImageAndActiveNavigation()
            }

            Constants.typeLocation -> {

                bottomSheetViewModel.fetchLocationById(getId)

                lifecycleScope.launchWhenStarted {
                    bottomSheetViewModel._locationByIdStateFlow.collectLatest {
                        it?.let {
                            binding.popUpStaticCharacters.text = getString(R.string.Residents)
                            binding.popUpEpisodeName.text = it.name
                            binding.popUpDate.text = it.dimension
                            val residentsOfPlanet = it.residents?.map { char ->
                                char.substring(startIndex = char.lastIndexOf("/") + 1)
                            }
                            if (residentsOfPlanet != null) {
                                locationViewModel.fetchGetMoreCharactersThanOne(residentsOfPlanet)
                            }
                        }
                    }
                }
                printImageAndActiveNavigation()
            }
        }
    }

    private fun printImageAndActiveNavigation(){

        lifecycleScope.launchWhenStarted {
            locationViewModel._characterFromLocationStateFlow.collectLatest { characterResponse ->
                characterResponse.let {
                    with(binding) {
                        it?.let { it -> popUpAdapter.addPopUpList(it) }
                        binding.recyclerViewCharacters.adapter = popUpAdapter
                    }
                }
            }
        }
        popUpAdapter.clickCharacter = { clickedItem ->
            val bundle = Bundle()
            clickedItem.id?.let { it -> bundle.putInt("detailId", it) }
            findNavController().navigate(com.example.rickandmorty.R.id.detailFragment, bundle)
        }
    }
}


