package com.ort.edu.parcialtp3

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ort.edu.parcialtp3.fragments.favoritos
import com.ort.edu.parcialtp3.fragments.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

val Context.dataStore by preferencesDataStore(name = "USER_PREFERENCES_NAME")

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment


        setUpDrawerLayout()



        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            var navViewComponent = findViewById<NavigationView>(R.id.nav_view)
            if (destination.id == R.id.loginFragment) {
                navViewComponent.visibility = View.GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
            } else {
                navViewComponent.visibility = View.VISIBLE
                supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)
                drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)

                if(destination.id == R.id.homeFragment){
                    arguments?.getString("usuario").let { UserSession.userName = it }
                }
            }
        }
    }

    private suspend fun logOut() {

            dataStore.edit { preferences ->
                preferences.clear()
            }
            navHostFragment.navController.popBackStack()
//
    }

    private fun setUpDrawerLayout() {
        val navController = navHostFragment.navController

        navigationView.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)

            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.logout -> lifecycleScope.launch(Dispatchers.IO) { withContext(Dispatchers.Main) { logOut() } }
                    R.id.favoritos4 -> replaceFragment(favoritos())
                    R.id.home -> replaceFragment(HomeFragment())
                    R.id.settings3 -> replaceFragment(SettingsActivity.SettingsFragment())
                }
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return false
    }
}

private fun MutablePreferences.remove(key: String) {

}

