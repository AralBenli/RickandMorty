package com.example.rickandmorty.ui.pages.intro_slider

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSliderBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.SharedPreferencesManager
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SliderFragment : BaseFragment<FragmentSliderBinding>() {


    var sliderAdapter: SliderAdapter? = null
    var position = 0


    override fun getViewBinding(): FragmentSliderBinding =
        FragmentSliderBinding.inflate(layoutInflater)


    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)


        val sliderItems: MutableList<SliderItem> = ArrayList()

        sliderItems.add(
            SliderItem(
                "Welcome to Rick And Morty Application",
                "If you re a rick and morty fan , this app suits for you.",
                R.drawable.morty_smith
            )
        )
        sliderItems.add(
            SliderItem(
                "What you can do in this app?",
                "You can search characters also you can look episodes and locations. ",
                R.drawable.rick_sanchez
            )
        )
        sliderItems.add(
            SliderItem(
                "What about favorite!",
                "You can add them to favorites and you always remember it.",
                R.drawable.favorite_slider
            )
        )

        sliderAdapter = SliderAdapter(requireContext(), sliderItems)
        binding.screenViewpager.adapter = sliderAdapter
        TabLayoutMediator(binding.tabLayoutIndicator, binding.screenViewpager)
        { _, _ -> }.attach()


        // next button click Listener
        binding.btnNext.setOnClickListener {
            position = binding.screenViewpager.currentItem
            Log.d("position", position.toString())
            if (position < sliderItems.size - 1) {
                position++
                binding.screenViewpager.currentItem = position

                if (position == sliderItems.size - 1)
                    setLastScreen()
                else
                    unSetLastScreen()
            } else if (position == sliderItems.size - 1) { // when we reach to the last screen

                skipIntro()
                findNavController().navigate(R.id.sliderToHome)
            }
        }
        binding.btnSkip.setOnClickListener {
            skipIntro()
            findNavController().navigate(R.id.sliderToHome)
        }
    }
    private fun skipIntro() {
        SharedPreferencesManager.putBoolean(requireContext(), "skipped", true)
    }

    private fun setLastScreen() {
        binding.txtNext.text = getString(R.string.done)
    }

    private fun unSetLastScreen() {
        binding.txtNext.text = getString(R.string.next)
    }
}
