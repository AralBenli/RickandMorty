package com.example.rickandmorty.ui.pages.episodes.adapter

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.EpisodesRowItemBinding
import com.example.rickandmorty.response.EpisodeItem


class EpisodeAdapter : PagingDataAdapter<EpisodeItem, EpisodeAdapter.EpisodeViewHolder>(EpisodeDiffUtil) {
    var clickEpisode: ((item: EpisodeItem) -> Unit)? = null


    inner class EpisodeViewHolder(private val binding: EpisodesRowItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(episodeList: EpisodeItem) {
            with(binding) {
                episodeDateTxt.text = episodeList.airDate
                episode.text = episodeList.episode
                episodeNameTxt.text = episodeList.name
                val staticTxtEpisode = SpannableString("Click to see characters")
                staticTxtEpisode.setSpan(UnderlineSpan(), 0 , staticTxtEpisode.length, 0)
                staticTxt.text = staticTxtEpisode

            }
            itemView.setOnClickListener {
                clickEpisode?.invoke(episodeList)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding =
            EpisodesRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)

    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem!!)
    }

    object EpisodeDiffUtil : DiffUtil.ItemCallback<EpisodeItem>() {
        override fun areItemsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem) =
            oldItem == newItem
    }

}