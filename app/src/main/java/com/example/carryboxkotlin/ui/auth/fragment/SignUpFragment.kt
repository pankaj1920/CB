package com.example.carryboxkotlin.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.carryboxkotlin.api.BaseClient
import com.example.carryboxkotlin.databinding.FragmentSignUpBinding
import com.example.carryboxkotlin.model.CommonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var signUpName: EditText
    lateinit var signUpEmail: EditText
    lateinit var signUpMobile: EditText
    lateinit var signUpPassword: EditText
    lateinit var signUpButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            generateOtp(name, email, mobile, password)

        }


    }

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


}