package com.bm.main.fpl.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.main.fpl.utils.Device;
import com.bm.main.scm.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.UserModel;
import com.bm.main.fpl.templates.TextViewPlus;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class AkunSayaActivity extends BaseActivity implements JsonObjectResponseCallback,TextToSpeech.OnInitListener  {
    private static final String TAG = AkunSayaActivity.class.getSimpleName();
    TextViewPlus textViewPlusAlias, textViewPlusIdOutlet, textViewPlusNamaOutlet;

    TextView textViewNamaPemilik, textViewNoHpPemilik, textViewEmailPemilik, textViewAlamatPemilik;
    TextView textViewNamaLoket, textViewNoHpOutlet, textViewNoWhatapp, textViewAlamatLoket;
//    ImageView imageViewRating, imageViewUpgrade;
    ImageView  imageViewUpgrade;
//    LinearLayout linRatingOutlet;
    private FrameLayout frame_upgrade;
    private String dataAkunTemp;
    private TextView view;

    LinearLayout linRatingOutletAkun;

    //  VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_saya);

        textToSpeech =new TextToSpeech(this, this);
        //  overridePendingTransition(0, 0);
        logEventFireBase("Akun Saya", "Akun Saya", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Akun Saya");
        init(0);
        UserModel userModel = PreferenceClass.getUser();
        frame_upgrade = findViewById(R.id.frame_upgrade);
        if (PreferenceClass.getUser().getRating().equals("5")) {
            frame_upgrade.setVisibility(View.INVISIBLE);
        }


        linRatingOutletAkun=findViewById(R.id.linRatingOutletAkun);
//        imageViewRating = findViewById(R.id.imageViewRating);
      //  linRatingOutlet=findViewById(R.id.linRatingOuletAkun);
        imageViewUpgrade = findViewById(R.id.imageViewUpgrade);
        textViewPlusAlias = findViewById(R.id.textViewPlusAlias);
        textViewPlusIdOutlet = findViewById(R.id.textViewPlusIdOutlet);
        textViewPlusNamaOutlet = findViewById(R.id.textViewPlusNamaOutlet);
        String alias = userModel.getAlias() == null ? "" : userModel.getAlias().toUpperCase();
        String outlet = userModel.getNama_loket() == null ? "" : userModel.getNama_loket().toUpperCase();
        String notlp_outlet = userModel.getNotelp_loket() == null ? "" : userModel.getNotelp_loket().toUpperCase();
        String notlp_wa = userModel.getNo_whatsapp_loket() == null ? "" : userModel.getNo_whatsapp_loket().toUpperCase();
        String alamat_outlet = userModel.getAlamat_loket() == null ? "" : userModel.getAlamat_loket().toUpperCase();

        textViewPlusAlias.setText(alias);
        textViewPlusIdOutlet.setText(PreferenceClass.getUser().getId_outlet().toUpperCase());
        textViewPlusNamaOutlet.setText(outlet);

        textViewNamaLoket = findViewById(R.id.textViewNamaLoket);
        textViewNamaLoket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptDialog(textViewNamaLoket, "Nama Loket", 1);
            }
        });
        textViewNamaLoket.setText(outlet);

        textViewNoHpOutlet = findViewById(R.id.textViewNoHpOutlet);
        textViewNoHpOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptDialog(textViewNoHpOutlet, "No Hp Loket", 0);
            }
        });
        textViewNoHpOutlet.setText(notlp_outlet);

        textViewNoWhatapp = findViewById(R.id.textViewNoWhatapp);
        textViewNoWhatapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptDialog(textViewNoWhatapp, "No Whatsapp", 0);
            }
        });
        textViewNoWhatapp.setText(notlp_wa);
        textViewAlamatLoket = findViewById(R.id.textViewAlamatLoket);
        textViewAlamatLoket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptDialog(textViewAlamatLoket, "Alamat Loket", 1);
            }
        });
        textViewAlamatLoket.setText(alamat_outlet);


        textViewNamaPemilik = findViewById(R.id.textViewNamaPemilik);
        textViewNamaPemilik.setText(PreferenceClass.getUser().getNama_pemilik().toUpperCase());
        textViewNoHpPemilik = findViewById(R.id.textViewNoHpPemilik);
        textViewNoHpPemilik.setText(PreferenceClass.getUser().getNotelp_pemilik().toUpperCase());
        textViewEmailPemilik = findViewById(R.id.textViewEmailPemilik);
        textViewEmailPemilik.setText(PreferenceClass.getUser().getEmail_pemilik().toUpperCase());
        textViewAlamatPemilik = findViewById(R.id.textViewAlamatPemilik);
        textViewAlamatPemilik.setText(PreferenceClass.getUser().getAlamat_pemilik().toUpperCase());
        final LinearLayout mLinDetail = findViewById(R.id.linRatingOutletAkun);

        switch (PreferenceClass.getUser().getRating()) {
            case "1":
                // imageViewRating.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star1));
              //  mLinDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.card_acc_basic));
                Glide.with(this).asBitmap().load(R.drawable.card_acc_basic).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                        super.onLoadFailed( errorDrawable);
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                        Resources res=getResources();
                        Drawable drawable=new BitmapDrawable(res, resource);
mLinDetail.setBackground(drawable);
                    }
                });

                break;
            case "3":
