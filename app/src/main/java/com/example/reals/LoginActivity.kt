package com.example.reals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.example.reals.databinding.ActivityLoginBinding
import com.example.reals.util.UiUtil
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseAuth.getInstance().currentUser?.let {
            //user logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.signupButton.setOnClickListener{
            login()
        }

        binding.goToSignupBtn.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

    }

    private fun setInProgress(boolean:Boolean){
        if(boolean){
            binding.progressBar.visibility = View.VISIBLE
            binding.signupButton.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.GONE
            binding.signupButton.visibility = View.VISIBLE
        }
    }

    private fun login(){
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.error = "Invalid Email"
            return
        }

        if (password.length < 6){
            binding.passwordInput.error = "Minimum 6 Characters"
        }

        loginWithFirebase(email, password)
    }

    private fun loginWithFirebase(email:String, password:String){
        setInProgress(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener {
            UiUtil.showToast(applicationContext, "Login successfully")
            setInProgress(false)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener{
            UiUtil.showToast(applicationContext, it.localizedMessage?:"Something went wrong")
            setInProgress(false)
        }
    }
}