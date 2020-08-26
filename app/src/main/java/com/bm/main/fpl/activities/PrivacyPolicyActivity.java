package com.bm.main.fpl.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.templates.SmartTextView;
import com.bm.main.fpl.templates.TextJustification;
import com.bm.main.fpl.utils.FormatString;

public class PrivacyPolicyActivity extends BaseActivity {
    private static final String TAG = PrivacyPolicyActivity.class.getSimpleName();
    //   private Toolbar mToolbar;
//    private LinearLayout mLinHeader;
//    private LinearLayout mLinDetail;
    private LinearLayout mLinMainPrivacy;
//    private Point size;
//    private float density;

//    private String[] arrayDetail_hentikan_akses;
//    private String[] arrayDetail_hukum;

//    private HtmlTextView htmlTextView;
    // private TextView htmlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
//        Display display = getWindowManager().getDefaultDisplay();
//        size=new Point();
//        DisplayMetrics dm=new DisplayMetrics();
//        display.getMetrics(dm);
//        density=dm.density;
//        display.getSize(size);
        initView();
        // Best to use indentation that matches screen density.
//        htmlTextView=findViewById(R.id.htmlTextView);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        htmlTextView.setListIndentPx(metrics.density * 10);
//
//        htmlTextView.setHtml(R.raw.privacy);
        // htmlTextView.setText(FormatString.htmlString(getResources().getString(R.string.privacy)));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            htmlTextView.setText(Html.fromHtml(getResources().getString(R.string.privacy), Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            htmlTextView.setText(Html.fromHtml(getResources().getString(R.string.privacy)));
//        }

        String[] arrayHeader = getResources().getStringArray(R.array.array_header_privacy);
        String[] arrayDetail_pengantar = getResources().getStringArray(R.array.array_pengantar);
        String[] arrayDetail_definisi = getResources().getStringArray(R.array.array_definisi);
        String[] arrayDetail_akses = getResources().getStringArray(R.array.array_akses_apps);
        String[] arrayDetail_privacy_policy = getResources().getStringArray(R.array.array_privacy_policy);
        String[] arrayDetail_password = getResources().getStringArray(R.array.array_password);
        String[] arraySubDetail_password = getResources().getStringArray(R.array.array_sub_password_1);
        String[] arrayDetail_smskey = getResources().getStringArray(R.array.array_sms_key);
        String[] arraySubDetail_smskey = getResources().getStringArray(R.array.array_sub_sms_key_2);
        String[] arrayDetail_layanan = getResources().getStringArray(R.array.array_layanan);
        String[] arraySubDetail_layanan = getResources().getStringArray(R.array.array_sub_layanan_6);
        String[] arrayDetail_tarif = getResources().getStringArray(R.array.array_tarif);
        String[] arrayDetail_bonus = getResources().getStringArray(R.array.array_bonus);
        String[] arraySubDetail_bonus = getResources().getStringArray(R.array.array_sub_bonus_3);
        String[] arrayDetail_hentikan_akses = getResources().getStringArray(R.array.array_hentikan_akses);
        String[] arrayDetail_batas_tanggungjawab = getResources().getStringArray(R.array.array_batas_tanggungjawab);
        String[] arraySubDetail_batas_tanggungjawab = getResources().getStringArray(R.array.array_sub_batas_tanggungjawab_2);
        String[] arrayDetail_perubahan = getResources().getStringArray(R.array.array_perubahan_ketentuan);
        String[] arrayRomawi = getResources().getStringArray(R.array.array_romawi);
        String[] arrayAlfabet = getResources().getStringArray(R.array.array_alfabet);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        Typeface typefaceRoboto = ResourcesCompat.getFont(this, R.font.roboto);
        //LinearLayout view = null;

