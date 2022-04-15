package com.example.githubusersubsmision2.favorite

import android.content.Context
import androidx.room.Room

class ConfiqDatabase(private val context: Context) {
    private val db = Room.databaseBuilder(context,Database::class.java,"usergithub.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.UserDao()
}