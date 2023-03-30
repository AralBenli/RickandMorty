package com.example.rickandmorty.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ProgressBarLayoutBinding
import com.example.rickandmorty.ui.pages.characters.adapter.CharacterAdapter


class PagingLoadStateAdapter : LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ProgressBarLayoutBinding.bind(itemView)
        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar2.isVisible = loadState is LoadState.Loading
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.progress_bar_layout, parent, false)
        return LoadStateViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)


}




