package com.example.weather_viewer.fragments.setting_fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Contacts.Settings.getSetting
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.weather_viewer.R
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.databinding.FragmentSettingBinding
import com.example.weather_viewer.main_activity.MainActivity
import com.example.weather_viewer.map_activity.MapActivity


class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    lateinit var settingViewModel: SettingViewModel
    var langS:String="en"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        settingViewModel =  ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(
            requireActivity().application
        ))[SettingViewModel::class.java]


        binding.saveButton.setOnClickListener{
            savedata()
        }

        binding.addLocationRadioButton.setOnClickListener{
            val intent = Intent(activity, MapActivity::class.java)
            intent.putExtra("type","setting")
            startActivity(intent)
        }
        getSetting()

        return binding.root
    }
    fun getSetting() {
        settingViewModel.getSetting().observe(viewLifecycleOwner) {
            val units: String = it.units
            val lang: String = it.lang
            val location: String = it.location
            Log.d("TAG", it.location)
            if (units == "standard") {
                binding.unitsRadioGroup.check(R.id.standardRadioButton)
            } else if (units == "imperial") {
                binding.unitsRadioGroup.check(R.id.imperialRadioButton)
            } else {
                binding.unitsRadioGroup.check(R.id.metricRadioButton)
            }

            if (lang == "en") {
                binding.langRadioGroup.check(R.id.EnglishRadioButton)
            } else {
                binding.langRadioGroup.check(R.id.ArabicRadioButton)
            }

            if (location == "add") {
                binding.locationRadioGroup.check(R.id.addLocationRadioButton)
            } else {
                binding.locationRadioGroup.check(R.id.gpsRadioButton)
            }
        }
    }
    private fun savedata() {
        val units: String = when (binding.unitsRadioGroup.checkedRadioButtonId) {
            R.id.standardRadioButton -> {
                "standard"
            }
            R.id.imperialRadioButton -> {
                "imperial"
            }
            else -> {
                "metric"
            }
        }


        val lang: String = if (binding.langRadioGroup.checkedRadioButtonId == R.id.EnglishRadioButton) {
            "en"
        } else {
            "ar"
        }
        langS=lang



        val location: String = if (binding.locationRadioGroup.checkedRadioButtonId == R.id.gpsRadioButton) {
            "gps"
        } else {
            "add"
        }

        settingViewModel.setSetting(SettingModel(units, lang, location))
        settingViewModel.setLocale(requireActivity(),langS)
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

}