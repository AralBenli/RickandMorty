package com.example.rickandmorty.ui.pages.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSplashBinding
import com.example.rickandmorty.ui.base.BaseFragment
import com.example.rickandmorty.ui.pages.main.MainActivity


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getViewBinding(): FragmentSplashBinding = FragmentSplashBinding.inflate(layoutInflater)


    override fun initViews() {
        (requireActivity() as MainActivity).bottomNavigation(false)
        (requireActivity() as MainActivity).searchIcon(false)
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
        splash()
    }

    private fun splash(){
        Handler(Looper.getMainLooper()).postDelayed({
            view?.post {
                findNavController().navigate(R.id.toHome)
            }
        }, 3000)
    }
}






