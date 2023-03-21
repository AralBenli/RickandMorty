package com.example.rickandmorty.ui.pages.characters


import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.characters.adapter.CharacterAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    override fun getViewBinding(): FragmentCharactersBinding =
        FragmentCharactersBinding.inflate(layoutInflater)


    private val charactersViewModel: CharactersViewModel by viewModels()
    private var characterAdapter = CharacterAdapter()

    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(true)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).searchIcon(true)
        (requireActivity() as MainActivity).actionBar(true)

        charactersViewModel.fetchCharacter(1)
        detailNavigation()
    }

    override fun observer() {
           lifecycleScope.launchWhenStarted {
               charactersViewModel._progressStateFlow.collectLatest { showProgress ->
                   if (showProgress) {
                       showLoadingProgress()
                   } else {
                       dismissLoadingProgress()
                   }
               }
           }


        lifecycleScope.launchWhenStarted {
            charactersViewModel._characterStateFlow.collectLatest {
                it?.let {
                    with(binding){
                        characterAdapter.addCharacterList(it.results)
                        recyclerViewCharacters.adapter = characterAdapter
                        recyclerViewCharacters.setHasFixedSize(true)
                    }
                }
            }
        }
    }
    private fun detailNavigation(){

        characterAdapter.clickCharacter = {
            val bundle = Bundle()
            it.id?.let { it -> bundle.putInt("detailId", it) }
            findNavController().navigate(R.id.detailFragment,bundle)
        }
    }
    /*private fun carouselSelector()  {
        binding.recyclerViewCarousel.adapter = carouselAdapter
        carouselAdapter.addCarouselList(scrollView)
        carouselAdapter.clickCarousel = {
            val bundle = Bundle()
            when (it) {
                1 -> {
                    bundle.putInt("id",1)
                    findNavController().navigate(R.id.detailFragment, bundle)
                }
                2 -> {
                    bundle.putInt("id",2)
                    findNavController().navigate(R.id.detailFragment, bundle)
                }
                3 -> {
                    bundle.putInt("id",3)
                    findNavController().navigate(R.id.detailFragment, bundle)
                }
                4 -> {
                    bundle.putInt("id",4)
                    findNavController().navigate(R.id.detailFragment, bundle)
                }
                5 -> {
                    bundle.putInt("id",5)
                    findNavController().navigate(R.id.detailFragment, bundle)
                }
            }
        }
    }*/

}

