package com.example.rickandmorty.ui.pages.episodes


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.episodes.adapter.EpisodeAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>() {

    override fun getViewBinding(): FragmentEpisodesBinding =
        FragmentEpisodesBinding.inflate(layoutInflater)

    private val episodeViewModel : EpisodeViewModel by viewModels()
    private var episodeAdapter = EpisodeAdapter()


    override fun initViews() {

        (requireActivity() as MainActivity).bottomNavigation(true)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).searchIcon(false)

        episodeViewModel.fetchEpisodes(1)

    }

    override fun observer() {

        lifecycleScope.launchWhenStarted {
            episodeViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgress()
                } else {
                    dismissLoadingProgress()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            episodeViewModel._episodeStateFlow.collectLatest {
                it?.let {
                    with(binding){
                        it.results?.let { it -> episodeAdapter.addEpisodeList(it) }
                        episodeRecyclerView.adapter = episodeAdapter
                        episodeRecyclerView.setHasFixedSize(true)
                    }
                }
            }
        }

        episodeAdapter.clickEpisode = {

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