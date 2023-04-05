package com.example.rickandmorty.ui.pages.favorite


import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentFavoriteBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.favorite.adapter.FavoriteAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    override fun getViewBinding(): FragmentFavoriteBinding =
        FragmentFavoriteBinding.inflate(layoutInflater)

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var favoriteAdapter = FavoriteAdapter()

    override fun onCreateViewBase() {
    }


    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).settings(true)
        detailNavigation()
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
}
