package com.example.visitcaresdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.video_call.IntiateSdk

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://vsyt.me/m/1XFjA45h"
        IntiateSdk.s(this, url, false)

    }
}