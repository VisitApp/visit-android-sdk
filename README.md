# Visit Android SDK

## Requirement
1. Your project needs have Kotlin configured to use the SDK (atleast Kotlin version 1.4.20).
2. Min SDK 16

## How to use

### Gradle Setup:
```
dependencies {  
        implementation 'com.github.VisitApp:visit-android-sdk:1.19'   
 }  
 ``` 
These dependencies are needed for smooth functioning of SDK.

 ```
    implementation 'com.github.delight-im:Android-AdvancedWebView:v3.0.0'
    
    ```
 4. Initiate SDK from current Activity 
 
    For Staging: 
    
          `IntiateSdk.s(this, "webview url", true);`
          
    For Production:
    
        `IntiateSdk.s(this, "webview url", false);`
        
___
Use `app-debug.apk` file to test sdk festure in Stage.