//            imageViewRating.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star3));
               // mLinDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.card_acc_pro));
                Glide.with(this).asBitmap().load(R.drawable.card_acc_pro).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                    @Override
                    public void onLoadFailed( Drawable errorDrawable) {
                          super.onLoadFailed( errorDrawable);
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                        Resources res=getResources();
                        Drawable drawable=new BitmapDrawable(res, resource);
                        mLinDetail.setBackground(drawable);
                    }
                });
                break;
            case "5":
//            imageViewRating.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star5));
               // mLinDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.card_acc_enterprise));
                Glide.with(this).asBitmap().load(R.drawable.card_acc_enterprise).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                    @Override
                    public void onLoadFailed( Drawable errorDrawable) {
                          super.onLoadFailed(errorDrawable);
                    }


                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                        Resources res=getResources();
                        Drawable drawable=new BitmapDrawable(res, resource);
                        mLinDetail.setBackground(drawable);
                    }
                });
                break;
        }
        LinearLayout mLinDetailStart = findViewById(R.id.linRatingOutletAkunStart);
        mLinDetailStart.removeAllViews();
        for(int i=0;i<Integer.parseInt(PreferenceClass.getUser().getRating());i++){
            int newHeight = 32; // New height in pixels
            int newWidth = 32; // New width in pixels
            LinearLayout childLayoutPengantar = new LinearLayout(this);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(newWidth,newHeight);
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
            childLayoutPengantar.setLayoutParams(linearParams);
            ImageView image_view = new ImageView(this);

            image_view.setBackground(ContextCompat.getDrawable(this,R.drawable.ic_star_white_24dp));

            childLayoutPengantar.addView(image_view);
            mLinDetailStart.addView(childLayoutPengantar,0);
        }

        LinearLayout carouselView = findViewById(R.id.carouselView);
        float heightDp = (float) (getResources().getDisplayMetrics().heightPixels * 2) / 6;
        //    private PreferenceClass preferenceClass;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) carouselView.getLayoutParams();

        lp.height = (int) heightDp;
        carouselView.setMinimumHeight(lp.height);

