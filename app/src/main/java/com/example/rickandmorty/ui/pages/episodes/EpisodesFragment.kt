package com.example.rickandmorty.ui.pages.episodes


import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.constants.Constants
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.episodes.adapter.EpisodeAdapter
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>() {

    override fun getViewBinding(): FragmentEpisodesBinding =
        FragmentEpisodesBinding.inflate(layoutInflater)

    private val episodeViewModel: EpisodeViewModel by viewModels()
    private var episodeAdapter = EpisodeAdapter()

    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(true)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).searchIcon(false)

        binding.episodeRecyclerView.adapter = episodeAdapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter(),
            footer = PagingLoadStateAdapter()
        )

        episodeViewModel.fetchEpisodes()
        navigation()
    }

    override fun observer() {
        episodeViewModel.episodes.observe(viewLifecycleOwner) { pagingData ->
            episodeAdapter.submitData(lifecycle, pagingData)
        }
    }


    private fun navigation() {
        /** Type(episode) , id and season text ; sent to Bottom Sheet Fragment  */
        episodeAdapter.clickEpisode = {
            val bundle = bundleOf("season" to it.episode, "id" to it.id)
            bundle.putInt("type", Constants.typeEpisode)
            findNavController().navigate(
                R.id.action_episodesFragment_to_bottomSheetFragment,
                bundle
            )
        }

    }


}


