package com.roomra.roomerAndroid.roomerandroid;

/**
 * Created by jeremyclifton on 7/24/14.
 */
public class ModularPost {
    private String markText;
    private String topicText;
    private String author;
    private int profilePhotoId;
    private int contentPhotoId;

    public ModularPost(String markText, String author, String topic, Integer profilePhotoId, Integer contentPhotoId) {
        this.markText = markText;
        this.topicText = topic;
        this.author = author;
        this.profilePhotoId = profilePhotoId;
        this.contentPhotoId = contentPhotoId;

    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setMarkText(String markText) {
        this.markText = markText;
    }

    public String getMarkText() {
        return markText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    public String getTopicText() {
        return topicText;
    }

    public int getProfilePhotoId() {
        return profilePhotoId;
    }

    public void setProfilePhotoId(int profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }

    public void setContentPhotoId(int contentPhotoId) {
        this.contentPhotoId = contentPhotoId;
    }

    public int getContentPhotoId() {
        return contentPhotoId;
    }
}
