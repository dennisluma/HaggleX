package com.dennisiluma.hagglex

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dennisiluma.hagglex.databinding.FragmentRegistrationBinding
import com.dennisiluma.hagglex.utils.util.snackBarMessage

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
            TODO("Implement logic")
        }
    }
    // Function to validate user input
    private fun validateUser(email: String, password: String,username:String,phoneNumber:String,country:String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            snackBarMessage("Please, input your valid email")
            return false
        }
        if (password.length < 9) {
            snackBarMessage("Password lengths must be greater than 8")
            return false
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}