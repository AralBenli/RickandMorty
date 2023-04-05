package com.example.rickandmorty.ui.pages.characters.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.CharacterLinearRowItemBinding
import com.example.rickandmorty.databinding.CharacterRowItemBinding
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.utils.Extensions.setImageUrl


class CharacterAdapter() :
    PagingDataAdapter<CharacterItem, RecyclerView.ViewHolder>(CharDiffUtil) {


    var clickCharacter: ((item: CharacterItem) -> Unit)? = null
    private var context: Context? = null

    companion object {
        private const val GRID_LAYOUT = 1
        private const val LINEARLAYOUT = 2
    }

    var viewtype = 1

    inner class CharacterGridViewHolder(private val binding: CharacterRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(characterList: CharacterItem ) {
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

    inner class CharacterLinearViewHolder(private val binding: CharacterLinearRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterList: CharacterItem) {
            with(binding) {
                linearName.text = characterList.name
                val url = characterList.image
                linearStatus.text = characterList.status
                url?.let { linearImage.setImageUrl(it) }
                when (characterList.status) {
                    "Alive" -> {
                        linearStatus.setTextColor(Color.parseColor("#00FF00"))
                    }
                    "Dead" -> {
                        linearStatus.setTextColor(Color.parseColor("#F00000"))
                    }
                    "unknown" -> {
                        linearStatus.setTextColor(Color.parseColor("#000000"))
                    }
                }
                linearGender.text = characterList.gender


                itemView.setOnClickListener {
                    clickCharacter?.invoke(characterList)
                }
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        when (holder) {
            is CharacterGridViewHolder -> {
                holder.bind(currentItem!!)

            }
            is CharacterLinearViewHolder -> {
                holder.bind(currentItem!!)

               /* holder.itemView.animation = AnimationUtils.loadAnimation(
                    holder.itemView.context,
                    R.anim.up_anim)*/

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewtype) {
            1 -> {
                CharacterGridViewHolder(
                    CharacterRowItemBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }
            2 -> {
                CharacterLinearViewHolder(
                    CharacterLinearRowItemBinding.inflate(
                        LayoutInflater.from(
                            context
                        ), parent, false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            (viewtype == 1) -> GRID_LAYOUT
            else -> {
                LINEARLAYOUT
            }
        }
    }

    object CharDiffUtil : DiffUtil.ItemCallback<CharacterItem>() {
        override fun areItemsTheSame(oldItem: CharacterItem, newItem: CharacterItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterItem, newItem: CharacterItem) =
            oldItem == newItem
    }


}

