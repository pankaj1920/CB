package com.example.carryboxkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carryboxkotlin.repository.AuthRepository
import com.example.carryboxkotlin.repository.BaseRepository

// to this class we will pass repository
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    /* here we are passing Base repository bcz we are creating all our repository
     with the help of this base repository so whenever we need to create the repository we need to
     extend the base repository that's y we are passing base repository as a constructor parameter to our
     view model factory bcz it is responsible for giving us all the viewModel that are required in our project
     */
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return  when{
            // here wew will check this viewmodel is assignable from our specific viewmodel or not
                // this create method is returing T so we will Cast as T
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->  AuthViewModel(repository as AuthRepository) as T

            // for now we have only one ViewModel so for every other case we will throw exception
            // so whenever we want new view model instance we will define inside this when block
           // eg :  modelClass.isAssignableFrom(LoginViewModel::class.java) ->  AuthViewModel(repository as AuthRepository) as T
            else -> throw IllegalAccessException("View Model class not found")
        }

    }
}