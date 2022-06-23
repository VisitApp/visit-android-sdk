package com.example.video_call.activity;

public interface VideoCallListener {
    void startVideoCall(int sessionId, int consultationId, String authToken);
    void closeView();
    void askForLocationPermission();
}
