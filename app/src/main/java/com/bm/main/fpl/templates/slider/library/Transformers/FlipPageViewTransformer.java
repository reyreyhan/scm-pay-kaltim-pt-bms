package com.bm.main.fpl.templates.slider.library.Transformers;

import android.os.Build;
import androidx.annotation.NonNull;
import android.view.View;

import com.bm.main.fpl.templates.slider.library.Tricks.ViewPagerEx;
import com.nineoldandroids.view.ViewHelper;

public class FlipPageViewTransformer extends BaseTransformer {

    @Override
    protected void onTransform(@NonNull View view, float position) {
        float percentage = 1 - Math.abs(position);
        if(Build.VERSION.SDK_INT >= 13){
            view.setCameraDistance(12000);
        }
        setVisibility(view, position);
        setTranslation(view);
        setSize(view, position, percentage);
        setRotation(view, position, percentage);
    }

    private void setVisibility(@NonNull View page, float position) {
        if (position < 0.5 && position > -0.5) {
            page.setVisibility(View.VISIBLE);
        } else {
            page.setVisibility(View.INVISIBLE);
        }
    }

    private void setTranslation(@NonNull View view) {
        ViewPagerEx viewPager = (ViewPagerEx) view.getParent();
        int scroll = viewPager.getScrollX() - view.getLeft();
        ViewHelper.setTranslationX(view,scroll);
    }

    private void setSize(@NonNull View view, float position, float percentage) {
        ViewHelper.setScaleX(view,(position != 0 && position != 1) ? percentage : 1);
        ViewHelper.setScaleY(view,(position != 0 && position != 1) ? percentage : 1);
    }

    private void setRotation(@NonNull View view, float position, float percentage) {
        if (position > 0) {
            ViewHelper.setRotationY(view,-180 * (percentage + 1));
        } else {
            ViewHelper.setRotationY(view,180 * (percentage + 1));
        }
    }
}