package com.example.huypham.assigment3_fx00066_youtubeplaylist.model;

import java.io.Serializable;

public class Video implements Serializable {
    private String videoID;
    private String videoTitle;
    private String videoDescription;
    private String videoThumbnails;

    private String videoURL;
    public static String TABLE_NAME ="video";
    public static String COLUMN_ID = "videoID";

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }



    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
    public String getVideoThumbnails() {
        return videoThumbnails;
    }
    public void setVideoThumbnails(String videoThumbnails) {
        this.videoThumbnails = videoThumbnails;
    }
}
