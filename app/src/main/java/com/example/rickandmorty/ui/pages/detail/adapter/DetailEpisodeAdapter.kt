package com.example.rickandmorty.ui.pages.detail.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.EpisodesRowItemBinding
import com.example.rickandmorty.response.EpisodeItem


class DetailEpisodeAdapter : RecyclerView.Adapter<DetailEpisodeAdapter.EpisodeViewHolder>() {

    private val episodeList: ArrayList<EpisodeItem> = arrayListOf()
    var clickEpisode: ((item: EpisodeItem) -> Unit)? = null


    inner class EpisodeViewHolder(private val binding: EpisodesRowItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(episodeList: EpisodeItem) {
            with(binding) {
                episodeNameTxt.text = episodeList.name
                episodeDateTxt.text = episodeList.airDate
                episode.text = episodeList.episode

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


}