package com.bm.main.fpl.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import androidx.annotation.NonNull;
//import androidx.core.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.pos.R;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.activities.DaftarPelangganActivity;
import com.bm.main.fpl.activities.InqueryResultActivity;
import com.bm.main.fpl.activities.PlnActivity;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukCode;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.InqModel;
import com.bm.main.fpl.models.PelangganModel;
import com.bm.main.fpl.templates.CustomFontCheckBox;
import com.bm.main.fpl.templates.RadioGroupTable;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;
import static com.bm.main.single.ftl.utils.FormatUtil.checkNumberFromString;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlnPraFragment extends Fragment implements ProgressResponseCallback {

    private static final String TAG =PlnPraFragment.class.getSimpleName() ;
    private View rootView;
     Context context;
//     RadioButton radioTabsItemButton;
//     RadioGroup radioTabs;
    public MaterialEditText materialEditTextIdPelanggan;
    public CustomFontCheckBox checkboxSimpanId;
    AppCompatButton button_lanjutkan;
    private String denom;
    RadioGroupTable radioGroupDenom;
    RadioButton radioButtonDenom;
    int selectedId;

    AppCompatButton appButtonItemTabIdPelanggan;
    AppCompatButton appButtonItemTabDaftarPelanggan;
    LinearLayout layout_no_connection;
    NestedScrollView linMain;
    public ImageView imageViewAddressBookPra,imageViewVoiceBookPra;

    RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,radioButton7,radioButton8,radioButton9;

    private PelangganModel pelangganModel;
    @NonNull
    private String prefProduct="Pelanggan" +ProdukCode.PLNPASC ;
    @NonNull
    ArrayList<PelangganModel.Response_value> data = new ArrayList<>();
    public PlnPraFragment() {
        // Required empty public constructor
    }
int countText=0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pln_pra, container, false);
        context = getActivity();//rootView.getContext();
        ((BaseActivity)context).logEventFireBase(ProdukGroup.PLN,ProdukCode.PLNPRA,EventParam.EVENT_ACTION_VISIT,EventParam.EVENT_SUCCESS,TAG);
        linMain=rootView.findViewById(R.id.linMain);
        checkboxSimpanId = rootView.findViewById(R.id.checkboxSimpanIdPra);
        radioButton1=rootView.findViewById(R.id.radioButton1);
        radioButton1.setButtonDrawable(new StateListDrawable());
        radioButton1.setButtonDrawable(null);
        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               String tag="20.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 2, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 2, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton1.setText(ss1);
                }else{
                    radioButton1.setText(tag);
                }
            }
        });
        radioButton2=rootView.findViewById(R.id.radioButton2);
        radioButton2.setButtonDrawable(null);
        radioButton2.setButtonDrawable(new StateListDrawable());
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              String tag="50.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 2, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 2, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton2.setText(ss1);
                }else{
                    radioButton2.setText(tag);
                }
            }
        });


        radioButton3=rootView.findViewById(R.id.radioButton3);
        radioButton3.setButtonDrawable(null);
        radioButton3.setButtonDrawable(new StateListDrawable());
        radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               String tag="100.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 3, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 3, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton3.setText(ss1);
                }else{
                    radioButton3.setText(tag);
                }
            }
        });
        radioButton4=rootView.findViewById(R.id.radioButton4);
        radioButton4.setButtonDrawable(null);
        radioButton4.setButtonDrawable(new StateListDrawable());
        radioButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tag="200.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 3, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 3, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton4.setText(ss1);
                }else{
                    radioButton4.setText(tag);
                }
            }
        });
        radioButton5=rootView.findViewById(R.id.radioButton5);
        radioButton5.setButtonDrawable(null);
        radioButton5.setButtonDrawable(new StateListDrawable());
        radioButton5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tag="500.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 3, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 3, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton5.setText(ss1);
                }else{
                    radioButton5.setText(tag);
                }
            }
        });
        radioButton6=rootView.findViewById(R.id.radioButton6);
        radioButton6.setButtonDrawable(null);
        radioButton6.setButtonDrawable(new StateListDrawable());
        radioButton6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tag="1.000.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 1, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 1, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton6.setText(ss1);
                }else{
                    radioButton6.setText(tag);
                }
            }
        });
        radioButton7=rootView.findViewById(R.id.radioButton7);
        radioButton7.setButtonDrawable(null);
        radioButton7.setButtonDrawable(new StateListDrawable());
        radioButton7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tag="5.000.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 1, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 1, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton7.setText(ss1);
                }else{
                    radioButton7.setText(tag);
                }
            }
        });
        radioButton8=rootView.findViewById(R.id.radioButton8);
        radioButton8.setButtonDrawable(null);
        radioButton8.setButtonDrawable(new StateListDrawable());
        radioButton8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tag="10.000.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 2, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 2, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton8.setText(ss1);
                }else{
                    radioButton8.setText(tag);
                }
            }
        });
        radioButton9=rootView.findViewById(R.id.radioButton9);
        radioButton9.setButtonDrawable(null);
        radioButton9.setButtonDrawable(new StateListDrawable());
        radioButton9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tag="50.000.000";
                if(isChecked) {
                    SpannableString ss1 = new SpannableString(tag);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 2, 0); // set size
                    ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 2, 0);// set color
