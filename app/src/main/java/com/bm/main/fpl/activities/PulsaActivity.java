package com.bm.main.fpl.activities;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
//import androidx.appcompat.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.ListGridProdukPulsaAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.PayModel;
import com.bm.main.fpl.models.ProdukPulsaModel;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.materialedittext.OnCutCopyPasteListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crowdfire.cfalertdialog.CFAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;
import static com.bm.main.single.ftl.utils.FormatUtil.checkNumberFromString;

public class PulsaActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback, ProgressResponseCallback, ListGridProdukPulsaAdapter.OnClickProduk {

    int selectedTab;
    private static final String TAG = PulsaActivity.class.getSimpleName();
    MaterialEditText materialEditTextNoHandphone;
    String type;
    ImageView imageViewPrefix;
    AVLoadingIndicatorView avi;
    ImageView imageViewAddressBook;
    //FrameLayout frameIcon;
    //  ProdukModel produkPulsaModel;

    //ScrollView fragmentReg,fragmentInet;
    RecyclerView recyclerViewNominalProduk;
    RecyclerView.LayoutManager mLayoutManagerProduk;
    //  StaggeredGridLayoutManager mLayoutManagerProduk;
//    GridLayoutManager mLayoutManagerProduk;
    ListGridProdukPulsaAdapter adapterProduk;
    @NonNull
    ArrayList<ProdukPulsaModel.Response_value> dataProduk = new ArrayList<>();
    ProdukPulsaModel produkPulsaModel;
    //AppCompatButton button_beli;
    @Nullable
    String kodeProdukPilihan, namaProdukPilihan;
    int countText = 0;
    TextView textViewPlusSukses;

    private ShimmerFrameLayout mShimmerViewContainer;
    private boolean isChecked;

    //    LinearLayout linHeader;
    private String tabPilihan;
    AppCompatButton appButtonItemTabRegular;
    AppCompatButton appButtonItemTabPaketData;
    LinearLayout linMain;
    LinearLayout layout_no_connection;
    private int actionCode;
    //NestedScrollView nestedScrollView;
    //FrameLayout bottom_toolbar;
    // LinearLayout lin_button_beli;
    View viewItemTabRegular, viewItemTabPaketData;

    ImageView imageViewVoicePulsa;
    private boolean isDelete;

    //    @SuppressLint("InflateParams")
//    @OverrideaddTextChangedListener
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa);
        //   getWindow().setBackgroundDrawable(null);
        logEventFireBase(ProdukGroup.PULSA, ProdukGroup.PULSA, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        overridePendingTransition(0, 0);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pulsa & Paket Data");
        init(0);
        //    lin_button_beli=findViewById(R.id.lin_button_beli);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
//        nestedScrollView = findViewById(R.id.nestedScrollView);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);

        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        //   frameIcon=findViewById(R.id.frameIcon);
        textViewPlusSukses = findViewById(R.id.textViewPlusSukses);
        recyclerViewNominalProduk = findViewById(R.id.recyclerViewNominalProduk);
        mLayoutManagerProduk = new GridLayoutManager(this, 3, LinearLayout.VERTICAL, false);
        recyclerViewNominalProduk.setHasFixedSize(false);
        recyclerViewNominalProduk.setLayoutManager(mLayoutManagerProduk);

        adapterProduk = new ListGridProdukPulsaAdapter(dataProduk, this, this);

        recyclerViewNominalProduk.setAdapter(adapterProduk);


        appButtonItemTabRegular = findViewById(R.id.appButtonItemTabRegular);
        viewItemTabRegular = findViewById(R.id.viewItemTabRegular);
//        appButtonItemTabRegular.setTypeface(tfRegular);
        appButtonItemTabRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLayout(0);
                selectedTab = 0;
            }
        });

        appButtonItemTabPaketData = findViewById(R.id.appButtonItemTabPaketData);
        viewItemTabPaketData = findViewById(R.id.viewItemTabPaketData);