//        videoView=findViewById(R.id.videoView);
//        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash);
//        videoView.setVideoURI(uri);
//        videoView.requestFocus();
//videoView.buildDrawingCache(true);
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                if(isFinishing()){
//                    return;
//                }
//               // videoView.stopPlayback();
//            }
//        });
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            public void onPrepared(MediaPlayer mp) {
//                videoView.start();
//               // View placeholder = findViewById(R.id.placeholder);
//
//               // placeholder.setVisibility(View.GONE);
//
//            }
//        });

        imageViewUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(AkunSayaActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    String apps = "com.android.chrome";
                    boolean installed  = Device.checkAppInstall(AkunSayaActivity.this, apps);
                    if (installed) {
                    String THE_URL = "https://www.fpkita.com:9191/index.php/modulorder/upgrade";
                    Intent browserIntent = new Intent();
                    browserIntent.setAction(Intent.ACTION_VIEW);
                    browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                    browserIntent.setData(Uri.parse(PreferenceClass.getUser().getUpgrade() + PreferenceClass.getAuth()));


                    startActivity(browserIntent);
                    }else{
                        Toast.makeText(AkunSayaActivity.this,"Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu",Toast.LENGTH_LONG).show();
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                        startActivity(webIntent);
                    }
                }
            }
        });
        if (frame_upgrade.getVisibility() == View.VISIBLE) {
            if (PreferenceClass.getInt(TAG, 0) != 1) {
                showCaseFirst(this, "", "Klik Icon untuk melakukan upgrade paket Anda", frame_upgrade);
                mGbuilder.setGuideListener(new GuideView.GuideListener() {
                    @Override
                    public void onDismiss(View view) {
//                       switch (view.getId()) {
//
//                           case R.id.frame_upgrade:
                        PreferenceClass.putInt(TAG, 1);
//                               return;
//                       }
//                       mGuideView = client.build();
//                       mGuideView.show();

                    }
                });
                mGuideView = mGbuilder.build();
                mGuideView.show();
            }
        }
        linRatingOutletAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak("Hai "+PreferenceClass.getUser().getNama_pemilik(), TextToSpeech.QUEUE_FLUSH, null);
                if(!textToSpeech.isSpeaking()) {
                    textToSpeech = new TextToSpeech(AkunSayaActivity.this, AkunSayaActivity.this);
                    System.out.println("tts restarted");
                    textToSpeech.speak("Hai "+PreferenceClass.getUser().getNama_pemilik(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
//            int ttsLang = textToSpeech.setLanguage(Locale.getDefault());

            int ttsLang = textToSpeech.setLanguage(new Locale("id","ID"));
            Log.d(TAG, "onInit: "+ttsLang);
            if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                    || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d(TAG, "The Language is not supported!"+TextToSpeech.LANG_MISSING_DATA+" "+TextToSpeech.LANG_NOT_SUPPORTED);
                textToSpeech.setLanguage(Locale.US);
            } else {
                Log.d(TAG, "Language Supported.");
                textToSpeech.setLanguage(new Locale("id","ID"));
            }
            Log.d(TAG, "Initialization success.");


        } else {
            Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //videoView.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        // if(textViewAlamatPemilik.getVisibility()==View.GONE) {
        closeKeyboard(this);
        //  }
        finish();
        //  overridePendingTransition(0, 0);


    }

    public static final int number = 0;
    public static final int character = 1;

    /**
     * This method init.
     *
     * @param type 0=number
     *             1=character
     */
    public void promptDialog(@NonNull final TextView textView, String hint, int type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AkunSayaActivity.this);

        //  LayoutInflater inflater = this.getLayoutInflater();
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup parent = findViewById(R.id.contentHost);
        final View dialogView = View.inflate(this, R.layout.promptdialog_custom_view, parent);
//        AppCompatButton dialognegativebtn =  dialogView.findViewById(R.id.dialog_negative_btn);
//        AppCompatButton dialogpositivebtn = dialogView.findViewById(R.id.dialog_positive_btn);
//        MaterialEditText etnamelong =  dialogView.findViewById(R.id.et_name_long);
//        MaterialEditText etname = dialogView.findViewById(R.id.et_name);
//        TextView dialogtitle = dialogView.findViewById(R.id.dialog_title);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        final AppCompatButton btn_positive = dialogView.findViewById(R.id.dialog_positive_btn);
        AppCompatButton btn_negative = dialogView.findViewById(R.id.dialog_negative_btn);
        TextView title = dialogView.findViewById(R.id.dialog_title);
        MaterialEditText et_name = null;// = dialogView.findViewById(R.id.et_name);
        title.setText(hint);
        if (type == number) {
            et_name = dialogView.findViewById(R.id.et_name);
            et_name.setVisibility(View.VISIBLE);
//    et_name.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//
//    et_name.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            et_name.setText(textView.getText().toString());

            et_name.setText(textView.getText().toString());
            et_name.selectAll();
            et_name.setHint(hint);
        } else if (type == character) {
            et_name = dialogView.findViewById(R.id.et_name_long);
            et_name.setVisibility(View.VISIBLE);
//    et_name.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//    et_name.setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTUVWXYZ,. ?!0123456789-/"));
//    et_name.setMaxLines(4);
//    et_name.setLines(4);
//    et_name.setSingleLine(false);
//et_name.setMaxCharacters(100);

            et_name.setText(textView.getText().toString());
            et_name.selectAll();
            et_name.setHint(hint);
            final MaterialEditText finalEt_name1 = et_name;
            et_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(@NonNull Editable s) {
//            if(s != null && s.length() > 0 && s.charAt(s.length() - 1) == ' '){
//                //dp something
//            }
                    if (s.length() > 100) {
                        finalEt_name1.setError("Melebihi Batas Karakter");
                        btn_positive.setEnabled(false);
                    } else if (s.length() <= 100) {
                        btn_positive.setEnabled(true);
                    }
                }
            });

        }


        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        final MaterialEditText finalEt_name = et_name;
        // Set positive/yes button click listener

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    if (finalEt_name.isFocusable() == true) {
                        inputManager.hideSoftInputFromWindow(finalEt_name.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }
                }


                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(AkunSayaActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    String name = finalEt_name.getText().toString();
//                Toast.makeText(getApplication(),
//                        "Submitted airLineName : " + airLineName, Toast.LENGTH_SHORT).show();
                    // Say hello to the submitter

                    //  closeKeyboardDialog(AkunSayaActivity.this,dialogView);
                    if (finalEt_name.getText().length() == 0) {
                        finalEt_name.setError("Tidak Boleh Kosong");
                    } else {

                        dialog.dismiss();
                        // textView.setText(airLineName);
//                        view=textView;
//                        dataAkunTemp=airLineName;
                        requestUpdateLoket(textView, name);
                    }


                }
            }
        });

        // Set negative/no button click listener
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/cancel the alert dialog
                //dialog.cancel();
                //  closeKeyboardDialog(AkunSayaActivity.this,dialogView);

                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    if (finalEt_name.isFocusable() == true) {
                        inputManager.hideSoftInputFromWindow(finalEt_name.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }
                }
