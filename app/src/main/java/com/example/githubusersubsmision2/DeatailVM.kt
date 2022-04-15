package com.example.githubusersubsmision2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubusersubsmision2.ResultCurotine.ResultVM
import com.example.githubusersubsmision2.apiremote.ApiConfiq
import com.example.githubusersubsmision2.datajson.ResponseGithubUser
import com.example.githubusersubsmision2.favorite.ConfiqDatabase
import com.example.githubusersubsmision2.favorite.Database
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DeatailVM(private val db: ConfiqDatabase) : ViewModel() {
    val ResultDeatilU = MutableLiveData<ResultVM>()
    val ResultFollowers = MutableLiveData<ResultVM>()
    val ResultFollowing = MutableLiveData<ResultVM>()
    val ResultSuccsesFavorite = MutableLiveData<Boolean>()
    val ResultDeleteFavorite = MutableLiveData<Boolean>()
    private var isFavorite = false
    fun saveUser(item: ResponseGithubUser.Item?) {
        viewModelScope.launch {
            item?.let {
                if (isFavorite) {
                    db.userDao.delete(item)
                    ResultDeleteFavorite.value =true
                } else {
                    db.userDao.insert(item)
                    ResultSuccsesFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavorite(id:Int, listenFavorite:() -> Unit){
        viewModelScope.launch {
           val user = db.userDao.findById(id)
            if(user != null){
                listenFavorite()
                isFavorite = true
            }
        }
    }
    fun getDetailusser(
        username: String,
        company: String,
        bio: String,
        location: String,
        login: String,
        follower: String,
        following: String,
        repos: String

    ) {
        viewModelScope.launch {
            flow {
                val responseflow = ApiConfiq
                    .githubService
                    .getDetailUser(username)

                emit(responseflow)
            }.onStart {
                ResultDeatilU.value = ResultVM.Loading(true)
            }.onCompletion {
                ResultDeatilU.value = ResultVM.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                ResultDeatilU.value = ResultVM.Error(it)
            }.collect {
                ResultDeatilU.value = ResultVM.Succes(it)
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            flow {
                val responseflow = ApiConfiq
                    .githubService
                    .getFollowers(username)

                emit(responseflow)
            }.onStart {
                ResultFollowers.value = ResultVM.Loading(true)
            }.onCompletion {
                ResultFollowers.value = ResultVM.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                ResultFollowers.value = ResultVM.Error(it)
            }.collect {
                ResultFollowers.value = ResultVM.Succes(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val responseflow = ApiConfiq
                    .githubService
                    .getFollowing(username)

                emit(responseflow)
            }.onStart {
                ResultFollowing.value = ResultVM.Loading(true)
            }.onCompletion {
                ResultFollowing.value = ResultVM.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                ResultFollowing.value = ResultVM.Error(it)
            }.collect {
                ResultFollowing.value = ResultVM.Succes(it)
            }
        }
    }

    class Factory(private val db: ConfiqDatabase) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = DeatailVM(db) as T
    }
}