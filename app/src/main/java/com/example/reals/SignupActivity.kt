package com.example.reals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.reals.databinding.ActivitySignupBinding
import com.example.reals.model.UserModel
import com.example.reals.util.UiUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener{
            signup()
        }

        binding.goToLoginBtn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    fun setInProgress(boolean:Boolean){
        if(boolean){
            binding.progressBar.visibility = View.VISIBLE
            binding.signupButton.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.GONE
            binding.signupButton.visibility = View.VISIBLE
        }
    }

    fun signup(){
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.cornfirmInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.error = "Invalid Email"
            return
        }

        if (password.length < 6){
            binding.passwordInput.error = "Minimum 6 Characters"
        }

        if(confirmPassword != password){
            binding.cornfirmInput.error = "Password does not match"
        }

        signupWithFirebase(email, password)
    }

    private fun signupWithFirebase(email :String, password:String){
        setInProgress(true)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            it.user?.let{user ->
                val userModel = UserModel(user.uid,email,email.substringBefore("@"))
                Firebase.firestore.collection("users").document(user.uid).set(userModel).addOnSuccessListener {
                    UiUtil.showToast(applicationContext, "Account created succesfully")
                    setInProgress(false)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }.addOnFailureListener {
            UiUtil.showToast(applicationContext, "Something went wrong")
            setInProgress(false)
        }
    }
}