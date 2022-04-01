package com.example.visitcaresdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.video_call.IntiateSdk

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prodMagicLink = "https://abhi.getvisitapp.xyz/sso?userParams=x5vRLFAfT0wRSbmG82ekrGJ_6_nmAt2tE3fWkQwvxyYHcu9a4aK7OHbrq8e0qsxyfCBnSalb9Xd-zfeNg8KyWTiE7VohEZJS8C1ZzAL2yBVtlF9oLd1UDGnssiBrtaKM2BIp0ZEMtKHWTai447V-19MOQb7zhFnfTPRZIo5EcaqPYS8H9tVtlLBtb_HX89P5Ncc9Spu6kp7WFo4bDq5O1_xCUGsfL5kaAAhE1EIt1Z2uZpDpBVY2qOK7tovqACqO&clientId=abhi-58fd14"
        IntiateSdk.s(this, prodMagicLink, false)

    }
}