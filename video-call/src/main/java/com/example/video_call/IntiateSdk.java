package com.example.video_call;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Keep;

import com.example.video_call.activity.SdkWebviewActivity;

@Keep
public class IntiateSdk {

    public static void s(Context c, String url, boolean isDebug){

        Intent intent = new Intent();
        intent.setClass(c, SdkWebviewActivity.class);
        intent.putExtra("isDebug", isDebug);
        intent.putExtra("url", url);
        c.startActivity(intent);

    }
}
