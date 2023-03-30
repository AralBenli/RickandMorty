package com.example.rickandmorty.ui.pages.characters


import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.characters.adapter.CharacterAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.CalculateWindowSize
import com.example.rickandmorty.utils.PagingLoadStateAdapter
import com.example.rickandmorty.utils.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    override fun getViewBinding(): FragmentCharactersBinding =
        FragmentCharactersBinding.inflate(layoutInflater)

    private val charactersViewModel: CharactersViewModel by viewModels()
    private var characterAdapter = CharacterAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var widthWindowClass: WindowSizeClass
    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(true)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).searchIcon(true)
        (requireActivity() as MainActivity).actionBar(true)
        widthWindowClass = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()


        binding.recyclerViewCharacters.adapter = characterAdapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter(),
            footer = PagingLoadStateAdapter()
        )
        charactersViewModel.fetchCharacters()

        detailNavigation()
        swipeRefresh()
        selectList()
    }

    override fun observer() {
        charactersViewModel.characters.observe(viewLifecycleOwner) { pagingData ->
            characterAdapter.submitData(lifecycle, pagingData)
        }
    }


    private fun detailNavigation() {
        characterAdapter.clickCharacter = {
            val bundle = Bundle()
            it.id?.let { its -> bundle.putInt("detailId", its) }
            findNavController().navigate(R.id.detailFragment, bundle)
        }
    }

    private fun swipeRefresh() {
        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            charactersViewModel.fetchCharacters()
            binding.homeSwipeRefreshLayout.isRefreshing = false

        }
    }

    private fun selectList() {
        binding.iconListChoice.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                /** if list icon selected it bind adapter for the linear layout manager */
                binding.recyclerViewCharacters.adapter =
                    characterAdapter.withLoadStateHeaderAndFooter(
                        header = PagingLoadStateAdapter(),
                        footer = PagingLoadStateAdapter()
                    )
                linearLayoutManager = LinearLayoutManager(requireContext())
                binding.recyclerViewCharacters.layoutManager = linearLayoutManager
                characterAdapter.viewtype = 2

                binding.recyclerViewCharacters.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
                /** fetching characters for the new layout manager when button clicked  */
                charactersViewModel.fetchCharacters()


            } else {
                /** default icon  it bind adapter for the grid layout manager widthWindowClass
                 * detect the width of screen and picks the right span count */
                binding.recyclerViewCharacters.adapter =
                    characterAdapter.withLoadStateHeaderAndFooter(
                        header = PagingLoadStateAdapter(),
                        footer = PagingLoadStateAdapter()
                    )

                val spanCount = if (widthWindowClass == WindowSizeClass.EXPANDED) 3 else 2
                gridLayoutManager =
                    GridLayoutManager(
                        requireContext(),
                        spanCount,
                        GridLayoutManager.VERTICAL,
                        false
                    )
                binding.recyclerViewCharacters.layoutManager = gridLayoutManager
                characterAdapter.viewtype = 1
                /** fetching characters for the new layout manager when button clicked  */
                charactersViewModel.fetchCharacters()
            }
        }
    }
}



