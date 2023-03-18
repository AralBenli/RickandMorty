package com.example.rickandmorty.ui.pages.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.main.MainActivity


class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun getViewBinding(): FragmentEpisodesBinding = FragmentEpisodesBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(true)
        (requireActivity() as MainActivity).backNavigation(true)

    }
}