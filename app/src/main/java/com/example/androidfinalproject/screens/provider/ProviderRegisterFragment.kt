package com.example.androidfinalproject.screens.provider

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.activity.MainActivity
import com.example.androidfinalproject.provider.account.Provider
import com.example.androidfinalproject.provider.account.ProviderViewModel
import kotlinx.android.synthetic.main.fragment_profile_provider.*
import kotlinx.android.synthetic.main.fragment_provider_register.*
import kotlinx.android.synthetic.main.fragment_user_register.*
import javax.inject.Inject

class ProviderRegisterFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var providerViewModel: ProviderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        providerViewModel.providerResponse.observe(viewLifecycleOwner,
            Observer {
                if (it.status == 400.toString()) {
                    Toast.makeText(this.context, "Username/Email Exist", Toast.LENGTH_SHORT)
                        .show()
                } else if (it.status == 200.toString()) {
                    Toast.makeText(this.context, "Register Success", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this.context, MainActivity::class.java))
                }
            })
        LoginProviderText.setOnClickListener(this)
        regisButtonProvider.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            LoginProviderText -> {
                view?.findNavController()
                    ?.navigate(R.id.action_global_chooseLoginFragment)
            }
            regisButtonProvider -> {
                if (inputEmailRegisProvider.text.toString() == "" || inputUnameRegisProvider.text.toString() == "" ||
                    InputPwRegisProvider.text.toString() == "" || inputPhoneRegisProvider.text.toString() == "" ||
                    inputFNameRegisProvider.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Must be filled", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val providerData =
                        Provider(
                            email = inputEmailRegisProvider.text.toString(),
                            username = inputUnameRegisProvider.text.toString(),
                            password = InputPwRegisProvider.text.toString(),
                            phone_number = inputPhoneRegisProvider.text.toString(),
                            fullname = inputFNameRegisProvider.text.toString()
                        )
                    providerViewModel.registerProvider(providerData)
                }
            }
        }
    }
}