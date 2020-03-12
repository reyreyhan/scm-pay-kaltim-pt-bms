package com.bm.main.fpl.templates;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.util.AttributeSet;




/**
 * Created by sarifhidayat on 12/5/17.
 */


public class CustomFontCheckBox extends AppCompatCheckBox {


    public CustomFontCheckBox(@NonNull Context context) {
            super(context);
          //  init(context);
        }

    public CustomFontCheckBox(@NonNull Context context, AttributeSet attrs) {
            super(context, attrs);
          //  init(context);
        }

    public CustomFontCheckBox(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
          //  init(context);
        }
//    private void init(Context context){
//        //set your typeface here.
////        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
//        Typeface font = ResourcesCompat.getFont(context,R.font.roboto_light);
//        setTypeface(font);
//        setTextColor(context.getResources().getColor(R.color.text_secondary));
//        setPadding(5,5,25,5);
//        setButtonDrawable(ContextCompat.getDrawable(context,R.drawable.checkbox_selector));
//    }
}

