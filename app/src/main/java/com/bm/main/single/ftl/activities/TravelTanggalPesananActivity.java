package com.bm.main.single.ftl.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.decorator.HighlightWeekendsDecorator;
import com.bm.main.fpl.templates.decorator.HighlightWeekendsSatDecorator;
import com.bm.main.fpl.templates.decorator.OneDayDecorator;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TravelTanggalPesananActivity extends BaseActivity implements OnDateSelectedListener, OnMonthChangedListener {
    private static final String TAG = TravelTanggalPesananActivity.class.getSimpleName();
    String initTanggal;
    String initValue;
    DateTimeFormatter formatter,formatterShow;

    MaterialCalendarView widget;
     OneDayDecorator oneDayDecorator;
    Calendar c;
    private CalendarDay awalDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_tanggal_pesanan_activity);
        initTanggal = getIntent().getStringExtra("initTanggal");
        initValue = getIntent().getStringExtra("initValue");
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Pilih Tanggal");
        init(1);
        oneDayDecorator = new OneDayDecorator(this);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", SBFApplication.config.locale);
        formatterShow = DateTimeFormatter.ofPattern("dd MMM yyyy",SBFApplication.config.locale);
//        Date currentDate = new Date();
        widget = findViewById(R.id.calendarView);
        // int showOtherDates = widget.getShowOtherDates();
        // MaterialCalendarView.showOtherMonths(showOtherDates);
//        c = Calendar.getInstance(config.locale);

//        c.setTime(currentDate);
        LocalDate currentDate = LocalDate.now();
        Log.d(TAG, "onCreate: "+initValue+" "+initTanggal);
        // Date adultdate = c.getTime();
        if(initValue == null || initValue.equals("")) {
            widget.setDateSelected(CalendarDay.today(), true);
        }else {
            Calendar cal = Calendar.getInstance(SBFApplication.config.locale);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);
//            try {
//                cal.setTime(sdf.parse(initValue));// all done
            LocalDate ld=LocalDate.parse(initValue);
            widget.setDateSelected(CalendarDay.from(ld), true);
//            } catch (ParseException e) {
//                widget.setDateSelected(CalendarDay.today(), true);
//            }
        }
//        widget.state().edit()
//                .setMinimumDate(CalendarDay.today())
//                .commit();


        if (initTanggal.equals("awal") ) {
//            c.add(Calendar.DAY_OF_YEAR, -90);
            final LocalDate min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusMonths(3);

            widget.state().edit()
                    // .setMinimumDate(c.getTime())
                    .setMinimumDate(min)
                    .commit();
            widget.state().edit()
                    .setMaximumDate(CalendarDay.today())
                    .commit();
        }

        if (initTanggal.equals("akhir") ) {
            // c.add(Calendar.DAY_OF_YEAR, -90);
//            LocalDate max =LocalDate.now();
            LocalDate ld=LocalDate.parse(initValue);
            LocalDate  min = LocalDate.of(ld.getYear(),ld.getMonthValue(),ld.getDayOfMonth());
            LocalDate max = LocalDate.of(ld.getYear(),ld.getMonthValue(),ld.getDayOfMonth()).plusMonths(1);
           // final LocalDate min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue()-3,currentDate.getDayOfMonth());

            if(ld.isEqual(currentDate)|| max.isAfter(currentDate) ){
                widget.state().edit()
                        .setMaximumDate(CalendarDay.today())
                        .commit();
//            }else if(max>=currentDate){
//                widget.state().edit()
//                        .setMaximumDate(CalendarDay.today())
//                        .commit();
//
            }else{
                widget.state().edit()
                        .setMaximumDate(max)
                        .commit();
            }
//            if(awalDate==CalendarDay.today()){
//                widget.state().edit()
//                        .setMinimumDate(CalendarDay.today())
//                        .commit();
//            }else {
            widget.state().edit()
//                    .setMinimumDate(c.getTime())
                    .setMinimumDate(min)
                    .commit();
            // }
        }
        widget.setContentDescription("Hari ini");
        widget.addDecorators(new HighlightWeekendsSatDecorator(), new HighlightWeekendsDecorator(), oneDayDecorator);

        widget.setOnDateChangedListener(this);

        widget.setOnMonthChangedListener(this);
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
        // Intent intent=new Intent(SearchFlightActivity.this, HomeActivity.class);

        //  startActivity(intent);
        Intent intent=new Intent();

        setResult(RESULT_CANCELED,intent);
        finish();
        //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        if (selected) {
            //  c.add(date.getDate(), -30);
            // Log.d(TAG, "onDateSelected: "+  );
//            if (initTanggal.equals("awal") ) {
//                awalDate=date;
//            }


            Intent i = new Intent();

            i.putExtra("date", getSelectedDatesString(date));

            i.putExtra("dateShow", getSelectedDatesStringShow(date));
            setResult(AppCompatActivity.RESULT_OK, i);
            finish();
            //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);

        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }
    private String getSelectedDatesString(@Nullable CalendarDay date) {
        //   CalendarDay date = widget.getSelectedDate();
        //  CalendarDay date = widget.setSelectedDate(CalendarDay.today());
        if (date == null) {
            return "No Selection";
        }
        return formatter.format(date.getDate());
    }

    private String getSelectedDatesStringShow(@Nullable CalendarDay date) {
        //  CalendarDay date = widget.getSelectedDate();
        //  CalendarDay date = widget.setSelectedDate(CalendarDay.today());
        if (date == null) {
            return "No Selection";
        }
        return formatterShow.format(date.getDate());
    }
}
