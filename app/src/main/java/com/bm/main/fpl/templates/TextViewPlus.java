package com.bm.main.fpl.templates;

/**
 * Created by sarifhidayat on 11/15/17.
 */

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import android.util.AttributeSet;

import com.bm.main.scm.R;

public class TextViewPlus extends AppCompatTextView {
    private static final String TAG = TextViewPlus.class.getSimpleName();

    public TextViewPlus(@NonNull Context context) {
        super(context);
        init(context);

    }

    public TextViewPlus(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
      //  setCustomFont(context, attrs);
     //   setCustomColorFont(context, attrs);
    }

    public TextViewPlus(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
      //  setCustomFont(context, attrs);
      //  setCustomColorFont(context, attrs);
    }

//    private void setCustomFont(Context ctx, AttributeSet attrs) {
//        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
//        //String customFont = a.getString(R.styleable.TextViewPlus_customFontPlus);
//      //  setCustomFont(ctx, customFont);
//
//        a.recycle();
//    }
//    private void setCustomColorFont(Context ctx, AttributeSet attrs) {
//        TypedArray da = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
//        int customColor = da.getColor(R.styleable.TextViewPlus_customColor,0);
//       setCustomColor(ctx,customColor);
//
//        da.recycle();
//    }

//    public boolean setCustomFont(Context ctx, String asset) {
//        Typeface tf = null;//ResourcesCompat.getFont(ctx,R.font.roboto_light);
//        try {
//            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
//
//        } catch (Exception e) {
//            Log.e(TAG, "Could not get typeface: "+e.getMessage());
//           // tf = ResourcesCompat.getFont(ctx,R.font.roboto_light);
//            return false;
//        }
//
//        setTypeface(tf);
//        setTextColor(ctx.getResources().getColor(R.color.text_secondary));
//        return true;
//    }

//    public boolean setCustomColor(Context ctx, int color) {
//
//        setTextColor(ctx.getResources().getColor(color));
//        return true;
//    }

    private void init(@NonNull Context context) {
        //set your typeface here.
//        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        Typeface font = ResourcesCompat.getFont(context, R.font.roboto_light);
        setTypeface(font);
    }

}
