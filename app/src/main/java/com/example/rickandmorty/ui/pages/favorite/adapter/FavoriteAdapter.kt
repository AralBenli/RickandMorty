package com.example.rickandmorty.ui.pages.favorite.adapter


import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.CharacterRowItemBinding
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.utils.Extensions.setImageUrl
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.rickandmorty.databinding.CharacterLinearRowItemBinding


class FavoriteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val episodeList: ArrayList<CharacterItem> = arrayListOf()
    var clickEpisode: ((item: CharacterItem) -> Unit)? = null
    private var context: Context? = null
    var viewtype = 1

    fun viewType(type: Int) {
        if (type == 1) {
            viewtype = 1
        }
        if (type == 2) {
            viewtype = 2
        }
    }
    companion object {
        private const val GRID_LAYOUT = 1
        private const val LINEARLAYOUT = 2
    }


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

    inner class FavoriteLinearViewHolder(private val binding: CharacterLinearRowItemBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteList: CharacterItem) {
            with(binding) {
                linearName.text = favoriteList.name
                val url = favoriteList.image
                url?.let { linearImage.setImageUrl(it) }
                linearStatus.text = favoriteList.status
                when (favoriteList.status) {
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
            }
            itemView.setOnClickListener {
                clickEpisode?.invoke(favoriteList)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewtype) {
            1 -> FavoriteViewHolder(
                    CharacterRowItemBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )

            2 ->
                FavoriteLinearViewHolder(
                    CharacterLinearRowItemBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false)
                )

            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = episodeList[position]

        when (holder) {
            is FavoriteViewHolder -> holder.bind(currentItem)
            is FavoriteLinearViewHolder -> holder.bind(currentItem)
        }




    }

    override fun getItemCount() = episodeList.size



    fun submitData(newList: List<CharacterItem>) {
        episodeList.clear()
        episodeList.addAll(newList)
        notifyDataSetChanged()
    }


}