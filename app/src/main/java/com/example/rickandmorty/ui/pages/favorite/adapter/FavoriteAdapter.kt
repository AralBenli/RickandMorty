package com.example.rickandmorty.ui.pages.favorite.adapter


import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.CharacterRowItemBinding
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.utils.Extensions.setImageUrl
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil



class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val episodeList: ArrayList<CharacterItem> = arrayListOf()
    var clickEpisode: ((item: CharacterItem) -> Unit)? = null


    inner class FavoriteViewHolder(private val binding: CharacterRowItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteList: CharacterItem) {
            with(binding) {
                charNameTxt.text = favoriteList.name
                val url = favoriteList.image
                url?.let { charImageView.setImageUrl(it) }

            }
            itemView.setOnClickListener {
                clickEpisode?.invoke(favoriteList)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            CharacterRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = episodeList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = episodeList.size



    fun submitData(newList: List<CharacterItem>) {
        episodeList.clear()
        episodeList.addAll(newList)
        notifyDataSetChanged()
    }


}