package com.example.rickandmorty.ui.pages.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSearchBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.characters.adapter.CharacterAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchViewModel: SearchViewModel by viewModels()
    private var searchAdapter = CharacterAdapter()
    lateinit var text: String
    private var status: String = "alive"
    override fun getViewBinding(): FragmentSearchBinding =
        FragmentSearchBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).backNavigation(true)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).actionBar(true)

        detailNavigation()
        setSearchView()
    }


    override fun observer() {

        lifecycleScope.launchWhenStarted {
            searchViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgress()
                } else {
                    dismissLoadingProgress()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            searchViewModel._searchStateFlow.collectLatest {
                it?.let {
                    with(binding) {
                        searchAdapter.addCharacterList(it.results)
                        searchRecyclerView.adapter = searchAdapter
                        searchRecyclerView.setHasFixedSize(true)
                    }
                }
            }
        }
    }

    private fun detailNavigation() {
        searchAdapter.clickCharacter = {
            val bundle = Bundle()
            it.id?.let { it -> bundle.putInt("detailId", it) }
            findNavController().navigate(R.id.detailFragment, bundle)
        }
    }


    private fun setSearchView() {
        binding.searchView.isIconified = false
        binding.searchView.isFocusable = true
        binding.searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    text = query
                    statusCheck()
                    searchViewModel.fetchSearch(text, status)
                    return true
                }
                return false
            }
        }
        )
    }

    private fun statusCheck() {
        with(binding) {
            statusGroup.setOnCheckedStateChangeListener { _, _ ->
                if (statusAlive.isChecked) {
                    status = "alive"
                    searchView.clearFocus()
                    searchViewModel.fetchSearch(text, status)
                }
                if (statusDead.isChecked) {
                    status = "dead"
                    searchView.clearFocus()
                    searchViewModel.fetchSearch(text, status)
                }
                if (statusUnknown.isChecked) {
                    status = "unknown"
                    searchView.clearFocus()
                    searchViewModel.fetchSearch(text, status)
                }
            }
        }
    }
}
