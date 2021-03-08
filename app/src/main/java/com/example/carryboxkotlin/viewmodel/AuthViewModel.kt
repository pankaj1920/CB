package com.example.carryboxkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carryboxkotlin.api.Resource
import com.example.carryboxkotlin.model.CommonModel
import com.example.carryboxkotlin.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    // in this view model we need repository that will hit the api
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginResponse:MutableLiveData<Resource<CommonModel>> = MutableLiveData()
    val loginResponse:LiveData<Resource<CommonModel>>
    // when we will get the login response we will set in _loginResponse
        get() = _loginResponse

    fun generateOtp(phone: String) {
        viewModelScope.launch {
             // here we call generateOtp function from this repository instance and it will return login response
            // which we will stoe in liveData
             _loginResponse.value = repository.generateOtp(phone)
        }
    }
}