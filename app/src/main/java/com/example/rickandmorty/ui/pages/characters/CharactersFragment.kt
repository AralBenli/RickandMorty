package com.example.rickandmorty.ui.pages.characters


import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.models.scrollView
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.characters.adapter.CarouselAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    override fun getViewBinding(): FragmentCharactersBinding =
        FragmentCharactersBinding.inflate(layoutInflater)


    private val charactersViewModel: CharactersViewModel by viewModels()
    private val carouselAdapter = CarouselAdapter()

    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(true)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).searchIcon(true)

        charactersViewModel.fetchCharacter()

        carouselSelector()





    }


    override fun observer() {
     /*   lifecycleScope.launchWhenStarted {
            charactersViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgress()
                } else {
                    dismissLoadingProgress()
                }
            }
        }*/


        lifecycleScope.launchWhenStarted {
            charactersViewModel._characterStateFlow.collectLatest {  data ->
                with(binding){

                }
            }
        }
    }




    private fun carouselSelector() {
        binding.recyclerViewCarousel.adapter = carouselAdapter
        carouselAdapter.addCarouselList(scrollView)
        carouselAdapter.clickCarousel = {
            when (it) {
                1 -> {}
                2 -> {}
                3 -> {}
                4 -> {}
                5 -> {}
            }
        }
    }
}