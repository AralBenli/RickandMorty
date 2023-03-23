package com.example.rickandmorty.ui.pages.locations.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.LocationRowItemBinding
import com.example.rickandmorty.response.LocationItem

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private val locationList: ArrayList<LocationItem> = arrayListOf()
    var clickLocation: ((item: LocationItem) -> Unit)? = null


    inner class LocationViewHolder(private val binding: LocationRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationList: LocationItem) {
            with(binding) {
                locationTypeTxt.text = locationList.type
                locationDimensionTxt.text = locationList.dimension
                locationWorldTxt.text = locationList.name
            }
            itemView.setOnClickListener {
                clickLocation?.invoke(locationList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {

        val binding =
            LocationRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)

    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val currentItem = locationList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount() = locationList.size

    fun addLocationList(list: List<LocationItem>) {
        locationList.clear()
        locationList.addAll(list)
        notifyDataSetChanged()
    }
}