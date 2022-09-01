package ar.scacchipa.twittercloneapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.ActivityMainLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainLayoutBinding.inflate(layoutInflater)
        setContentView( binding.root )

        binding.mainBottomNavigationView.itemIconTintList = null
        binding.mainBottomNavigationView.itemActiveIndicatorColor = null

        val bottomNavView: BottomNavigationView = binding.mainBottomNavigationView

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_nav_graph)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_home_nav,
                R.id.fragment_search_nav,
                R.id.fragment_bell_nav,
                R.id.fragment_message_nav
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)

        this.supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            elevation = 0f
        }
    }
}
