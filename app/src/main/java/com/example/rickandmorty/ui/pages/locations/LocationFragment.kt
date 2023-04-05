package com.example.rickandmorty.ui.pages.locations


import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.constants.Constants
import com.example.rickandmorty.databinding.FragmentLocationBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.locations.adapter.LocationAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>() {


    override fun getViewBinding(): FragmentLocationBinding =
        FragmentLocationBinding.inflate(layoutInflater)

    private val locationViewModel: LocationViewModel by viewModels()
    private var locationAdapter = LocationAdapter()

    override fun onCreateViewBase() {
        locationViewModel.fetchLocations()
    }
    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).settings(true)


        binding.locationRecyclerView.adapter = locationAdapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter(),
            footer = PagingLoadStateAdapter()
        )
        navigation()

    }

    override fun observer() {
        /** Collecting data*/
        lifecycleScope.launchWhenStarted {
            locationViewModel._locationStateFlow.collectLatest { pagingData ->
                locationAdapter.submitData(lifecycle , pagingData)

            }

        }

    }

    private fun navigation() {
        /** Type(location) and id sent to Bottom Sheet Fragment  */
        locationAdapter.clickLocation = {
            val bundle = bundleOf("type" to Constants.typeLocation, "id" to it.id)
            findNavController().navigate(R.id.locationToBottom, bundle)
        }
    }
}

