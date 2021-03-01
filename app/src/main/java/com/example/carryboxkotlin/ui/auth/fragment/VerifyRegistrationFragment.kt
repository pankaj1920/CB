package com.example.carryboxkotlin.ui.auth.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.carryboxkotlin.R
import com.example.carryboxkotlin.api.BaseClient
import com.example.carryboxkotlin.databinding.FragmentVerifyRegistrationBinding
import com.example.carryboxkotlin.model.CommonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyRegistrationFragment : Fragment() {

    lateinit var binding:FragmentVerifyRegistrationBinding
    private val args: VerifyRegistrationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVerifyRegistrationBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = args.name
        val email = args.email
        val mobile = args.mobile
        val password = args.password


        binding.verfiyOtp.setOnClickListener {
            val otp = binding.otpEt.text.toString().trim()

            val call: Call<CommonModel> = BaseClient.instance.register(phone = mobile,
            otp = otp,email_id = email,password = password,name = name)

            call.enqueue(object : Callback<CommonModel> {
                override fun onResponse(call: Call<CommonModel>, response: Response<CommonModel>) {
                    if (response.isSuccessful) {
                        val dataResponse: CommonModel? = response.body()
                        if (dataResponse?.status.equals("1")) {
                            Toast.makeText(context, dataResponse?.message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, dataResponse?.message, Toast.LENGTH_SHORT)
                                .show()
                        }


                    } else {
                        Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                    Toast.makeText(context, "on Failure " + t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

}