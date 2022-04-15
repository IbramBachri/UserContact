package com.example.githubusersubsmision2.apiremote

// karna optional, saya kena kendal di softwere posman, Autorization tidak bisa di cenang di header
//import com.example.githubusersubsmision2.BuildConfig
import com.example.githubusersubsmision2.datajson.ResponeDetailUser
import com.example.githubusersubsmision2.datajson.ResponseGithubUser
import retrofit2.http.*

interface  Githubservice {

    // karna optional, saya kena kendal di softwere posman, Autorization tidak bisa di centang di header

    @JvmSuppressWildcards
    @GET("users")

    suspend fun getUser(
        //@Header(" Authorization") authorization: String = BuildConfig.TOKEN
    ): MutableList<ResponseGithubUser.Item>

    //Detai user
    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path ("username") username: String,
        //@Header(" Authorization") authorization: String = BuildConfig.TOKEN


    ): ResponeDetailUser

    //folower
    @JvmSuppressWildcards
    @GET("/users/{username}/followers")
    suspend fun getFollowers(
        @Path ("username") username: String,
        //@Header(" Authorization") authorization: String = BuildConfig.TOKEN


    ): MutableList<ResponseGithubUser.Item>

    //following
    @JvmSuppressWildcards
    @GET("/users/{username}/following")
    suspend fun getFollowing(

        @Path ("username") username: String,
        //@Header(" Authorization")authorization: String = BuildConfig.TOKEN


    ): MutableList<ResponseGithubUser.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUser(

        @QueryMap params: Map<String, Any>,
        //@Header(" Authorization") authorization: String = BuildConfig.TOKEN


    ): ResponseGithubUser
}