//                TextView tv= (TextView) findViewById(R.id.textview);
//                tv.setText(ss1);
                    radioButton9.setText(ss1);
                }else{
                    radioButton9.setText(tag);
                }
            }
        });


        imageViewAddressBookPra=rootView.findViewById(R.id.imageViewAddressBookPra);
        imageViewVoiceBookPra=rootView.findViewById(R.id.imageViewVoiceBookPra);
        layout_no_connection=rootView.findViewById(R.id.layout_no_connection);
        AppCompatButton button_coba_lagi = rootView.findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInq();
            }
        });
       // radioTabs = rootView.findViewById(R.id.radioGroupTabs);
        appButtonItemTabIdPelanggan=rootView.findViewById(R.id.appButtonItemTabIdPelanggan);
        appButtonItemTabIdPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLayout(0);
            }
        });
        appButtonItemTabDaftarPelanggan=rootView.findViewById(R.id.appButtonItemTabDaftarPelanggan);
        appButtonItemTabDaftarPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLayout(1);
            }
        });
        radioGroupDenom = rootView.findViewById(R.id.radioGroupDenom);
        selectedId = radioGroupDenom.getCheckedRadioButtonId();
        Log.d(TAG, "onCreate: " + selectedId);
        radioButtonDenom = rootView.findViewById(selectedId);

        radioGroupDenom.onClick(radioButtonDenom);
        Log.d(TAG, "onCreate: "+radioButtonDenom.getTag().toString());
        denom = radioButtonDenom.getTag().toString();
//        String s= "Hello Everyone";
//        SpannableString ss1=  new SpannableString(radioButtonDenom.getText().toString());
//        ss1.setSpan(new RelativeSizeSpan(2f), 0,2, 0); // set size
//        ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.md_orange_500)), 0, 2, 0);// set color
////                TextView tv= (TextView) findViewById(R.id.textview);
////                tv.setText(ss1);
//        radioButtonDenom.setText(ss1);

        materialEditTextIdPelanggan = rootView.findViewById(R.id.materialEditTextIdPelanggan);
        materialEditTextIdPelanggan.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                //here is your code
                countText = s.length();

//                if (s.length() >= 4) {
//                    Log.d(TAG, "onTextChanged: "+s.length());
//
//
//                    // requestPrefixInternet(s.toString(),"INTERNET");
//                } else if (s.length() < 4) {
//
//
//                } else if (s.length() < 8) {
//
//                    //adapterProduk.clearSelections();
//
//                }
//                else
                if(s.length()==0){
                    checkboxSimpanId.setVisibility(View.VISIBLE);

                    checkboxSimpanId.setChecked(false);
                }
//                else{
//                    //if(s.length()==1){
//                    // if(textViewPlusSukses.getVisibility()==View.VISIBLE){
//                    //      textViewPlusSukses.setVisibility(View.GONE);
//                    //  }
//
//
//
//                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