        // ViewGroup parent = findViewById(R.id.contentHost);
        LinearLayout view ;
      //  view.removeAllViews();
        for (int i = 0; i < arrayHeader.length; i++) {

            view=(LinearLayout) inflater.inflate(R.layout.layout_privacy_policy, null);
//                    ((LinearLayout) findViewById(R.id.sections)).addView(view, i);

            TextView textViewNoHeader = view.findViewById(R.id.textViewNoHeader);
            TextView textViewHeader = view.findViewById(R.id.textViewHeader);



            mLinMainPrivacy.addView(view, i);


//            LinearLayout.LayoutParams paramsH = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            paramsH.setMargins(15,10,0,10);
//            textViewHeader.setLayoutParams(paramsH);

            textViewNoHeader.setText(arrayRomawi[i]);

            textViewNoHeader.setTypeface(typefaceRoboto, Typeface.BOLD);
            textViewNoHeader.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
            textViewNoHeader.setTextSize(14);
            textViewNoHeader.setLineSpacing(0, 1.5f);
            textViewNoHeader.setGravity(Gravity.START | Gravity.CENTER);


            textViewHeader.setText(arrayHeader[i]);
            textViewHeader.setTypeface(typefaceRoboto, Typeface.BOLD);
            textViewHeader.setTextSize(14);
            textViewHeader.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
            textViewHeader.setLineSpacing(0, 1.5f);
            textViewHeader.setGravity(Gravity.START | Gravity.CENTER);


            LinearLayout mLinDetail = view.findViewById(R.id.linDetail);
            mLinDetail.removeAllViews();
            if (i == 0) {
                for (int j = 0; j < arrayDetail_pengantar.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                    SmartTextView contentDetailTextView = new SmartTextView(this);
                    contentDetailTextView.setText(arrayDetail_pengantar[j]);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentDetailTextView.setJustified(true);
                    //TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }
            if (i == 1) {
                SmartTextView contentHeaderDetailTextView = new SmartTextView(this);
                contentHeaderDetailTextView.setText(R.string.detail_header_definisi);
                contentHeaderDetailTextView.setTextSize(12);
                contentHeaderDetailTextView.setLineSpacing(0, 1.5f);
                contentHeaderDetailTextView.setGravity(Gravity.START);
                contentHeaderDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                contentHeaderDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                contentHeaderDetailTextView.setJustified(true);
//                TextJustification.justify(contentHeaderDetailTextView);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 10);
                contentHeaderDetailTextView.setLayoutParams(params);

                mLinDetail.addView(contentHeaderDetailTextView);
                for (int j = 0; j < arrayDetail_definisi.length; j++) {
//
                    //   int x=j+1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(arrayAlfabet[j]);
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));

                    SmartTextView contentDetailTextView = new SmartTextView(this);
                    contentDetailTextView.setText(arrayDetail_definisi[j]);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentDetailTextView.setJustified(true);
//                    TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);

//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }
            if (i == 2) {

                SmartTextView contentHeaderDetailTextView = new SmartTextView(this);
                contentHeaderDetailTextView.setText(R.string.p_detail_kecakapan_hukum);
                contentHeaderDetailTextView.setTextSize(14);
                contentHeaderDetailTextView.setLineSpacing(0, 1.5f);
                contentHeaderDetailTextView.setGravity(Gravity.START);
                contentHeaderDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                contentHeaderDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                contentHeaderDetailTextView.setJustified(true);
//                TextJustification.justify(contentHeaderDetailTextView);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 10);
                contentHeaderDetailTextView.setLayoutParams(params);


                mLinDetail.addView(contentHeaderDetailTextView);
            }
            if (i == 3) {

                SmartTextView contentHeaderDetailTextView = new SmartTextView(this);
                contentHeaderDetailTextView.setText(R.string.p_detail_haki);
                contentHeaderDetailTextView.setTextSize(14);
                contentHeaderDetailTextView.setLineSpacing(0, 1.5f);
                contentHeaderDetailTextView.setGravity(Gravity.START);
                contentHeaderDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                contentHeaderDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                contentHeaderDetailTextView.setJustified(true);
//                TextJustification.justify(contentHeaderDetailTextView);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 10);
                contentHeaderDetailTextView.setLayoutParams(params);


                mLinDetail.addView(contentHeaderDetailTextView);
            }

            if (i == 4) {
                for (int j = 0; j < arrayDetail_akses.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


//                    SmartTextView contentDetailTextView = new SmartTextView(this);
                    TextView contentDetailTextView = new TextView(this);
                    contentDetailTextView.setLinkTextColor(ContextCompat.getColor(this,R.color.md_blue_500));
                    contentDetailTextView.setAutoLinkMask(Linkify.ALL);
                    String sAkses = arrayDetail_akses[j];//getResources().getString(R.string.petunjuk_aktivasi_4);
                    //SpannableString ss = new SpannableString(FormatString.htmlString(sAkses));
//                    if(j==0||j==2) {
//                        contentDetailTextView.setText(FormatString.htmlString(sAkses));
//                    }else{
                        contentDetailTextView.setText(sAkses);
//                    }
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
//                    contentDetailTextView.setJustified(true);
                    TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }
            if (i == 5) {
                for (int j = 0; j < arrayDetail_privacy_policy.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                    SmartTextView contentDetailTextView = new SmartTextView(this);
                    contentDetailTextView.setText(arrayDetail_privacy_policy[j]);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentDetailTextView.setJustified(true);
//                    TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }
            if (i == 6) {
                for (int j = 0; j < arrayDetail_password.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                    SmartTextView contentDetailTextView = new SmartTextView(this);
                    contentDetailTextView.setText(arrayDetail_password[j]);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentDetailTextView.setJustified(true);
                    // TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    if (j == 2) {
//                        LinearLayout mLinSubDetail = view.findViewById(R.id.linSubDetail);
//                        mLinSubDetail.setVisibility(View.VISIBLE);
//                        mLinSubDetail.removeAllViews();
                        for (int k = 0; k < arraySubDetail_password.length; k++) {
                            //   int y = k + 1;

                            LinearLayout childLayoutSubPassword = new LinearLayout(this);

                            LinearLayout.LayoutParams linearParamsSubPassword = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            linearParamsSubPassword.setMargins(25, 10, 0, 10);
                            childLayoutSubPassword.setLayoutParams(linearParamsSubPassword);
                            childLayoutSubPassword.setWeightSum(2);


                            TextView contentTextViewSub = new TextView(this);
                            contentTextViewSub.setText(arrayAlfabet[k]);
                            contentTextViewSub.setTextSize(14);
                            contentTextViewSub.setLineSpacing(0, 1.5f);
                            contentTextViewSub.setGravity(Gravity.START|Gravity.CENTER);
                            contentTextViewSub.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentTextViewSub.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                            SmartTextView contentSubDetailTextView = new SmartTextView(this);
                            contentSubDetailTextView.setText(arraySubDetail_password[k]);
                            contentSubDetailTextView.setTextSize(14);
                            contentSubDetailTextView.setLineSpacing(0, 1.5f);
                            contentSubDetailTextView.setGravity(Gravity.START);
                            contentSubDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentSubDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                            contentSubDetailTextView.setJustified(true);
                            // TextJustification.justify(contentDetailTextView);


                            contentTextViewSub.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                            contentSubDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));

                            childLayoutSubPassword.addView(contentSubDetailTextView, 0);
                            childLayoutSubPassword.addView(contentTextViewSub, 0);

                            mLinDetail.addView(childLayoutSubPassword);
                        }
                    }


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);
                    // mLinDetail.addView(childLayoutPengantar);


                }
            }

            if (i == 7) {
                for (int j = 0; j < arrayDetail_smskey.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                    SmartTextView contentDetailTextView = new SmartTextView(this);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_password[j],Html.FROM_HTML_MODE_COMPACT));
//                    }else{
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_password[j]));
//                    }
                    contentDetailTextView.setText(arrayDetail_smskey[j]);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentDetailTextView.setJustified(true);
                    //TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    if (j == 3) {
//                        LinearLayout mLinSubDetail = view.findViewById(R.id.linSubDetail);
//                        mLinSubDetail.setVisibility(View.VISIBLE);
//                        mLinSubDetail.removeAllViews();
                        for (int k = 0; k < arraySubDetail_smskey.length; k++) {
                            //   int y = k + 1;

                            LinearLayout childLayoutSubPassword = new LinearLayout(this);

                            LinearLayout.LayoutParams linearParamsSubPassword = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            linearParamsSubPassword.setMargins(25, 10, 0, 10);
                            childLayoutSubPassword.setLayoutParams(linearParamsSubPassword);
                            childLayoutSubPassword.setWeightSum(2);


                            TextView contentTextViewSub = new TextView(this);
                            contentTextViewSub.setText(arrayAlfabet[k]);
                            contentTextViewSub.setTextSize(14);
                            contentTextViewSub.setLineSpacing(0, 1.5f);
                            contentTextViewSub.setGravity(Gravity.START | Gravity.CENTER);
                            contentTextViewSub.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentTextViewSub.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                            SmartTextView contentSubDetailTextView = new SmartTextView(this);
                            contentSubDetailTextView.setText(arraySubDetail_smskey[k]);
                            contentSubDetailTextView.setTextSize(14);
                            contentSubDetailTextView.setLineSpacing(0, 1.5f);
                            contentSubDetailTextView.setGravity(Gravity.START);
                            contentSubDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentSubDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                            contentSubDetailTextView.setJustified(true);
                            // TextJustification.justify(contentDetailTextView);


                            contentTextViewSub.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                            contentSubDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));

                            childLayoutSubPassword.addView(contentSubDetailTextView, 0);
                            childLayoutSubPassword.addView(contentTextViewSub, 0);

                            mLinDetail.addView(childLayoutSubPassword);
                        }
                    }


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }

            if (i == 8) {
                int j = 0;
                for (j = 0; j < arrayDetail_layanan.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                   // SmartTextView contentDetailTextView = new SmartTextView(this);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_password[j],Html.FROM_HTML_MODE_COMPACT));
//                    }else{
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_password[j]));
//                    }
                    TextView contentDetailTextView = new TextView(this);
                    contentDetailTextView.setLinkTextColor(ContextCompat.getColor(this,R.color.md_blue_500));
                    contentDetailTextView.setAutoLinkMask(Linkify.ALL);
                    String sLayanan = arrayDetail_layanan[j];//getResources().getString(R.string.petunjuk_aktivasi_4);
                    SpannableString ss = new SpannableString(FormatString.htmlString(sLayanan));

                    contentDetailTextView.setText(ss);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
