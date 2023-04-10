package com.example.weather_viewer.fragments.setting_fragment

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.GeneralRepository
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.helper.GeneralFunctions

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val mApplication: Application = application
    val generalRepository: GeneralRepository = GeneralRepository.getInstance(mApplication)

    private val generalFunctions = GeneralFunctions()

    fun setLocale(activity: Activity, languageCode: String?) {
        generalFunctions.setLocale(activity, languageCode)
    }

    fun getSetting(): LiveData<SettingModel> {
        return generalRepository.getSetting()
    }

    fun setSetting(setttingModel: SettingModel) {
        generalRepository.setSetting(setttingModel)
    }
}
