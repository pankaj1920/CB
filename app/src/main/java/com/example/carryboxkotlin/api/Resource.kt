package com.example.carryboxkotlin.api

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body

// this class we will use to wrap the api response to handle the sucess and failure properly
// this class will also be generic and it will handle all kind of api response
sealed class Resource<out T>{
    // here we will define 2 data classes one for sucess and one for failure

    // for this class we will define the type T and to the constructor we will pass the value of Type T
    data class Success <out T>(val value:T):Resource<T>()

    // it wont return T bcz it is a failure situation
    // we will return the value which is require to handle the failure
    data class Failure(
        // is networkError will be true when we have network error
        val isNetworkError:Boolean,
        // errorCode is for getting the actual error code of api in case we dont have the network error
        val errorCode:Int?,

        // this is to get error response body
        val errorBody: ResponseBody?
    ):Resource<Nothing>() // for error it will return us nothing


}
