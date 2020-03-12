package com.bm.main.fpl.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.pos.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.InqModel;
import com.bm.main.fpl.templates.CustomFontCheckBox;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.materialedittext.OnCutCopyPasteListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.zxing.client.android.Intents;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class PascaBayarActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback {
    private static final String TAG = PascaBayarActivity.class.getSimpleName();
    // private Context context;
    MaterialEditText materialEditTextProduk;
    MaterialEditText materialEditTextIdPelanggan;
    CustomFontCheckBox checkboxSimpanId;
    AppCompatButton button_lanjutkan;
    private String produkCode;
    //    private RadioGroup radioTabs;
//    private RadioButton radioTabsItemButton;
    // private LinearLayout bottom_toolbar;
//    AppCompatButton appButtonItemTabIdPelanggan;
//    AppCompatButton appButtonItemTabDaftarPelanggan;
    // FrameLayout frameIcon;
    ImageView imageViewPrefix;
    AVLoadingIndicatorView avi;
//    private int PICK_CONTACT = 5;
    ImageView imageViewAddressBook;
    LinearLayout linMain, layout_no_connection;
//    Context context;
    private View menuItemView;
//    private boolean isDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pasca_bayar);
//        Log.d(TAG, "onCreate: ");
        logEventFireBase(ProdukGroup.HPPASCA,ProdukGroup.HPPASCA,EventParam.EVENT_ACTION_VISIT,EventParam.EVENT_SUCCESS,TAG);
//        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pulsa pascabayar");
        init(0);

        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInq();
            }
        });
        //radioTabs = findViewById(R.id.radioGroupTabs);
        imageViewPrefix = findViewById(R.id.imageViewPrefix);
        avi = findViewById(R.id.avi);

        // frameIcon = findViewById(R.id.frameIcon);
        materialEditTextProduk = findViewById(R.id.materialEditTextProduk);
        materialEditTextProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PascaBayarActivity.this, ListGridProdukActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Produk Pascabayar");
                intent.putExtra("product", "TELP_PASCA");
                intent.putExtra("hint", "Pilih Provider");
                startActivityForResult(intent, ActionCode.LIST_PRODUK);
                //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
            }
        });
        materialEditTextIdPelanggan = findViewById(R.id.materialEditTextIdPelanggan);
        materialEditTextIdPelanggan.setOnCutCopyPasteListener(new OnCutCopyPasteListener() {


            @Override
            public void onCut() {
                // Do your onCut reactions
            }

            @Override
            public void onCopy() {
                // Do your onCopy reactions
            }

            @Override
            public void onPaste() {
                String selectedNumber =materialEditTextIdPelanggan.getEditableText().toString();
                // selectedNumber= selectedNumber.replaceAll("....", "$0 ");
                selectedNumber = selectedNumber.replace("-", "");
                //  phoneInput.setText(selectedNumber);
                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                if (selectedNumber.startsWith("62")) {
                    if (!selectedNumber.startsWith("9")) {
                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                    }
                }
                materialEditTextIdPelanggan.setText("");
//                materialEditTextNoHandphone.setCharacterDelay(10);
//                materialEditTextNoHandphone.animateText(selectedNumber);
//                materialEditTextNoHandphone.setMaskedText("**** **** **** ****");
                materialEditTextIdPelanggan.setText(selectedNumber);
                // materialEditTextNoHandphone.setSelection(selectedNumber.length());
                // requestPrefixRegular(selectedNumber);
                // Do your onPaste reactions
            }
        });

        materialEditTextIdPelanggan.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                //here is your code
                countText = s.length();
//
//                if (s.length() >= 4) {
//                    Log.d(TAG, "onTextChanged: " + s.length());
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
                if (materialEditTextIdPelanggan.getEditableText().length() == 0) {
                    checkboxSimpanId.setVisibility(View.VISIBLE);

                    checkboxSimpanId.setChecked(false);
                }