//        appButtonItemTabPaketData.setTypeface(tfRegular);
        appButtonItemTabPaketData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLayout(1);
                selectedTab = 1;
            }
        });

        imageViewPrefix = findViewById(R.id.imageViewPrefix);
        avi = findViewById(R.id.avi);
        //imageViewPrefix.setVisibility(View.GONE);
        avi.setVisibility(View.GONE);
        materialEditTextNoHandphone = findViewById(R.id.materialEditTextNoHandphone);
        materialEditTextNoHandphone.setOnCutCopyPasteListener(new OnCutCopyPasteListener() {
            @Override
            public void onCut() {
            }

            @Override
            public void onCopy() {
            }

            @Override
            public void onPaste() {
                String selectedNumber = materialEditTextNoHandphone.getEditableText().toString();
                selectedNumber = selectedNumber.replace("-", "");
                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                if (selectedNumber.startsWith("62")) {
                    if (!selectedNumber.startsWith("9")) {
                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                    }
                }
                materialEditTextNoHandphone.setText("");
                materialEditTextNoHandphone.setText(selectedNumber);
                countText = selectedNumber.length();
                requestPrefixRegular(selectedNumber);
            }
        });
        materialEditTextNoHandphone.addTextChangedListener(new TextWatcher() {

            private String selectedNumber;

            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s.toString() + " " + count + " " + start + " " + before);
                countText = start;
                selectedNumber = s.toString();

                selectedNumber = selectedNumber.replace("-", "");
                if (selectedNumber.startsWith("62")) {
                    if (!selectedNumber.startsWith("9")) {
                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                    }
                }

                if (countText == 5) {
                    Log.d(TAG, "onTextChanged: " + s.length());
                    if (adapterProduk.getItemCount() > 0) {
                        Log.d(TAG, "onTextChanged: " + adapterProduk.getItemCount());
                    } else {
                        Log.d(TAG, "onTextChanged: " + adapterProduk.getItemCount());
                        avi.setVisibility(View.VISIBLE);

                        requestPrefixRegular(selectedNumber);

                        mShimmerViewContainer.startShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.VISIBLE);
                    }
                } else if (countText < 5) {
                    imageViewPrefix.setVisibility(View.GONE);
                    avi.setVisibility(View.GONE);
                    adapterProduk.removeData();
                    if (isChecked) {
                        adapterProduk.clearSelections();
                        kodeProdukPilihan = "";
                        namaProdukPilihan = "";
                        isChecked = false;
                    }
                } else if (countText < 10) {
                    if (isChecked) {
                        adapterProduk.clearSelections();
                        kodeProdukPilihan = "";
                        namaProdukPilihan = "";
                        isChecked = false;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(@NonNull Editable s) {
                if (s.length() > 0) {
                    if (textViewPlusSukses.getVisibility() == View.VISIBLE) {
                        textViewPlusSukses.setVisibility(View.GONE);
                    }
                }
            }
        });

        imageViewAddressBook = findViewById(R.id.imageViewAddressBook);
        imageViewAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContacts();
            }
        });

        requestPopUpPromo("PULSA");
        tabPilihan = "Pulsa Regular";

        attachKeyboardListeners();

        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionCode == ActionCode.LIST_PRODUK) {
                    //   materialEditTextNoHandphone.performClick();
                    requestPrefixRegular(materialEditTextNoHandphone.getEditableText().toString());
                } else if (actionCode == ActionCode.PAY_PULSA) {
                    requestPayment();
                }
            }
        });

        imageViewVoicePulsa = findViewById(R.id.imageViewVoicePulsa);
        imageViewVoicePulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoice();
            }
        });

        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        openShowCasePulsa();
    }

    private void requestPayment() {
        logEventFireBase(ProdukGroup.PULSA, tabPilihan + " " + kodeProdukPilihan, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestPulsaPayment(kodeProdukPilihan, materialEditTextNoHandphone.getEditableText().toString().replace(" ", "")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponsePayment(this, jsonObject, ActionCode.PAY_PULSA, this);
        actionCode = ActionCode.PAY_PULSA;

        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void requestPrefixRegular(@NonNull String prefix) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestPulsaPrefix(prefix.replace(" ", "")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PRODUK, this);
        actionCode = ActionCode.LIST_PRODUK;
        Log.d(TAG, "requestPrefixRegular: ");

    }

    private void requestLayout(int selTab) {
        if (isChecked) {
            adapterProduk.clearSelections();
            kodeProdukPilihan = "";
            namaProdukPilihan = "";
            isChecked = false;
        }
        switch (selTab) {
            case 0:
                tabPilihan = "Pulsa Regular";
                viewItemTabRegular.setBackground(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                viewItemTabPaketData.setBackground(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                appButtonItemTabRegular.setBackgroundDrawable(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.selector_tab_btn_pulsa));
                appButtonItemTabPaketData.setBackgroundDrawable(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.unselector_tab_btn_pulsa));
                appButtonItemTabRegular.setTextColor(ContextCompat.getColor(PulsaActivity.this, R.color.colorPrimary_ppob));
                appButtonItemTabPaketData.setTextColor(ContextCompat.getColor(PulsaActivity.this, R.color.md_white_1000));
                if (materialEditTextNoHandphone.getEditableText().length() >= 4) {
                    adapterProduk.getFilter().filter("REGULAR");
                } else if (materialEditTextNoHandphone.getEditableText().length() < 4) {
                    if (produkPulsaModel != null) {
                        adapterProduk.getFilter().filter("");
                    }
                }
                break;
            case 1:
                tabPilihan = "Paket Data";
                viewItemTabRegular.setBackground(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                viewItemTabPaketData.setBackground(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                appButtonItemTabRegular.setBackgroundDrawable(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.unselector_tab_btn_pulsa));
                appButtonItemTabPaketData.setBackgroundDrawable(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.selector_tab_btn_pulsa));
                appButtonItemTabRegular.setTextColor(ContextCompat.getColor(PulsaActivity.this, R.color.md_white_1000));
                appButtonItemTabPaketData.setTextColor(ContextCompat.getColor(PulsaActivity.this, R.color.colorPrimary_ppob));
                if (materialEditTextNoHandphone.getEditableText().length() >= 4) {
                    adapterProduk.getFilter().filter("INTERNET");
                } else if (materialEditTextNoHandphone.getEditableText().length() < 4) {
                    if (produkPulsaModel != null) {
                        adapterProduk.getFilter().filter("");
                    }
                }
                break;
        }

        Log.d(TAG, "requestLayout: " + selectedTab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    private void startVoice() {
        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Fastpay Voice Command \n Ucapkan Nomor HP Pelanggan Anda");
            startActivityForResult(intent, RequestCode.ActionCode_VOICE_RECOGNITION);
        } else {
            showToast("Maaf,Fastpay Command Voice tidak di support di Handphone Anda");
        }
    }

    @Override
    public void onBackPressed() {
        if (bottom_toolbar.getVisibility() == View.GONE) {
            closeKeyboard(this);
        }
        if (selectedTab != 0) {
            requestLayout(0);
            selectedTab = 0;
        } else {
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        if (layout_no_connection.getVisibility() == View.VISIBLE) {
            layout_no_connection.setVisibility(View.GONE);
        }
        if (linMain.getVisibility() == View.GONE) {
            linMain.setVisibility(View.VISIBLE);
        }

        if (actionCode == ActionCode.PAY_PULSA) {//PAY
            Log.d(TAG, "onSuccess: PAY");
            closeProgressBarDialog();
            PayModel payModel = gson.fromJson(response.toString(), PayModel.class);
            switch (payModel.getResponse_code()) {
                case ResponseCode.SUCCESS:
                    logEventFireBase(ProdukGroup.PULSA, tabPilihan + " " + kodeProdukPilihan, EventParam.EVENT_ACTION_RESULT_PAY, EventParam.EVENT_SUCCESS, TAG);
                    textViewPlusSukses.setVisibility(View.VISIBLE);
                    textViewPlusSukses.startAnimation(slideDown);
                    PreferenceClass.putString("saldo", FormatString.CurencyIDR(payModel.getSaldo()));
                    textViewPlusSukses.setText(String.format(getString(R.string.sukses_pulsa), namaProdukPilihan, materialEditTextNoHandphone.getEditableText()));
                    new_popup_alert(PulsaActivity.this, "Info", String.format(getString(R.string.sukses_pulsa), namaProdukPilihan, materialEditTextNoHandphone.getEditableText()));

                    materialEditTextNoHandphone.setText("");
                    imageViewPrefix.setVisibility(View.GONE);
                    avi.setVisibility(View.GONE);
                    adapterProduk.removeData();
                    navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
                    break;
                case "03":
                    new_popup_alert_session(this, "Informasi", payModel.getResponse_desc());
                    break;
                case "06":
                    new_popup_alert_session_deposit(this, "Informasi", payModel.getResponse_desc());
                    break;
                default:
                    logEventFireBase(ProdukGroup.PULSA, tabPilihan + " " + kodeProdukPilihan, EventParam.EVENT_ACTION_RESULT_PAY, EventParam.EVENT_NOT_SUCCESS, TAG);
                    new_popup_alert(PulsaActivity.this, "Info", payModel.getResponse_desc());
                    break;
            }

        } else if (actionCode == ActionCode.LIST_PRODUK) {
            Log.d(TAG, "onSuccess: LIST " + response.toString());
            PreferenceClass.putJSONObject("pulsa_list", response);
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
            produkPulsaModel = gson.fromJson(response.toString(), ProdukPulsaModel.class);
            switch (produkPulsaModel.getResponse_code()) {
                case ResponseCode.SUCCESS:
                    dataProduk.clear();
                    dataProduk.addAll(produkPulsaModel.getResponse_value());

                    adapterProduk.notifyDataSetChanged();
                    Glide.with(this).asBitmap().load(produkPulsaModel.getResponse_value().get(0).getProduct_url()).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(imageViewPrefix) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                            // here it's similar to RequestListener, but with less information (e.g. no model available)
                            super.onResourceReady(resource, animation);
                            imageViewPrefix.setVisibility(View.VISIBLE);
                            avi.setVisibility(View.GONE);
                            // here you can be sure it's already set
                        }

                        // +++++ OR +++++
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            imageViewPrefix.setVisibility(View.VISIBLE);
                            avi.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            avi.setVisibility(View.GONE);
                        }
                    });
                    recyclerViewNominalProduk.setVisibility(View.VISIBLE);
                    if (selectedTab == 0) {
                        adapterProduk.getFilter().filter("REGULAR");
                    } else if (selectedTab == 1) {
                        adapterProduk.getFilter().filter("INTERNET");
                    }
                    break;
                case "03":
                    new_popup_alert_session(this, "Informasi", produkPulsaModel.getResponse_desc());
                    break;
                default:
                    imageViewPrefix.setVisibility(View.GONE);
                    avi.setVisibility(View.GONE);

                    adapterProduk.removeData();
                    if (isChecked) {
                        adapterProduk.clearSelections();
                        kodeProdukPilihan = "";
                        namaProdukPilihan = "";
                        isChecked = false;
                    }
                    materialEditTextNoHandphone.setAnimation(BaseActivity.animShake);
                    materialEditTextNoHandphone.startAnimation(BaseActivity.animShake);
                    materialEditTextNoHandphone.setError("No Handphone Salah");
                    Device.vibrate(this);

                    break;
            }
        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        if (responseCode.equals(NETWORK_ERROR)) {
            if (this.actionCode == ActionCode.PAY_PULSA) {
                closeProgressBarDialog();
                new_popup_alert_failure(PulsaActivity.this, responseDescription);
            } else {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                avi.setVisibility(View.GONE);
                imageViewPrefix.setVisibility(View.VISIBLE);
                imageViewPrefix.setImageDrawable(null);
                imageViewPrefix.setImageDrawable(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.ic_no_internet));
                linMain.setVisibility(View.GONE);
                layout_no_connection.setVisibility(View.VISIBLE);
            }
        } else {
            if (this.actionCode == ActionCode.PAY_PULSA) {
                closeProgressBarDialog();
                new_popup_alert_failure(PulsaActivity.this, responseDescription);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri result;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ActionCode.PICK_CONTACT:
                    Cursor cursor = null;
                    String phoneNumber = "";
                    final List<String> allNumbers = new ArrayList<>();
                    try {
                        result = data.getData();
                        assert result != null;
                        String id = result.getLastPathSegment();
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                        assert cursor != null;
                        int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        if (cursor.moveToFirst()) {
                            while (!cursor.isAfterLast()) {
                                phoneNumber = cursor.getString(phoneIdx);
                                allNumbers.add(phoneNumber);
                                cursor.moveToNext();
                            }
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "onActivityResult error: " + e.toString());
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        HashSet hs = new HashSet();
                        hs.addAll(allNumbers);
                        allNumbers.clear();
                        allNumbers.addAll(hs);
                        final String[] items = allNumbers.toArray(new String[allNumbers.size()]);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(PulsaActivity.this, R.style.AlertDialogCustom));

                        ViewGroup parent = findViewById(R.id.contentHost);
                        View dialogView = View.inflate(this, R.layout.dialog_custom_contact, parent);

                        builder.setView(dialogView);
                        TextView textViewTitleDialog = dialogView.findViewById(R.id.textViewTitleDialog);
                        textViewTitleDialog.setText("Pilih Nomor Handphone Tujuan");

                        //Prepare ListView in dialog
                        ListView listViewPhoneNumber = dialogView.findViewById(R.id.listViewPhoneNumber);
                        final AlertDialog alert = builder.create();

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                R.layout.layout_item_phonenumber, R.id.textViewItemPhone, items);
                        listViewPhoneNumber.setAdapter(adapter);
                        listViewPhoneNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                String selectedNumber = items[position];
                                selectedNumber = selectedNumber.replace("-", "");
                                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                                if (selectedNumber.startsWith("62")) {
                                    if (!selectedNumber.startsWith("9")) {
                                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                                    }
                                }
                                materialEditTextNoHandphone.setText("");
                                materialEditTextNoHandphone.setText(selectedNumber);
                                countText = selectedNumber.length();
                                requestPrefixRegular(selectedNumber.replace(" ", ""));
                                alert.dismiss();
                            }
                        });

                        if (allNumbers.size() > 1) {
                            alert.show();
                        } else {
                            String selectedNumber = phoneNumber;
                            selectedNumber = selectedNumber.replace("-", "");
                            selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                            if (selectedNumber.startsWith("62")) {
                                if (!selectedNumber.startsWith("9")) {
                                    selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                                }
                            }
                            materialEditTextNoHandphone.setText("");

                            materialEditTextNoHandphone.setText(selectedNumber);
                            countText = selectedNumber.length();

                            requestPrefixRegular(selectedNumber);
                        }
                    }
                    break;
                case RequestCode.ActionCode_VOICE_RECOGNITION:
                    ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    // a method that finds the best matched player, and updates the view.
