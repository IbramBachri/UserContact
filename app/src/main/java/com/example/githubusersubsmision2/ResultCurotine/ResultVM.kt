package com.example.githubusersubsmision2.ResultCurotine


sealed class ResultVM{
    data class Succes<out T>(val data: T) : ResultVM()
    data class  Error (val exception: Throwable) : ResultVM()
    data class  Loading (val isloading: Boolean) : ResultVM()
}
