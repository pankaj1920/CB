package com.example.carryboxkotlin.api

import com.example.carryboxkotlin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseClient {

    companion object {
        private val BASE_URL = "http://192.168.43.133:8000/api/customer/"
    }

    // function to create retrofit client
    // it will be a generic function
    fun <Api> buildApi(
        api: Class<Api>
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().also { client ->
                    // if we are running the debug version then only we need to add httpLoginInterceptor
                    if (BuildConfig.DEBUG){
                        val logging = HttpLoggingInterceptor();
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }

                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    /*  val instance: CarryBoxApi by lazy {
          val retrofit = Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build()
          retrofit.create(CarryBoxApi::class.java)
      }*/

}