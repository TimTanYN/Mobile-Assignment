package com.example.tracking

import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

abstract class BaseNavigationActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    fun initToolbarAndNavigation() {
        val drawer_layout: DrawerLayout = findViewById(R.id.drawer)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val nav_view: NavigationView = findViewById(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        val drawer_layout: DrawerLayout = findViewById(R.id.drawer)
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: android.view.MenuItem): Boolean {
        val drawer_layout: DrawerLayout = findViewById(R.id.drawer)
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // Handle the home action
            }
            R.id.nav_gallery -> {
                val intent = Intent(this, DiseaseTabs::class.java)
                startActivity(intent)
            }
            R.id.nav_slideshow -> {
                val intent = Intent(this, PharmacyTabs::class.java)
                startActivity(intent)
            }
            R.id.food_pyramid -> {
                val intent = Intent(this, FoodPyramidTab::class.java)
                startActivity(intent)
            }
            R.id.diet -> {
                val intent = Intent(this, DietPlanTab::class.java)
                startActivity(intent)
            }
            R.id.Survey -> {
                val intent = Intent(this, RiskSurvey::class.java)
                startActivity(intent)
            }
            R.id.Appointment -> {
                val intent = Intent(this, Appointment_Main::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun Create() {
        // BottomNavigationView setup
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navigateToActivity(MapActivity::class.java)
                    true
                }
                R.id.Search -> {
                    navigateToActivity(Cart::class.java)
                    true
                }
                R.id.person -> {
                    navigateToActivity(Cart::class.java)
                    true
                }
                R.id.cart -> {
                    navigateToActivity(Cart::class.java)
                    true
                }
                // ... add more cases for other menu items
                else -> false
            }
        }

        // FloatingActionButton setup
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            navigateToActivity(Cart::class.java)
        }
    }

    protected fun navigateToActivity(activityClass: Class<*>) {
        if (this::class.java != activityClass) {
            val intent = Intent(this, activityClass)
            startActivity(intent)
        }}
}

