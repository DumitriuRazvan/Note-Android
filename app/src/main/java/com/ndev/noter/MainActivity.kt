package com.ndev.noter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navigationView:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        /// Declaram toate fragmentele intre care vor naviga
        val homeFragment = HomeFragment()
        val notesFragment = NotesFragment()
        val networkFragment = NetworkFragment()
        val settingsFragment = SettingsFragment()


        /// Setam fragmentul curent ca fiind homeFragment
        setCurrentFragment(homeFragment)


        /// Cand dam click pe navigatie o sa faca replace la fragmentul corespunzator
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home-> setCurrentFragment(homeFragment)
                R.id.nav_notes -> setCurrentFragment(notesFragment)
                R.id.nav_network -> setCurrentFragment(networkFragment)
                R.id.nav_settings -> setCurrentFragment(settingsFragment)
            }
            true
        }


    }

    // Functie ajutatoare pentru a inlocui fragmentul
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.body_container,fragment)
            commit()
        }
}