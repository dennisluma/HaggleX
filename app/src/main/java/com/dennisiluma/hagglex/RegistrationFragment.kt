package com.dennisiluma.hagglex

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.dennisiluma.hagglex.databinding.FragmentRegistrationBinding
import com.dennisiluma.hagglex.utils.SharedPreferenceManager
import com.dennisiluma.hagglex.utils.util.snackBarMessage
import com.hagglex.graphql.LoginMutation
import com.hagglex.graphql.RegisterMutation
import com.hagglex.graphql.type.CreateUserInput
import com.hagglex.graphql.type.LoginInput
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*on backpresed icon behaviour*/
        binding.registrationBackArrow.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.registrationSignupButton.setOnClickListener {
            val email = binding.registrationEmailField.text.toString().trim()
            val password = binding.registrationPasswordField.text.toString().trim()
            val username = binding.registratonUsernameField.text.toString().trim()
            val referralCode = binding.registractionReferalCodeField.text.toString().trim()
            val country = binding.countryCodePicker.selectedCountryName.toString()
            val countryCode = binding.countryCodePicker.selectedCountryCode.toString()
            val manualPhoneNumberInput = binding.registrationPhonenumberField.text.toString().trim()
            val phoneNumber = "$countryCode+$manualPhoneNumberInput"
            createUser(email, password, username, phoneNumber, referralCode, country)
        }
    }
    // Function to create user or handle exceptions appropriately
    private fun createUser(
        email: String,
        password: String,
        username: String,
        phoneNumber: String,
        referralCode: String? = null,
        country: String
    ) {
        if (validateUser(email, password,username,phoneNumber,country)) {
            val user = CreateUserInput(
                email = email,
                username = username,
                password = password,
                phonenumber = phoneNumber,
                referralCode = Input.fromNullable(referralCode),
                country = "Nigeria",
                currency = "Dollar"
            )
            lifecycleScope.launch {
                val response = try {
                    /*Use apollo client to send our data to graphql API and handles the results and exceptions that follows */
                    getApolloClient(requireContext()).mutate(RegisterMutation(data = user)).await()
                } catch (e: Exception) {
                    throw java.lang.Exception(e)
                }

                val registerObject = response.data?.register
                //the registerObject is an object that contains a generated token and user details from api

                if (registerObject == null || response.hasErrors()) {
                    snackBarMessage("${response.errors?.get(0)?.message}")
                    binding.registrationProgressbar.visibility = View.GONE
                } else {
                    binding.registrationProgressbar.visibility = View.GONE
                    snackBarMessage("Registration is Successful")
                    SharedPreferenceManager(requireContext()).saveToken(registerObject.token)
                        /* if response is successful, collect our email input and navigate to verifyemail fragment*/
                    val bundle = bundleOf("email" to email)
                    findNavController().navigate(R.id.verifyEmailFragment, bundle)

                }
            }
        }
    }
    // Function to validate user input
    private fun validateUser(email: String, password: String,username:String,phoneNumber:String,country:String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            snackBarMessage("Please, input your valid email")
            return false
        }
        if (password.length < 8) {
            snackBarMessage("Password lengths must be greater than 7")
            return false
        }
        if(username.isEmpty() && phoneNumber.isEmpty() && country.isEmpty()){
            return false

        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}