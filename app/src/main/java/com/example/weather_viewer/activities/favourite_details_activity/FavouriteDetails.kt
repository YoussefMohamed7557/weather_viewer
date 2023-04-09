package com.example.weather_viewer.activities.favourite_details_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_viewer.R
import com.example.weather_viewer.activities.main_activity.MainActivity
import com.example.weather_viewer.data_classes.favourite.Daily
import com.example.weather_viewer.data_classes.favourite.Hourly
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.databinding.ActivityFavouriteDetailsBinding

class FavouriteDetails : AppCompatActivity() {
    private lateinit var binding : ActivityFavouriteDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var adapter: HourlyAdabter
    private lateinit var dailyadapter: DailyAdapter
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_details)
        detailsViewModel=  ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[DetailsViewModel::class.java]
        setCurrentBtnClickListener()
        setTodayBtnClickListener()
        setThisWeekBtnClickListener()
        adapter= HourlyAdabter(detailsViewModel)
        dailyadapter= DailyAdapter(detailsViewModel)
        val intent: Intent = intent
        val lat=intent.getStringExtra("lat")
        val lon=intent.getStringExtra("lon")
        Log.d("TAG","${intent.getStringExtra("lat")}")
        Log.d("TAG","${intent.getStringExtra("lon")}")
        detailsViewModel.getOneFav(lat!!, lon!!)
        detailsViewModel.oneFav.observe(this) {
            if (it != null)
                initUI(it)
            loadHourly(it.hourly)
            loadDaily(it.daily)
            detailsViewModel.loadImage(binding.currentModeImg, it.current.weather[0].icon)
        }
        binding.currentTempUnic.text=detailsViewModel.getUnites(MainActivity.units)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUI(it: FavData) {
        Log.d("TAG", "icon ${it.current.weather[0].icon}")
        binding.currentCity.text = it.timezone
        binding.description.text = it.current.weather[0].description
        binding.currentTemp.text = it.current.temp.toString()
        binding.humidityPercentage.text = it.current.humidity.toString()
        binding.windSpeedPercentage.text = it.current.wind_speed.toString()
        binding.pressurePercentage.text = it.current.pressure.toString()
        binding.cloudsPercentage.text = it.current.clouds.toString()
        binding.currentTime.text = detailsViewModel.fermatTime(it.current.dt)
        binding.currentDate.text = detailsViewModel.formatDate(it.current.dt)
        binding.sunrisetime.text = detailsViewModel.fermatTime(it.current.sunrise)
        binding.sunsetdate.text = detailsViewModel.fermatTime(it.current.sunset)
    }
    fun loadHourly(hourlyList: List<Hourly>){
        val lay : RecyclerView.LayoutManager= LinearLayoutManager(this)
        binding.hourlyList.layoutManager=lay
        adapter.models= hourlyList
        binding.hourlyList.adapter=adapter
    }
    fun loadDaily(dailyList: List<Daily>){
        val lay : RecyclerView.LayoutManager= LinearLayoutManager(this)
        binding.dialyList.layoutManager=lay
        dailyadapter.models=dailyList
        binding.dialyList.adapter=dailyadapter
    }
    fun setCurrentBtnClickListener(){
        binding.cureentCard.setOnClickListener {
            binding.cureentCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.purple_500
            )
            binding.daialyCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.white
            )
            binding.hoourlyCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.white
            )
            binding.currentList.visibility = View.VISIBLE
            binding.hourlyList.visibility = View.GONE
            binding.dialyList.visibility = View.GONE
            binding.currentext.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.white
                )
            )
            binding.dailyText.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.purple_700
                )
            )
            binding.hourlyText.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.purple_700
                )
            )
        }
    }
    fun setTodayBtnClickListener(){
        binding.hoourlyCard.setOnClickListener {
            binding.hoourlyCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.purple_500
            )
            binding.cureentCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.white
            )
            binding.daialyCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.white
            )
            binding.hourlyList.visibility = View.VISIBLE
            binding.dialyList.visibility = View.GONE
            binding.currentList.visibility = View.GONE
            binding.hourlyText.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.white
                )
            )
            binding.currentext.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.purple_700
                )
            )
            binding.dailyText.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.purple_700
                )
            )
        }

    }
    fun setThisWeekBtnClickListener(){
        binding.daialyCard.setOnClickListener {
            binding.daialyCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.purple_500
            )
            binding.hoourlyCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.white
            )
            binding.cureentCard.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.white
            )
            binding.dialyList.visibility = View.VISIBLE
            binding.hourlyList.visibility = View.GONE
            binding.currentList.visibility = View.GONE
            binding.dailyText.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.white
                )
            )
            binding.hourlyText.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.purple_700
                )
            )
            binding.currentext.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.purple_700
                )
            )
        }
    }

}