//                    contentDetailTextView.setJustified(true);
                    TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
                if (j == 6) {
//                        LinearLayout mLinSubDetail = view.findViewById(R.id.linSubDetail);
//                        mLinSubDetail.setVisibility(View.VISIBLE);
//                        mLinSubDetail.removeAllViews();
                    for (int k = 0; k < arraySubDetail_layanan.length; k++) {
                        //   int y = k + 1;

                        LinearLayout childLayoutSubPassword = new LinearLayout(this);

                        LinearLayout.LayoutParams linearParamsSubPassword = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        linearParamsSubPassword.setMargins(25, 10, 0, 10);
                        childLayoutSubPassword.setLayoutParams(linearParamsSubPassword);
                        childLayoutSubPassword.setWeightSum(2);


                        TextView contentTextViewSub = new TextView(this);
                        contentTextViewSub.setText(arrayAlfabet[k]);
                        contentTextViewSub.setTextSize(14);
                        contentTextViewSub.setLineSpacing(0, 1.5f);
                        contentTextViewSub.setGravity(Gravity.START | Gravity.CENTER);
                        contentTextViewSub.setTypeface(typefaceRoboto, Typeface.NORMAL);
                        contentTextViewSub.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                        SmartTextView contentSubDetailTextView = new SmartTextView(this);
                        contentSubDetailTextView.setText(arraySubDetail_layanan[k]);
                        contentSubDetailTextView.setTextSize(14);
                        contentSubDetailTextView.setLineSpacing(0, 1.5f);
                        contentSubDetailTextView.setGravity(Gravity.START);
                        contentSubDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                        contentSubDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                        contentSubDetailTextView.setJustified(true);
                        // TextJustification.justify(contentDetailTextView);


                        contentTextViewSub.setLayoutParams(new TableLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                        contentSubDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));

                        childLayoutSubPassword.addView(contentSubDetailTextView, 0);
                        childLayoutSubPassword.addView(contentTextViewSub, 0);

                        mLinDetail.addView(childLayoutSubPassword);
                    }
                }
            }

            if (i == 9) {
                for (int j = 0; j < arrayDetail_tarif.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


//                    SmartTextView contentDetailTextView = new SmartTextView(this);
                    TextView contentDetailTextView = new TextView(this);
                    contentDetailTextView.setLinkTextColor(ContextCompat.getColor(this,R.color.md_blue_500));
                    contentDetailTextView.setAutoLinkMask(Linkify.ALL);
                    String sTarif = arrayDetail_tarif[j];//getResources().getString(R.string.petunjuk_aktivasi_4);
                    SpannableString ss = new SpannableString(FormatString.htmlString(sTarif));
                    contentDetailTextView.setText(ss);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
//                    contentDetailTextView.setJustified(true);
                    TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }


            if (i == 10) {
                int j = 0;
                for (j = 0; j < arrayDetail_bonus.length; j++) {
//
                    int x = j + 1;


                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                    TextView contentDetailTextView = new AppCompatTextView(this);
//                    SmartTextView contentDetailTextView = new SmartTextView(this);
                    //SmartTextView.registerFont(SmartTextView.FontType.Reg, Typeface.DEFAULT);
                    contentDetailTextView.setLinkTextColor(ContextCompat.getColor(this,R.color.md_blue_500));
                    contentDetailTextView.setAutoLinkMask(Linkify.ALL);
                    String sBonus = arrayDetail_bonus[j];//getResources().getString(R.string.petunjuk_aktivasi_4);
                    SpannableString ss = new SpannableString(FormatString.htmlString(sBonus));
//                    ClickableSpan clickableSpan = new ClickableSpan() {
//                        @Override
//                        public void onClick(@NonNull View textView) {
//                            // startActivity(new Intent(MyActivity.this, NextActivity.class));
//                           // liveChat();
//                        }
//
//                        @Override
//                        public void updateDrawState(@NonNull TextPaint ds) {
//                            super.updateDrawState(ds);
//                            ds.setUnderlineText(false);
//                        }
//                    };
//                    ss.setSpan(clickableSpan, 47, 64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    contentDetailTextView.setText(ss);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_bonus[j],Html.FROM_HTML_MODE_COMPACT));
//                    }else{
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_bonus[j]));
//                    }

//                    if (j == 2) {

                        //contentDetailTextView.setHighlightColor(Color.TRANSPARENT);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            contentDetailTextView.setText(Html.fromHtml(getString(R.string.url_fastpay), Html.FROM_HTML_MODE_COMPACT));
//                        } else {
//                            contentDetailTextView.setText(Html.fromHtml(getString(R.string.url_fastpay)));
//                        }

//                    } else {
                        contentDetailTextView.setText(ss);
//                    }
                    contentDetailTextView.setMovementMethod(LinkMovementMethod.getInstance());
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                 //   contentDetailTextView.setTypeface(typefaceRoboto, 0);
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
//
                    //contentDetailTextView.setJustified(true);
                   TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));
                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);
                    // Log.d(TAG, "onCreate: "+j);


                }
                if (j == 3) {

                    for (int k = 0; k < arraySubDetail_bonus.length; k++) {
                        LinearLayout childLayoutSubPassword = new LinearLayout(this);


                        LinearLayout.LayoutParams linearParamsSubPassword = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        linearParamsSubPassword.setMargins(25, 10, 0, 10);
                        childLayoutSubPassword.setLayoutParams(linearParamsSubPassword);
                        childLayoutSubPassword.setWeightSum(2);


                        TextView contentTextViewSub = new TextView(this);
                        contentTextViewSub.setText(arrayAlfabet[k]);
                        contentTextViewSub.setTextSize(14);
                        contentTextViewSub.setLineSpacing(0, 1.5f);
                        contentTextViewSub.setGravity(Gravity.START | Gravity.CENTER);
                        contentTextViewSub.setTypeface(typefaceRoboto, Typeface.NORMAL);
                        contentTextViewSub.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));


                        SmartTextView contentSubDetailTextView = new SmartTextView(this);
                        contentSubDetailTextView.setText(arraySubDetail_bonus[k]);
                        contentSubDetailTextView.setTextSize(14);
                        contentSubDetailTextView.setLineSpacing(0, 1.5f);
                        contentSubDetailTextView.setGravity(Gravity.START);
                        contentSubDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                        contentSubDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                        contentSubDetailTextView.setJustified(true);
                        // TextJustification.justify(contentDetailTextView);


                        contentTextViewSub.setLayoutParams(new TableLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                        contentSubDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));

                        childLayoutSubPassword.addView(contentSubDetailTextView, 0);
                        childLayoutSubPassword.addView(contentTextViewSub, 0);
                        mLinDetail.addView(childLayoutSubPassword);

                    }
                }
            }


            if (i == 11) {
                SmartTextView contentHeaderDetailTextView = new SmartTextView(this);
                contentHeaderDetailTextView.setText(R.string.detail_header_hentikan_akses);
                contentHeaderDetailTextView.setTextSize(14);
                contentHeaderDetailTextView.setLineSpacing(0, 1.5f);
                contentHeaderDetailTextView.setGravity(Gravity.START);
                contentHeaderDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                contentHeaderDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                contentHeaderDetailTextView.setJustified(true);
                // TextJustification.justify(contentHeaderDetailTextView);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 10);
                contentHeaderDetailTextView.setLayoutParams(params);

                mLinDetail.addView(contentHeaderDetailTextView);
                for (int j = 0; j < arrayDetail_hentikan_akses.length; j++) {
//
                    //   int x=j+1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(arrayAlfabet[j]);
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);

                    SmartTextView contentDetailTextView = new SmartTextView(this);
                    contentDetailTextView.setText(arrayDetail_hentikan_akses[j]);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentDetailTextView.setJustified(true);
//                    TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);

//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }


            if (i == 12) {
                for (int j = 0; j < arrayDetail_batas_tanggungjawab.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);


                    SmartTextView contentDetailTextView = new SmartTextView(this);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_password[j],Html.FROM_HTML_MODE_COMPACT));
