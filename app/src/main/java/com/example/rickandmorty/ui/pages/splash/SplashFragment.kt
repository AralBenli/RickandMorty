package com.example.rickandmorty.ui.pages.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSplashBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.main.MainActivity
import com.example.rickandmorty.utils.SharedPreferencesManager


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getViewBinding(): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater)

    override fun onCreateViewBase() {

    }

    override fun initViews() {

        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
        splash()
    }

    private fun splash() {
        Handler(Looper.getMainLooper()).postDelayed({
            view?.post {
                if (SharedPreferencesManager.getBoolean(requireContext(), "skipped", false)) {
                    findNavController().navigate(R.id.toHome)
                } else {
                    findNavController().navigate(R.id.toSlider)
                }
            }
        }, 3000)
    }
}






