package com.example.rickandmorty.ui.pages.detail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.constants.Constants
import com.example.rickandmorty.databinding.FragmentDetailBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.episodes.EpisodeViewModel
import com.example.rickandmorty.ui.pages.episodes.adapter.EpisodeAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.Extensions.setImageUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getViewBinding(): FragmentDetailBinding =
        FragmentDetailBinding.inflate(layoutInflater)

    private val detailViewModel: DetailViewModel by viewModels()
    private var detailEpisodeAdapter = EpisodeAdapter()
    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).backNavigation(true)
        (requireActivity() as MainActivity).searchIcon(false)

        val getId = requireArguments().getInt("detailId")
        detailViewModel.fetchCharacterById(getId)

    }

    override fun observer() {
        popUpWindows()
        lifecycleScope.launchWhenStarted {
            detailViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgress()
                } else {
                    dismissLoadingProgress()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            detailViewModel._detailStateFlow.collectLatest { detailResponse ->
                detailResponse?.let {
                    binding.apply {
                        it.image?.let { it -> detailCharImageView.setImageUrl(it) }
                        detailCharNameTxt.text = it.name
                        detailCharStatusTxt.text = it.status
                        detailCharSpeciesTxt.text = it.species
                        detailCharOriginTxt.text = it.origin?.name
                        val episodeRange = it.episode?.map {
                            it.substring(startIndex = it.lastIndexOf("/") + 1)
                        }
                        detailViewModel.fetchEpisodesFromCharacter(episodeRange.toString())
                        Log.d("msg",episodeRange.toString())

                        when (detailCharStatusTxt.text) {
                            "Alive" -> {
                                detailStatusIcon.setImageResource(R.drawable.online)
                            }
                            "Dead" -> {
                                detailStatusIcon.setImageResource(R.drawable.offline)
                            }
                            "unknown" -> {
                                detailStatusIcon.setImageResource(R.drawable.unknown_icon)
                            }
                        }
                        when (it.gender) {
                            "Male" -> {
                                detailGenderIcon.setImageResource(R.drawable.male_icon)
                            }
                            "Female" -> {
                                detailGenderIcon.setImageResource(R.drawable.female_icon)
                            }
                            "Unknown" -> {
                                detailGenderIcon.setImageResource(R.drawable.unknown_icon)
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            detailViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgress()
                } else {
                    dismissLoadingProgress()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            detailViewModel._characterEpisodesStateFlow.collectLatest {
                it?.let {
                    with(binding) {
                        it.let { it -> detailEpisodeAdapter.addEpisodeList(it) }
                        detailCharEpisodesRecyclerView.adapter = detailEpisodeAdapter
                        detailCharEpisodesRecyclerView.setHasFixedSize(true)
                    }
                }
            }
        }
    }

    private fun popUpWindows() {
        detailEpisodeAdapter.clickEpisode = {
            val bundle = bundleOf("season" to it.episode , "id" to it.id)
            bundle.putInt("type",Constants.typeEpisode)
            findNavController().navigate(R.id.detailtoBottom , bundle)
        }
    }
}