//                    }else{
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_password[j]));
//                    }
                    contentDetailTextView.setText(arrayDetail_batas_tanggungjawab[j]);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentDetailTextView.setJustified(true);
                    //TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    if (j == 2) {
//                        LinearLayout mLinSubDetail = view.findViewById(R.id.linSubDetail);
//                        mLinSubDetail.setVisibility(View.VISIBLE);
//                        mLinSubDetail.removeAllViews();
                        for (int k = 0; k < arraySubDetail_batas_tanggungjawab.length; k++) {
                            //   int y = k + 1;

                            LinearLayout childLayoutSubPassword = new LinearLayout(this);

                            LinearLayout.LayoutParams linearParamsSubPassword = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            linearParamsSubPassword.setMargins(25, 10, 0, 10);
                            childLayoutSubPassword.setLayoutParams(linearParamsSubPassword);
                            childLayoutSubPassword.setWeightSum(2);


                            TextView contentTextViewSub = new TextView(this);
                            contentTextViewSub.setText(arrayAlfabet[k]);
                            contentTextViewSub.setTextSize(14);
                            contentTextViewSub.setLineSpacing(0, 1.5f);
                            contentTextViewSub.setGravity(Gravity.START | Gravity.CENTER);
                            contentTextViewSub.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentTextViewSub.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));

                            SmartTextView contentSubDetailTextView = new SmartTextView(this);
                            contentSubDetailTextView.setText(arraySubDetail_batas_tanggungjawab[k]);
                            contentSubDetailTextView.setTextSize(14);
                            contentSubDetailTextView.setLineSpacing(0, 1.5f);
                            contentSubDetailTextView.setGravity(Gravity.START);
                            contentSubDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentSubDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                            contentSubDetailTextView.setJustified(true);
                            // TextJustification.justify(contentDetailTextView);


                            contentTextViewSub.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                            contentSubDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));

                            childLayoutSubPassword.addView(contentSubDetailTextView, 0);
                            childLayoutSubPassword.addView(contentTextViewSub, 0);

                            mLinDetail.addView(childLayoutSubPassword);
                        }
                    }


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }


            if (i == 13) {
                for (int j = 0; j < arrayDetail_perubahan.length; j++) {
//
                    int x = j + 1;

                    LinearLayout childLayoutPengantar = new LinearLayout(this);
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0, 10, 0, 10);
                    childLayoutPengantar.setLayoutParams(linearParams);
                    childLayoutPengantar.setWeightSum(2);


                    TextView contentTextView = new TextView(this);
                    contentTextView.setText(String.valueOf(x) + ".");
                    contentTextView.setTextSize(14);
                    contentTextView.setLineSpacing(0, 1.5f);
                    contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                    contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);


                    SmartTextView contentDetailTextView = new SmartTextView(this);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_password[j],Html.FROM_HTML_MODE_COMPACT));
