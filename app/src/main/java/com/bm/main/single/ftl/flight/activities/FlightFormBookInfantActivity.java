package com.bm.main.single.ftl.flight.activities;

import android.app.Activity;
//import androidx.appcompat.app.AlertDialog;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.bm.main.fpl.templates.clearableedittext.ClearableEditText;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.Validate;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import android.support.v7.app.AppCompatActivity;

public class FlightFormBookInfantActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemClickListener {

    private static final String TAG=FlightFormBookInfantActivity.class.getSimpleName();
    TextView titleHeader, actBatal;
    MaterialEditText infantTitle, infantBorn;
    ClearableEditText infantName;
    String sInfantTitle, sInfantName, sInfantBorn;
    String sInfantNationality, sInfantNoPassport, sInfantIssuingCountry, sInfantExpirationDate;
    String titleTemp;
    static String dateBorn;
    static String dateExpired;
    AppCompatButton btnSelesai;
    private ListPopupWindow lpw;
    private String[] list;
    private String[] listTitle;
    private boolean isNew=false;


    MaterialEditText infantNationality, infantIssuingCountry, infantExpirationDate;
    ClearableEditText infantNoPassport;
//    private ListCountryAdapter adapter;
    static String nasionalityKode;
    static String issuingNasionalityKode;
    int count=0;
    private TextView textViewCheckLenghtName;
int nomor=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_form_book_infant_activity);
        Intent intent = this.getIntent();
        if (intent != null)
            isNew = intent.getBooleanExtra(FlightKeyPreference.isNewFlight, false);
        sInfantTitle = intent.getStringExtra("infantTitle");
        titleTemp = intent.getStringExtra("infantTitleTemp");
        sInfantName = intent.getStringExtra("infantName");
        sInfantBorn = intent.getStringExtra("infantBornShow");
        nomor = intent.getIntExtra("nomor",0);
        dateBorn = intent.getStringExtra("infantBorn");

        sInfantNationality=intent.getStringExtra("infantNationality");
        nasionalityKode=intent.getStringExtra("infantNationalityKode");
        sInfantNoPassport=intent.getStringExtra("infantNoPassport");
        sInfantIssuingCountry=intent.getStringExtra("infantIssuingCountry");
        issuingNasionalityKode=intent.getStringExtra("infantIssuingCountryKode");
        sInfantExpirationDate=intent.getStringExtra("infantExpiredShow");
        dateExpired = intent.getStringExtra("infantExpired");

        toolbar = findViewById(R.id.toolbarForm);
        titleHeader = toolbar.findViewById(R.id.titleToolbar);
        textViewCheckLenghtName = findViewById(R.id.textViewCheckLenghtName);
        infantTitle = findViewById(R.id.textTitleInfant);
        infantName = findViewById(R.id.textNameInfant);
        infantBorn = findViewById(R.id.textBornInfant);
        infantBorn.setOnClickListener(this);



        infantNationality = findViewById(R.id.textNationalityInfant);
        infantNationality.setOnClickListener(this);
        infantNoPassport = findViewById(R.id.textNoPassportInfant);
        infantIssuingCountry = findViewById(R.id.textIssuingCountryInfant);
        infantIssuingCountry.setOnClickListener(this);
        infantExpirationDate = findViewById(R.id.textExpirationDateInfant);
        infantExpirationDate .setOnClickListener(this);
        
        actBatal = toolbar.findViewById(R.id.actionToolbar);
        titleHeader.setText("DATA PENUMPANG BAYI");
        infantTitle.setOnTouchListener(this);
        infantName.setOnEditorActionListener(new NextOnEditorActionListener());

        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter.AllCaps();
        if(PreferenceClass.getString(FlightKeyPreference.airlineCode,"").equals("TPGA")){
           String warning="Nama penumpang dewasa ke "+nomor+ " dan bayi tidak boleh > 29 karakter";
            textViewCheckLenghtName.setText(warning);
            textViewCheckLenghtName.setVisibility(View.VISIBLE);
           if(nomor==1) {
               count = PreferenceClass.getString(FlightKeyPreference.namaPenumpangAdult1, "").length();
           }else if(nomor==2) {
               count = PreferenceClass.getString(FlightKeyPreference.namaPenumpangAdult2, "").length();
           }else if(nomor==3) {
               count = PreferenceClass.getString(FlightKeyPreference.namaPenumpangAdult3, "").length();
           }else if(nomor==4) {
               count = PreferenceClass.getString(FlightKeyPreference.namaPenumpangAdult4, "").length();
           }else if(nomor==5) {
               count = PreferenceClass.getString(FlightKeyPreference.namaPenumpangAdult5, "").length();
           }else if(nomor==6) {
               count = PreferenceClass.getString(FlightKeyPreference.namaPenumpangAdult6, "").length();
           }else if(nomor==7) {
               count = PreferenceClass.getString(FlightKeyPreference.namaPenumpangAdult7, "").length();
           }
           if(29-count<0){
             //  infantName.setError("Nama Penumpang Dewasa dan Bayi Tidak Boleh Melebihi 29 karakter");
               filter[1] = new InputFilter.LengthFilter(0);
               infantName.setMinCharacters(0);
               infantName.setMaxCharacters(0);
           }else {
               filter[1] = new InputFilter.LengthFilter(29 - count);
               infantName.setMinCharacters(0);
               infantName.setMaxCharacters(29 - count);
           }

            infantName.setFloatingLabelText("Nama Bayi");
            infantName.setHint("Nama Bayi");

        }else{
            filter[1] = new InputFilter.LengthFilter(100);
            infantName.setMaxCharacters(100);
            infantName.setFloatingLabelText("Nama Lengkap (Sesuai KK/Paspor)");
            infantName.setHint("Nama Lengkap (Sesuai KK/Paspor)");
        }



        infantName.setFilters(filter);
        actBatal.setText("Batal");
        actBatal.setOnClickListener(this);
        btnSelesai = findViewById(R.id.btn_selesai);
