package com.bm.main.fpl.models;

/**
 * Created by sarifhidayat on 7/24/17.
 */

public class FCMModel {

    private String title;
    private String message;
    private String image;

    public FCMModel(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
