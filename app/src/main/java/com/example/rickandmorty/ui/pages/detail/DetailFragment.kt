package com.example.rickandmorty.ui.pages.detail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.R
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
    private val detailEpisodeViewModel: EpisodeViewModel by viewModels()
    private var detailEpisodeAdapter = EpisodeAdapter()
    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).backNavigation(true)
        (requireActivity() as MainActivity).searchIcon(false)

        val getId = requireArguments().getInt("detailId")

        detailViewModel.fetchCharacterById(getId)
        detailEpisodeViewModel.fetchEpisodes(1)

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
            detailEpisodeViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgress()
                } else {
                    dismissLoadingProgress()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            detailEpisodeViewModel._episodeStateFlow.collectLatest {
                it?.let {
                    with(binding) {
                        it.results?.let { it -> detailEpisodeAdapter.addEpisodeList(it) }
                        detailCharEpisodesRecyclerView.adapter = detailEpisodeAdapter
                        detailCharEpisodesRecyclerView.setHasFixedSize(true)
                    }
                }
            }
        }
    }

    private fun popUpWindows() {
        detailEpisodeAdapter.clickEpisode = {

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
    }
}

/*
detailEpisodeAdapter.addEpisodeList(it.results)
val imageView = view?.findViewById<ImageView>(R.id.popUpImageView)
val url = it.results[0].url
imageView?.setImageUrl(url)
val date = view?.findViewById<TextView>(R.id.popUpDate)
date?.text = it.results[0].episode
val realiseDate = view?.findViewById<TextView>(R.id.popUpSeasonTxt)
val name = view?.findViewById<TextView>(R.id.popUpNameTxt)*/
