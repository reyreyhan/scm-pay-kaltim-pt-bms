package com.bm.main.fpl.templates.mask_formatter;


/**
 * Created by sarifhidayat on 2/15/19.
 **/

import android.text.InputType;
public class CharInputType {

    public static int getKeyboardTypeForNextChar(char nextChar) {
        if (Character.isDigit(nextChar)) {
            return InputType.TYPE_CLASS_NUMBER;
        }

        switch (nextChar) {
            case 'd':
                return InputType.TYPE_CLASS_NUMBER;
            case 'A':
            case 'Z':
            case '%':
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
        }

        return InputType.TYPE_CLASS_TEXT;
    }

}
