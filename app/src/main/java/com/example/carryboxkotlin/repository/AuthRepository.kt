package com.example.carryboxkotlin.repository

import com.example.carryboxkotlin.api.CarryBoxApi

class AuthRepository(
    private val api:CarryBoxApi
): BaseRepository() {


    // fun to hit our generateOtp api and this function will call safe api call
    suspend fun generateOtp(
        phone:String
    ) = safeApiCall {
            api.generateMobileOtp(phone)
    }

}