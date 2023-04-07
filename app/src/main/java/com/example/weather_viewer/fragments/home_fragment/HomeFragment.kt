package com.example.weather_viewer.fragments.home_fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_viewer.R
import com.example.weather_viewer.data_classes.one_call.Daily
import com.example.weather_viewer.data_classes.one_call.Hourly
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.databinding.FragmentHomeBinding
import com.example.weather_viewer.main_activity.MainActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HourlyAdabter
    private lateinit var dailyadapter: DailyAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)) [HomeViewModel::class.java]
        reload()
        adapter = HourlyAdabter(homeViewModel)
        dailyadapter = DailyAdapter(homeViewModel)
        setCurrentBtnClickListener()
        setTodayBtnClickListener()
        setThisWeekBtnClickListener()
        
        binding.reload.setOnClickListener {
            Log.d("TAG", "clicked")
            reload()
        }
        
        homeViewModel.getRoomData().observe(viewLifecycleOwner) {
            if (it.size == 1) {
                initUI(it[0])
                loadHourly(it[0].hourly)
                loadDaily(it[0].daily)
                homeViewModel.loadImage(binding.currentModeImg, it[0].current.weather[0].icon)
            }
        }
         
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun reload() {
        Log.d("TAG", "reload")

        lateinit var settingModel: SettingModel
        homeViewModel.getSetting().observe(viewLifecycleOwner) { it ->
            Log.d("TAG", "it.        lang" + it.lang)
            settingModel = it
            MainActivity.units = settingModel.units
            binding.currentTempUnic.text = homeViewModel.getUnites(settingModel.units)


            if (settingModel.location == "gps") {
                homeViewModel.gettingLocation(this.requireContext(), requireActivity()).observe(viewLifecycleOwner) {
                    val location = it
                    Log.d("TAG", "it.location" + location.latitude)
                    homeViewModel.loadOnlineData(
                        location.latitude.toString(),
                        location.longitude.toString(),
                        settingModel.lang,
                        settingModel.units,
                        requireActivity()
                    )
                }

            } else {
                homeViewModel.getLocationSettnig().observe(viewLifecycleOwner) {
                    Log.d("TAG", "it.latitude" + it.latitude)
                    Log.d("TAG", "it.latitude" + it.longitude)

                    homeViewModel.loadOnlineData(
                        it.latitude.toString(),
                        it.longitude.toString(),
                        settingModel.lang,
                        settingModel.units,
                        requireActivity()
                    )
                }

            }
        }
    }

    private fun loadDaily(dailyList: List<Daily>) {
        val lay: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.dialyList.layoutManager = lay
        dailyadapter.models = dailyList
        binding.dialyList.adapter = dailyadapter
    }

    private fun loadHourly(hourlyList: List<Hourly>) {
        val lay: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.hourlyList.layoutManager = lay
        adapter.models = hourlyList
        binding.hourlyList.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUI(it: AllData) {
        Log.d("TAG", "icon ${it.current.weather[0].icon}")
        binding.currentCity.text = it.timezone
        binding.description.text = it.current.weather[0].description
        binding.currentTemp.text = it.current.temp.toString()
        binding.humidityPercentage.text = it.current.humidity.toString()
        binding.windSpeedPercentage.text = it.current.wind_speed.toString()
        binding.pressurePercentage.text = it.current.pressure.toString()
        binding.cloudsPercentage.text = it.current.clouds.toString()
        binding.currentTime.text = homeViewModel.formateTime(it.current.dt)
        binding.currentDate.text = homeViewModel.formateDate(it.current.dt)
        binding.sunrisetime.text = homeViewModel.formateTime(it.current.sunrise)
        binding.sunsetdate.text = homeViewModel.formateTime(it.current.sunset)
    }

    fun setCurrentBtnClickListener(){
        binding.cureentCard.setOnClickListener {
            binding.cureentCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.purple_500
            )
            binding.daialyCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.white
            )
            binding.hoourlyCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.white
            )
            binding.currentList.visibility = View.VISIBLE
            binding.hourlyList.visibility = View.GONE
            binding.dialyList.visibility = View.GONE
            binding.currentext.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.white
                )
            )
            binding.dailyText.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.purple_700
                )
            )
            binding.hourlyText.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.purple_700
                )
            )
        }
    }
    fun setTodayBtnClickListener(){
        binding.hoourlyCard.setOnClickListener {
            binding.hoourlyCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.purple_500
            )
            binding.cureentCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.white
            )
            binding.daialyCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.white
            )
            binding.hourlyList.visibility = View.VISIBLE
            binding.dialyList.visibility = View.GONE
            binding.currentList.visibility = View.GONE
            binding.hourlyText.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.white
                )
            )
            binding.currentext.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.purple_700
                )
            )
            binding.dailyText.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.purple_700
                )
            )
        }

    }
    fun setThisWeekBtnClickListener(){
        binding.daialyCard.setOnClickListener {
            binding.daialyCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.purple_500
            )
            binding.hoourlyCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.white
            )
            binding.cureentCard.backgroundTintList = ContextCompat.getColorStateList(
                requireActivity(),
                R.color.white
            )
            binding.dialyList.visibility = View.VISIBLE
            binding.hourlyList.visibility = View.GONE
            binding.currentList.visibility = View.GONE
            binding.dailyText.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.white
                )
            )
            binding.hourlyText.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.purple_700
                )
            )
            binding.currentext.setTextColor(
                ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.purple_700
                )
            )
        }
    }


}



