package com.bm.main.single.ftl.train.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ListPopupWindow;
import android.text.Html;
import android.text.InputFilter;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.clearableedittext.ClearableEditText;
import com.bm.main.fpl.utils.Validate;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.train.constants.TrainKeyPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrainFormBookAdultActivity extends BaseActivity implements View.OnClickListener{
    TextView titleHeader, actBatal;
    //    MaterialEditText adultTitle, adultName, adultId, adultBorn,adultPhone;
    MaterialEditText adultId, adultBorn,adultPhone;
    ClearableEditText adultName;
    //    String sAdultTitle, sAdultName, sAdultId, sAdultBorn,sAdultPhone;
    String  sAdultName, sAdultId, sAdultBorn,sAdultPhone;
    // String titleTemp;
    static String dateBorn;
    AppCompatButton btnSelesai;
    private ListPopupWindow lpw;
    private String[] list;
    private String[] listTitle;

    private boolean isNew=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_form_book_adult_activity);
        Intent intent=this.getIntent();
        if(intent!=null)
            isNew=intent.getBooleanExtra(TrainKeyPreference.isNewTrain,false);
        //sAdultTitle = intent.getStringExtra("titleAdult");
        // titleTemp = intent.getStringExtra("titleTempAdult");
        sAdultName = intent.getStringExtra( "nameAdult");
        sAdultId = intent.getStringExtra("idAdult");
        sAdultBorn = intent.getStringExtra( "bornShowAdult");
        sAdultPhone = intent.getStringExtra( "phoneAdult");
        dateBorn = intent.getStringExtra( "bornAdult");



        toolbar = findViewById(R.id.toolbarForm);
        titleHeader = toolbar.findViewById(R.id.titleToolbar);
        //  adultTitle = (MaterialEditText) findViewById(R.id.textTitleAdult);
        adultName = findViewById(R.id.textNameAdult);
        adultId = findViewById(R.id.textIDAdult);
        adultBorn = findViewById(R.id.textBornAdult);
        adultPhone = findViewById(R.id.textPhoneAdult);
        adultPhone.setHint(Html.fromHtml(getString(R.string.hint_no_handphone_passanger)));
        adultPhone.setFloatingLabelText(Html.fromHtml(getString(R.string.hint_no_handphone)));
        adultBorn.setOnClickListener(this);
        actBatal = toolbar.findViewById(R.id.actionToolbar);

        titleHeader.setText("DATA PENUMPANG DEWASA");
        //  adultTitle.setOnTouchListener(this);

        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter.AllCaps();
        filter[1] = new InputFilter.LengthFilter(100);
        adultName.setFilters(filter);

        actBatal.setText("Batal");
        actBatal.setOnClickListener(this);

        btnSelesai = findViewById(R.id.btn_selesai);
        btnSelesai.setOnClickListener(this);

//        list = new String[]{"Title", "Tuan", "Nyonya", "Nona"};
//        listTitle = new String[]{"Title", "Tn", "Ny", "Nona"};
//        lpw = new ListPopupWindow(this);
//        lpw.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, list));
//        lpw.setAnchorView(adultTitle);
//        lpw.setModal(true);
//        lpw.setOnItemClickListener(this);


        //adultBorn.setOnTouchListener(this);
