package com.bm.main.single.ftl.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.decorator.HighlightWeekendsDecorator;
import com.bm.main.fpl.templates.decorator.HighlightWeekendsSatDecorator;
import com.bm.main.fpl.templates.decorator.OneDayDecorator;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TravelTanggalActivity extends BaseActivity implements OnDateSelectedListener, OnMonthChangedListener {
    private static final String TAG = TravelTanggalActivity.class.getSimpleName();
    //    SimpleDateFormat formatter,formatterFlight,formatterShow,formatterShowDate,formatterShowMonth,formatterShowDayYear,formatterBornShow;
    DateTimeFormatter formatter,formatterFlight,formatterShow,formatterShowDate,formatterShowMonth,formatterShowDayYear,formatterBornShow;
    String initTanggal,initValue;
    MaterialCalendarView widget;
    private OneDayDecorator oneDayDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanggal);
        initTanggal = getIntent().getStringExtra("initTanggal");
        initValue = getIntent().getStringExtra("initValue");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pilih Tanggal");
        init(1);
        oneDayDecorator = new OneDayDecorator(this);
//         formatter = new SimpleDateFormat("yyyy-MM-dd",config.locale);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", SBFApplication.config.locale);
        formatterFlight = DateTimeFormatter.ofPattern("MM/dd/yyyy",SBFApplication.config.locale);


        formatterShow = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", SBFApplication.config.locale);
        formatterShowDate = DateTimeFormatter.ofPattern("dd",SBFApplication.config.locale);
        formatterShowMonth = DateTimeFormatter.ofPattern("MMMM",SBFApplication.config.locale);
        formatterShowDayYear = DateTimeFormatter.ofPattern("EEEE, yyyy",SBFApplication.config.locale);


        formatterBornShow = DateTimeFormatter.ofPattern("dd MMMM yyyy",SBFApplication.config.locale);

        LocalDate currentDate = LocalDate.now();
        Log.d(TAG, "onCreate: " + CalendarDay.today());
        widget = findViewById(R.id.calendarView);
        int showOtherDates = widget.getShowOtherDates();
//        Calendar c = Calendar.getInstance(config.locale);
//        CalendarDay c=CalendarDay.today();
        //c.setTime(currentDate);
        // Date adultdate = c.getTime();
//        LocalDateTime adultdate = c.getTime();
        if (initTanggal.equals("bornAdultFlight")) {
//            c.add(Calendar.YEAR, -12);
//            c.getYear()-12;
//            final LocalDate min = LocalDate.of(currentDate.getYear(), Month.JANUARY, 1);
            final LocalDate maxAdult = LocalDate.of(currentDate.getYear()-12,currentDate.getMonthValue(),currentDate.getDayOfMonth());
            widget.state().edit()
//                    .setMaximumDate(CalendarDay.from(adultdate))
                    .setMaximumDate(maxAdult)
                    .commit();
            MaterialCalendarView.showOutOfRange(showOtherDates);
        } else {
            if(initValue == null || initValue.equals("")) {
                widget.setDateSelected(CalendarDay.today(), true);

            }else {

                //Calendar cal = Calendar.getInstance(config.locale);

                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", config.locale);
//                DateTimeFormatter sdf=new DateTimeFormatter().
                LocalDate ld=LocalDate.parse(initValue);
//                try {
                //  cal.setTime(sdf.parse(initValue));// all done

                Log.d(TAG, "onCreate: "+initValue+" "+CalendarDay.from(ld).toString());
                    widget.setDateSelected(CalendarDay.from(ld), true);

//                } catch (ParseException e) {
//                    widget.setDateSelected(CalendarDay.today(), true);
//                }
            }
            widget.state().edit()
                    .setMinimumDate(CalendarDay.today())
                    .commit();
//            if (initTanggal.equals("hotelDatePicker")) {
//                c.add(Calendar.YEAR, 1);
//                widget.state().edit()
//                        .setMaximumDate(c.getTime())
//                        .commit();
//            }
            if (initTanggal.equals("pergi")) {
                LocalDate max = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth()).plusDays(1);
               // widget.setSelectedDate(max);
            }else
            if (initTanggal.equals("arrival_dateTrain") || initTanggal.equals("daparture_dateTrain")) {
//                c.add(Calendar.DAY_OF_YEAR, 90);
                final LocalDate max = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue()+3,currentDate.getDayOfMonth());
                widget.state().edit()
//                        .setMaximumDate(c.getTime())
                        .setMaximumDate(max)
                        .commit();
            }
//            widget.setContentDescription("Hari ini");
            widget.setContentDescriptionCalendar("Hari ini");

//            widget.setDateTextAppearance(R.string.hint_id_pelanggan);
            widget.addDecorators(new HighlightWeekendsSatDecorator(), new HighlightWeekendsDecorator(), oneDayDecorator);
//            widget.addDecorators(oneDayDecorator);
        }
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        if (selected) {
            Intent i = new Intent();
            if (initTanggal.equals("bornAdultFlight")) {
                i.putExtra("date", getSelectedDatesStringFlight(date));
            }else {
                i.putExtra("date", getSelectedDatesString(date));
            }

            if (initTanggal.equals("pergi")) {
                i.putExtra("dateShow", getSelectedDatesStringShow(date));
                i.putExtra("dateShowDate", getSelectedDatesStringShowDate(date));
                i.putExtra("dateShowMonth", getSelectedDatesStringShowMonth(date));
                i.putExtra("dateShowDayYear", getSelectedDatesStringShowDayYear(date));

            }else {
                i.putExtra("dateShow", getSelectedDatesStringShow(date));
            }
            setResult(Activity.RESULT_OK, i);
            finish();
            overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
        }
    }

    private String getSelectedDatesString(@Nullable CalendarDay date) {
        if (date == null) {
            return "No Selection";
        }
        //  FORMATTER.format(date.getDate()
        return formatter.format(date.getDate());
    }
    private String getSelectedDatesStringFlight(@Nullable CalendarDay date) {
        if (date == null) {
            return "No Selection";
        }
        return formatterFlight.format(date.getDate());
    }

    private String getSelectedDatesStringShow(@Nullable CalendarDay date) {
        if (date == null) {
            return "No Selection";
        }
        return formatterShow.format(date.getDate());
    }

    private String getSelectedDatesStringShowDate(@Nullable CalendarDay date) {
        if (date == null) {
            return "No Selection";
        }
        return formatterShowDate.format(date.getDate());
    }

    private String getSelectedDatesStringShowMonth(@Nullable CalendarDay date) {
        if (date == null) {
            return "No Selection";
        }
        return formatterShowMonth.format(date.getDate());
    }

    private String getSelectedDatesStringShowDayYear(@Nullable CalendarDay date) {
        if (date == null) {
            return "No Selection";
        }
        return formatterShowDayYear.format(date.getDate());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }
}
