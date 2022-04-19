package com.example.powerrangers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.powerrangers.data.MediaApplication
import com.example.powerrangers.data.MediaViewModel
import com.example.powerrangers.data.MediaViewModelFactory

class MainActivity : AppCompatActivity() {
    private val wordViewModel: MediaViewModel by viewModels {
        MediaViewModelFactory((application as MediaApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


