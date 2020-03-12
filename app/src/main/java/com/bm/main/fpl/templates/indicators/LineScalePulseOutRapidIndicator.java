package com.bm.main.fpl.templates.indicators;

/**
 * Created by sarifhidayat on 10/27/17.
 */
import android.animation.ValueAnimator;
import androidx.annotation.NonNull;

import java.util.ArrayList;
public class LineScalePulseOutRapidIndicator extends LineScaleIndicator {

    @NonNull
    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators=new ArrayList<>();
        long[] delays=new long[]{400,200,0,200,400};
        for (int i = 0; i < 5; i++) {
            final int index=i;
            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.4f,1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                    scaleYFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
        }
        return animators;
    }
}
