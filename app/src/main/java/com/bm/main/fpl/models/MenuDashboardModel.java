package com.bm.main.fpl.models;

/**
 * Created by sarifhidayat on 3/16/18.
 **/

public class MenuDashboardModel {
    private String name;
    private int photo;

    public MenuDashboardModel(String name, int photo) {
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
