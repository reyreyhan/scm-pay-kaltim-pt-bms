package com.bm.main.materialedittext;

/**
 * Created by sarifhidayat on 11/15/17.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;

import com.bm.main.pos.R;

public class EditTextFontCustom extends AppCompatEditText {
    private static final String TAG = "TextView";

    public EditTextFontCustom(Context context) {
        super(context);
    }

    public EditTextFontCustom(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public EditTextFontCustom(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(@NonNull Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        String customFont = a.getString(R.styleable.MaterialEditText_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(@NonNull Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: "+e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }
}
