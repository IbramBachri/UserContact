package com.example.githubusersubsmision2.favorite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubusersubsmision2.datajson.ResponseGithubUser

@Database(entities = [ResponseGithubUser.Item::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase(){
    abstract fun UserDao(): UserDao
}