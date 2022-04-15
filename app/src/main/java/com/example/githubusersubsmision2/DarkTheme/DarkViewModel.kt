package com.example.githubusersubsmision2.DarkTheme

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class DarkViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getTheme() = pref.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
    class  Factory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DarkViewModel(pref) as T
    }
}