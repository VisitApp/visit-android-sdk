package com.example.video_call.model

import androidx.annotation.Keep

@Keep
data class ErrorModel(
    val errorMessage: String?,
    val message: String?,
    val status: Int
)