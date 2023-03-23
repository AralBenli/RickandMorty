package com.example.rickandmorty.ui.pages.main

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = findViewById(R.id.navigationBottom)
        bottomNavigationView = binding.navigationBottom
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.navigationBottom.setupWithNavController(navHostFragment.navController)
        bottomNavigationView.itemIconTintList = null



        binding.searchIcon.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.searchFragment)
        }

        binding.backIcon.setOnClickListener {
            navHostFragment.findNavController().popBackStack()
        }

        initViews()
    }


    fun bottomNavigation(visibility: Boolean) {
        if (visibility) {
            binding.navigationBottom.visibility = View.VISIBLE
        } else {
            binding.navigationBottom.visibility = View.INVISIBLE
        }
    }

    fun searchIcon(visibility: Boolean) {
        if (visibility) {
            binding.searchIcon.visibility = View.VISIBLE
        } else {
            binding.searchIcon.visibility = View.INVISIBLE

        }
    }

    fun backNavigation(visibility: Boolean) {
        if (visibility) {
            binding.backIcon.visibility = View.VISIBLE
        } else {
            binding.backIcon.visibility = View.INVISIBLE
        }
    }

    fun actionBar(visibility: Boolean){
        if (visibility) {
            binding.customActionBar.visibility = View.VISIBLE
        } else {
            binding.customActionBar.visibility = View.INVISIBLE
        }
    }

    private fun initViews() {

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.characters -> {
                    navHostFragment.findNavController().navigate(R.id.charactersFragment)
                    item.isChecked
                }
                R.id.locations -> {
                    navHostFragment.findNavController().navigate(R.id.locationFragment)
                }
                R.id.episodes -> {
                    navHostFragment.findNavController().navigate(R.id.episodesFragment)
                }
            }
            true
        }
    }
}



