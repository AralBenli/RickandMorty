package com.example.rickandmorty.ui.pages.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.CharacterRowItemBinding
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.utils.Extensions.setImageUrl

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val characterList: ArrayList<CharacterItem> = arrayListOf()
    var clickCharacter: ((item: CharacterItem) -> Unit)? = null


    inner class CharacterViewHolder(private val binding: CharacterRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterList: CharacterItem) {
            with(binding) {
                charNameTxt.text = characterList.name
                val url = characterList.image
                url?.let { charImageView.setImageUrl(it) }
            }
            itemView.setOnClickListener {
                clickCharacter?.invoke(characterList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val binding =
            CharacterRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentItem = characterList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount() = characterList.size

    fun addCharacterList(list: List<CharacterItem>?) {
        characterList.clear()
        list?.let { characterList.addAll(it) }
        notifyDataSetChanged()
    }
}