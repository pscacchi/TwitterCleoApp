package ar.scacchipa.twittercloneapp.component

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.ActivityMainLayoutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainCloneActivity: AppCompatActivity() {

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

        this.window?.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                    View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            statusBarColor = ContextCompat.getColor(this@MainCloneActivity, R.color.white)
            navigationBarColor = ContextCompat.getColor(this@MainCloneActivity, R.color.on_primary)
        }
        this.supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            elevation = 0f
        }
    }
}
