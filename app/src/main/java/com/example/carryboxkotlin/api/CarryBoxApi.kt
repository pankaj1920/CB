package com.example.carryboxkotlin.api

import com.example.carryboxkotlin.model.CommonModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CarryBoxApi {

    @FormUrlEncoded
    @POST("generateMobileOtp")
    fun generateMobileOtp(
        @Field("phone") phone: String
    ): Call<CommonModel>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("phone")phone:String,
        @Field("otp")otp:String,
        @Field("name")name:String,
        @Field("email_id")email_id:String,
        @Field("password")password:String,
    ):Call<CommonModel>
}