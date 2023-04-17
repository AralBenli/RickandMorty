package com.example.rickandmorty.ui.pages.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSearchBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.ui.pages.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchViewModel: SearchViewModel by viewModels()
    private var searchAdapter = SearchAdapter()
    lateinit var text: String
    lateinit var status : String
    override fun getViewBinding(): FragmentSearchBinding =
        FragmentSearchBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).backNavigation(true)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).settings(false)

        binding.searchRecyclerView.adapter = searchAdapter
        detailNavigation()
        setSearchView()
    }


    override fun observer() {

        lifecycleScope.launchWhenStarted {
            searchViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    binding.searchProgressBar.visibility = View.VISIBLE
                } else {
                    binding.searchProgressBar.visibility = View.GONE

                }
            }
        }

        lifecycleScope.launchWhenStarted {
            searchViewModel._searchStateFlow.collectLatest {
                it?.let {
                    with(binding) {
                        it.results?.let { its -> searchAdapter.addCharacterList(its) }
                        searchRecyclerView.setHasFixedSize(true)
                    }
                }
            }
        }
    }

    private fun detailNavigation() {
        searchAdapter.clickCharacter = {
            val bundle = Bundle()
            it.id?.let { id -> bundle.putInt("detailId", id) }
            findNavController().navigate(R.id.searchToDetail, bundle)
        }
    }


    private fun setSearchView() {
        with(binding){
            searchView.isIconified = false
            searchView.isFocusable = true
            searchView.requestFocusFromTouch()
            searchView.onActionViewExpanded()
            searchView.imeOptions = EditorInfo.IME_ACTION_DONE
            searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        query.also { text = it }
                        fetchInitialSearch()
                        statusCheck()
                        return false
                    }
                    return false
                }
            })


        }


    }

    private fun fetchInitialSearch() {
        searchViewModel.getSearchAll(text)
    }


    private fun statusCheck() {
        with(binding) {
            statusGroup.setOnCheckedStateChangeListener { _, _ ->
                if (statusAll.isChecked){
                    searchView.clearFocus()
                    searchRecyclerView.layoutManager?.scrollToPosition(0)
                    searchViewModel.getSearchAll(text)
                }
                if (statusAlive.isChecked) {
                    status = "alive"
                    searchView.clearFocus()
                    searchRecyclerView.layoutManager?.scrollToPosition(0)
                    searchViewModel.fetchSearch(text, status)
                }
                if (statusDead.isChecked) {
                    status = "dead"
                    searchView.clearFocus()
                    searchRecyclerView.layoutManager?.scrollToPosition(0)
                    searchViewModel.fetchSearch(text, status)
                }
                if (statusUnknown.isChecked) {
                    status = "unknown"
                    searchView.clearFocus()
                    searchRecyclerView.layoutManager?.scrollToPosition(0)
                    searchViewModel.fetchSearch(text, status)
                }
            }
        }
    }
}
