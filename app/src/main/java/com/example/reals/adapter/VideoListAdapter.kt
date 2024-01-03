package com.example.reals.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.reals.ProfileActivity
import com.example.reals.R
import com.example.reals.databinding.VideoItemRowBinding
import com.example.reals.model.UserModel
import com.example.reals.model.VideoModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class VideoListAdapter(
options: FirestoreRecyclerOptions<VideoModel>
) :FirestoreRecyclerAdapter<VideoModel, VideoListAdapter.VideoViewHolder>(options) {

    inner class VideoViewHolder(private val binding: VideoItemRowBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindVideo(videoModel:VideoModel){
            //bind user data
            Firebase.firestore.collection("users").document(videoModel.uploaderId).get().addOnSuccessListener {
                val userModel = it?.toObject(UserModel::class.java)
                userModel?.apply{
                    binding.usernameView.text = username

                    //bind profile pic
                    Glide.with(binding.profileIcon).load(profilePic).circleCrop().apply(
                        RequestOptions().placeholder(R.drawable.baseline_person_24)
                    ).into(binding.profileIcon)

                    binding.userDetailLayout.setOnClickListener{
                        val intent = Intent(binding.userDetailLayout.context, ProfileActivity::class.java)
                        intent.putExtra("profile_user_id", id)
                        binding.userDetailLayout.context.startActivity(intent)
                    }
                }
            }

            binding.captionView.text = videoModel.title
            binding.progressBar.visibility = View.VISIBLE


            //bind video
            binding.videoView?.apply{
                setVideoPath(videoModel.url)
                setOnPreparedListener{
                    binding.progressBar.visibility = View.GONE
                    it.start()
                    it.isLooping = true
                }

                //play pause
                setOnClickListener{
                    if(isPlaying){
                        resume()
                        binding.pauseBtn.visibility = View.VISIBLE
                    }else{
                        start()
                        binding.pauseBtn.visibility = View.GONE
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, model: VideoModel) {
        holder.bindVideo(model)
    }
}