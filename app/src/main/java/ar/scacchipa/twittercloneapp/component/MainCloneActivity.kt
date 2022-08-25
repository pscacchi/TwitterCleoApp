package ar.scacchipa.twittercloneapp.component

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.ActivityMainLayoutBinding
import ar.scacchipa.twittercloneapp.databinding.LayoutActionbarHomeBinding
import ar.scacchipa.twittercloneapp.databinding.LayoutActionbarSearchBinding
import ar.scacchipa.twittercloneapp.viewmodel.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainCloneActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainLayoutBinding
    private val mainViewModel: MainActivityViewModel by viewModel()

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

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            when (navDestination.id) {
                R.id.fragment_home_nav -> setHomeActionBar()
                R.id.fragment_search_nav,
                R.id.fragment_bell_nav,
                R.id.fragment_message_nav -> setSearchActionBar()
            }
        }
        binding.drawerView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item_log_out -> mainViewModel.onLogOut()
            }
            true
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)

        this.supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            elevation = 0f
        }

        mainViewModel.credentialStatus.observe(this) { status ->
            if (status.not()) {
                val intent = Intent(
                    this,
                    LoginCloneActivity::class.java
                )
                intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity( intent )
            }
        }
    }

    private fun openDrawer() {
        binding.root.openDrawer(GravityCompat.START)
    }

    private fun setHomeActionBar() {
        this.supportActionBar?.let { actionBar ->
            actionBar.setCustomView(R.layout.layout_actionbar_home)
            LayoutActionbarHomeBinding
                .bind(actionBar.customView)
                .profileImageView.setOnClickListener { view ->
                    when (view.id) {
                        R.id.profileImageView -> this.openDrawer()
                    }
                }
        }
    }
    private fun setSearchActionBar() {
        this.supportActionBar?.let { actionBar ->
            actionBar.setCustomView(R.layout.layout_actionbar_search)
            LayoutActionbarSearchBinding
                .bind(actionBar.customView)
                .profileImageView
                .setOnClickListener { view ->
                    when (view.id) {
                        R.id.profileImageView -> this.openDrawer()
                    }
                }
        }
    }
}
