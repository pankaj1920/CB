package com.example.kotlinretrofit.api

import com.example.kotlinretrofit.model.QuoteModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface Api {

/*    @GET("quotes")
    fun getQuote():Call<QuoteModel>*/

    @GET("quotes")
    suspend fun getQuote():Response<QuoteModel>
}