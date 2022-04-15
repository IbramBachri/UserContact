package com.example.githubusersubsmision2.favorite

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubusersubsmision2.datajson.ResponseGithubUser


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ResponseGithubUser.Item)

    @Query("SELECT * FROM user")
    fun loadAll(): LiveData<MutableList<ResponseGithubUser.Item>>

    @Query("SELECT * FROM user WHERE id LIKE :Id LIMIT 1")
    fun findById(Id: Int): ResponseGithubUser.Item

    @Delete
    fun delete(user: ResponseGithubUser.Item)

}