package com.example.video_call.view;

import com.example.video_call.model.RoomDetails;

public interface TwillioVideoView {

    void roomDetails(RoomDetails roomDetails);
    void setError(String message);
}

