package com.dennisiluma.hagglex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.dennisiluma.hagglex.databinding.FragmentVerifyEmailBinding
import com.dennisiluma.hagglex.utils.util.snackBarMessage
import com.hagglex.graphql.ResendVerificationCodeQuery
import com.hagglex.graphql.VerifyUserMutation
import com.hagglex.graphql.type.EmailInput
import com.hagglex.graphql.type.VerifyUserInput
import kotlinx.coroutines.launch

class VerifyEmailFragment : Fragment() {
    private var _binding: FragmentVerifyEmailBinding? = null
    private val binding get() = _binding!!
    private var email: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerifyEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Get email value from nav args coming from registration fragment*/
        email = arguments?.getString("email")
        /*Handle on back press*/
        binding.arrowLeft.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.verifyMeButton.setOnClickListener {
            verifyUser(Integer.parseInt(binding.verificationCodeField.text.toString()))
        }
        /*Resend verification code*/
        binding.resendcode.setOnClickListener {
            resendVerificationCode(email.toString())
        }
    }

    // Function to resend verification code
    private fun resendVerificationCode(email: String) {
        binding.verifyemailProgressbar.visibility = View.VISIBLE
        lifecycleScope.launch {
            val response = try {
                getApolloClient(requireContext()).query(
                    ResendVerificationCodeQuery(
                        email = Input.fromNullable(
                            EmailInput(email)
                        )
                    )
                ).await()
            } catch (e: java.lang.Exception) {
                null
            }

            val result = response?.data

            if (result == null || response.hasErrors()) {
                binding.verifyemailProgressbar.visibility = View.GONE
                snackBarMessage("${response?.errors?.get(0)?.message}")
            } else {
                binding.verifyemailProgressbar.visibility = View.GONE
                snackBarMessage("Verification code sent")
            }
        }
    }

    // Function to verify user
    private fun verifyUser(verificationPin: Int) {
        if (binding.verificationCodeField.text.isNullOrEmpty()) {
            snackBarMessage("Fields cannot be empty")
            return
        }
        binding.verifyemailProgressbar.visibility = View.VISIBLE
        val pin = VerifyUserInput(verificationPin)
        lifecycleScope.launch {
            val response = try {
                getApolloClient(requireContext()).mutate(VerifyUserMutation(Input.fromNullable(pin)))
                    .await()
            } catch (e: Exception) {
                null
            }
            val result = response?.data?.verifyUser

            if (result == null || response.hasErrors()) {
                binding.verifyemailProgressbar.visibility = View.GONE
                response?.errors?.get(0)?.message?.let { snackBarMessage(it) }
            } else {
                binding.verifyemailProgressbar.visibility = View.GONE
                snackBarMessage("Account Has Been Verified")
                findNavController().navigate(R.id.setupCompleteFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}