package com.example.rickandmorty.ui.pages.favorite


import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentFavoriteBinding
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.characters.adapter.CharacterAdapter
import com.example.rickandmorty.ui.pages.favorite.adapter.FavoriteAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.CalculateWindowSize
import com.example.rickandmorty.utils.PagingLoadStateAdapter
import com.example.rickandmorty.utils.SharedPreferencesManager
import com.example.rickandmorty.utils.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    override fun getViewBinding(): FragmentFavoriteBinding =
        FragmentFavoriteBinding.inflate(layoutInflater)

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var favoriteAdapter = FavoriteAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var widthWindowClass: WindowSizeClass
    lateinit var data : List<CharacterItem>
    override fun onCreateViewBase() {
    }


    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).settings(true)
        widthWindowClass = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()
        detailNavigation()
        selectList()
        binding.recyclerViewFavorite.adapter = favoriteAdapter
    }

    override fun observer() {
        lifecycleScope.launch {
            favoriteViewModel.fetchFavoriteCharacters().collectLatest{ data ->
                favoriteAdapter.submitData(data)
            }
        }
    }

    private fun detailNavigation() {
        favoriteAdapter.clickEpisode = {
            val bundle = Bundle()
            it.id?.let { its -> bundle.putInt("detailId", its) }
            findNavController().navigate(R.id.favToDetail, bundle)
        }
    }

    private fun selectList() {
        /**LINEAR LIST*/
        binding.favoriteIconListChoice.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.recyclerViewFavorite.adapter = favoriteAdapter

                /** this gives the white line between the vertical items in linear layout 'DIVIDER' */
                binding.recyclerViewFavorite.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        1)
                )
                /** if --- list icon  --- selected it bind adapter for the linear layout manager */
                linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.recyclerViewFavorite.layoutManager = linearLayoutManager
                favoriteAdapter.viewtype = 2

            } else {
                /**GRID LIST*/
                binding.recyclerViewFavorite.adapter = favoriteAdapter

                /** removes the white line between the vertical items in linear layout 'DIVIDER' */
                binding.recyclerViewFavorite.removeItemDecorationAt(0)

                /** --- default icon ---  it bind adapter for the grid layout manager widthWindowClass
                 * detect the width of screen and picks the right span count */
                val spanCount = if (widthWindowClass == WindowSizeClass.EXPANDED) 3 else 2
                gridLayoutManager = GridLayoutManager(requireContext(), spanCount,
                    GridLayoutManager.VERTICAL,
                    false
                )
                binding.recyclerViewFavorite.layoutManager = gridLayoutManager
                favoriteAdapter.viewtype = 1
            }
        }
    }

}