//        MaterialRippleLayout.on(btnSelesai).rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                .rippleColor(R.color.md_orange_200)
//                .rippleHover(true)
//                .create();
        btnSelesai.setOnClickListener(this);
        list = new String[]{"Tuan", "Nona"};
        listTitle = new String[]{"Tn", "Nona"};
        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list));
        lpw.setAnchorView(infantTitle);
        lpw.setModal(true);
        lpw.setOnItemClickListener(this);
        clearAll(isNew);
    }

    private void clearAll(boolean isNew) {
        if (isNew) {
            infantTitle.setText("");
            infantName.setText("");
            infantBorn.setText("");
            dateBorn="";

            if (PreferenceClass.getInt(FlightKeyPreference.isInternational, 0) == 0) {
                infantNationality.setVisibility(View.GONE);
                infantNoPassport.setVisibility(View.GONE);
                infantIssuingCountry.setVisibility(View.GONE);
                infantExpirationDate.setVisibility(View.GONE);
            } else {
                infantNationality.setVisibility(View.VISIBLE);
                infantBorn.setVisibility(View.VISIBLE);
                infantNoPassport.setVisibility(View.VISIBLE);
                infantIssuingCountry.setVisibility(View.VISIBLE);
                infantExpirationDate.setVisibility(View.VISIBLE);
            }

            infantNationality.setText("");
            infantNoPassport.setText("");
            infantIssuingCountry.setText("");
            infantExpirationDate.setText("");
            dateExpired="";
            
        }
        else {
            infantTitle.setText(sInfantTitle);
            infantName.setText(sInfantName);
            infantBorn.setText(sInfantBorn);


            if (PreferenceClass.getInt(FlightKeyPreference.isInternational, 0) == 0) {
                infantNationality.setVisibility(View.GONE);
                infantNoPassport.setVisibility(View.GONE);
                infantIssuingCountry.setVisibility(View.GONE);
                infantExpirationDate.setVisibility(View.GONE);
            } else {
                infantNationality.setVisibility(View.VISIBLE);
                infantBorn.setVisibility(View.VISIBLE);
                infantNoPassport.setVisibility(View.VISIBLE);
                infantIssuingCountry.setVisibility(View.VISIBLE);
                infantExpirationDate.setVisibility(View.VISIBLE);
            }
            infantNationality.setText(sInfantNationality);
            String flag = "https://www.countryflags.io/" + nasionalityKode + "/shiny/64.png";
            Glide.with(this).asBitmap().load(flag).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(30, 30).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                    int h = drawable.getIntrinsicHeight();
                    int w = drawable.getIntrinsicWidth();
                    drawable.setBounds(0, 0, w, h);
                    infantNationality.setCompoundDrawables(drawable, null, null, null);
                }
            });

            infantIssuingCountry.setText(sInfantIssuingCountry);
            String flagIssuing = "https://www.countryflags.io/" + issuingNasionalityKode + "/shiny/64.png";
            Glide.with(this).asBitmap().load(flagIssuing).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(30, 30).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                    int h = drawable.getIntrinsicHeight();
                    int w = drawable.getIntrinsicWidth();
                    drawable.setBounds(0, 0, w, h);
                    infantIssuingCountry.setCompoundDrawables(drawable, null, null, null);
                }
            });

            infantExpirationDate.setText(sInfantExpirationDate);
            infantNoPassport.setText(sInfantNoPassport);


        }
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.actionToolbar) {
            onBackPressed();
        }
        else if (id == R.id.btn_selesai) {
            closeKeyboard(this);
            selesai();
        }
        else if (id == R.id.textBornInfant) {
            openCalendar();
        } else if (id == R.id.textExpirationDateInfant) {
            openCalendarExpired();
        } else if (id == R.id.textNationalityInfant) {
            Intent intent = new Intent(this, FlightCountryActivity.class);
            startActivityForResult(intent, TravelActionCode.NASIONALITY);
        } else if (id == R.id.textIssuingCountryInfant) {
            Intent intent = new Intent(this, FlightCountryActivity.class);
            startActivityForResult(intent, TravelActionCode.ISSUING_NASIONALITY);
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
        String itemTemp = listTitle[position];
        infantTitle.setText(item);
        titleTemp = itemTemp;
        lpw.dismiss();
    }

    @Override
    public boolean onTouch(@NonNull View v, @NonNull MotionEvent event) {
        if (v.getId() == R.id.textTitleInfant) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw.show();
                return true;
            }
        }
        return false;
    }

    private void openCalendar() {
        (new DatePickerFragment()).show(getFragmentManager(), String.valueOf(AlertDialog.THEME_HOLO_LIGHT));
    }

    private void openCalendarExpired() {
        (new ExpiredDatePickerFragment()).show(getFragmentManager(), String.valueOf(AlertDialog.THEME_HOLO_LIGHT));
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        SimpleDateFormat formatterFlight = new SimpleDateFormat("MM/dd/yyyy", new Locale("id"));
        @NonNull
        SimpleDateFormat formatterBornShow = new SimpleDateFormat("dd MMMM yyyy", new Locale("id"));

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance(new Locale("id"));
          //  Date currentDate = new Date();
            //calendar.setTime(currentDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
           // calendar.set(year, month, day, 0, 0, 0);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);

           // dpd.getDatePicker().getYear();
            calendar.add(Calendar.YEAR, 0);
          //  calendar.get(Calendar.YEAR);
//            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
            calendar.add(Calendar.DAY_OF_MONTH, -6);
//            dpd.getDatePicker().setMaxDate(new Date().getTime());
            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            calendar.add(Calendar.MONTH, -23);
//            calendar.add(Calendar.MONTH, +1);
//            calendar.add(Calendar.YEAR, -2);
            calendar.add(Calendar.DAY_OF_MONTH, +1);
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                dpd.setTitle("Tanggal Lahir Bayi");//Prevent Date picker from creating extra Title.!
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = getActivity().findViewById(R.id.textBornInfant);
            Calendar cal = Calendar.getInstance(new Locale("id"));
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            String formattedDateShow = formatterBornShow.format(chosenDate);
            String formattedDate = formatterFlight.format(chosenDate);
            tv.setText(formattedDateShow);
            dateBorn = formattedDate;
        }
    }

    public static class ExpiredDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        SimpleDateFormat formatterFlight = new SimpleDateFormat("MM/dd/yyyy", new Locale("id"));
        @NonNull
        SimpleDateFormat formatterBornShow = new SimpleDateFormat("dd MMMM yyyy", new Locale("id"));


        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance(new Locale("id"));
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String[] departureDateFlight= PreferenceClass.getString(FlightKeyPreference.departureDateFlight,"").split("-");
            int year=Integer.parseInt(departureDateFlight[0]);
            int month=Integer.parseInt(departureDateFlight[1]);
            int day=Integer.parseInt(departureDateFlight[2]);

            calendar.add(Calendar.DAY_OF_MONTH, day);
            calendar.add(Calendar.MONTH, month);
            calendar.add(Calendar.YEAR, year);
            calendar.set(year, month, day, 0, 0, 0);
            Log.d(TAG, "onCreateDialog: "+year+" "+month+" "+day);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
