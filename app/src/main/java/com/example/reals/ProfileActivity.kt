package com.example.reals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.reals.databinding.ActivityProfileBinding
import com.example.reals.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class ProfileActivity : AppCompatActivity() {

    lateinit var binding:ActivityProfileBinding
    lateinit var profileUserId: String
    lateinit var currentUserId:String
    lateinit var profileUserModel: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profileUserId = intent.getStringExtra("profile_user_id")!!
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!

        if(profileUserId == currentUserId){
            binding.profileBtn.text = "Logout"
            binding.profileBtn.setOnClickListener{
                logout()
            }
        }else{
            //other user profiles
        }

        getProfileDataFromFirebase()

    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun getProfileDataFromFirebase(){
     Firebase.firestore.collection("users").document(profileUserId).get().addOnSuccessListener {
         profileUserModel = it.toObject(UserModel::class.java)!!
         setUI()
     }
    }

    private fun setUI(){
        profileUserModel?.apply {
            Glide.with(binding.profilePic).load(profilePic).apply(
                RequestOptions().placeholder(R.drawable.baseline_account_circle_24)
            ).into(binding.profilePic)
            binding.profileUsername.text = username
            binding.progressBar.visibility = View.INVISIBLE
            binding.followingCount.text = followingList.size.toString()
            binding.followersCount.text = followersList.size.toString()
            Firebase.firestore.collection("videos").whereEqualTo("uploaderId", profileUserId).get().addOnSuccessListener{
                binding.postCount.text = it.size().toString()
            }
        }
    }
}