package com.example.video_call.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.video_call.R
import im.delight.android.webview.AdvancedWebView
import org.json.JSONException
import org.json.JSONObject

class SdkWebviewActivity : AppCompatActivity(), AdvancedWebView.Listener,
    VideoCallListener {

    lateinit var webview: AdvancedWebView
    lateinit var infoView: LinearLayout

    lateinit var url: String
    var isDebug: Boolean = false
    val LOCATION_PERMISSION_REQUEST_CODE = 787


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk)
        webview = findViewById(R.id.webview)
        infoView = findViewById(R.id.infoView)


        infoView.setVisibility(View.GONE)
        url = intent.extras!!.getString("url")!!
        isDebug = intent.extras!!.getBoolean("isDebug");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        webview.setListener(this, this)
        webview.setGeolocationEnabled(true)
        webview.setMixedContentAllowed(true)
        webview.setCookiesEnabled(true)
        webview.setThirdPartyCookiesEnabled(true)
        webview.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
//                Toast.makeText(WVActivity.this, "Finished loading " + url, Toast.LENGTH_SHORT).show();
            }
        })
        val webAppInterface: WebAppInterface = WebAppInterface(this)
        webAppInterface.setListener(this)
        webview.addJavascriptInterface(webAppInterface, "Android")

        webview.setWebChromeClient(object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                //                Toast.makeText(WVActivity.this, title, Toast.LENGTH_SHORT).show();
            }
        })
        //Log.d(TAG, "onCreate: " + Visit.getInstance().getSessionManager().getAuthToken());
        //Log.d(TAG, "onCreate: " + Visit.getInstance().getSessionManager().getAuthToken());


        webview.loadUrl(url)
//        else {
//            mWebView.loadData(html_2,"text/html; charset=utf-8", "UTF-8");
//        }

    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {

    }

    override fun onPageFinished(url: String?) {

    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {

    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {

        try {
            val uri = Uri.parse(url)
            startActivity(Intent(Intent.ACTION_VIEW, uri))

        } catch (e: Exception) {

        }

    }

    override fun onExternalPageRequest(url: String?) {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        webview.onActivityResult(requestCode, resultCode, intent)
    }


    class WebAppInterface
    /**
     * Instantiate the interface and set the context
     */ internal constructor(var mContext: Context) {
        private var listener: VideoCallListener? = null
        fun setListener(listener: VideoCallListener?) {
            this.listener = listener
        }

        @JavascriptInterface
        @Throws(JSONException::class)
        fun initiateVideoCall(sessionId: Int, consultationId: Int, authToken: String) {

            // listener.transactionSuccess(decodeString(response));
            listener!!.startVideoCall(sessionId, consultationId, authToken)
        }


        @Throws(JSONException::class)
        private fun decodeString(response: String): JSONObject {
            return if (Build.VERSION.SDK_INT >= 24) {
                JSONObject(Html.fromHtml(response, Html.FROM_HTML_MODE_LEGACY).toString())
            } else {
                JSONObject(Html.fromHtml(response).toString())
            }
        }

        @JavascriptInterface
        @Throws(JSONException::class)
        fun closeView() {
            Log.d("mytag","closeView() called")
            listener!!.closeView()
        }

        @JavascriptInterface
        fun getLocationPermissions() {
            Log.d("mytag", "getLocationPermissions() called.")
            listener!!.askForLocationPermission()
        }

    }

    override fun startVideoCall(sessionId: Int, consultationId: Int, authToken: String?) {

        val intent = Intent(
            this,
            TwillioVideoCallActivity::class.java
        )
        intent.putExtra("isDebug", isDebug)
        intent.putExtra("sessionId", sessionId)
        intent.putExtra("consultationId", consultationId)
        intent.putExtra("authToken", authToken)
        startActivity(intent)
    }

    override fun closeView() {
        finish()
    }

    override fun askForLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webview.canGoBack()) {
                        webview.goBack()
                        Log.d("Webview Url", webview.url.toString())
                        if (webview.url!!.contains("home")) {
                            finish()
                        }
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {}
        }
    }


}