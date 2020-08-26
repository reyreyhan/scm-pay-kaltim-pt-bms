package com.bm.main.single.ftl.ship.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.ship.adapters.ShipCalendarAdapter;
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;

import java.util.Calendar;

public class ShipTanggalActivity extends BaseActivity implements ShipCalendarAdapter.OnClickJadwalKeberangkatan {
    private static final String TAG =ShipTanggalActivity.class.getSimpleName() ;
    int selectedYear = 0;
    int selectedMonth = 0;
    int currentYear = 0;
    int currentMonth = 0;

    // TextView tvPenumpangLaki, tvPenumpangPerempuan;
    // TextView tvOrigin, tvDestination;
    // AppCompatButton btnCari;

    TextView textViewYear;
    RecyclerView recyclerViewCalendar;
    ShipCalendarAdapter calendarAdapter;
     LinearLayout relativeTahun;
    RecyclerView.LayoutManager mLayoutManagerCalendar;
//    OnClickPilih listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_tanggal_activity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pilih Jadwal Keberangkatan");
        init(1);

        relativeTahun=findViewById(R.id.relativeTahun);
        recyclerViewCalendar = findViewById(R.id.recyclerViewCalendar);
        mLayoutManagerCalendar = new GridLayoutManager(this,3, RecyclerView.VERTICAL,false);
        recyclerViewCalendar.setHasFixedSize(false);
        recyclerViewCalendar.setLayoutManager(mLayoutManagerCalendar);
        calendarAdapter = new ShipCalendarAdapter(this,this);
        calendarAdapter.selectedIndex = Calendar.getInstance().get(Calendar.MONTH);
        recyclerViewCalendar.setAdapter(calendarAdapter);
//        recyclerViewCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(selectedYear == currentYear && position < (currentMonth)) {
////                    showToastCustom(StartShipActivity.this, 1, "Tidak bisa memilih bulan sebelumnya");
//                    return;
//                }
//                selectedMonth = position;
//                PreferenceClass.putInt(ShipKeyPreference.shipMonth,selectedMonth);
//                calendarAdapter.selectedIndex = position;
//                calendarAdapter.notifyDataSetChanged();
//            }
//        });
        ImageView btnPrevYear = findViewById(R.id.btnPrevYear);
        ImageView btnNextYear = findViewById(R.id.btnNextYear);
        textViewYear = findViewById(R.id.textViewYear);

        btnPrevYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedYear - 1 < currentYear) {
                    return;
                }
                selectedYear--;
                calendarAdapter.selectedYear--;
                if(selectedYear == currentYear)
                    selectedMonth = currentMonth;

                textViewYear.setText(String.valueOf(selectedYear));
                PreferenceClass.putInt(ShipKeyPreference.shipYear,selectedYear);
                calendarAdapter.selectedIndex = selectedMonth;
                calendarAdapter.notifyDataSetChanged();
            }
        });

        btnNextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedYear++;
                calendarAdapter.selectedYear++;
                textViewYear.setText(String.valueOf(selectedYear));
                PreferenceClass.putInt(ShipKeyPreference.shipYear,selectedYear);
                calendarAdapter.selectedIndex = selectedMonth;
                calendarAdapter.notifyDataSetChanged();
                // reloadView(true);
            }
        });

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        calendarAdapter.currentYear = calendar.get(Calendar.YEAR);
        calendarAdapter.currentMonth = calendar.get(Calendar.MONTH);
        currentMonth = calendar.get(Calendar.MONTH);

        if(selectedYear < currentYear) {
            selectedYear = currentYear;
            calendarAdapter.selectedYear = currentYear;
            if(selectedMonth < currentMonth)
                selectedMonth = currentMonth;
        }
        textViewYear.setText(String.valueOf(selectedYear));
        calendarAdapter.selectedIndex = selectedMonth;
        calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickJadwalKeberangkatan(int position) {


        if(selectedYear == currentYear && position < (currentMonth)) {
//                    showToastCustom(StartShipActivity.this, 1, "Tidak bisa memilih bulan sebelumnya");
            return;
        }
        selectedMonth = position;
        PreferenceClass.putInt(ShipKeyPreference.shipMonth,selectedMonth);
        Log.d(TAG, "onClickJadwalKeberangkatan: "+selectedMonth);
        Intent i = new Intent();
        i.putExtra("selectedMonth", selectedMonth);
        i.putExtra("selectedYear",selectedYear);
//        i.putExtra("airportNama", airport.getBandara());
        setResult(Activity.RESULT_OK, i);
        finish();
        overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
        calendarAdapter.selectedIndex = position;
        calendarAdapter.notifyDataSetChanged();

    }


//    public class CalendarAdapter extends RecyclerView<> {
//        private Context mContext;
//        public int selectedIndex = 0;
//        String[] months = new String[] {
//                "Januari", "Februari", "Maret",
//                "April", "Mei", "Juni",
//                "Juli", "Agustus", "September",
//                "Oktober", "November", "Desember"
//        };
//        public CalendarAdapter(Context c) {
//            mContext = c;
//
//        }
//
//        public int getCount() {
//            return months.length;
//        }
//
//        public Object getItem(int position) {
//            return null;
//        }
//
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        // create a new ImageView for each item referenced by the Adapter
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//
//            View view = inflater.inflate(R.layout.ship_item_month, null);
//            TextView tvMonth = view.findViewById(R.id.tvMonth);
//
//
//            tvMonth.setText(months[position]);
//
//            if(selectedYear * 100 + position < currentYear * 100 + currentMonth) {
//                tvMonth.setTextColor(ContextCompat.getColor(ShipTanggalActivity.this, R.color.md_white_1000));
////                view.setBackgroundResource(R.color.md_grey_400);
//                tvMonth.setBackgroundResource(R.drawable.selector_button_grey_pressed);
//            }else {
//                if(selectedIndex == position) {
//                    tvMonth.setTextColor(ContextCompat.getColor(ShipTanggalActivity.this, R.color.md_blue_500));
//                    tvMonth.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    //view.setBackgroundResource(R.color.colorPrimary);
//                }else{
////                    tvMonth.setTextColor(Color.BLACK);
//////                    view.setBackgroundResource(R.color.md_white_1000);
//                    tvMonth.setTextColor(ContextCompat.getColor(ShipTanggalActivity.this, R.color.md_orange_500));
////                view.setBackgroundResource(R.color.md_grey_400);
//                    tvMonth.setBackgroundResource(R.drawable.selector_button_orange_white_pressed_grid);
//                }
//            }
//
//            return view;
//        }
//
//    }

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
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }



}
