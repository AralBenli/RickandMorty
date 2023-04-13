package com.example.rickandmorty.ui.pages.intro_slider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.SliderItemBinding

/**
 * Created by AralBenli on 13.04.2023.
 */
class SliderAdapter constructor(
    val context: Context,
    private val listSlider: List<SliderItem>
) : RecyclerView.Adapter<SliderAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: SliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(imageId: Int,title:String,subtitle:String) {
            binding.introTitle.text = title
            binding.introDescription.text = subtitle
            binding.introImg.setImageResource(imageId)
        }
    }

    override fun getItemCount(): Int = listSlider.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = SliderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.setData(listSlider[position].imageId,listSlider[position].title,listSlider[position].description)
    }
}