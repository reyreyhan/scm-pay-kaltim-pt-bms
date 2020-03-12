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
import com.bm.main.fpl.utils.Validate;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShipFormBookInfantActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemClickListener {
    TextView titleHeader, actBatal;
    MaterialEditText infantSex, infantBorn;
    ClearableEditText infantName;
    String sInfantSex, sInfantName, sInfantBorn;
    String sexTemp;
    static String dateBorn;
    AppCompatButton btnSelesai;
    private ListPopupWindow lpw;
    private String[] list;
    private String[] listSex;
    private boolean isNew=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_form_book_infant_activity);
        Intent intent = this.getIntent();
        if (intent != null)
            isNew = intent.getBooleanExtra(ShipKeyPreference.isNewShip, false);
        sInfantSex = intent.getStringExtra("sexInfant");
        sexTemp = intent.getStringExtra("sexTempInfant");
        sInfantName = intent.getStringExtra("nameInfant");
        sInfantBorn = intent.getStringExtra("bornShowInfant");
        dateBorn = intent.getStringExtra("bornInfant");
        toolbar = findViewById(R.id.toolbarForm);
        titleHeader = toolbar.findViewById(R.id.titleToolbar);
        infantSex = findViewById(R.id.textSexInfant);
        infantName = findViewById(R.id.textNameInfantShip);
        infantBorn = findViewById(R.id.textBornInfantShip);
        infantBorn.setOnClickListener(this);
        actBatal = toolbar.findViewById(R.id.actionToolbar);
        titleHeader.setText("DATA PENUMPANG BAYI");
        infantSex.setOnTouchListener(this);
        infantName.setOnEditorActionListener(new NextOnEditorActionListener());
        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter.AllCaps();
        filter[1] = new InputFilter.LengthFilter(100);
        infantName.setFilters(filter);
        actBatal.setText("Batal");
        actBatal.setOnClickListener(this);
        btnSelesai = findViewById(R.id.btn_selesaiShip);
//        MaterialRippleLayout.on(btnSelesai).rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                .rippleColor(R.color.md_orange_200)
//                .rippleHover(true)
//                .create();
        btnSelesai.setOnClickListener(this);
        list = new String[]{"Pria", "Wanita"};
        listSex = new String[]{"M", "F"};
        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list));
        lpw.setAnchorView(infantSex);
        lpw.setModal(true);
        lpw.setOnItemClickListener(this);
        clearAll(isNew);
    }

    private void clearAll(boolean isNew) {
        if (isNew) {
            infantSex.setText("");
            infantName.setText("");
            infantBorn.setText("");
        }
        else {
            infantSex.setText(sInfantSex);
            infantName.setText(sInfantName);
            infantBorn.setText(sInfantBorn);
        }
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.actionToolbar) {
            onBackPressed();
        }
        else if (id == R.id.btn_selesaiShip) {
            closeKeyboard(this);
            selesai();
        }
        else if (id == R.id.textBornInfantShip) {
            openCalendar();
        }
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
        infantSex.setText(item);
        sexTemp = itemTemp;
        lpw.dismiss();
    }

    @Override
    public boolean onTouch(View v, @NonNull MotionEvent event) {
//        if (v.getId() == R.id.textSexInfant) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw.show();
                return true;
            }
//        }
        return false;
    }

    private void openCalendar() {
        (new DatePickerFragment()).show(getFragmentManager(), String.valueOf(AlertDialog.THEME_HOLO_LIGHT));
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
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            //calendar.add(Calendar.YEAR, 0);
            dpd.getDatePicker().setMaxDate(new Date().getTime());
            calendar.add(Calendar.YEAR, -2);
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                dpd.setTitle("Tanggal Lahir Bayi");//Prevent Date picker from creating extra Title.!
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = getActivity().findViewById(R.id.textBornInfantShip);
            Calendar cal = Calendar.getInstance(SBFApplication.config.locale);
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            String formattedDateShow = formatterBornShow.format(chosenDate);
            String formattedDate = formatterFlight.format(chosenDate);
            tv.setText(formattedDateShow);
            dateBorn = formattedDate;
        }
    }

    private void selesai() {
        if (!Validate.checkEmptyEditText(infantSex, "Tidak Boleh Kosong")) {
            infantSex.setAnimation(animShake);
            infantSex.startAnimation(animShake);
            getar();
            return;
        }
        if (!Validate.checkEmptyEditText(infantName, "Tidak Boleh Kosong")) {
            infantName.setAnimation(animShake);
            infantName.startAnimation(animShake);
            getar();
            return;
        }
        if (!Validate.checkEmptyEditText(infantBorn, "Tidak Boleh Kosong")) {
            infantBorn.setAnimation(animShake);
            infantBorn.startAnimation(animShake);
            getar();
            return;
        }
        Intent i = new Intent();
        i.putExtra("infantSex", infantSex.getText().toString());
        i.putExtra("infantSexTemp", sexTemp);
        i.putExtra("infantName", infantName.getText().toString());
        i.putExtra("infantBornShow", infantBorn.getText().toString());
        i.putExtra("infantBorn", dateBorn);
        i.putExtra(ShipKeyPreference.isNewShip, isNew);
        i.putExtra("status", "infant");
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    class NextOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(@NonNull TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                openCalendar();
            }
            return false;
        }
    }

}
