package com.bm.main.fpl.templates.decorator;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.graphics.fonts.FontStyle;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.widget.TextView;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.threeten.bp.LocalDate;
//import java.util.Date;

/**
 * Decorate da day by making the text big and bold
 */
public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;
   // private final Drawable drawable;
    Context mContext;
    public OneDayDecorator(Context context) {
        date = CalendarDay.today();
        mContext=context;
       // drawable = context.getResources().getDrawable(R.drawable.today);
    }

    @Override
    public boolean shouldDecorate(@NonNull CalendarDay day) {
//        return date != null && day.equals(date);
        return day.equals(date);
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
//        TextView text =new TextView(mContext);
//        text.setText("H");
//        text.setTextColor(ContextCompat.getColor(mContext,R.color.md_orange_500));
//        text.setTextSize(R.dimen._10sdp);
//        view.addSpan(text);
      //  view.addSpan(new FontStyle(R.font.roboto));
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.2f));
      //  view.setBackgroundDrawable(drawable);
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(LocalDate date) {
        this.date = CalendarDay.from(date);

    }
}