//                if (finalEt_name.getText().length() == 0) {
//                    finalEt_name.setError("Tidak Boleh Kosong");
//                } else {
                dialog.dismiss();
//                }

                //  Toast.makeText(getApplication(),
                //          "No button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // this.onDismiss(dialog);
                Log.d(TAG, "onDismiss: ");


                // onBackPressed();
//        if(finalEt_name.isFocusableInTouchMode()) {
//            IBinder token =       finalEt_name.getWindowToken();
//            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(token, 0);
//        }
//        View view = AkunSayaActivity.this.getCurrentFocus();
//        if (view != null)
//        {
//            closeKeyboardDialog(view);
//        }
            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
    }

    private void requestUpdateLoket(@NonNull TextView textView, String name) {
        JSONObject jsonObject = new JSONObject();
        try {
            switch (textView.getTag().toString()) {
                case "Nama Loket":
                    jsonObject = new JSONObject(stringJson.requestUpdateLoket(name, textViewAlamatLoket.getText().toString(), textViewNoHpOutlet.getText().toString(), textViewNoWhatapp.getText().toString()));
                    break;
                case "No Hp Outlet":
                    jsonObject = new JSONObject(stringJson.requestUpdateLoket(textViewNamaLoket.getText().toString(), textViewAlamatLoket.getText().toString(), name, textViewNoWhatapp.getText().toString()));
                    break;
                case "Nomor Whatsapp":
                    jsonObject = new JSONObject(stringJson.requestUpdateLoket(textViewNamaLoket.getText().toString(), textViewAlamatLoket.getText().toString(), textViewNoHpOutlet.getText().toString(), name));
                    break;
                case "Alamat Loketnya":
                    jsonObject = new JSONObject(stringJson.requestUpdateLoket(textViewNamaLoket.getText().toString(), name, textViewNoHpOutlet.getText().toString(), textViewNoWhatapp.getText().toString()));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(AkunSayaActivity.this, jsonObject, ActionCode.UPDATE_LOKET, this);
    }


    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        //  Gson gson = new GsonBuilder().create();
        Log.d(TAG, "onSuccess: " + response.toString());

//        try {
//            if (response.getString("response_code").equals("03")) {
//                new_popup_alert_session(this, "Informasi",response.getString("response_desc"));
//            } else {
        if (actionCode == ActionCode.UPDATE_LOKET) {
            try {
                if (response.getString("response_code").equals("00")) {
                    UserModel userModel = PreferenceClass.getUser();
                    userModel.setNama_loket(response.getString("nama_loket"));
                    userModel.setAlamat_loket(response.getString("alamat_loket"));
                    userModel.setNo_whatsapp_loket(response.getString("no_whatsapp_loket"));
                    userModel.setNotelp_loket(response.getString("notelp_loket"));

                    PreferenceClass.storedUser(userModel);
                    finish();
                    startActivity(getIntent());
                } else if (response.getString("response_code").equals("03")) {
                    new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
                }
            } catch (JSONException jsone) {
                Log.d(TAG, "JSONException: " + jsone.toString());
            }
        }
//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        Log.d(TAG, "onFailure: ");
        new_popup_alert_failure_pay(AkunSayaActivity.this, responseDescription);
//        Intent intent=getIntent();
//        startActivity(intent);
    }

}
