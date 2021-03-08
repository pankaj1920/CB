package com.example.carryboxkotlin.repository

import com.example.carryboxkotlin.api.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

// it will be the base repository for all our repository classes

// we are creating base repository bcz we wont be creating instance of this base repository class
// directly but we will inhert this class to our actuall concreate repository  clases
abstract class BaseRepository {

    // here we will define the function and this function is for safely call the api
    // this function will return the Resource of Type T which we create to wrap the success or failure response
    // here T is the actual response we should get

     /*
     now we have this function which will execute the api call and to this function we will pass the parameter
    that is our api call and it is another suspending function
    */
    suspend fun<T> safeApiCall(
         // this apiCall function will return T
         apiCall:suspend () -> T
    ):Resource<T>{
        // inside the safeApiCall function we will execute the apiCall function
         // we will execute all the api call in corotine Io dispatcher
        return  withContext(Dispatchers.IO){
            try {
               /*  here we are executing apiCall and if it is sucess we will get result directly
               * and we will put the result to our resource sealed class
               * */
                Resource.Success(apiCall.invoke())
            }catch (throwable:Throwable){
                // inside this catch method we will handle the failure
                when(throwable){
                    is HttpException -> {
                        // in case of http exception network error will be false and we will get error code and response
                        Resource.Failure(false,throwable.code(),throwable.response()?.errorBody())
                    }
                    else->{
                        // if it is not HttpException then it is Network error
                        Resource.Failure( true,null,null)
                    }
                }

            }
        }
    }

}