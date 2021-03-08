package com.example.carryboxkotlin.ui.auth.BaseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.carryboxkotlin.api.BaseClient
import com.example.carryboxkotlin.repository.BaseRepository
import com.example.carryboxkotlin.viewmodel.ViewModelFactory

// this is the base fragment and we will extend this fragment to all other fragments
/* for ever fragment we have something common eg viewModel Repository, dataBinding will be
* common in every fragment, so we will define the generic type of these three things inside this BaseFragment
*  here we are using base repository bcz we are using this base repository for all the repository
* */
abstract class BaseFragment<VM : ViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: B
    protected val baseClient = BaseClient()
    lateinit var viewModel:VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inatalizing the Binding
        binding = getFragmentBinding(inflater, container)


         /*here we need to inatalize the view model
         we cannot directly inatalize the view model bcz our viewmodel contain the constructor parameter
         we need to pass repository to our view model and whenever we have some constructor parameter in our viewmodel
         we need to create a view model factory that will provide us the view model and we will create single view model factory for our project
         that is responsible for providing all the view model */

        // we need the factory instance to create our view model and pass the Base Repository,
        // so we are pass getFragmentRepository() which will return BaseReposiotry
        val factory = ViewModelFactory(getFragmentRepository())
        // so now with this factory we can create a view model instance
        // in get method we need to pass actual view model class which here we are getting from getViewModel
         viewModel = ViewModelProvider(this,factory).get(getViewModel())
        return binding.root
    }


    // wee need the actuall binding when we will extend this base fragment so we will create some abstract function
    // one fun will define the view model class, one will define actual binding and one will define the repository
    // it will return ViewModelClass
    abstract fun getViewModel(): Class<VM>

    // in this fragment we will pass the inflater and container bcz these are required to inflate the viewBinding
    //// it will return B i.e ViewBinding
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    // it will return R i.e Repository
    abstract fun getFragmentRepository(): R
}