package com.ort.edu.parcialtp3

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ort.edu.parcialtp3.UserSession.userName

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment : NavHostFragment
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navigationView : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        findViewById<NavigationView>(R.id.nav_view)
//            .setupWithNavController(navController)

        setUpDrawerLayout()

        navHostFragment.navController.addOnDestinationChangedListener{controller, destination, arguments ->
            var navViewComponent = findViewById<NavigationView>(R.id.nav_view)
            if (destination.id == R.id.loginFragment){
                navViewComponent.visibility = View.GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
            }else{
                navViewComponent.visibility = View.VISIBLE
                supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)

                if(destination.id == R.id.favoritos4){
                    arguments?.getString("username")?.let{
                        userName = it
                        UserSession.userName = it
                    }
                }
            }


        }
    }


    fun setUpDrawerLayout(){
        val navController = navHostFragment.navController

        navigationView.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return false
    }


//    fun setDrawerLocked(){
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//    }

//    fun setDrawerUnlocked(){
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//    }
}