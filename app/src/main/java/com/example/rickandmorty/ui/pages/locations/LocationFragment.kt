package com.example.rickandmorty.ui.pages.locations

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentLocationBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.episodes.adapter.EpisodeAdapter
import com.example.rickandmorty.ui.pages.locations.adapter.LocationAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    private val locationViewModel: LocationViewModel by viewModels()
    private var locationAdapter = LocationAdapter()


    override fun getViewBinding(): FragmentLocationBinding = FragmentLocationBinding.inflate(layoutInflater)





    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).bottomNavigation(true)
        (requireActivity() as MainActivity).actionBar(true)
        (requireActivity() as MainActivity).searchIcon(false)

        locationViewModel.fetchLocations(1)

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
            locationViewModel._locationStateFlow.collectLatest {
                it.let {
                    with(binding){
                        if (it != null){
                            it.results?.let { it -> locationAdapter.addLocationList(it) }
                            locationRecyclerView.adapter = locationAdapter
                            locationRecyclerView.setHasFixedSize(true)
                        }
                    }
                }
            }
        }


        locationAdapter.clickLocation = {

            val acceptBinding = layoutInflater.inflate(R.layout.fragment_pop_up, null)
            val characterDialog = Dialog(requireContext())
            characterDialog.setContentView(acceptBinding)
            characterDialog.setCanceledOnTouchOutside(true)
            characterDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            characterDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            characterDialog.window?.setDimAmount(0.85f)
            characterDialog.show()
        }

        val staticTxt = view?.findViewById<TextView>(R.id.popUpStaticCharacters)
        staticTxt?.text = "RESIDENTS"

    }
}