package com.example.video_call.network

import com.example.video_call.model.RoomDetails
import com.example.video_call.model.SessionRoom
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("video-call/video-session-info")
    suspend fun getRoomDetails(
        @Query("sessionId") sessionId: Int,
        @Query("consultationId") consultationId: Int
    ): SessionRoom


}