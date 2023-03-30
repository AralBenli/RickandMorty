package com.example.rickandmorty.ui.pages.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.utils.SharedPreferencesManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        loadTheme()
        initViews()
        navigation()
    }

    fun navigation() {
        binding.searchIcon.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.searchFragment)
        }

        binding.backIcon.setOnClickListener {
            navHostFragment.findNavController().popBackStack()
        }

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

    fun actionBar(visibility: Boolean) {
        if (visibility) {
            binding.customActionBar.visibility = View.VISIBLE
        } else {
            binding.customActionBar.visibility = View.INVISIBLE
        }
    }

    private fun initViews() {

        /** Bottom navigation setup and removing icon's default color */

        bottomNavigationView = binding.navigationBottom
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.navigationBottom.setupWithNavController(navHostFragment.navController)
        bottomNavigationView.itemIconTintList = null
        binding.navigation.itemIconTintList = null



        /** Bottom menu navigation */

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.characters -> {
                    navHostFragment.findNavController().navigate(R.id.charactersFragment)
                }
                R.id.locations -> {
                    navHostFragment.findNavController().navigate(R.id.locationFragment)
                }
                R.id.episodes -> {
                    navHostFragment.findNavController().navigate(R.id.episodesFragment)
                }
                R.id.settings -> {
                    binding.drawerLayout.openDrawer(GravityCompat.END)
                }
            }

            true
        }


        /** Drawer menu  navigation */

        binding.navigation.setNavigationItemSelectedListener { drawerItem ->

            when (drawerItem.itemId) {
                R.id.darkTheme -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    SharedPreferencesManager.putBoolean(applicationContext, "nightMode", true)
                    binding.drawerLayout.closeDrawer(GravityCompat.END)
                }
                R.id.lightTheme -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    SharedPreferencesManager.putBoolean(applicationContext, "nightMode", false)
                    binding.drawerLayout.closeDrawer(GravityCompat.END)
                }
                R.id.changeLanguage -> {

                }
                else -> {}
            }
            false
        }
    }

    /** Last selected theme loading when reopening app */
    private fun loadTheme() {
        if (SharedPreferencesManager.getBoolean(applicationContext, "nightMode", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}



