package com.example.carryboxkotlin.api

import com.example.carryboxkotlin.model.CommonModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CarryBoxApi {

    @FormUrlEncoded
    @POST("generateMobileOtp")
    suspend fun generateMobileOtp(
        @Field("phone") phone: String
    ): CommonModel

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("phone")phone:String,
        @Field("otp")otp:String,
        @Field("name")name:String,
        @Field("email_id")email_id:String,
        @Field("password")password:String,
    ):CommonModel
}