# Visit Android SDK

## Requirement
1. Your project needs have Kotlin configured to use the SDK (atleast Kotlin version 1.4.20).
2. Min SDK 16

## How to use
1. * Copy the `video-call-release.aar` present inside `app/video-call-release.arr` and paste in the inside the module where you like to use it.
     Or 
   * You can clone the repo and inside android studio `Build->Rebuild`, a new .aar file will be generated inside `VisitCareSDK\video-call\build\outputs\aar\`


2. Include the .aar file in `build.gradle` of the module where it will get used 

    `implementation files('video-call-release.aar')`

3. Add the following dependencies inside the `build.gradle` of the module using the `.aar` file:
    ```
    implementation 'com.twilio:video-android:5.10.1'
    implementation 'com.twilio:audioswitch:1.0.0'

    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2'


    implementation 'com.github.bumptech.glide:glide:4.11.0'
    kapt 'com.github.bumptech.glide:compiler:4.11.0'

    def retrofit_version="2.9.0"
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation 'de.hdodenhof:circleimageview:3.1.0'

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation 'com.github.delight-im:Android-AdvancedWebView:v3.0.0'
    
    ```
 4. Initiate SDK from current Activity 
 
    For Staging: 
    
          `IntiateSdk.s(this, "webview url", true);`
          
    For Production:
    
        `IntiateSdk.s(this, "webview url", false);`
        
___
Use `app-debug.apk` file to test sdk festure in Stage.