//                else {
//                    //if(s.length()==1){
//                    // if(textViewPlusSukses.getVisibility()==View.VISIBLE){
//                    //      textViewPlusSukses.setVisibility(View.GONE);
//                    //  }
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
                
//                Log.d(TAG, "afterTextChanged: ");
//                if (s.length() > 0) {
//
//                } else if (s.length() < 8) {
//
//                }

//                if (isDelete) {
//                    isDelete = false;
//                    return;
//                }

//                String val = s.toString();
//                String a = "";
//                String b = "";
//                String c = "";
//                String d = "";
//                String e = "";
//                if (val != null && val.length() > 0) {
//                    val = val.replace(" ", "");
//                    if (val.length() >= 4) {
//                        a = val.substring(0, 4);
//                    } else if (val.length() < 4) {
//                        a = val.substring(0, val.length());
//                    }
//                    if (val.length() >= 8) {
//                        b = val.substring(4, 8);
//                        c = val.substring(8, val.length());
//                    } else if (val.length() > 4 && val.length() < 8) {
//                        b = val.substring(4, val.length());
//                    }
//
//                    if (val.length() >= 12) {
//                        c = val.substring(8, 12);
//                        d = val.substring(12, val.length());
//                    } else if (val.length() > 8 && val.length() < 12) {
//                        c = val.substring(8, val.length());
//                    }
//
//                    if (val.length() >= 16) {
//                        d = val.substring(12, 16);
//                        e = val.substring(16, val.length());
//                    } else if (val.length() > 12 && val.length() < 16) {
//                        d = val.substring(12, val.length());
//                    }
//
//                    StringBuffer stringBuffer = new StringBuffer();
//                    if (a != null && a.length() > 0) {
//                        stringBuffer.append(a);
//                        if (a.length() == 4) {
//                            stringBuffer.append(" ");
//                        }
//                    }
//                    if (b != null && b.length() > 0) {
//                        stringBuffer.append(b);
//                        if (b.length() == 4) {
//                            stringBuffer.append(" ");
//                        }
//                    }
//                    if (c != null && c.length() > 0) {
//                        stringBuffer.append(c);
//                        if (c.length() == 4) {
//                            stringBuffer.append(" ");
//                        }
//                    }
//                    if (d != null && d.length() > 0) {
//                        stringBuffer.append(d);
//                        if (d.length() == 4) {
//                            stringBuffer.append(" ");
//                        }
//                    }
//                    if (e != null && e.length() > 0) {
//                        stringBuffer.append(e);
//                    }
//                    materialEditTextIdPelanggan.removeTextChangedListener(this);
//                    materialEditTextIdPelanggan.setText(stringBuffer.toString());
//                    materialEditTextIdPelanggan.setSelection(materialEditTextIdPelanggan.getText().toString().length());
//                    materialEditTextIdPelanggan.addTextChangedListener(this);
//                } else {
//                    materialEditTextIdPelanggan.removeTextChangedListener(this);
//                    materialEditTextIdPelanggan.setText("");
//                    materialEditTextIdPelanggan.addTextChangedListener(this);
//                }
            }
        });
        //  radioTabs.setOnCheckedChangeListener(this);


