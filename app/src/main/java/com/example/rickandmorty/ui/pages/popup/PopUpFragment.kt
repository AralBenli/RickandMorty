package com.example.rickandmorty.ui.pages.popup

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.databinding.FragmentPopUpBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.characters.CharactersViewModel
import com.example.rickandmorty.ui.pages.popup.adapter.PopUpAdapter
import kotlinx.coroutines.flow.collectLatest

class PopUpFragment : BaseFragment<FragmentPopUpBinding>(){

    override fun getViewBinding(): FragmentPopUpBinding =
        FragmentPopUpBinding.inflate(layoutInflater)


    private val episodeViewModel : CharactersViewModel by viewModels()
    private var popUpAdapter = PopUpAdapter()

    override fun initViews() {

        episodeViewModel.fetchCharacter(1)

    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            episodeViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgress()
                } else {
                    dismissLoadingProgress()
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            episodeViewModel._characterStateFlow.collectLatest {
                it?.let {
                    with(binding){
                        it.results?.let { it -> popUpAdapter.addPopUpList(it) }
                        recyclerViewCharacters.adapter = popUpAdapter
                        recyclerViewCharacters.setHasFixedSize(true)
                    }
                }
            }
        }
    }
}
