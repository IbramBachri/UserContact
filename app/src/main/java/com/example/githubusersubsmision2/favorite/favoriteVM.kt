package com.example.githubusersubsmision2.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersubsmision2.DeatailVM

class favoriteVM(private val ConfiqDatabase: ConfiqDatabase): ViewModel() {

    fun  getUserFavorite() = ConfiqDatabase.userDao.loadAll()

    class Factory(private val db: ConfiqDatabase) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = favoriteVM(db) as T
    }
}