//        appButtonItemTabIdPelanggan = findViewById(R.id.appButtonItemTabIdPelanggan);
//        //appButtonItemTabIdPelanggan.setFocusable(true);
//        appButtonItemTabIdPelanggan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestLayout(0);
//            }
//        });
//        appButtonItemTabDaftarPelanggan = findViewById(R.id.appButtonItemTabDaftarPelanggan);
//        //  appButtonItemTabDaftarPelanggan.setFocusable(false);
//        appButtonItemTabDaftarPelanggan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestLayout(1);
//            }
//        });

        checkboxSimpanId = findViewById(R.id.checkboxSimpanId);
        checkboxSimpanId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (((CheckBox) view).isChecked()) {
                    Log.d(TAG, "onClick: true ");
                    isSave = "true";
                } else {
                    Log.d(TAG, "onClick: false");
                    isSave = "false";
                }
            }
        });
        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialEditTextProduk.getEditableText().toString().isEmpty()) {
                    materialEditTextProduk.setAnimation(animShake);
                    materialEditTextProduk.startAnimation(animShake);
                    materialEditTextProduk.setError("Produk Pulsa Pascabayar Tidak Boleh Kosong");
                    Device.vibrate(PascaBayarActivity.this);
                    return;
                }
                if (materialEditTextIdPelanggan.getEditableText().toString().isEmpty()) {
                    materialEditTextIdPelanggan.setAnimation(BaseActivity.animShake);
                    materialEditTextIdPelanggan.startAnimation(BaseActivity.animShake);
                    materialEditTextIdPelanggan.setError("ID Pelanggan Tidak Boleh Kosong");
                    Device.vibrate(PascaBayarActivity.this);
                    return;
                }

                requestInq();
            }
        });

        imageViewAddressBook = findViewById(R.id.imageViewAddressBook);
        imageViewAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//
//                startActivityForResult(intent, ActionCode.PICK_CONTACT);
                requestLayout(1);
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                requestPopUpPromo(ProdukGroup.HPPASCA);
                navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
                attachKeyboardListeners();
                materialEditTextProduk.performClick();
            }
        });


     //   materialEditTextProduk.performClick();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                menuItemView = findViewById(R.id.action_right_scanner);
                if (PreferenceClass.getInt(TAG, 0) != 1) {
                    showCaseFirst(PascaBayarActivity.this, "", getResources().getString(R.string.content_icon_scanner), menuItemView);

                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
                        @Override
                        public void onDismiss(@NonNull View view) {
                            switch (view.getId()) {

                                case R.id.action_right_scanner:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_icon_book)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(imageViewAddressBook)
                                            .build();
                                    break;
                                case R.id.imageViewAddressBook:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_cek_simpan)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(checkboxSimpanId)
                                            .build();
                                    break;

                                case R.id.checkboxSimpanId:
                                    PreferenceClass.putInt(TAG, 1);
                                    return;
                            }
                            mGuideView = mGbuilder.build();
                            mGuideView.show();

                        }
                    });
                    mGuideView = mGbuilder.build();
                    mGuideView.show();
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
        } else if (id == R.id.action_right_scanner) {

            openScanner(PascaBayarActivity.this);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (bottom_toolbar.getVisibility() == View.GONE) {
            closeKeyboard(this);
        }
        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }

    private void requestInq() {
        logEventFireBase(ProdukGroup.HPPASCA,materialEditTextProduk.getEditableText().toString(),EventParam.EVENT_ACTION_REQUEST_INQ,EventParam.EVENT_SUCCESS,TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestInquiryTelpPasca(produkCode, materialEditTextIdPelanggan.getEditableText().toString().replace(" ",""), isSave));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.INQ, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this,R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }

