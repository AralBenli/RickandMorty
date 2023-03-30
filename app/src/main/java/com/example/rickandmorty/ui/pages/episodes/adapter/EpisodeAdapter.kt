package com.example.rickandmorty.ui.pages.episodes.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.EpisodesRowItemBinding
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.response.EpisodeItem


class EpisodeAdapter() : PagingDataAdapter<EpisodeItem, EpisodeAdapter.EpisodeViewHolder>(EpisodeDiffUtil) {

    private val episodeList: ArrayList<EpisodeItem> = arrayListOf()
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

    override fun getItemCount() = episodeList.size

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val currentItem = episodeList[position]
        holder.bind(currentItem)
    }

    fun addEpisodeList(list: List<EpisodeItem>) {
        episodeList.clear()
        episodeList.addAll(list)
        notifyDataSetChanged()
    }
    object EpisodeDiffUtil : DiffUtil.ItemCallback<EpisodeItem>() {
        override fun areItemsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem) =
            oldItem == newItem
    }

}