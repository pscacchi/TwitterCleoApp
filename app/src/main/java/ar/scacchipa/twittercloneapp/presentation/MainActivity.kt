package ar.scacchipa.twittercloneapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainLayoutBinding
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainLayoutBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val bottomNavView: BottomNavigationView = binding.mainBottomNavigationView

        bottomNavView.itemIconTintList = null
        bottomNavView.itemActiveIndicatorColor = null

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
                R.id.nav_item_log_out -> viewModel.onLogOut()
            }
            true
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)

        this.supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            elevation = 0f
        }

        viewModel.credentialStatus.observe(this) { status ->
            if (status == false) {
                val intent = Intent(
                    this,
                    LoginActivity::class.java
                )
                intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity( intent )
            }
        }
    }

    private fun setHomeActionBar() {
        this.supportActionBar?.let { actionBar ->
            actionBar.setCustomView(R.layout.layout_actionbar_home)
            LayoutActionbarHomeBinding
                .bind(actionBar.customView)
                .profileImageView
                .setOnClickListener { onClickDrawerItem(it) }
        }
    }

    private fun setSearchActionBar() {
        this.supportActionBar?.let { actionBar ->
            actionBar.setCustomView(R.layout.layout_actionbar_search)
            LayoutActionbarSearchBinding
                .bind(actionBar.customView)
                .profileImageView
                .setOnClickListener { onClickDrawerItem(it) }
        }
    }

    private fun openDrawer() {
        binding.root.openDrawer(GravityCompat.START)
    }
    private fun onClickDrawerItem(view: View) {
        when (view.id) {
            R.id.profileImageView -> this.openDrawer()
        }
    }
}
