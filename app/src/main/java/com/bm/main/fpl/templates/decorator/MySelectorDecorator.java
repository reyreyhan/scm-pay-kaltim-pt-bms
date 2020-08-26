package com.bm.main.fpl.templates.decorator;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.bm.main.scm.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;


/**
 * Use da custom selector
 */
public class MySelectorDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public MySelectorDecorator(@NonNull AppCompatActivity context) {
        drawable = context.getResources().getDrawable(R.drawable.today);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