//        sAdultTitle = (String) MemoryStore.get(this, "adultTitle");
//        titleTemp = (String) MemoryStore.get(this, "adultTitleTemp");
//        sAdultName = (String) MemoryStore.get(this, "adultName");
//        sAdultId = (String) MemoryStore.get(this, "adultId");
//        sAdultBorn = (String) MemoryStore.get(this, "adultBornShow");
//        sAdultPhone = (String) MemoryStore.get(this, "adultPhone");
//        dateBorn = (String) MemoryStore.get(this, "adultBorn");

        clearAll(isNew);



    }
    public void clearAll(boolean isNew){
        if(isNew){
            //  adultTitle.setText("");
            adultName.setText("");
            adultId.setText("");
            adultBorn.setText("");
            adultPhone.setText("");
        }else{
            //  adultTitle.setText(sAdultTitle);
            adultName.setText(sAdultName);
            adultId.setText(sAdultId);
            adultBorn.setText(sAdultBorn);
            adultPhone.setText(sAdultPhone);
        }


    }
    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.actionToolbar) {
            onBackPressed();

        } else if (id == R.id.btn_selesai) {
            closeKeyboard(this);

            selesai();


        }else if(id==R.id.textBornAdult){
            openCalendar();
        }
    }

    @Override
    public void onBackPressed() {
        // Intent intent=new Intent(SearchFlightActivity.this, HomeActivity.class);

        //  startActivity(intent);
        closeKeyboard(this);
        setResult(Activity.RESULT_CANCELED);
        finish();
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String item = list[position];
//        String itemTemp = listTitle[position];
//        if (position == 0) {
//            adultTitle.setText("");
//            adultTitle.setHint(item);
//        } else {
//
//            adultTitle.setText(item);
//            titleTemp = itemTemp;
//        }
//        lpw.dismiss();
//    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        // final int DRAWABLE_RIGHT = 2;
//        int id = v.getId();
//        if (id == R.id.textTitleAdult) {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                //     if (event.getX() >= (v.getWidth() - ((EditText) v)
//                //           .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//
//                lpw.show();
//                return true;
//                // }
//            }
//        }
//        return false;
//    }

    public void openCalendar() {

//        Intent intent = null;
//
//
//        intent = new Intent(FormBookAdultActivity.this, TanggalActivity.class);
//        intent.putExtra("initTanggal", "bornAdultFlight");
//
//        startActivityForResult(intent, 1);
//        overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);

        DialogFragment dFragment = new DatePickerFragment();

        // Show the date picker dialog fragment
//        dFragment.show(getFragmentManager(), "Date Picker");
        dFragment.show(getFragmentManager(), String.valueOf(AlertDialog.THEME_HOLO_LIGHT));
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("BOOK", "onActivityResult: " + data);
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {// adult 1
//            adultBorn.setText(data.getStringExtra("dateShow"));
//            dateBorn = data.getStringExtra("date");
//            MemoryStore.set(this, "adultBorn", data.getStringExtra("date"));
//            MemoryStore.set(this, "adultBornShow", data.getStringExtra("dateShow"));
////            depDate = data.getStringExtra("date");
//        }
//    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @NonNull
        SimpleDateFormat formatterTrain = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);
        @NonNull
        SimpleDateFormat formatterBornShow = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance(SBFApplication.config.locale);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int nameDay = calendar.get(Calendar.DAY_OF_WEEK);

            /*
                We should use THEME_HOLO_LIGHT, THEME_HOLO_DARK or THEME_TRADITIONAL only.

                The THEME_DEVICE_DEFAULT_LIGHT and THEME_DEVICE_DEFAULT_DARK does not work
                perfectly. This two theme set disable color of disabled dates but users can
                select the disabled dates also.

                Other three themes act perfectly after defined enabled date range of date picker.
                Those theme completely hide the disable dates from date picker object.
             */
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);

            dpd.setTitle(nameDay+", "+day+" "+month+" "+year);
            /*
                add(int field, int value)
                    Adds the given amount to a Calendar field.
             */
            // Add 3 days to Calendar
            calendar.add(Calendar.YEAR, -3);
            /*
                getTimeInMillis()
                    Returns the time represented by this Calendar,
                    recomputing the time from its fields if necessary.

                getDatePicker()
                Gets the DatePicker contained in this dialog.

                setMinDate(long minDate)
                    Sets the minimal date supported by this NumberPicker
                    in milliseconds since January 1, 1970 00:00:00 in getDefault() time zone.

                setMaxDate(long maxDate)
                    Sets the maximal date supported by this DatePicker in milliseconds
                    since January 1, 1970 00:00:00 in getDefault() time zone.
             */

            // Set the Calendar new date as maximum date of date picker
            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            // Subtract 6 days from Calendar updated date
            // calendar.add(Calendar.YEAR, -12);

            // Set the Calendar new date as minimum date of date picker
            //  dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());

            // So, now date picker selectable date range is 7 days only

            // Return the DatePickerDialog
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                dpd.setTitle("Tanggal Lahir Dewasa");//Prevent Date picker from creating extra Title.!
            }
            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            // Do something with the chosen date
            TextView tv = getActivity().findViewById(R.id.textBornAdult);

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance(SBFApplication.config.locale);
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style and locale
            // DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, BaseActivity.getInstance().config.locale);
            String formattedDateShow = formatterBornShow.format(chosenDate);
            String formattedDate = formatterTrain.format(chosenDate);
            // formatter.format(date.getDate()


            // Display the chosen date to app interface
            tv.setText(formattedDateShow);
            dateBorn=formattedDate;
            // MemoryStore.set(getActivity(), "adultBorn", formattedDate);
            // MemoryStore.set(getActivity(), "adultBornShow", formattedDateShow);
        }
    }


    private void selesai(){
        //    MaterialEditText adultTitle, adultName, adultId, adultBorn,adultPhone;
//        if (!Validate.checkEmptyEditText(adultTitle, "Tidak Boleh Kosong")) {
//            adultTitle.setAnimation(animShake);
//            adultTitle.startAnimation(animShake);
//            vib.vibrate(120);
//            return;
//        }

        if (!Validate.checkEmptyEditText(adultName, "Tidak Boleh Kosong")) {
            adultName.setAnimation(animShake);
            adultName.startAnimation(animShake);
            getar();
            return;
        }

//        if (!Validate.checkEmptyEditText(adultBorn, "Tidak Boleh Kosong")) {
//            adultBorn.setAnimation(animShake);
//            adultBorn.startAnimation(animShake);
//            vib.vibrate(120);
//            return;
//        }

        if (!Validate.checkEmptyEditText(adultId, "Tidak Boleh Kosong")) {
            adultId.setAnimation(animShake);
            adultId.startAnimation(animShake);
            getar();
            return;
        }
        if (!Validate.checkEmptyEditText(adultPhone, "Tidak Boleh Kosong")) {
            adultPhone.setAnimation(animShake);
            adultPhone.startAnimation(animShake);
            getar();
            return;
        }
        if(adultPhone.getText().toString().length()<8){
            adultPhone.setError("No Handphone harus terdiri dari 8-16 angka");
            adultPhone.setAnimation(animShake);
            adultPhone.startAnimation(animShake);
            getar();
            return;
        }

        if (!adultPhone.getText().toString().substring(0,2).equals("08")) {
            adultPhone.setError("No Handphone Tidak Valid");
            adultPhone.setAnimation(animShake);
            adultPhone.startAnimation(animShake);
            getar();
            return;
        }
        Intent i = new Intent();
        //i.putExtra("adultTitle", adultTitle.getText().toString());
        //  i.putExtra("adultTitleTemp", titleTemp);
        i.putExtra("adultName", adultName.getText().toString());
        i.putExtra("adultId", adultId.getText().toString());
        i.putExtra("adultPhone", adultPhone.getText().toString());
        i.putExtra("adultBornShow", adultBorn.getText().toString());
        i.putExtra("adultBorn", dateBorn);
        i.putExtra(TrainKeyPreference.isNewTrain,isNew);
        i.putExtra("status","adult");
        setResult(Activity.RESULT_OK, i);
        finish();




    }
}
