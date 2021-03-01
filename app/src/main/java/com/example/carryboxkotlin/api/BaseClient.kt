package com.example.carryboxkotlin.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseClient {

    private val BASE_URL = "http://192.168.43.133:8000/api/customer/"

    val instance: CarryBoxApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(CarryBoxApi::class.java)
    }

}