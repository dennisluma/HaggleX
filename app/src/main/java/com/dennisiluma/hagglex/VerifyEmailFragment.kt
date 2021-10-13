package com.dennisiluma.hagglex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dennisiluma.hagglex.databinding.FragmentRegistrationBinding
import com.dennisiluma.hagglex.databinding.FragmentVerifyEmailBinding

class VerifyEmailFragment : Fragment() {
    private var _binding: FragmentVerifyEmailBinding? = null
    private val binding get() = _binding!!
    private var email:String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerifyEmailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Get email value from nav args coming from registration fragment*/
        email = arguments?.getString("email")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}