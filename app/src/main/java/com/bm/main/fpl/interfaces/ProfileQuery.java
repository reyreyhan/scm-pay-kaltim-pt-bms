package com.bm.main.fpl.interfaces;

import android.provider.ContactsContract;

/**
 * Created by sarifhidayat on 11/11/18.
 **/
public interface ProfileQuery {
    String[] PROJECTION = {
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
    };

    int ADDRESS = 0;
    int IS_PRIMARY = 1;
}
