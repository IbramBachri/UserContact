package com.example.githubusersubsmision2

import android.util.Log
import androidx.lifecycle.*
import com.example.githubusersubsmision2.DarkTheme.SettingPreferences
import com.example.githubusersubsmision2.ResultCurotine.ResultVM
import com.example.githubusersubsmision2.apiremote.ApiConfiq
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ViewModelMain(private val preferences: SettingPreferences): ViewModel() {

    val ResultGitHub = MutableLiveData<ResultVM>()

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun getusser (){
        viewModelScope.launch {
                flow {
                    val responseflow = ApiConfiq
                        .githubService
                        .getUser()

                    emit(responseflow)
                }.onStart {
                    ResultGitHub.value = ResultVM.Loading(true)
                }.onCompletion {
                    ResultGitHub.value = ResultVM.Loading(false)
                }.catch {
                    Log.e("Error",  it.message.toString())
                    it.printStackTrace()
                    ResultGitHub.value = ResultVM.Error(it)
                }.collect {
                    ResultGitHub.value = ResultVM.Succes(it)
                }
        }
    }

    fun getusser (username: String){
        viewModelScope.launch {
                flow {
                    val responseflow = ApiConfiq
                        .githubService
                        .searchUser(
                            mapOf(
                            "q" to username,
                            "per page" to 30 // menentukan limit, boleh pakai boleh tidak
                        ))

                    emit(responseflow)
                }.onStart {
                    ResultGitHub.value = ResultVM.Loading(true)
                }.onCompletion {
                    ResultGitHub.value = ResultVM.Loading(false)
                }.catch {
                    Log.e("Error",  it.message.toString())
                    it.printStackTrace()
                    ResultGitHub.value = ResultVM.Error(it)
                }.collect {
                    ResultGitHub.value = ResultVM.Succes(it.items)
                }
        }
    }

    class Factory(private val preferences: SettingPreferences) :
            ViewModelProvider.NewInstanceFactory(){
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    ViewModelMain(preferences)as T
            }
}
