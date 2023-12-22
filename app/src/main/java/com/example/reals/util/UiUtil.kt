package com.example.reals.util

import android.content.Context
import android.widget.Toast

object UiUtil {
    fun showToast(context: Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}