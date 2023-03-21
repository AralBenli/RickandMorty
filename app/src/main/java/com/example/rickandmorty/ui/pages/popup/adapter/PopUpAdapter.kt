package com.example.rickandmorty.ui.pages.popup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.EpisodePopUpCharactersRowItemBinding
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.utils.Extensions.setImageUrl

class PopUpAdapter : RecyclerView.Adapter<PopUpAdapter.PopUpViewHolder>() {

    private val popUpMovieList: ArrayList<CharacterItem> = arrayListOf()

    inner class PopUpViewHolder(private val binding: EpisodePopUpCharactersRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterList: CharacterItem) {
            with(binding) {
                popUpNameTxt.text = characterList.name
                val url = characterList.image
                url?.let { popUpImageView.setImageUrl(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopUpViewHolder {
        val binding =
            EpisodePopUpCharactersRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopUpViewHolder(binding)
    }

    override fun getItemCount() = popUpMovieList.size

    override fun onBindViewHolder(holder: PopUpViewHolder, position: Int) {
        val currentItem = popUpMovieList[position]
        holder.bind(currentItem)
    }

    fun addPopUpList(list: List<CharacterItem>) {
        popUpMovieList.clear()
        popUpMovieList.addAll(list)
        notifyDataSetChanged()
    }


}