//            try {
//                if (response.getString("response_code").equals("03")) {
//                    new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//                } else {
                    InqModel inqModel = gson.fromJson(response.toString(), InqModel.class);
            switch (inqModel.getResponse_code()) {
                case ResponseCode.SUCCESS:
                    logEventFireBase(ProdukGroup.HPPASCA, materialEditTextProduk.getEditableText().toString(), EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_SUCCESS, TAG);
                    PreferenceClass.putString("saldo", FormatString.CurencyIDR(inqModel.getSaldo()));
                    // BaseActivity.getInstanceBaseActivity().navigationTopCustom(getActivity(),FormatString.CurencyIDR(inqModel.getSaldo()));
                    Intent intent = new Intent(PascaBayarActivity.this, InqueryResultActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("title", ProdukGroup.HPPASCA);
                    intent.putExtra("produkCode", produkCode);
                    intent.putExtra("produkGroup", ProdukGroup.HPPASCA);
                    intent.putExtra("subject", materialEditTextProduk.getEditableText().toString());
                    intent.putExtra("id_pel", materialEditTextIdPelanggan.getEditableText().toString().replace(" ", ""));
                    intent.putExtra("nominal", "0");

                    intent.putExtra("inq_result", inqModel);
                    intent.putExtra("struk", inqModel.getResponse_desc());
                    intent.putExtra("struk_show", inqModel.getStruk_show());
                    startActivityForResult(intent, 2);
                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                case "03":
                    new_popup_alert_session(this, "Informasi", inqModel.getResponse_desc());
                    break;
                default:
                    logEventFireBase(ProdukGroup.HPPASCA, materialEditTextProduk.getEditableText().toString(), EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_NOT_SUCCESS, TAG);
                    materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
                    break;
            }
//        String.format(getString(R.string.response_description),"sukses");
//                }
//            } catch (JSONException jsone) {
//                Log.d(TAG, "JSONException: " + jsone.toString());
//            }
        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.INQ) {
                linMain.setVisibility(View.GONE);
                layout_no_connection.setVisibility(View.VISIBLE);
            }
        }
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
        }

    }

    private void requestLayout(int selectedTab) {
        Log.d(TAG, "requestLayout: " + selectedTab);
        switch (selectedTab) {
            case 0:
                checkboxSimpanId.setVisibility(View.VISIBLE);
                checkboxSimpanId.setChecked(false);
                materialEditTextIdPelanggan.setText("");
                break;
            case 1:
                if (materialEditTextProduk.getEditableText().toString().isEmpty()) {
                    materialEditTextProduk.setAnimation(animShake);
                    materialEditTextProduk.startAnimation(animShake);
                    materialEditTextProduk.setError("Produk Pascabayar Tidak Boleh Kosong");
                    Device.vibrate(PascaBayarActivity.this);
                    requestLayout(0);
                } else {
                    Intent intent = new Intent(this, DaftarPelangganActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("title", "Pelanggan Pulsa Pascabayar");
                    intent.putExtra("product", produkCode);
                    startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 0) {
//                produkCode = data.getStringExtra("kodeProduk");
//                materialEditTextProduk.setText(data.getStringExtra("namaProduk"));
//            //    frameIcon.setVisibility(View.VISIBLE);
//                Picasso.with(this)
//                        .load(data.getStringExtra("urlProduk")).fit().into(imageViewPrefix, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        imageViewPrefix.setVisibility(View.VISIBLE);
//                        imageViewPrefix.getDrawingCache(true);
//                        avi.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError() {
//                        avi.setVisibility(View.GONE);
//                        imageViewPrefix.setVisibility(View.GONE);
//                    }
//
//                });
//
//
//            } else if (requestCode == 1) {
//                String idPel = data.getStringExtra("id_pel");
//                materialEditTextIdPelanggan.setText(idPel);
//            } else if (requestCode == 2) {
//
//                navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
////                radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
////                radioTabsItemButton.setChecked(true);
//                requestLayout(0);
//                materialEditTextProduk.setText("");
//            } else if (requestCode == PICK_CONTACT) {
//
//
//                Uri contactData = data.getData();
//                Cursor cur = managedQuery(contactData, null, null, null, null);
//                ContentResolver contect_resolver = getContentResolver();
//
//                if (cur.moveToFirst()) {
//                    String id = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//                    String airLineName = "";
//                    String no = "";
//
//                    @SuppressLint("Recycle") Cursor phoneCur = contect_resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
//
//                    if (phoneCur != null && phoneCur.moveToFirst()) {
//                        airLineName = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                        no = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    }
//
//                    Log.d(TAG, airLineName + " : " + no);
//                    materialEditTextIdPelanggan.setText(no.replaceAll("[^0-9]", "").replaceFirst("62", "0"));
//                    // txt.append(airLineName + " : " + no + "\n");
//
//                    id = null;
//                    airLineName = null;
//                    no = null;
//                    phoneCur = null;
//                }
//                contect_resolver = null;
//                cur = null;
//
//            }
//        }
//        else if(resultCode==Activity.RESULT_CANCELED){
//            if(requestCode==1) {
//
//                radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//                radioTabsItemButton.setChecked(true);
//                requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
//                // materialEditTextProduk.setText("");
//
//            }
//        }


//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case ActionCode.PICK_CONTACT:
//                    // final EditText phoneInput = (EditText) findViewById(R.id.phoneNumberInput);
//                    Cursor cursor = null;
//                    String phoneNumber = "";
//                    final List<String> allNumbers = new ArrayList<>();
//                    int phoneIdx = 0;
//                    try {
//                        Uri result = data.getData();
//                        String id = result.getLastPathSegment();
//                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id }, null);
//                        phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                        if (cursor.moveToFirst()) {
//                            while (cursor.isAfterLast() == false) {
//                                phoneNumber = cursor.getString(phoneIdx);
//                                allNumbers.add(phoneNumber);
//                                cursor.moveToNext();
//                            }
//                        } else {
//                            //no results actions
//                        }
//                    } catch (Exception e) {
//                        //error actions
//                    } finally {
//                        if (cursor != null) {
//                            cursor.close();
//                        }
//                        HashSet hs = new HashSet();
//                        hs.addAll(allNumbers);
//                        allNumbers.clear();
//                        allNumbers.addAll(hs);
////                        final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
//                        final String[] items = allNumbers.toArray(new String[allNumbers.size()]);
//                        final AlertDialog.Builder client = new AlertDialog.Builder(PascaBayarActivity.this);
//
//                        LayoutInflater inflater = this.getLayoutInflater();
//                        View dialogView = inflater.inflate(R.layout.dialog_custom_contact, null);
//
//                        client.setView(dialogView);
//                        TextView textViewTitleDialog=dialogView.findViewById(R.id.textViewTitleDialog);
//                        textViewTitleDialog.setText("Pilih Nomor Handphone Tujuan");
//                        //  TextView textViewItemDialog=dialogView.findViewById(R.id.textViewItemDialog);
//
//                        //Prepare ListView in dialog
//                        ListView listViewPhoneNumber = (ListView)dialogView.findViewById(R.id.listViewPhoneNumber);
//                        final AlertDialog alert = client.create();
//
//                        ArrayAdapter<String> adapter
//                                = new ArrayAdapter<String>(this,
//                                R.layout.layout_item_phonenumber,R.id.textViewItemPhone, items);
//                        listViewPhoneNumber .setAdapter(adapter);
//                        listViewPhoneNumber .setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view,
//                                                    int position, long id) {
//                               
////                                Toast.makeText(AndroidCustomDialogActivity.this,
////                                        parent.getItemAtPosition(position).toString() + " clicked",
////                                        Toast.LENGTH_LONG).show();
//                                //   dismissDialog(CUSTOM_DIALOG_ID);
//                                String selectedNumber = items[position].toString();
//                                selectedNumber = selectedNumber.replace("-", "");
//                                //  phoneInput.setText(selectedNumber);
//                                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
//                                if(selectedNumber.startsWith("62")) {
//                                    if(!selectedNumber.startsWith("9")) {
//                                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
//                                    }
//                                }
//                                materialEditTextIdPelanggan.setText("");
//                                materialEditTextIdPelanggan.setCharacterDelay(50);
//                                materialEditTextIdPelanggan.animateText(selectedNumber);
//                                alert.dismiss();
//
//                            }});
//
//                        if(allNumbers.size() > 1) {
//                            alert.show();
////                            alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            alert.getWindow().setBackgroundDrawableResource(R.drawable.alert_dialog_edge);
//                        } else {
//                            String selectedNumber = phoneNumber.toString();
//                            selectedNumber = selectedNumber.replace("-", "");
////                            phoneInput.setText(selectedNumber);
//                            selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
//                            if(selectedNumber.startsWith("62")) {
//                                if (!selectedNumber.startsWith("9")) {
//                                    selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
//                                }
//                            }
//                            materialEditTextIdPelanggan.setText("");
//                            materialEditTextIdPelanggan.setCharacterDelay(50);
//                            materialEditTextIdPelanggan.animateText(selectedNumber);
//                        }
//
//                        if (phoneNumber.length() == 0) {
//                            //no numbers found actions
//                        }
//                    }
//                    break;
//            }
//        } else {
//            //activity result error actions
//        }

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PRODUK) {
                produkCode = data.getStringExtra("kodeProduk");
                materialEditTextProduk.setText(data.getStringExtra("namaProduk"));
                //    frameIcon.setVisibility(View.VISIBLE);
//                Picasso.with(this)
//                        .load(data.getStringExtra("urlProduk")).fit().into(imageViewPrefix, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        imageViewPrefix.setVisibility(View.VISIBLE);
//                        imageViewPrefix.getDrawingCache(true);
//                        avi.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError() {
//                        avi.setVisibility(View.GONE);
//                        imageViewPrefix.setVisibility(View.GONE);
//                    }
//
//                });
                Glide.with(this).load(data.getStringExtra("urlProduk")).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new DrawableImageViewTarget(imageViewPrefix) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> animation) {
                        // here it's similar to RequestListener, but with less information (e.g. no model available)
                        super.onResourceReady(resource, animation);
                        imageViewPrefix.setVisibility(View.VISIBLE);

                        avi.setVisibility(View.GONE);
                        // here you can be sure it's already set
                    }

                    // +++++ OR +++++
                    @Override
                    protected void setResource(Drawable resource) {
                        // this.getView().setImageDrawable(resource); is about to be called
                        super.setResource(resource);
                        imageViewPrefix.setVisibility(View.VISIBLE);
//
                        avi.setVisibility(View.GONE);
                        // here you can be sure it's already set
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                        avi.setVisibility(View.GONE);
                        imageViewPrefix.setVisibility(View.VISIBLE);
                    }
                });


            } else if (requestCode == ActionCode.LIST_PELANGGAN) {
                checkboxSimpanId.setVisibility(View.GONE);

                checkboxSimpanId.setChecked(false);
                String idPel = data.getStringExtra("id_pel");
                materialEditTextIdPelanggan.setText(idPel);
            } else if (requestCode == 2) {

                navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
//                radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//                radioTabsItemButton.setChecked(true);
                requestLayout(0);
                materialEditTextProduk.setText("");
            } else if (requestCode == ActionCode.BARCODE) {

                String idPel = data.getStringExtra(Intents.Scan.RESULT);

                materialEditTextIdPelanggan.setText(idPel);

            }
        } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
//            if (requestCode == ActionCode.LIST_PRODUK) {
////
////                radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
////                radioTabsItemButton.setChecked(true);
////                requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
////                 materialEditTextProduk.setText("");
////
//            } else
                if (requestCode == ActionCode.LIST_PELANGGAN) {
                checkboxSimpanId.setVisibility(View.VISIBLE);

                checkboxSimpanId.setChecked(false);
            }
        }
    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        int selectedId = group.getCheckedRadioButtonId();
//        Log.d(TAG, "onCheckedChanged: " + selectedId + "  " + checkedId);
//        radioTabsItemButton = findViewById(selectedId);
//        requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
//    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown
        Log.d(TAG, "onShowKeyboard: " + keyboardHeight);
//        if(bottom_toolbar.getVisibility()==View.VISIBLE) {
        bottom_toolbar.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onHideKeyboard() {
        Log.d(TAG, "onHideKeyboard: ");
        // do things when keyboard is hidden
        bottom_toolbar.setVisibility(View.VISIBLE);
    }

//    @Override
//    protected void onDestroy() {
//        Glide.get(this).clearMemory();
//        super.onDestroy();
//    }

    protected void onResume() {
        Log.d(TAG, "onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
    }
}
