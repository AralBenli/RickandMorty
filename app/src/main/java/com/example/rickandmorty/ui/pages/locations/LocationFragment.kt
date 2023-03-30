package com.example.rickandmorty.ui.pages.locations

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.constants.Constants
import com.example.rickandmorty.databinding.FragmentLocationBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.locations.adapter.LocationAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    private val locationViewModel: LocationViewModel by viewModels()
    private var locationAdapter = LocationAdapter()
    private var characterList : List<String>? = emptyList()

    override fun getViewBinding(): FragmentLocationBinding = FragmentLocationBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).bottomNavigation(true)
        (requireActivity() as MainActivity).actionBar(true)
        (requireActivity() as MainActivity).searchIcon(false)

        locationViewModel.fetchLocations(1)
        binding.locationRecyclerView.adapter = locationAdapter
    }

    override fun observer() {

        lifecycleScope.launchWhenStarted {
            locationViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgress()
                } else {
                    dismissLoadingProgress()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            locationViewModel._locationStateFlow.collectLatest { locationResponse ->
                locationResponse.let {
                    with(binding){
                        if (it != null){
                            it.results.let {
                                if (it != null) {
                                    locationAdapter.addLocationList(it)
                                }
                            }

                            locationRecyclerView.setHasFixedSize(true)
                        }
                    }
                }
            }
        }

        /** Type(location) and id sent to Bottom Sheet Fragment  */
        locationAdapter.clickLocation = {
            val bundle = bundleOf("type" to Constants.typeLocation , "id" to it.id )
            findNavController().navigate(R.id.locationToBottom , bundle)
        }
    }
}