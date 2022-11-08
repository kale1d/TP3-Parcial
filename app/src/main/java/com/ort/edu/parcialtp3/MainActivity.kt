package com.ort.edu.parcialtp3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                if(destination.id == R.id.character_details){
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                    drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
//                    NavigationUI.navigateUp(navHostFragment.navController, drawerLayout)
                }
            }
        }
    }


    private suspend fun logOut() {

            dataStore.edit { preferences ->
                preferences.clear()
            }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()

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
                    R.id.favoritesFragment -> navController.navigate(R.id.favoritesFragment)
                    R.id.home -> navController.navigate(R.id.homeFragment)
                    R.id.settingsFragment -> replaceFragment(SettingsActivity.SettingsFragment())
                }
                onSupportNavigateUp()
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
            supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        }
        return false
    }
}

private fun Intent.setFlags(b: Boolean) {

}

private fun MutablePreferences.remove(key: String) {

}

