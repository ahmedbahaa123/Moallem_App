package com.bahaa.task.video.model;

import com.google.firebase.firestore.DocumentId;

public class VideoModel {

    @DocumentId
    private String videoId;
    private String url;
    private String image;

    public VideoModel() {

    }

    public VideoModel(String videoId, String url, String image) {
        this.videoId = videoId;
        this.url = url;
        this.image = image;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
