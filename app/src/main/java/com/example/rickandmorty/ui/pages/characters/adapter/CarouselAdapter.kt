package com.example.rickandmorty.ui.pages.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.CarouselRowItemBinding
import com.example.rickandmorty.models.CarouselData
import com.example.rickandmorty.utils.Extensions.setImage

class CarouselAdapter : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    private val carouselList: ArrayList<CarouselData> = arrayListOf()
    var clickCarousel: ((id: Int) -> Unit)? = null

    inner class CarouselViewHolder(private val binding: CarouselRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carouselList: CarouselData) {
            binding.carouselImage.setImage(carouselList.image)
            binding.carouselName.text = carouselList.name

            itemView.setOnClickListener {
                clickCarousel?.invoke(carouselList.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding =
            CarouselRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return carouselList.size
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val currentItem = carouselList[position]
        holder.bind(currentItem)
    }

    fun addCarouselList(list: List<CarouselData>) {
        carouselList.clear()
        carouselList.addAll(list)
        notifyDataSetChanged()
    }

}