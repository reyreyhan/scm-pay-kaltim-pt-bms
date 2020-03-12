package com.bm.main.fpl.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.templates.decorator.HighlightWeekendsDecorator;
import com.bm.main.fpl.templates.decorator.HighlightWeekendsSatDecorator;
import com.bm.main.fpl.templates.decorator.OneDayDecorator;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

//import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class TanggalActivity extends BaseActivity implements OnDateSelectedListener {
    private static final String TAG = TanggalActivity.class.getSimpleName();
    String initTanggal;
    String initValue;
    DateTimeFormatter formatter,formatterShow;

    MaterialCalendarView widget;
     OneDayDecorator oneDayDecorator;
    private int initTabs;
    LocalDate awalDate;

    //     Calendar c;
//    private CalendarDay awalDate;
//LocalDate min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanggal);
        initTabs = getIntent().getIntExtra("initTabs",0);
        initTanggal = getIntent().getStringExtra("initTanggal");
        initValue = getIntent().getStringExtra("initValue");
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Pilih Tanggal");
        init(1);
        oneDayDecorator = new OneDayDecorator(this);
        // formatter = new SimpleDateFormat("yyyy-MM-dd",config.locale);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", SBFApplication.config.locale);
        //  formatterShow = new SimpleDateFormat("dd MMM yyyy",config.locale);
        formatterShow = DateTimeFormatter.ofPattern("dd MMM yyyy",SBFApplication.config.locale);
//        Date currentDate = new Date();
        LocalDate currentDate = LocalDate.now();
        widget = findViewById(R.id.calendarView);
        // int showOtherDates = widget.getShowOtherDates();
        // MaterialCalendarView.showOtherMonths(showOtherDates);
//       c = Calendar.getInstance(config.locale);

//        c.setTime(currentDate);
        Log.d(TAG, "onCreate: "+initValue+" "+initTanggal);
        // Date adultdate = c.getTime();
        if(initValue == null || initValue.equals("")) {
            widget.setDateSelected(CalendarDay.today(), true);
        }else {
            // Calendar cal = Calendar.getInstance(config.locale);
            // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", config.locale);
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
            LocalDate min = null;
            if(initTabs==0){//transaksi
//                min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusWeeks(Integer.parseInt(PreferenceClass.getString(RConfig.DateRangeReport,"1")));
                min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusMonths(3);
            }else if(initTabs==1){//mutasi
                min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusMonths(3);
            }else if(initTabs==2){//komisi
                min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusMonths(3);
            }
            // LocalDate min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusWeeks(1);

            widget.state().edit()
                    .setMinimumDate(min)
                    .commit();
            widget.state().edit()
                    .setMaximumDate(CalendarDay.today())
                    .commit();
        }else

        if (initTanggal.equals("akhir") ) {
//            c.add(Calendar.DAY_OF_YEAR, -90);
            // LocalDate min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusWeeks(1);
//            currentDate =awalDate;
//            LocalDate now = null;

             LocalDate max =LocalDate.now();
            LocalDate ld=LocalDate.parse(initValue);
            LocalDate  min = LocalDate.of(ld.getYear(),ld.getMonthValue(),ld.getDayOfMonth());
            if(initTabs==0){//transaksi
           //     min = LocalDate.of(ld.getYear(),ld.getMonthValue(),ld.getDayOfMonth());
                max = LocalDate.of(ld.getYear(),ld.getMonthValue(),ld.getDayOfMonth()).plusWeeks(Integer.parseInt(PreferenceClass.getString(RConfig.DateRangeReport,"1")));
//                min =awalDate;// LocalDate.of(awalDate.getYear(),awalDate.getMonthValue(),awalDate.getDayOfMonth()).plusWeeks(Integer.parseInt(PreferenceClass.getString(RConfig.DateRangeReport,"1")));
            }else if(initTabs==1){//mutasi
//                min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusWeeks(Integer.parseInt(PreferenceClass.getString(RConfig.DateRangeMutasi,"1")));
                max = LocalDate.of(ld.getYear(),ld.getMonthValue(),ld.getDayOfMonth()).plusWeeks(Integer.parseInt(PreferenceClass.getString(RConfig.DateRangeMutasi,"1")));
            }else if(initTabs==2){//komisi
//                min = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).minusWeeks(Integer.parseInt(PreferenceClass.getString(RConfig.DateRangeKomisi,"1")));
                max = LocalDate.of(ld.getYear(),ld.getMonthValue(),ld.getDayOfMonth()).plusWeeks(Integer.parseInt(PreferenceClass.getString(RConfig.DateRangeKomisi,"1")));
            }


            Log.d(TAG, "onCreate: AKHIR "+min.isAfter(currentDate));
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
                    .setMinimumDate(min)
                    .commit();
            // }
        }
        widget.setContentDescription("Hari ini");
        // oneDayDecorator.setDate(min);
        widget.addDecorators(new HighlightWeekendsSatDecorator(), new HighlightWeekendsDecorator(), new OneDayDecorator(this));

        widget.setOnDateChangedListener(this);

        //    widget.setOnMonthChangedListener(this);
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
             Log.d(TAG, "onDateSelected: "+  date.getDate());
//            if (initTanggal.equals("awal") ) {
//                awalDate= date.getDate();
//            }


            Intent i = new Intent();

            i.putExtra("date", getSelectedDatesString(date));

            i.putExtra("dateShow", getSelectedDatesStringShow(date));
            setResult(AppCompatActivity.RESULT_OK, i);
            finish();
            //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);

        }
    }

    //    @Override
//    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
//
//    }
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
