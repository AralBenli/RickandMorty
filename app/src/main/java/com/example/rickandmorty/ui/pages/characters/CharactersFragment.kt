package com.example.rickandmorty.ui.pages.characters


import androidx.fragment.app.viewModels
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    override fun getViewBinding(): FragmentCharactersBinding =
        FragmentCharactersBinding.inflate(layoutInflater)


    private val charactersViewModel: CharactersViewModel by viewModels()

    override fun initViews() {
        charactersViewModel.fetchCharacter()
    }


}