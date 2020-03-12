package com.bm.main.fpl.staggeredgridApp;

public class ItemObjectsMenuBeranda {
    private String name;
    private int photo;
    private String urlPhoto;
    private int last;

    public ItemObjectsMenuBeranda(String name, int photo, int last) {
        this.name = name;
        this.photo = photo;
        this.last = last;
    }

    public ItemObjectsMenuBeranda(String name, String photo, int last) {
        this.name = name;
        this.urlPhoto = photo;
        this.last= last;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
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

    public boolean isComingSoon = false;
    public boolean isPromo = false;
    public boolean isHot = false;
    public boolean isNew = false;
    public boolean isUseIdOutlet = false;
    public String urlProduk = "";
}
