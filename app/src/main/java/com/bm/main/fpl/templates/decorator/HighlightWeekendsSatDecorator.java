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
public class HighlightWeekendsSatDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();

    @NonNull
    private final Drawable highlightDrawableSat;

    private static final int colorSat = Color.parseColor("#64B5F6");

    public HighlightWeekendsSatDecorator() {

        highlightDrawableSat = new ColorDrawable(colorSat);
    }

    @Override
    public boolean shouldDecorate(@NonNull CalendarDay day) {
        final DayOfWeek weekDay = day.getDate().getDayOfWeek();
        return weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY;
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLUE));
       // view.setBackgroundDrawable(highlightDrawableSat);
    }
}
