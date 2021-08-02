package com.example.video_call.activity

import android.content.Context
import android.content.Intent
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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.video_call.R
import com.example.video_call.databinding.SdkWebView
import im.delight.android.webview.AdvancedWebView
import org.json.JSONException
import org.json.JSONObject

class SdkWebviewActivity : AppCompatActivity(), AdvancedWebView.Listener,
    VideoCallListener {

    lateinit var binding: SdkWebView
    lateinit var url: String
    var isDebug: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sdk)
        binding.infoView.setVisibility(View.GONE)
        url = intent.extras!!.getString("url")!!
        isDebug = intent.extras!!.getBoolean("isDebug");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        binding.webview.setListener(this, this)
        binding.webview.setGeolocationEnabled(false)
        binding.webview.setMixedContentAllowed(true)
        binding.webview.setCookiesEnabled(true)
        binding.webview.setThirdPartyCookiesEnabled(true)
        binding.webview.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
//                Toast.makeText(WVActivity.this, "Finished loading " + url, Toast.LENGTH_SHORT).show();
            }
        })
        val webAppInterface: WebAppInterface = WebAppInterface(this)
        webAppInterface.setListener(this)
        binding.webview.addJavascriptInterface(webAppInterface, "Android")

        binding.webview.setWebChromeClient(object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                //                Toast.makeText(WVActivity.this, title, Toast.LENGTH_SHORT).show();
            }
        })
        //Log.d(TAG, "onCreate: " + Visit.getInstance().getSessionManager().getAuthToken());
        //Log.d(TAG, "onCreate: " + Visit.getInstance().getSessionManager().getAuthToken());


        binding.webview.loadUrl(url)
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
        binding.webview.onActivityResult(requestCode, resultCode, intent)
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


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (binding.webview.canGoBack()) {
                        binding.webview.goBack()
                        Log.d("Webview Url", binding.webview.url.toString())
                        if (binding.webview.url!!.contains("home")) {
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


}