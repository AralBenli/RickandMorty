package com.example.rickandmorty.ui.pages.main


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.chuckerteam.chucker.api.Chucker
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.utils.SharedPreferencesManager
import com.example.rickandmorty.utils.openDeepLink
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        super.onCreate(savedInstanceState)
        baseUrl = getBaseUrl()
        Toast.makeText(this, baseUrl, Toast.LENGTH_SHORT).show()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTheme()
        initViews()
        navigation()
        pickTheme()
        openSettings()
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
            binding.customActionBar.visibility = View.GONE
        }
    }

    fun settings(visibility: Boolean) {
        if (visibility) {
            binding.settings.visibility = View.VISIBLE
        } else {
            binding.settings.visibility = View.INVISIBLE
        }
    }

    private fun initViews() {

        //region BOTTOM NAVIGATION REGION
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
                R.id.favorites -> {
                    navHostFragment.findNavController().navigate(R.id.favoriteFragment)
                }
            }

            true
        }
        //endregion

        //region DRAWER MENU NAVIGATION
        /** Drawer menu  navigation */

        binding.navigation.setNavigationItemSelectedListener { drawerItem ->

            when (drawerItem.itemId) {
                R.id.characters -> {
                    navHostFragment.findNavController().navigate(R.id.charactersFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)

                }
                R.id.locations -> {
                    navHostFragment.findNavController().navigate(R.id.locationFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)


                }
                R.id.episodes -> {
                    navHostFragment.findNavController().navigate(R.id.episodesFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.chucker -> {
                    val intent = Chucker.getLaunchIntent(this@MainActivity)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.favorites -> {
                    navHostFragment.findNavController().navigate(R.id.favoriteFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)


                }
                R.id.deepLinkButton -> {
                    val deepLinkUrl = "moviedatabase://"
                    val githubLink = "https://github.com/AralBenli/MovieAPP"
                    openDeepLink(deepLinkUrl, githubLink)
                }
                R.id.darkMode -> {
                    if (binding.themeCheckBox.isChecked) {
                        binding.themeCheckBox.isChecked = false
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        SharedPreferencesManager.putBoolean(applicationContext, "nightMode", false)
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                }
                R.id.lightMode -> {
                    if (!binding.themeCheckBox.isChecked) {
                        binding.themeCheckBox.isChecked = true
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        SharedPreferencesManager.putBoolean(applicationContext, "nightMode", true)
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }

                }
                else -> {}
            }
            false
        }
        //endregion

    }
    //region THEME FUNCTIONS REGION
    /** Last selected theme loading when reopening app */
    private fun loadTheme() {
        if (SharedPreferencesManager.getBoolean(applicationContext, "nightMode", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.themeCheckBox.isChecked = false
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.themeCheckBox.isChecked = true

        }
    }

    /** checkbox listener for the theme selection -- dark theme -- light theme ---
     * and saving state with shared preferences and loading it when application opens again */
    private fun pickTheme() {
        binding.themeCheckBox.setOnCheckedChangeListener { _, isTrue ->
            if (isTrue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SharedPreferencesManager.putBoolean(applicationContext, "nightMode", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SharedPreferencesManager.putBoolean(applicationContext, "nightMode", true)
            }
        }
    }
    //endregion

    private fun openSettings() {
        binding.settings.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private external fun getBaseUrl(): String

    companion object {
        var baseUrl: String = ""

        init {
            System.loadLibrary("ndk")
        }
    }
}



