package com.bm.main.fpl.staggeredgridApp;

public class MenuPerbankanModel {
    private String name;
    private int photo;

    public MenuPerbankanModel(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
