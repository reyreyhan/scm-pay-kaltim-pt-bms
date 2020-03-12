package com.bm.main.single.ftl.ship.activities;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ListPopupWindow;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.clearableedittext.ClearableEditText;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.Validate;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShipFormBookAdultActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemClickListener {
    private static final String TAG = ShipFormBookAdultActivity.class.getSimpleName();
    TextView titleHeader, actBatal;
    MaterialEditText adultSex, adultId, adultBorn;
    ClearableEditText adultName;
    String sAdultSex, sAdultName, sAdultId, sAdultBorn;
    String sexTemp;
    static String dateBorn;
    AppCompatButton btnSelesai;
    private ListPopupWindow lpw;
    private String[] listSex;
    private String[] list;

//    private String isBirthDay;
//    private String isIdentityNumber;
   // private boolean isFirstPassenger;

    boolean isNew=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_form_book_adult_activity);
        Intent intent = this.getIntent();
        if (intent != null) {
            isNew = intent.getBooleanExtra(ShipKeyPreference.isNewShip, false);
            //    isFirstPassenger = intent.getBooleanExtra("isFirstPassenger", false);
            sAdultSex = intent.getStringExtra("sexAdult");
            sexTemp = intent.getStringExtra("sexTempAdult");
            sAdultName = intent.getStringExtra("nameAdult");
            sAdultId = intent.getStringExtra("idAdult");
            sAdultBorn = intent.getStringExtra("bornShowAdult");
            dateBorn = intent.getStringExtra("bornAdult");
        }





        toolbar = findViewById(R.id.toolbarForm);
        titleHeader = toolbar.findViewById(R.id.titleToolbar);
        adultSex = findViewById(R.id.textSexAdult);
        adultName = findViewById(R.id.textNameAdultShip);

        adultId = findViewById(R.id.textIDAdultShip);
        adultBorn = findViewById(R.id.textBornAdultShip);
        adultBorn.setOnClickListener(this);


        actBatal = toolbar.findViewById(R.id.actionToolbar);
        titleHeader.setText("DATA PENUMPANG DEWASA");
        adultSex.setOnTouchListener(this);
        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter.AllCaps();
        filter[1] = new InputFilter.LengthFilter(100);
        adultName.setFilters(filter);
        actBatal.setText("Batal");
        actBatal.setOnClickListener(this);
        btnSelesai = findViewById(R.id.btn_selesaiShip);
        MaterialRippleLayout.on(btnSelesai).rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(R.color.md_orange_200)
                .rippleHover(true)
                .create();
        btnSelesai.setOnClickListener(this);
        adultName.setOnEditorActionListener(new DoneOnEditorActionListener());
        if(PreferenceClass.getString(ShipKeyPreference.avail_gender,"").equals("M")) {
            list = new String[]{"Pria"};
            listSex = new String[]{"M"};
        }else if(PreferenceClass.getString(ShipKeyPreference.avail_gender,"").equals("F")) {
            list = new String[]{ "Wanita"};
            listSex = new String[]{"F"};
        }else if(PreferenceClass.getString(ShipKeyPreference.avail_gender,"").equals("A")) {
            list = new String[]{"Pria", "Wanita"};
            listSex = new String[]{"M", "F"};
        }
        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list));
        lpw.setAnchorView(adultSex);
        lpw.setModal(true);
        lpw.setOnItemClickListener(this);

        clearAll(isNew);
    }

    private void clearAll(boolean isNew) {
        Log.d(TAG, "clearAll: " + isNew);
        if (isNew) {
//            if (isIdentityNumber.equals("0")) {
//                adultId.setVisibility(View.GONE);
//            } else {
//                adultId.setVisibility(View.VISIBLE);
//            }
//            if (isBirthDay.equals("0")) {
//                adultBorn.setVisibility(View.GONE);
//            } else {
//                adultBorn.setVisibility(View.VISIBLE);
//            }


            adultSex.setText("");
            adultName.setText("");
            adultId.setText("");
            adultBorn.setText("");


        } else {
            adultSex.setText(sAdultSex);
            adultName.setText(sAdultName);
//            if (isIdentityNumber.equals("0")) {
//                adultId.setVisibility(View.GONE);
//            } else {
//                adultId.setVisibility(View.VISIBLE);
//            }
            adultId.setText(sAdultId);
//            if (isBirthDay.equals("0")) {
//                adultBorn.setVisibility(View.GONE);
//            } else {
//                adultBorn.setVisibility(View.VISIBLE);
//            }
            adultBorn.setText(sAdultBorn);

        }
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.actionToolbar) {
            onBackPressed();
        } else if (id == R.id.btn_selesaiShip) {

            selesai();
        } else if (id == R.id.textBornAdultShip) {
            openCalendar();
        }
    }

    private void selesai() {
        closeKeyboard(this);
        if (!Validate.checkEmptyEditText(adultSex, "Tidak Boleh Kosong")) {
            adultSex.setAnimation(animShake);
            adultSex.startAnimation(animShake);
            getar();
            return;
        }
        if (!Validate.checkEmptyEditText(adultName, "Tidak Boleh Kosong")) {
            adultName.setAnimation(animShake);
            adultName.startAnimation(animShake);
            getar();
            return;
        }
//        if (adultId.getVisibility() == View.VISIBLE) {
            if (!Validate.checkEmptyEditText(adultId, "Tidak Boleh Kosong")) {
                adultId.setAnimation(animShake);
                adultId.startAnimation(animShake);
                getar();
                return;
            }
//        }
//        if (adultBorn.getVisibility() == View.VISIBLE) {
            if (!Validate.checkEmptyEditText(adultBorn, "Tidak Boleh Kosong")) {
                adultBorn.setAnimation(animShake);
                adultBorn.startAnimation(animShake);
                getar();
                return;
            }
//        }


        Intent i = new Intent();
        i.putExtra("adultSex", adultSex.getText().toString());
        i.putExtra("adultSexTemp", sexTemp);
        i.putExtra("adultName", adultName.getText().toString().replaceAll("[']", "`"));
        i.putExtra("adultId", adultId.getText().toString());

        i.putExtra("adultBornShow", adultBorn.getText().toString());
        i.putExtra("adultBorn", dateBorn);
        i.putExtra(ShipKeyPreference.isNewShip, isNew);
        i.putExtra("status", "adult");
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private void openCalendar() {
        (new DatePickerFragment()).show(getFragmentManager(), String.valueOf(AlertDialog.THEME_HOLO_LIGHT));
    }

    @Override
    public void onBackPressed() {
        closeKeyboard(this);

        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = list[position];
        String itemTemp = listSex[position];
        adultSex.setText(item);
        sexTemp = itemTemp;
        lpw.dismiss();
    }

    @Override
    public boolean onTouch(@NonNull View v, @NonNull MotionEvent event) {
        int id = v.getId();

            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw.show();
                return true;
            }

        return false;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        SimpleDateFormat formatterFlight = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);
        @NonNull
        SimpleDateFormat formatterBornShow = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance(SBFApplication.config.locale);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            int day = calendar.get(Calendar.DAY_OF_YEAR);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            //calendar.add(Calendar.DAY_OF_MONTH,);
            calendar.add(Calendar.YEAR, -2);
            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                dpd.setTitle("Tanggal Lahir Dewasa");//Prevent Date picker from creating extra Title.!
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = getActivity().findViewById(R.id.textBornAdultShip);
            Calendar cal = Calendar.getInstance( SBFApplication.config.locale);
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            String formattedDateShow = formatterBornShow.format(chosenDate);
            String formattedDate = formatterFlight.format(chosenDate);
            tv.setText(formattedDateShow);
            dateBorn = formattedDate;
        }
    }


    class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(@NonNull TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                btnSelesai.performClick();
            }
            return false;
        }
    }
}
