package com.bm.main.fpl.templates.slider.library.Transformers;

import androidx.annotation.NonNull;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class StackTransformer extends BaseTransformer {

	@Override
	protected void onTransform(@NonNull View view, float position) {
		ViewHelper.setTranslationX(view,position < 0 ? 0f : -view.getWidth() * position);
	}

}
