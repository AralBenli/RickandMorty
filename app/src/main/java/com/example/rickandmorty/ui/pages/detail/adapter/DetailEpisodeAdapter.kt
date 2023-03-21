package com.example.rickandmorty.ui.pages.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.EpisodesRowItemBinding
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.models.EpisodeItem


class DetailEpisodeAdapter : RecyclerView.Adapter<DetailEpisodeAdapter.DetailViewHolder>(){

    private val episodeList: ArrayList<EpisodeItem> = arrayListOf()


    inner class DetailViewHolder(private val binding : EpisodesRowItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(episodeList: EpisodeItem) {
            with(binding){

                

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {

        val binding = EpisodesRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DetailViewHolder(binding)

    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val currentItem = episodeList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount() = episodeList.size

    fun addEpisodeList(list: List<EpisodeItem>) {
        episodeList.clear()
        episodeList.addAll(list)
        notifyDataSetChanged()
    }
}