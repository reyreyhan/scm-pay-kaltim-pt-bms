package com.bm.main.fpl.templates.choosephotohelper.callback;

@FunctionalInterface
public interface ChoosePhotoCallback<T> {

    void onChoose(T photo);
}