//                    }else{
//                        contentDetailTextView.setText(Html.fromHtml(arrayDetail_password[j]));
//                    }
                    contentDetailTextView.setText(arrayDetail_perubahan[j]);
                    contentDetailTextView.setTextSize(14);
                    contentDetailTextView.setLineSpacing(0, 1.5f);
                    contentDetailTextView.setGravity(Gravity.START);
                    contentDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                    contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                    contentDetailTextView.setJustified(true);
                    //TextJustification.justify(contentDetailTextView);


                    contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                    contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));


                    childLayoutPengantar.addView(contentDetailTextView, 0);
                    childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                    mLinDetail.addView(childLayoutPengantar);


                }
            }


            if (i == 14) {

                SmartTextView contentHeaderDetailTextView = new SmartTextView(this);
                contentHeaderDetailTextView.setText(R.string.detail_header_hukum);
                contentHeaderDetailTextView.setTextSize(14);
                contentHeaderDetailTextView.setLineSpacing(0, 1.5f);
                contentHeaderDetailTextView.setGravity(Gravity.START);
                contentHeaderDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                contentHeaderDetailTextView.setTextColor(ContextCompat.getColor(this,R.color.md_black_1000));
                contentHeaderDetailTextView.setJustified(true);
                // TextJustification.justify(contentHeaderDetailTextView);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 10);
                contentHeaderDetailTextView.setLayoutParams(params);


                mLinDetail.addView(contentHeaderDetailTextView);
            }


        }


    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        init(1);
        mLinMainPrivacy = findViewById(R.id.linMainPrivacy);
        mLinMainPrivacy.removeAllViews();
        // mLinHeader =  findViewById(R.id.linHeader);
        //mLinDetail =  findViewById(R.id.linDetail);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify da parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_right_filter){
//            onBackPressed();
//        }else
        if(id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        //   overridePendingTransition(0,0);
    }
}
