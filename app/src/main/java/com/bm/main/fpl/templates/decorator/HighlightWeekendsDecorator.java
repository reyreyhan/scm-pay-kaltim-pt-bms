package com.bm.main.fpl.templates.decorator;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;
import java.util.Calendar;

/**
 * Highlight Saturdays and Sundays with da background
 */
public class HighlightWeekendsDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();
    @NonNull
    private final Drawable highlightDrawable;

    private static final int color = Color.parseColor("#D32F2F");


    public HighlightWeekendsDecorator() {
        highlightDrawable = new ColorDrawable(color);

    }

    @Override
    public boolean shouldDecorate(@NonNull CalendarDay day) {
        final DayOfWeek weekDay = day.getDate().getDayOfWeek();
        return weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY;
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
     //   view.setBackgroundDrawable(highlightDrawable);
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}
