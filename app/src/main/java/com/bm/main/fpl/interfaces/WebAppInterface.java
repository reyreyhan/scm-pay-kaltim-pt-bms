package com.bm.main.fpl.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.bm.main.fpl.templates.DialogBox;

/**
 * Created by sarifhidayat on 3/11/19.
 **/
public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a Message box from the web page */
    @JavascriptInterface
    public void androidAlert(String message) {
        DialogBox dbx = new DialogBox();
        dbx.dialogBox(message, "I get it", "",mContext);
    }
}
