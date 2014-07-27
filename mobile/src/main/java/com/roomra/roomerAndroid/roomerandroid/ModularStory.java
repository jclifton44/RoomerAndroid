package com.roomra.roomerAndroid.roomerandroid;

/**
 * Created by jeremyclifton on 7/24/14.
 */
public class ModularStory {
    private String markText;
    private String topicText;
    private int contentPhotoId;

    public ModularStory(String markText, String topic, Integer contentPhotoId) {
        this.markText = markText;
        this.topicText = topic;
        this.contentPhotoId = contentPhotoId;

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

    public void setContentPhotoId(int contentPhotoId) {
        this.contentPhotoId = contentPhotoId;
    }

    public int getContentPhotoId() {
        return contentPhotoId;
    }
}
