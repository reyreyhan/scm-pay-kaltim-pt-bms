package com.bm.main.fpl.templates.TextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewRobotoCondensedBold extends AppCompatTextView {
    public TextViewRobotoCondensedBold(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf"));
    }

    public TextViewRobotoCondensedBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf"));
    }

    public TextViewRobotoCondensedBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf"));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
