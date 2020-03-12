/**
 * Copyright 2016 Keepsafe Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain da copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bm.main.fpl.templates.taptargetview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import androidx.annotation.NonNull;

/**
 * A small wrapper around {@link ValueAnimator} to provide da client-like interface
 */
class FloatValueAnimatorBuilder {
  final ValueAnimator animator;

  EndListener endListener;

  interface UpdateListener {
    void onUpdate(float lerpTime);
  }

  interface EndListener {
    void onEnd();
  }

  protected FloatValueAnimatorBuilder() {
    this(false);
  }

  protected FloatValueAnimatorBuilder(boolean reverse) {
    if (reverse) {
      this.animator = ValueAnimator.ofFloat(1.0f, 0.0f);
    } else {
      this.animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    }
  }

  @NonNull
  public FloatValueAnimatorBuilder delayBy(long millis) {
    animator.setStartDelay(millis);
    return this;
  }

  @NonNull
  public FloatValueAnimatorBuilder duration(long millis) {
    animator.setDuration(millis);
    return this;
  }

  @NonNull
  public FloatValueAnimatorBuilder interpolator(TimeInterpolator lerper) {
    animator.setInterpolator(lerper);
    return this;
  }

  @NonNull
  public FloatValueAnimatorBuilder repeat(int times) {
    animator.setRepeatCount(times);
    return this;
  }

  @NonNull
  public FloatValueAnimatorBuilder onUpdate(@NonNull final UpdateListener listener) {
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(@NonNull ValueAnimator animation) {
        listener.onUpdate((float) animation.getAnimatedValue());
      }
    });
    return this;
  }

  @NonNull
  public FloatValueAnimatorBuilder onEnd(final EndListener listener) {
    this.endListener = listener;
    return this;
  }

  public ValueAnimator build() {
    if (endListener != null) {
      animator.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          endListener.onEnd();
        }
      });
    }

    return animator;
  }
}
