package com.example.carryboxkotlin.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.carryboxkotlin.api.BaseClient
import com.example.carryboxkotlin.api.CarryBoxApi
import com.example.carryboxkotlin.api.Resource
import com.example.carryboxkotlin.databinding.FragmentSignUpBinding
import com.example.carryboxkotlin.model.CommonModel
import com.example.carryboxkotlin.repository.AuthRepository
import com.example.carryboxkotlin.ui.auth.BaseFragment.BaseFragment
import com.example.carryboxkotlin.viewmodel.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// whenever we will extend base fragment we need to define all three values
// and orveride the abstract function

class SignUpFragment : BaseFragment<AuthViewModel, FragmentSignUpBinding, AuthRepository>() {

    lateinit var signUpName: EditText
    lateinit var signUpEmail: EditText
    lateinit var signUpMobile: EditText
    lateinit var signUpPassword: EditText
    lateinit var signUpButton: Button

// we dont have to override onCreateView bcz we already overriden in base fragment

    /* override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         // Inflate the layout for this fragment
 //        return inflater.inflate(R.layout.fragment_sign_up, container, false)
         binding = FragmentSignUpBinding.inflate(inflater, container, false)
         return binding.root
     }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //we need to observer the liveData
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            // here we will get Resourece of type common model
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })


        signUpName = binding.signUpName
        signUpEmail = binding.signUpEmail
        signUpMobile = binding.signUpMobile
        signUpPassword = binding.signUpPassword
        signUpButton = binding.signUpButton

        signUpButton.setOnClickListener {
            val name = signUpName.text.toString().trim()
            val email = signUpEmail.text.toString().trim()
            val mobile = signUpMobile.text.toString().trim()
            val password = signUpPassword.text.toString().trim()

            if (name.isEmpty()) {
                signUpName.error = "Name Required"
                signUpName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                signUpEmail.error = "Email Required"
                signUpEmail.requestFocus()
                return@setOnClickListener
            }

            if (mobile.isEmpty()) {
                signUpMobile.error = "Mobile Required"
                signUpMobile.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                signUpPassword.error = "Password Required"
                signUpPassword.requestFocus()
                return@setOnClickListener
            }


            // now we need define viewModel of auth and call the api form viewModel
            viewModel.generateOtp(phone = mobile)

        }


    }

    override fun getViewModel(): Class<AuthViewModel> {
        return AuthViewModel::class.java
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater, container, false)
    }

    override fun getFragmentRepository(): AuthRepository {
        return AuthRepository(baseClient.buildApi(CarryBoxApi::class.java))
    }


/*

    private fun generateOtp(name: String, email: String,  mobile: String,password: String,) {

        val call:Call<CommonModel> = BaseClient.instance.generateMobileOtp(mobile)

        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: Response<CommonModel>
            ) {
                if (response.isSuccessful) {

                    val otpResponse: CommonModel? = response.body()
                    if (otpResponse?.status.equals("1")) {
                        Toast.makeText(context, otpResponse?.message, Toast.LENGTH_SHORT).show()

                        val action:NavDirections = SignUpFragmentDirections.actionSignUpFragmentToVerifyRegistrationFragment(name, email, mobile, password)
                            view!!.findNavController().navigate(action)
                    } else {
                        Toast.makeText(context, otpResponse?.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                Toast.makeText(context, "On Failure " + t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
*/


}