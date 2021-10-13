package com.dennisiluma.hagglex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dennisiluma.hagglex.databinding.FragmentLoginBinding
import com.dennisiluma.hagglex.utils.util.snackBarMessage

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setting click listener on "createaccounttext"
        binding.createAccountText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        // Implementation when the loginButton is clicked
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailField.text.toString()
            val password = binding.loginPasswordField.text.toString()
            loginUser(email, password)
        }

    }

    // Function to login user and handle inputs validations
    private fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            snackBarMessage("Fields should not be empty")
            return
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}