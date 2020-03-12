package com.bm.main.fpl.templates.slider.library.Transformers;

/**
 * Created by daimajia on 14-5-29.
 */
import androidx.annotation.NonNull;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class AccordionTransformer extends BaseTransformer {

    @Override
    protected void onTransform(@NonNull View view, float position) {
        ViewHelper.setPivotX(view,position < 0 ? 0 : view.getWidth());
        ViewHelper.setScaleX(view,position < 0 ? 1f + position : 1f - position);
    }

}