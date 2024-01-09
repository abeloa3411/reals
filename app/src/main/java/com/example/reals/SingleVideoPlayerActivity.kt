package com.example.reals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reals.adapter.VideoListAdapter
import com.example.reals.databinding.ActivitySingleVideoPlayerBinding
import com.example.reals.model.VideoModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SingleVideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleVideoPlayerBinding
    private lateinit var videoId : String
    private lateinit var adapter:VideoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoId = intent.getStringExtra("videoId")!!

        setUpViewPager()
    }

    private fun setUpViewPager(){
        val options = FirestoreRecyclerOptions.Builder<VideoModel>().setQuery(
            Firebase.firestore.collection("videos").whereEqualTo("videoId", videoId),
            VideoModel::class.java
        ).build()

        adapter = VideoListAdapter(options)
        binding.viewPager.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }
}