//                    findBestMatchPlayer(results);
                    Log.d(TAG, "onActivityResult: " + Arrays.asList(matches));
                    String voiceNumber = matches.get(0).toString().trim().replaceAll("[^0-9]", "");
                    if (checkNumberFromString(voiceNumber)) {
                        materialEditTextNoHandphone.setText("");

                        materialEditTextNoHandphone.setText(voiceNumber);
                        countText = voiceNumber.length();
                        requestPrefixRegular(voiceNumber);
                    } else {
                        materialEditTextNoHandphone.setAnimation(BaseActivity.animShake);
                        materialEditTextNoHandphone.startAnimation(BaseActivity.animShake);
                        materialEditTextNoHandphone.setError("Maaf,Yang Anda ucapakan bukan nomor");
                        Device.vibrate(this);
                    }
                    break;
            }
        }
    }

    @Override
    public void onClickProduk(@NonNull ProdukPulsaModel.Response_value produk, int adapterPos) {
        Log.d(TAG, "onClickProduk: " + produk.getProduct_code() + " " + produk.getProduct_name() + " " + produk.getProduct_nominal() + " " + produk.getProduct_nominal().replace("Rp ", "").replace(".", ""));
        if (materialEditTextNoHandphone.getEditableText().toString().isEmpty()) {
            materialEditTextNoHandphone.setAnimation(BaseActivity.animShake);
            materialEditTextNoHandphone.startAnimation(BaseActivity.animShake);
            materialEditTextNoHandphone.setError("No Handphone Tidak Boleh Kosong");
            Device.vibrate(this);
        } else if (countText >= 9) {
            String nominal = produk.getProduct_nominal().replace("Rp ", "").replace(".", "").trim();
            isChecked = true;
            adapterProduk.clearSelections();
            adapterProduk.toggleSelection(adapterPos);
            kodeProdukPilihan = produk.getProduct_code();
            namaProdukPilihan = produk.getProduct_name();
            closeKeyboard(this);
            String produkName = produk.getProduct_name();
            ViewGroup parent = findViewById(R.id.contentHost);
            View v = View.inflate(this, R.layout.dialog_header_pulsa_layout, parent);

            final ImageView imageViewProvider = v.findViewById(R.id.imageViewProvider);
            Glide.with(this).asBitmap().load(produk.getProduct_url()).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).override(50, 50).into(new BitmapImageViewTarget(imageViewProvider) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                    // here it's similar to RequestListener, but with less information (e.g. no model available)
                    super.onResourceReady(resource, animation);
                    // here you can be sure it's already set
                }

                // +++++ OR +++++
                @Override
                protected void setResource(Bitmap resource) {
                    super.setResource(resource);
                    imageViewProvider.setVisibility(View.VISIBLE);
                    imageViewProvider.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // here you can be sure it's already set
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    imageViewProvider.setVisibility(View.GONE);
                }
            });

            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(PulsaActivity.this, R.style.CFDialog);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                    .setBackgroundColor(ContextCompat.getColor(PulsaActivity.this, R.color.black_overlay_dark))
                    .setTitle("Pembelian " + tabPilihan)
                    .setMessage("Apakah Anda yakin akan membeli pulsa " + produkName + " ke nomor " + materialEditTextNoHandphone.getEditableText() + " ?")
                    .setTextGravity(Gravity.CENTER)
                    .setHeaderView(v)
                    .setCancelable(true);
            builder.addButton("Beli Sekarang", -1, ContextCompat.getColor(PulsaActivity.this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(@NonNull DialogInterface dialog, int which) {
                    dialog.dismiss();
                    requestPayment();

                }
            });
            builder.onDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    adapterProduk.clearSelections();
                }
            });

            builder.show();
        }
    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        Log.d(TAG, "onShowKeyboard: " + keyboardHeight);
        bottom_toolbar.setVisibility(View.GONE);
    }

    @Override
    protected void onHideKeyboard() {
        Log.d(TAG, "onHideKeyboard: ");
        bottom_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mShimmerViewContainer.stopShimmerAnimation();
        Glide.get(this).clearMemory();
        super.onDestroy();
    }

    private void openShowCasePulsa() {
        if (PreferenceClass.getInt(TAG, 0) != 1) {
            showCaseFirst(this, "", "Klik Icon untuk memilih Kontak yang ada di handphone", imageViewAddressBook);

            mGbuilder.setGuideListener(new GuideView.GuideListener() {
                @Override
                public void onDismiss(View view) {
                    PreferenceClass.putInt(TAG, 1);
                }
            });
            mGuideView = mGbuilder.build();
            mGuideView.show();
        }
    }

    public void showContacts() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "showContacts: ");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, RequestCode.ActionCode_READ_CONTACTS);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, ActionCode.PICK_CONTACT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCode.ActionCode_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                new_popup_alert_two_buttonPermision(PulsaActivity.this, "Peringatan", R.string.content_alert_kontak);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}