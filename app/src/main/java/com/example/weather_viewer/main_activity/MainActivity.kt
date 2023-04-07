package com.example.weather_viewer.main_activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.weather_viewer.R
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.fragments.alert_fragment.AlertFragment
import com.example.weather_viewer.databinding.ActivityMainBinding
import com.example.weather_viewer.fragments.favourite_fragment.FavouriteFragment
import com.example.weather_viewer.fragments.home_fragment.HomeFragment
import com.example.weather_viewer.fragments.setting_fragment.SettingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: Fragment
    private lateinit var mainViewModel: MainViewModel
    private lateinit var list: List<FavData>
    private lateinit var setting: SettingModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel(application)
        updateFavourite()
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

    @SuppressLint("SuspiciousIndentation")
    private fun updateFavourite() {
        list = mainViewModel.getFavDataNotLiveData()
        mainViewModel.getSettnig().observe(this@MainActivity) {
            setting = it
            CoroutineScope(Dispatchers.IO).launch {
//            mainViewModel.deleteAllFav()
                for (i in list) {
                    mainViewModel.saveFav(
                        i.lat.toString(),
                        i.lon.toString(),
                        setting.lang,
                        setting.units
                    )
                }
            }
        }
    }

    companion object {
        var readFromDatabase: Boolean = false
        var units: String = "standard"
    }
    override fun onDestroy() {
        super.onDestroy()
        readFromDatabase = false
    }
}