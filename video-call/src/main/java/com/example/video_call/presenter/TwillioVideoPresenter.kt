package com.example.video_call.presenter

import com.example.video_call.network.ApiService
import com.example.video_call.util.ErrorHandler
import com.example.video_call.view.TwillioVideoView
import kotlinx.coroutines.*

class TwillioVideoPresenter(var apiService: ApiService, var twillioVideoView: TwillioVideoView) {

    var TAG = this.javaClass.simpleName

    var coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        FirebaseCrashlytics.getInstance().recordException(throwable);

        val errorMessage: String? = ErrorHandler().parseException(throwable)

        CoroutineScope(Dispatchers.Main).launch {
            errorMessage?.let {
                twillioVideoView.setError(it)
            }
        }
    }

    fun getRoomDetails(sessionId: Int, consultationId: Int) {
        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
            var response = apiService.getRoomDetails(sessionId, consultationId)

            withContext(Dispatchers.Main) {
                if (response.message == "success") {
                    twillioVideoView.roomDetails(response.roomDetails)
                } else {
                    response.errorMessage?.let {
                        twillioVideoView.setError(response.errorMessage)
                    }
                }
            }
        }
    }

}


