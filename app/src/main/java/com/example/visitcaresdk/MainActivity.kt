package com.example.visitcaresdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.video_call.IntiateSdk

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prodMagicLink = "https://vsyt.me/m/1XFjA45h"
        val debugLink = "https://care.getvisitapp.xyz/"
        IntiateSdk.s(this, debugLink, false)

    }
}