//            calendar.add(Calendar.MONTH, month);
            calendar.add(Calendar.MONTH, +6);
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = getActivity().findViewById(R.id.textExpirationDateInfant);
            Calendar cal = Calendar.getInstance(new Locale("id"));
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            String formattedDateShow = formatterBornShow.format(chosenDate);
            String formattedDate = formatterFlight.format(chosenDate);
            tv.setText(formattedDateShow);
            dateExpired = formattedDate;
        }
    }

    private void selesai() {
        if (!Validate.checkEmptyEditText(infantTitle, "Tidak Boleh Kosong")) {
            infantTitle.setAnimation(animShake);
            infantTitle.startAnimation(animShake);
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

        if (infantNationality.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(infantNationality, "Tidak Boleh Kosong")) {
                infantNationality.setAnimation(animShake);
                infantNationality.startAnimation(animShake);
                getar();
                return;
            }
        }

        if (infantNoPassport.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(infantNoPassport, "Tidak Boleh Kosong")) {
                infantNoPassport.setAnimation(animShake);
                infantNoPassport.startAnimation(animShake);
                getar();
                return;
            }else if(!infantNoPassport.isCharactersCountValid()){
                infantNoPassport.setAnimation(animShake);
                infantNoPassport.startAnimation(animShake);
                infantNoPassport.setError("Nomor Passport kurang dari minimun");
                getar();
                return;
            }
        }
        if (infantIssuingCountry.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(infantIssuingCountry, "Tidak Boleh Kosong")) {
                infantIssuingCountry.setAnimation(animShake);
                infantIssuingCountry.startAnimation(animShake);
                getar();
                return;
            }
        }
        if (infantExpirationDate.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(infantExpirationDate, "Tidak Boleh Kosong")) {
                infantExpirationDate.setAnimation(animShake);
                infantExpirationDate.startAnimation(animShake);
                getar();
                return;
            }
        }
        
        Intent i = new Intent();
        i.putExtra("infantTitle", infantTitle.getText().toString());
        i.putExtra("infantTitleTemp", titleTemp);
        i.putExtra("infantName", infantName.getText().toString());
        i.putExtra("infantBornShow", infantBorn.getText().toString());
        i.putExtra("infantBorn", dateBorn);

        i.putExtra("infantNoPassport", infantNoPassport.getText().toString());
        i.putExtra("infantNationality", infantNationality.getText().toString());
        i.putExtra("infantNationalityKode", nasionalityKode);
        i.putExtra("infantIssuingCountry", infantIssuingCountry.getText().toString());
        i.putExtra("infantIssuingCountryKode", issuingNasionalityKode);
        i.putExtra("infantExpiredShow", infantExpirationDate.getText().toString());
        i.putExtra("infantExpired", dateExpired);
        
        i.putExtra(FlightKeyPreference.isNewFlight, isNew);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TravelActionCode.NASIONALITY) {
            infantNationality.setText(data.getStringExtra("countryNama"));
            nasionalityKode = data.getStringExtra("countryKode");
            String flag = "https://www.countryflags.io/" + nasionalityKode + "/shiny/64.png";
            Glide.with(this).asBitmap().load(flag).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(30, 30).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                    int h = drawable.getIntrinsicHeight();
                    int w = drawable.getIntrinsicWidth();
                    drawable.setBounds(0, 0, w, h);
                    infantNationality.setCompoundDrawables(drawable, null, null, null);
                }
            });

        } else if (resultCode == Activity.RESULT_OK && requestCode == TravelActionCode.ISSUING_NASIONALITY) {
            infantIssuingCountry.setText(data.getStringExtra("countryNama"));
            issuingNasionalityKode = data.getStringExtra("countryKode");
            String flag = "https://www.countryflags.io/" + issuingNasionalityKode + "/shiny/64.png";
            Glide.with(this).asBitmap().load(flag).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(30, 30).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                    int h = drawable.getIntrinsicHeight();
                    int w = drawable.getIntrinsicWidth();
                    drawable.setBounds(0, 0, w, h);
                    infantIssuingCountry.setCompoundDrawables(drawable, null, null, null);
                }
            });
        }


    }

}