//                if(s.length()>0) {
//
//                }else if(s.length()<8){
//
//                }
            }
        });
       // radioTabs.setOnCheckedChangeListener(this);



        imageViewAddressBookPra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLayout(1);
            }
        });

        imageViewVoiceBookPra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoice();
            }
        });

        checkboxSimpanId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (((CheckBox) view).isChecked()) {
                    Log.d(TAG, "onClick: true ");
                    BaseActivity.isSave = "true";
                } else {
                    Log.d(TAG, "onClick: false");
                    BaseActivity.isSave = "false";
                }
            }
        });
        button_lanjutkan = rootView.findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(String.valueOf(materialEditTextIdPelanggan.getText()).isEmpty()){
                    materialEditTextIdPelanggan.setAnimation(BaseActivity.animShake);
                    materialEditTextIdPelanggan.startAnimation(BaseActivity.animShake);
                    materialEditTextIdPelanggan.setError("ID Pelanggan Tidak Boleh Kosong");
                    Device.vibrate(materialEditTextIdPelanggan.getContext());
                    return;
                }

                selectedId = radioGroupDenom.getCheckedRadioButtonId();

                radioButtonDenom = rootView.findViewById(selectedId);

                radioGroupDenom.onClick(radioButtonDenom);
              //  Log.d(TAG, "on lanjutkan: "+radioButtonDenom.getTag().toString());
                denom = radioButtonDenom.getTag().toString();

                requestInq();
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ((PlnActivity)context).openShowCasePlnPra(context);
            }});
        return rootView;
    }
    private void requestInq() {
        ((BaseActivity)context).logEventFireBase(ProdukGroup.PLN,ProdukCode.PLNPRA,EventParam.EVENT_ACTION_REQUEST_INQ+" "+denom,EventParam.EVENT_SUCCESS,TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(BaseActivity.stringJson.requestPLNPrabayar(String.valueOf(materialEditTextIdPelanggan.getText()), denom, BaseActivity.isSave));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse((AppCompatActivity) getActivity(), jsonObject, ActionCode.INQ, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = rootView.findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(context,R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        ((PlnActivity)context).openProgressBarDialog(getActivity(), view);
    }

    private void requestLayout(int selectedTab) {
        Log.d(TAG, "requestLayout: " + selectedTab);
        switch (selectedTab) {
            case 0:

                checkboxSimpanId.setVisibility(View.VISIBLE);
                materialEditTextIdPelanggan.setText("");
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.selector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.unselector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(context,R.color.md_white_1000));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(context,R.color.md_grey_300));
                break;
            case 1:
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.unselector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.selector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(context,R.color.md_grey_300));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(context,R.color.md_white_1000));
                Intent intent = new Intent(getActivity(), DaftarPelangganActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("title", "Pelanggan PLN Pra Bayar/Token");
                intent.putExtra("product", ProdukCode.PLNPRA);
                intent.putExtra("hint", R.string.hint_cari_pelanggan);
                startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
              //  ((BaseActivity)context).overridePendingTransition(0, 0);
                checkboxSimpanId.setVisibility(View.GONE);
                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if(requestCode==ActionCode.LIST_PELANGGAN) {
                String idPel = data.getStringExtra("id_pel");
                materialEditTextIdPelanggan.setText(idPel);
                checkboxSimpanId.setVisibility(View.GONE);

                checkboxSimpanId.setChecked(false);
            }else if(requestCode==2) {
                ((BaseActivity)context).navigationBottomCustom(((BaseActivity)context).bottom_toolbar,(PreferenceClass.getString("saldo","0")));
                //radioTabsItemButton = rootView.findViewById(R.id.radioButtonItemTabIdPelanggan);
               // radioTabsItemButton.setChecked(true);
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.selector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.unselector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(context,R.color.md_white_1000));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(context,R.color.md_grey_300));
                requestLayout(0);
                checkboxSimpanId.setChecked(false);
            }else if(requestCode==RequestCode.ActionCode_VOICE_RECOGNITION) {
                ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    Log.d(TAG, "onActivityResult: "+ Arrays.asList(matches));
//                    requestPrefixRegular(selectedNumber);
                //String results = data.getStringExtra(SpeechRecognizer.RESULTS_RECOGNITION);

                // a method that finds the best matched player, and updates the view.
//                    findBestMatchPlayer(results);
                Log.d(TAG, "onActivityResult: " + Arrays.asList(matches));
                String voiceNumber = matches.get(0).toString().trim().replaceAll("[^0-9]", "");
                if(checkNumberFromString(voiceNumber)) {
                    materialEditTextIdPelanggan.setText("");
//                            materialEditTextNoHandphone.setCharacterDelay(10);
//                            materialEditTextNoHandphone.animateText(selectedNumber);
                    //materialEditTextNoHandphone.setMaskedText("**** **** **** ****");

                    materialEditTextIdPelanggan.setText(voiceNumber);
                    countText = voiceNumber.length();
//                    BaseActivity.isSave = "true";
//                    requestInq();
                }else{
                    materialEditTextIdPelanggan.setAnimation(BaseActivity.animShake);
                    materialEditTextIdPelanggan.startAnimation(BaseActivity.animShake);
                    materialEditTextIdPelanggan.setError("Maaf,Yang Anda ucapakan bukan nomor");
                    Device.vibrate(materialEditTextIdPelanggan.getContext());
                }
            }
        }
        else if(resultCode== AppCompatActivity.RESULT_CANCELED){
//            if(requestCode==1) {
//
//
//                requestLayout(0);
//
//
//            }
            if(requestCode==ActionCode.LIST_PELANGGAN){
                checkboxSimpanId.setVisibility(View.VISIBLE);

                checkboxSimpanId.setChecked(false);
            }
        }
    }


