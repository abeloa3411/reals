
package com.example.reals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reals.databinding.ActivityMainBinding
import com.example.reals.util.UiUtil

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

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
                    UiUtil.showToast(this, "Add video")
                }

                R.id.bottom_menu_profile -> {
                    UiUtil.showToast(this, "Profile")
                }
            }

            false
        }
    }
}