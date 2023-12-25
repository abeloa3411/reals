package com.example.reals

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.reals.databinding.ActivityVideoUploadBinding
import com.example.reals.util.UiUtil

class VideoUpload : AppCompatActivity() {

    lateinit var binding:ActivityVideoUploadBinding
    private var selectedVideoUri:Uri? = null
    lateinit var videoLauncher:ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            selectedVideoUri = result.data?.data
            showPostView()
        }

        binding.addIcon.setOnClickListener{
            checkPermissionAndOpenVideoPicker()
        }

        binding.submitPostBtn.setOnClickListener{
            uploadVideo()
        }
    }

    private fun uploadVideo(){
        if(binding.postThumbnailInput.text.toString().isEmpty()){
            binding.postThumbnailInput.setError("Enter caption")
        }
        setInProgress(true)

        selectedVideoUri?.apply {

        }
    }

    private fun setInProgress(boolean:Boolean){
        if(boolean){
            binding.progressBar.visibility = View.VISIBLE
            binding.submitPostBtn.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.GONE
            binding.submitPostBtn.visibility = View.VISIBLE
        }
    }

    private fun showPostView(){
        binding.postView.visibility = View.VISIBLE
        binding.videoUpload.visibility = View.GONE
    }

    private fun checkPermissionAndOpenVideoPicker(){
        var readExternalVide0 : String=""

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            readExternalVide0 = android.Manifest.permission.READ_MEDIA_VIDEO
        }else{
            readExternalVide0 = android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if(ContextCompat.checkSelfPermission(this, readExternalVide0) == PackageManager.PERMISSION_GRANTED){
            openVideoPicker()
        }else{
            ActivityCompat.requestPermissions(
                this, arrayOf(readExternalVide0),100
            )
        }

    }

    private fun openVideoPicker(){
    var intent = Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        videoLauncher.launch(intent)
    }
}