//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        int selectedId = group.getCheckedRadioButtonId();
//        Log.d(TAG, "onCheckedChanged: " + selectedId + "  " + checkedId);
//        radioTabsItemButton = rootView.findViewById(selectedId);
//        requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
//    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        if(actionCode==ActionCode.INQ) {
            ((PlnActivity)context).closeProgressBarDialog();

            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }
            InqModel inqModel = ((BaseActivity)context).gson.fromJson(response.toString(), InqModel.class);
            if (inqModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                ((BaseActivity)context).logEventFireBase(ProdukGroup.PLN,ProdukCode.PLNPRA,EventParam.EVENT_ACTION_REQUEST_INQ+" "+denom,EventParam.EVENT_SUCCESS,TAG);
               PreferenceClass.putString("saldo", FormatString.CurencyIDR(inqModel.getSaldo()));
                // BaseActivity.getInstanceBaseActivity().navigationTopCustom(getActivity(),FormatString.CurencyIDR(inqModel.getSaldo()));


                Intent intent = new Intent(context, InqueryResultActivity.class);
             //   intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "PLN");
                intent.putExtra("produkCode", ProdukCode.PLNPRA);
                intent.putExtra("produkGroup", ProdukGroup.PLN);
                intent.putExtra("subject", "PLN Pra Bayar/Token");
                intent.putExtra("id_pel", String.valueOf(materialEditTextIdPelanggan.getText()));
                intent.putExtra("inq_result", inqModel);
                intent.putExtra("struk", inqModel.getResponse_desc());
                intent.putExtra("struk_show", inqModel.getStruk_show());
                startActivityForResult(intent, 2);
              //  ((BaseActivity)context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

//        String.format(getString(R.string.response_description),"sukses");
            } else {
                ((BaseActivity)context).logEventFireBase(ProdukGroup.PLN,ProdukCode.PLNPRA,EventParam.EVENT_ACTION_REQUEST_INQ+" "+denom,EventParam.EVENT_NOT_SUCCESS,TAG);
                materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
            }
        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        // String.format(setString(R.string.response_description)
        if(responseCode.equals(NETWORK_ERROR)){
            linMain.setVisibility(View.GONE);
            layout_no_connection.setVisibility(View.VISIBLE);
        }
        if(actionCode==ActionCode.INQ) {
            ((PlnActivity)context).closeProgressBarDialog();
        }
//BaseActivity.getInstanceBaseActivity().snackBarCustomAction(rootView.findViewById(R.id.fragment),),R.string.tutup,BaseActivity.getInstanceBaseActivity().WARNING);

    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

    private void startVoice() {

        PackageManager pm = getActivity().getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        Intent intent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Fastpay Voice Command \n Ucapkan nomor Id PLN Pelanggan Anda");
            startActivityForResult(intent, RequestCode.ActionCode_VOICE_RECOGNITION);
        } else {
            ( (BaseActivity)getActivity()).showToast("Maaf,Fastpay Command Voice tidak di support di Handphone Anda");
        }


    }


}
