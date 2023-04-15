package com.example.rickandmorty.ui.pages.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.constants.Constants
import com.example.rickandmorty.databinding.FragmentDetailBinding
import com.example.rickandmorty.local.favorite.toFavoriteEntity
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.detail.adapter.DetailEpisodeAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.Extensions.setImageUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getViewBinding(): FragmentDetailBinding =
        FragmentDetailBinding.inflate(layoutInflater)

    private val detailViewModel: DetailViewModel by viewModels()
    private var detailEpisodeAdapter = DetailEpisodeAdapter()
    lateinit var layoutManager: LinearLayoutManager

    override fun initViews() {

        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).backNavigation(true)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).settings(false)
        val getId = requireArguments().getInt("detailId")
        val getItem = requireArguments().getParcelable<CharacterItem>("characterItem")



        with(binding) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            detailCharEpisodesRecyclerView.layoutManager = layoutManager
            detailCharEpisodesRecyclerView.adapter = detailEpisodeAdapter
            detailCharEpisodesRecyclerView.isNestedScrollingEnabled = false

        }

        detailViewModel.fetchCharacterById(getId)
        popUpWindows()
        shareCharacter(getId)
        scrollHelper()

        with(binding) {
            detailCharImageView.setImageUrl(getItem?.image.toString())
            detailCharNameTxt.text = getItem?.name
            detailCharStatusTxt.text = getItem?.status
            detailCharSpeciesTxt.text = getItem?.species
            detailCharOriginTxt.text = getItem?.origin?.name
        }
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            detailViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    binding.detailProgressBar.visibility = View.VISIBLE
                } else {
                    binding.detailProgressBar.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            detailViewModel._detailStateFlow.collectLatest { detailResponse ->
                detailResponse?.let {
                    binding.apply {
                        favoriteCharacter(it)
                        it.image?.let { it -> detailCharImageView.setImageUrl(it) }
                        detailCharNameTxt.text = it.name
                        detailCharStatusTxt.text = it.status
                        detailCharSpeciesTxt.text = it.species
                        detailCharOriginTxt.text = it.origin?.name
                        val episodeRange = it.episode?.map {
                            it.substring(startIndex = it.lastIndexOf("/") + 1)
                        }
                        detailViewModel.fetchEpisodesFromCharacter(episodeRange.toString())
                        Log.d("msg", episodeRange.toString())

                        when (detailCharStatusTxt.text) {
                            "Alive" -> {
                                detailStatusIcon.setImageResource(R.drawable.online)
                            }
                            "Dead" -> {
                                detailStatusIcon.setImageResource(R.drawable.offline)
                            }
                            "unknown" -> {
                                detailStatusIcon.setImageResource(R.drawable.icon_unknown)
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
                                detailGenderIcon.setImageResource(R.drawable.icon_unknown)
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            detailViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    binding.detailProgressBar.visibility = View.VISIBLE
                } else {
                    binding.detailProgressBar.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            detailViewModel._characterEpisodesStateFlow.collectLatest {
                it?.let {
                    with(binding) {
                        it.let { it -> detailEpisodeAdapter.addEpisodeList(it) }
                        detailCharEpisodesRecyclerView.setHasFixedSize(true)
                    }
                }
            }
        }
    }

    private fun scrollHelper() {
        binding.detailCharEpisodesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            private var isScrollingRight = false

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dx > 0) {
                    // Scrolling right
                    isScrollingRight = true
                } else if (dx < 0) {
                    // Scrolling left
                    isScrollingRight = false
                }
            }

            @SuppressLint("SuspiciousIndentation")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position = layoutManager.findFirstVisibleItemPosition()
                    val lastPosition = layoutManager.findLastVisibleItemPosition()

                    if (isScrollingRight && position != lastPosition) {
                        // Scroll to the next item
                        recyclerView.smoothScrollToPosition(position + 1)
                    } else if (!isScrollingRight && position >= 1 && position <= lastPosition - 1) {
                        // Scroll to the previous item
                        val previousPosition = position - 1
                        if (layoutManager.findViewByPosition(previousPosition) != null) {
                            recyclerView.smoothScrollToPosition(previousPosition)
                        } else {
                            recyclerView.smoothScrollToPosition(position)
                        }
                    } else if (!isScrollingRight && position == 0) {
                        // Scroll to the first item
                        recyclerView.smoothScrollToPosition(0)
                    }
                }
            }
        })
    }

    private fun popUpWindows() {
        detailEpisodeAdapter.clickEpisode = {
            val bundle = bundleOf("season" to it.episode, "id" to it.id)
            bundle.putInt("type", Constants.typeEpisode)
            findNavController().navigate(R.id.detailtoBottom, bundle)
        }
    }

    private fun favoriteCharacter(character: CharacterItem) {
        with(binding) {
            favoriteButton.isChecked = character.isFavorite
            favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                character.isFavorite = isChecked
                detailViewModel.isFavorite = isChecked
                lifecycleScope.launch {
                    detailViewModel.updateFavoriteState(character.id ?: 0, isChecked)
                    if (isChecked) {
                        detailViewModel.addCharacterToFavorites(character.toFavoriteEntity())
                        showCustomToast(
                            Status.Added,
                            "Character added to favorites",
                            this@DetailFragment
                        )
                    } else {
                        detailViewModel.deleteCharacter(character.toFavoriteEntity())
                        showCustomToast(
                            Status.Removed,
                            "Character removed from favorites",
                            this@DetailFragment
                        )
                    }
                }
            }
        }
    }


    private fun shareCharacter(charId: Int) {
        with(binding) {
            shareIcon.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, "${Constants.BASE_URL}character/${charId}")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share To:"))
            }
        }
    }
}


