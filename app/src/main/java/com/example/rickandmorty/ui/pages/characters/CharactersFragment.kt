package com.example.rickandmorty.ui.pages.characters


import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.example.rickandmorty.utils.mapToCharacterItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    override fun getViewBinding(): FragmentCharactersBinding =
        FragmentCharactersBinding.inflate(layoutInflater)

    private val charactersViewModel: CharactersViewModel by viewModels()
    private var characterAdapter = CharacterAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var widthWindowClass: WindowSizeClass


    override fun onCreateViewBase() {
        /** returning character list screen from detail screen
         * with back press , this helps to continue same page if i call this on
         * init views always page refreshes when back press */

        charactersViewModel.fetchCharacters()

    }

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).searchIcon(true)
        (requireActivity() as MainActivity).actionBar(true)
        (requireActivity() as MainActivity).settings(true)
        (requireActivity() as MainActivity).bottomNavigation(false)

            widthWindowClass = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()
            binding.recyclerViewCharacters.adapter = characterAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(),
                footer = PagingLoadStateAdapter()
            )

        detailNavigation()
        swipeRefresh()
        selectList()
    }

    override fun observer() {
        /** Collecting data */
        try {
            lifecycleScope.launchWhenStarted {
                charactersViewModel._characterStateFlow.collectLatest { pagingData ->
                    characterAdapter.submitData(lifecycle, mapToCharacterItem(pagingData) )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun detailNavigation() {
        characterAdapter.clickCharacter = {
            val bundle = bundleOf("characterItem" to it, "detailId" to it.id)
            findNavController().navigate(R.id.charToDetail, bundle)
        }
    }

    private fun swipeRefresh() {
        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            binding.recyclerViewCharacters.visibility = View.GONE
            binding.recyclerViewCharacters.visibility = View.VISIBLE
            charactersViewModel.fetchCharacters()
            binding.homeSwipeRefreshLayout.isRefreshing = false

        }
    }

    private fun selectList() {
        /** // Switch to linear layout*/

        binding.iconListChoice.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.recyclerViewCharacters.adapter = characterAdapter.withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(),
                    footer = PagingLoadStateAdapter()
                )

                /** this gives the white line between the vertical items in linear layout 'DIVIDER' */
                binding.recyclerViewCharacters.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        1
                    )
                )
                /** if --- list icon  --- selected it bind adapter for the linear layout manager */
                linearLayoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.recyclerViewCharacters.layoutManager = linearLayoutManager
                characterAdapter.viewType(2)
                characterAdapter.notifyDataSetChanged()
/*
                runLayoutAnimation(binding.recyclerViewCharacters)
*/

            } else {
                /** / Switch to Grid layout**/
                binding.recyclerViewCharacters.adapter = characterAdapter.withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(),
                    footer = PagingLoadStateAdapter()
                )

                /** removes the white line between the vertical items in linear layout 'DIVIDER' */
                binding.recyclerViewCharacters.removeItemDecorationAt(0)

                /** --- default icon ---  it bind adapter for the grid layout manager widthWindowClass
                 * detect the width of screen and picks the right span count */
                val spanCount = if (widthWindowClass == WindowSizeClass.EXPANDED) 3 else 2
                gridLayoutManager = GridLayoutManager(
                    requireContext(), spanCount,
                    GridLayoutManager.VERTICAL,
                    false
                )
                binding.recyclerViewCharacters.layoutManager = gridLayoutManager
                characterAdapter.viewType(1)
                characterAdapter.notifyDataSetChanged()
/*
                runLayoutAnimation(binding.recyclerViewCharacters)
*/

            }
        }
    }
}




