package com.bm.main.fpl.templates;

/**
 * Created by sarifhidayat on 2019-07-09.
 **/

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
public class ColorAnimator {
    public static ObjectAnimator ofColor(Object target, String propertyName, int from, int to) {
        return ObjectAnimator.ofObject(target, propertyName, new ColorEvaluator(), from, to);
    }

    public static ObjectAnimator ofColor(Object target, String propertyName, int to) {
        return ObjectAnimator.ofObject(target, propertyName, new ColorEvaluator(), to);
    }

    public static ObjectAnimator ofBackgroundColor(View target, int from, int to) {
        return ObjectAnimator.ofObject(new ViewBackgroundWrapper(target), "backgroundColor", new ColorEvaluator(), from, to);
    }

    public static ObjectAnimator ofBackgroundColor(View target, int to) {
        return ObjectAnimator.ofObject(new ViewBackgroundWrapper(target), "backgroundColor", new ColorEvaluator(), to);
    }

    private static class ColorEvaluator implements TypeEvaluator<Integer> {

        @Override
        public Integer evaluate(float fraction, Integer start, Integer end) {
            int startA, startR, startG, startB;
            int startValue = start != null ? start : 0;
            int endValue = end != null ? end : 0;
            int aDelta = (int) ((Color.alpha(endValue) - (startA = Color.alpha(startValue))) * fraction);
            int rDelta = (int) ((Color.red(endValue) - (startR = Color.red(startValue))) * fraction);
            int gDelta = (int) ((Color.green(endValue) - (startG = Color.green(startValue))) * fraction);
            int bDelta = (int) ((Color.blue(endValue) - (startB = Color.blue(startValue))) * fraction);
            return Color.argb(startA + aDelta, startR + rDelta, startG + gDelta, startB + bDelta);
        }
    }

    /**
     * Helper class which allows retrieval of a {@link View}'s background as a color.
     */
    public static class ViewBackgroundWrapper {

        private View mView;

        public ViewBackgroundWrapper(View v) {
            mView = v;
        }

        public int getBackgroundColor() {
            try {
                return ((ColorDrawable) mView.getBackground()).getColor();
            } catch (ClassCastException cce) {
                // The background isn't a ColorDrawable (could be BitmapDrawable etc.) - throw a more descriptive error
                throw new IllegalStateException(
                        String.format("Attempt to read View background color when background isn't a ColorDrawable (is %s instead)",
                                mView.getBackground().getClass().getSimpleName()));
            }
        }

        public void setBackgroundColor(int color) {
            mView.setBackgroundColor(color);
        }

    }
}
