
package com.example.reals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reals.adapter.VideoListAdapter
import com.example.reals.databinding.ActivityMainBinding
import com.example.reals.model.VideoModel
import com.example.reals.util.UiUtil
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var adapter: VideoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.setOnItemSelectedListener { menuItem ->

            when(menuItem.itemId){
                R.id.bottom_menu_home -> {
                    UiUtil.showToast(this, "home")
                }

                R.id.bottom_menu_add_video -> {
                    startActivity(Intent(this, VideoUpload::class.java))
                }

                R.id.bottom_menu_profile -> {
                    UiUtil.showToast(this, "Profile")
                }
            }

            false
        }
        setUpViewPager()
    }

    private fun setUpViewPager(){
        val options = FirestoreRecyclerOptions.Builder<VideoModel>().setQuery(
            Firebase.firestore.collection("videos"),
            VideoModel::class.java
        ).build()

        adapter = VideoListAdapter(options)
        binding.viewPager.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}