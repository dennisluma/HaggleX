package com.dennisiluma.hagglex.utils

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

object util {
    fun Fragment.snackBarMessage(message: String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

}

const val USERTOKEN = ""