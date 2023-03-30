package com.example.rickandmorty.ui.pages.locations.adapter


import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.LocationRowItemBinding
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.response.LocationItem

class LocationAdapter : PagingDataAdapter<LocationItem, LocationAdapter.LocationViewHolder>(LocationDiffUtil) {

    private val locationList: ArrayList<LocationItem> = arrayListOf()
    var clickLocation: ((item: LocationItem) -> Unit)? = null


    inner class LocationViewHolder(private val binding: LocationRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationList: LocationItem) {
            with(binding) {
                locationTypeTxt.text = locationList.type
                locationDimensionTxt.text = locationList.dimension
                locationWorldTxt.text = locationList.name
                val staticText = SpannableString("Click to see residents of planet")
                staticText.setSpan(UnderlineSpan(), 0, staticText.length, 0)
                locationClickToSeeStaticTxt.text = staticText

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

    object LocationDiffUtil : DiffUtil.ItemCallback<LocationItem>() {
        override fun areItemsTheSame(oldItem: LocationItem, newItem: LocationItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LocationItem, newItem: LocationItem) =
            oldItem == newItem
    }
}