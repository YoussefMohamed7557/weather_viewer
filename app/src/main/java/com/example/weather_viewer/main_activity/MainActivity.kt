package com.example.weather_viewer.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.weather_viewer.R
import com.example.weather_viewer.alert_fragment.AlertFragment
import com.example.weather_viewer.databinding.ActivityMainBinding
import com.example.weather_viewer.favourite_fragment.FavouriteFragment
import com.example.weather_viewer.home_fragment.HomeFragment
import com.example.weather_viewer.setting_fragment.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: Fragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeItem_menu -> {
                    fragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragment)
                        .commit()
                }
                R.id.favorite_menu -> {
                    fragment = FavouriteFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment)
                        .commit()
                }
                R.id.alert_menu -> {
                    fragment = AlertFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment)
                        .commit()
                }
                else -> {
                    fragment = SettingFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment)
                        .commit()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    companion object {
        var units: String = "standard"
    }
}