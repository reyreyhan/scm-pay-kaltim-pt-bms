package com.bm.main.fpl.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
//import androidx.appcompat.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.bm.main.fpl.adapters.ListGridTopupAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.EmoneyModel;
import com.bm.main.fpl.models.PayModel;
import com.bm.main.fpl.models.ProdukPulsaModel;
import com.bm.main.fpl.templates.CardNumberTextWatcher;
import com.bm.main.fpl.templates.TextViewPlus;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.MyClipboardManager;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.materialedittext.OnCutCopyPasteListener;
import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class TopUpEmoneyActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback, ProgressResponseCallback, ListGridTopupAdapter.OnClickProduk, ListGridProdukPulsaAdapter.OnClickProduk, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String TAG = TopUpEmoneyActivity.class.getSimpleName();
    private static final int PICK_CONTACT = 1;
    RecyclerView recyclerViewProduk;
    private static final String TAGETOLL = "ETOLL";
    private static final String TAGSALDO = "SALDO";
    @NonNull
    ArrayList<EmoneyModel.Response_value> data = new ArrayList<>();
    EmoneyModel emoneyModel;

    SearchView searchView;
    SearchManager searchManager;
    ListGridTopupAdapter adapter;
    //RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManagerProduk;
    @Nullable
    String kodeProduk, namaProduk, urlProduk, is_flag;
    boolean isBayar = false;

    LinearLayout linList;
    LinearLayout relPay;

    //    ImageView imageViewProduk;
//    TextView textViewPlusNamaProduk;
    RecyclerView recyclerViewNominalProduk;
    @NonNull
    ArrayList<ProdukPulsaModel.Response_value> dataProduk = new ArrayList<>();
    ProdukPulsaModel produkPulsaModel;
    ListGridProdukPulsaAdapter adapterProduk;
    MaterialEditText materialEditTextNoHandphone;
    //    AVLoadingIndicatorView avi;
    ImageView imageViewAddressBook;
    @Nullable
    String kodeProdukPilihan = "";
    //    AppCompatButton appCompatButtonBayar;
    TextView textViewPlusSukses;
    @Nullable
    String namaProdukPilihan;
    //  private LinearLayout bottom_toolbar;

    LinearLayout linMain, layout_no_connection;
    private AppCompatButton button_coba_lagi;
    int reqTag = 0;
    private String nominal;
    @Nullable
    private String produkName;

    LinearLayout lin_no_hp, lin_no_kartu;
    MaterialEditText materialEditTextNoKartu;
    private String idPelanggan;
    @NonNull
    private String product = "Emoney";
    ShimmerFrameLayout shimmer_view_container;
//    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_emoney);
        //   getWindow().setBackgroundDrawable(null);
        logEventFireBase(ProdukGroup.EMONEY, ProdukGroup.EMONEY, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Topup Emoney");
        init(0);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        shimmer_view_container = findViewById(R.id.shimmer_view_container);
//        nestedScrollViewProduk = findViewById(R.id.nestedScrollViewProduk);
//        nestedScrollView = findViewById(R.id.nestedScrollView);
        linMain = findViewById(R.id.linMain);
        lin_no_hp = findViewById(R.id.lin_no_hp);
        lin_no_kartu = findViewById(R.id.lin_no_kartu);
        materialEditTextNoKartu = findViewById(R.id.materialEditTextNoKartu);
        materialEditTextNoKartu.addTextChangedListener(new CardNumberTextWatcher(materialEditTextNoKartu));

        layout_no_connection = findViewById(R.id.layout_no_connection);
        button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reqTag == 0) {
                    requestDaftarProduk();
                } else if (reqTag == 1) {
                    requestNominalProduk();

                } else {
                    requestPayment();
                }
            }
        });

        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));

        linList = findViewById(R.id.linList);
        relPay = findViewById(R.id.relPay);
        //  imageViewProduk = findViewById(R.id.imageViewProduk);
        //   textViewPlusNamaProduk = findViewById(R.id.textViewPlusNamaProduk);

        textViewPlusSukses = findViewById(R.id.textViewPlusSukses);
//        appCompatButtonBayar = findViewById(R.id.appCompatButtonBayar);
        imageViewAddressBook = findViewById(R.id.imageViewAddressBook);
        materialEditTextNoHandphone = findViewById(R.id.materialEditTextNoHandphone);
        materialEditTextNoHandphone.setOnCutCopyPasteListener(new OnCutCopyPasteListener() {


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
                String selectedNumber = materialEditTextNoHandphone.getEditableText().toString();
                // selectedNumber= selectedNumber.replaceAll("....", "$0 ");
                selectedNumber = selectedNumber.replace("-", "");
                //  phoneInput.setText(selectedNumber);
                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                if (selectedNumber.startsWith("62")) {
                    if (!selectedNumber.startsWith("9")) {
                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                    }
                }
                materialEditTextNoHandphone.setText("");
//                materialEditTextNoHandphone.setCharacterDelay(10);
//                materialEditTextNoHandphone.animateText(selectedNumber);
//                materialEditTextNoHandphone.setMaskedText("**** **** **** ****");
                materialEditTextNoHandphone.setText(selectedNumber);
                // materialEditTextNoHandphone.setSelection(selectedNumber.length());
                // requestPrefixRegular(selectedNumber);
                // Do your onPaste reactions
            }
        });
//        materialEditTextNoHandphone.addTextChangedListener(new PhoneNumberTextWatcher(materialEditTextNoHandphone));
//        materialEditTextNoHandphone = findViewById(R.id.materialEditTextNoHandphone);

        recyclerViewNominalProduk = findViewById(R.id.recyclerViewNominalProduk);
        //  avi = findViewById(R.id.avi);

        imageViewAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//
//                startActivityForResult(intent, ActionCode.PICK_CONTACT);
                showContacts();
            }
        });
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(false);
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTypeface(tfLight);
        searchAutoComplete.setTextSize(14);
        recyclerViewProduk = findViewById(R.id.recyclerViewProduk);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Cari Jenis Topup Emoney");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewProduk.setHasFixedSize(false);
//        recyclerViewProduk.setNestedScrollingEnabled(false);
        //  ViewCompat.setNestedScrollingEnabled(recyclerViewProduk, false);
        //recyclerViewPropinsi.setItemViewCacheSize(1024);
        recyclerViewProduk.setLayoutManager(mLayoutManager);


        //recyclerViewPropinsi.setLayoutManager(linearLayoutManager);
//        recyclerViewPropinsi.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // mLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);

        adapter = new ListGridTopupAdapter(data, this, this);

        recyclerViewProduk.setAdapter(adapter);


        if (PreferenceClass.getJSONObject(product).length() > 0) {
            Log.d(TAG, "onCreate: masuk sini");
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }

            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }
            if (shimmer_view_container.getVisibility() == View.GONE) {
                shimmer_view_container.setVisibility(View.VISIBLE);
                shimmer_view_container.startShimmerAnimation();
            }
            emoneyModel = gson.fromJson(PreferenceClass.getJSONObject(product).toString(), EmoneyModel.class);
            if (emoneyModel.getResponse_value().size() > 0) {
                data.clear();
                data.addAll(emoneyModel.getResponse_value());
                //  setData(emoneyModel.getData());
                adapter.notifyDataSetChanged();
                //  recyclerViewPropinsi.setVisibility(View.VISIBLE);
                if (shimmer_view_container.getVisibility() == View.VISIBLE) {
                    shimmer_view_container.setVisibility(View.GONE);
                    shimmer_view_container.stopShimmerAnimation();
                }
            }
        }

        requestDaftarProduk();


        mLayoutManagerProduk = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewNominalProduk.setHasFixedSize(false);
//        recyclerViewNominalProduk.setNestedScrollingEnabled(false);
        //  ViewCompat.setNestedScrollingEnabled(recyclerViewNominalProduk, false);
        recyclerViewNominalProduk.setLayoutManager(mLayoutManagerProduk);

        adapterProduk = new ListGridProdukPulsaAdapter(dataProduk, this, this);
        recyclerViewNominalProduk.setAdapter(adapterProduk);

        requestPopUpPromo("TOPUP_EMONEY");
        attachKeyboardListeners();

    }

    private void Topup() {
        if (kodeProdukPilihan.isEmpty()) {
            recyclerViewNominalProduk.setAnimation(BaseActivity.animShake);
            recyclerViewNominalProduk.startAnimation(BaseActivity.animShake);

            //  snackBarCustomAction(findViewById(R.id.bottom_toolbar), R.string.warning_nominal_produk, "", R.string.tutup, WARNING);
            //   String msg=getResources().getString(R.string.warning_nominal_produk);
            MyDynamicToast.warningMessage(TopUpEmoneyActivity.this, getResources().getString(R.string.warning_nominal_produk));
            Device.vibrate(TopUpEmoneyActivity.this);
            return;
        }
        if (is_flag.equals("ID")) {
            if (materialEditTextNoKartu.getText().toString().isEmpty()) {
                materialEditTextNoKartu.setAnimation(BaseActivity.animShake);
                materialEditTextNoKartu.startAnimation(BaseActivity.animShake);
                materialEditTextNoKartu.setError("No Kartu Tidak Boleh Kosong");
                Device.vibrate(TopUpEmoneyActivity.this);
                return;
            } else {
                idPelanggan = materialEditTextNoKartu.getText().toString().replace("-", "").replace(" ", "");
            }
        } else {
            if (materialEditTextNoHandphone.getText().toString().isEmpty()) {
                materialEditTextNoHandphone.setAnimation(BaseActivity.animShake);
                materialEditTextNoHandphone.startAnimation(BaseActivity.animShake);
                materialEditTextNoHandphone.setError("No Handphone Tidak Boleh Kosong");
                Device.vibrate(TopUpEmoneyActivity.this);
                return;
            } else {
                idPelanggan = materialEditTextNoHandphone.getText().toString().replace("-", "").replace(" ", "");
            }
        }
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(TopUpEmoneyActivity.this, R.layout.dialog_header_layout, parent);
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(TopUpEmoneyActivity.this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(TopUpEmoneyActivity.this, R.color.black_overlay))
                .setTitle("Pembelian Topup")
                .setMessage("Apakah Anda yakin akan membeli Topup " + produkName + " ?")
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Topup Sekarang", -1, ContextCompat.getColor(TopUpEmoneyActivity.this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), (dialog, which) -> {
            // Toast.makeText(BaseActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            requestPayment();

        });
        builder.onDismissListener(dialog -> adapterProduk.clearSelections());

        builder.show();
    }

    @Override
    public void onPause() {
        shimmer_view_container.stopShimmerAnimation();
        super.onPause();
    }

    private void requestPayment() {
        logEventFireBase(ProdukGroup.EMONEY, produkName, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestPaymentGame(kodeProdukPilihan, idPelanggan));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponsePayment(this, jsonObject, ActionCode.PAY_PULSA, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }


    private void requestDaftarProduk() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestProviderTopup());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PRODUK_GAME, this);
    }

    private void requestNominalProduk() {
        logEventFireBase(ProdukGroup.EMONEY, namaProduk, EventParam.EVENT_ACTION_CHOICE, EventParam.EVENT_SUCCESS, TAG);
        reqTag = 1;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestProductGame(kodeProduk));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_NOMINAL_PRODUK, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
//        TextView text = view.findViewById(R.id.textContentProgressBar);
//        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
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
        if (bottom_toolbar.getVisibility() == View.GONE) {
            closeKeyboard(this);
        }


        if (!isBayar) {

            finish();
        } else {
            reqTag = 0;
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            isBayar = false;
            toolbar.setTitle("Voucher Topup Emoney");
            linList.setVisibility(View.VISIBLE);
            relPay.setVisibility(View.GONE);
        }

        //  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }


    @Override
    public boolean onClose() {
        adapter.getFilter().filter("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        closeKeyboard(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

//    @Override
//    public void onClick(View view) {
//
//    }

    @Override
    public void onSuccess(int actionCode, @NonNull final JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        if (layout_no_connection.getVisibility() == View.VISIBLE) {
            layout_no_connection.setVisibility(View.GONE);
        }
        if (linMain.getVisibility() == View.GONE) {
            linMain.setVisibility(View.VISIBLE);
        }
        if (shimmer_view_container.getVisibility() == View.VISIBLE) {
            shimmer_view_container.setVisibility(View.GONE);
            shimmer_view_container.stopShimmerAnimation();
        }

//        try {
//            if (response.getString("response_code").equals("03")) {
//                if (actionCode == ActionCode.LIST_NOMINAL_PRODUK || actionCode == ActionCode.PAY_PULSA) {
//                    closeProgressBarDialog();
//                }
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {

        if (actionCode == ActionCode.LIST_PRODUK_GAME) {
            emoneyModel = gson.fromJson(response.toString(), EmoneyModel.class);
            if (emoneyModel.getResponse_code().equals(ResponseCode.SUCCESS)) {

                JSONObject obj = PreferenceClass.getJSONObject(product);
                JSONArray array = new JSONArray();
                try {
                    array = obj.getJSONArray("response_value");
                } catch (JSONException e) {
                    //  e.printStackTrace();
                }

//                        if (PreferenceClass.getJSONObject(product).length() != response.length()) {
                if (array.length() != emoneyModel.getResponse_value().size()) {
                    PreferenceClass.putJSONObject(product, response);
                    data.clear();
                    data.addAll(emoneyModel.getResponse_value());
                    adapter.notifyDataSetChanged();
                }
                recyclerViewProduk.setVisibility(View.VISIBLE);
//                        nestedScrollViewProduk.setVisibility(View.VISIBLE);
//                data.addAll(emoneyModel.getData());
                //  setData(emoneyModel.getData());
//                adapter.notifyDataSetChanged();
//                    }else if (emoneyModel.getResponse_code().equals("03")) {
//                        new_popup_alert_session(this, "Informasi", emoneyModel.getResponse_desc());
            } else {
//non success
            }
        }
        // reqTag=1;
        if (actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
            closeProgressBarDialog();

            dataProduk.clear();
            kodeProdukPilihan = "";
            namaProdukPilihan = "";
            produkPulsaModel = gson.fromJson(response.toString(), ProdukPulsaModel.class);
            if (produkPulsaModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                dataProduk.addAll(produkPulsaModel.getResponse_value());
                //  setData(emoneyModel.getData());
                adapterProduk.clearSelections();
                //adapterProduk.notifyDataSetChanged();
                //adapterProduk.clearSelections();
                toolbar.setTitle("Nominal Voucher Topup Emoney");
                isBayar = true;
                reqTag = 2;
                linList.setVisibility(View.GONE);
                relPay.setVisibility(View.VISIBLE);
                if (textViewPlusSukses.getVisibility() == View.VISIBLE) {
                    textViewPlusSukses.setVisibility(View.GONE);
                }

                //   textViewPlusNamaProduk.setText(namaProduk);
//                    Picasso.with(this)
//                            .load(urlProduk).into(imageViewProduk, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            avi.setVisibility(View.GONE);
//                      //      imageViewProduk.getDrawingCache(true);
//                        }
//
//                        @Override
//                        public void onError() {
//                            avi.setVisibility(View.GONE);
////
//                            imageViewProduk.setImageDrawable(ContextCompat.getDrawable(TopUpEmoneyActivity.this, R.drawable.ic_menu_topup_emoney));
//
//                        }
//
//                    });
//                        Glide.with(this).load(urlProduk).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG, 50)).diskCacheStrategy(DiskCacheStrategy.RESULT).into(new BitmapImageViewTarget(imageViewProduk) {
//                            @Override
//                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> animation) {
//                                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                                super.onResourceReady(resource, animation);
//
//                                avi.setVisibility(View.GONE);
//                                // here you can be sure it's already set
//                            }
//
//                            // +++++ OR +++++
//                            @Override
//                            protected void setResource(Bitmap resource) {
//                                // this.getView().setImageDrawable(resource); is about to be called
//                                super.setResource(resource);
//
//                                avi.setVisibility(View.GONE);
//                                // here you can be sure it's already set
//                            }
//
//                            @Override
//                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                                super.onLoadFailed(e, errorDrawable);
//                                avi.setVisibility(View.GONE);
////
//                                imageViewProduk.setImageDrawable(ContextCompat.getDrawable(TopUpEmoneyActivity.this, R.drawable.ic_menu_topup_emoney));
//
//                            }
//                        });
                if (is_flag.equals("ID")) {
                    showCaseEtoll();
                } else {
                    showCaseSaldo();
                }
            } else if (produkPulsaModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", produkPulsaModel.getResponse_desc());
            } else {
                //NON SUCCESS
            }
        }
        if (actionCode == ActionCode.PAY_PULSA) {
            closeProgressBarDialog();
            PayModel payModel = gson.fromJson(response.toString(), PayModel.class);
            if (payModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                logEventFireBase(ProdukGroup.EMONEY, produkName, EventParam.EVENT_ACTION_RESULT_PAY, EventParam.EVENT_SUCCESS, TAG);
                reqTag = 0;
                // try {
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(payModel.getSaldo().toString()));
                navigationBottomCustom(bottom_toolbar, FormatString.CurencyIDR(payModel.getSaldo().toString()));


                onBackPressed();
                if (is_flag.equals("ID")) {
                    materialEditTextNoKartu.setText("");

                } else {
                    materialEditTextNoHandphone.setText("");

                }
                textViewPlusSukses.setVisibility(View.VISIBLE);
                textViewPlusSukses.startAnimation(slideUp);

                textViewPlusSukses.setText("Pembelian Topup Emoney " + namaProdukPilihan + " Berhasil!");
                //   snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, "Pembelian voucher " + namaProdukPilihan + " Berhasil!", R.string.tutup, INFO);
                //  MyDynamicToast.informationMessage(TopUpEmoneyActivity.this, R.string.sukses_terbayar, "Pembelian voucher " + namaProdukPilihan + " Berhasil!");


                new_popup_alert(TopUpEmoneyActivity.this, getString(R.string.sukses_terbayar), "Pembelian voucher " + namaProdukPilihan + " Berhasil!");

                //  } else {
                // textViewPlusSukses.setText("Pembelian voucher "+namaProdukPilihan+" Gagal!");
                //       snackBarCustomAction(findViewById(R.id.bottom_toolbar), Integer.parseInt(String.format(getString(R.string.alert_gagal_beli), "voucher " + namaProdukPilihan)), R.string.tutup, ALERT);
                // closeProgressBarDialog();

                // } catch (JSONException e) {
                //    e.printStackTrace();
                // }
            } else if (payModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", payModel.getResponse_desc());
            } else if (payModel.getResponse_code().equals("06")) {
                new_popup_alert_session_deposit(this, "Informasi", payModel.getResponse_desc());

            } else {
                logEventFireBase(ProdukGroup.EMONEY, produkName, EventParam.EVENT_ACTION_RESULT_PAY, EventParam.EVENT_NOT_SUCCESS, TAG);
                if (is_flag.equals("ID")) {
                    materialEditTextNoKartu.setError(payModel.getResponse_desc());
                } else {
                    materialEditTextNoHandphone.setError(payModel.getResponse_desc());
                }

                //   snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, response.get("response_desc").toString(), R.string.tutup, ALERT);
                //   MyDynamicToast.errorMessage(TopUpEmoneyActivity.this, payModel.getResponse_desc());
                new_popup_alert(TopUpEmoneyActivity.this, "Info", payModel.getResponse_desc());

            }

        }

//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }


    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String
            responseDescription, Throwable throwable) {
//        if (actionCode == ActionCode.PAY_PULSA) {
//            MyDynamicToast.errorMessage(GameActivity.this, responseDescription);
//            closeProgressBarDialog();
//        }else {
//        if (responseCode.equals(NETWORK_ERROR)) {
//            if (actionCode == ActionCode.PAY_PULSA || actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
//
//                //   MyDynamicToast.errorMessage(TopUpEmoneyActivity.this, "Cek Koneksi Internet Anda Atau Coba Kembali");
//                new_popup_alert_failure(TopUpEmoneyActivity.this, responseDescription);
//            } else
//
//                if (actionCode == ActionCode.LIST_PRODUK_GAME) {
//                    if (PreferenceClass.getJSONObject(product).length() > 0) {
//
//                    } else {
//                        shimmer_view_container.setVisibility(View.GONE);
//                        shimmer_view_container.stopShimmerAnimation();
//                        linMain.setVisibility(View.GONE);
//                        //  layout_data_empty.setVisibility(View.GONE);
//                        layout_no_connection.setVisibility(View.VISIBLE);
//                    }
//                }
//
////                if (PreferenceClass.getJSONObject(product).length() == 0) {
////                    linMain.setVisibility(View.GONE);
////                    layout_no_connection.setVisibility(View.VISIBLE);
////                }
////            }
//            if (actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
//                new_popup_alert_failure(TopUpEmoneyActivity.this, responseDescription);
//            }
//
//        } else {
//            if (actionCode == ActionCode.PAY_PULSA || actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
//                closeProgressBarDialog();
//            }
//            // MyDynamicToast.errorMessage(TopUpEmoneyActivity.this, responseDescription);
//            new_popup_alert_failure(TopUpEmoneyActivity.this, responseDescription);
//        }

        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.PAY_PULSA || actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
                closeProgressBarDialog();
                new_popup_alert_failure(TopUpEmoneyActivity.this, responseDescription);
                //   MyDynamicToast.errorMessage(GameActivity.this, "Cek Koneksi Internet Anda Atau Coba Kembali");


            } else if (actionCode == ActionCode.LIST_PRODUK_GAME) {
//                if (PreferenceClass.getJSONObject(product).length() > 0) {
//                    linMain.setVisibility(View.VISIBLE);
//                    recyclerViewProduk.setVisibility(View.VISIBLE);
//                    layout_no_connection.setVisibility(View.GONE);
//                } else {
                shimmer_view_container.setVisibility(View.GONE);
                shimmer_view_container.stopShimmerAnimation();
                linMain.setVisibility(View.GONE);
                //  layout_data_empty.setVisibility(View.GONE);
                layout_no_connection.setVisibility(View.VISIBLE);
//                }
            }
//                if (PreferenceClass.getJSONObject(product).length() == 0) {
//                    linMain.setVisibility(View.GONE);
//                    layout_no_connection.setVisibility(View.VISIBLE);
//                }
//            }
//            if (actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
//                new_popup_alert_failure(TopUpEmoneyActivity.this, responseDescription);
//            }

        } else {
            if (actionCode == ActionCode.PAY_PULSA || actionCode == ActionCode.LIST_NOMINAL_PRODUK) {
                closeProgressBarDialog();
            }
            //MyDynamicToast.errorMessage(GameActivity.this, responseDescription);
            new_popup_alert_failure(TopUpEmoneyActivity.this, responseDescription);
        }


    }

    @Override
    public void onClickProduk(@NonNull EmoneyModel.Response_value produk) {
        kodeProduk = produk.getGame_code();
        namaProduk = produk.getGame_name();
        urlProduk = produk.getGame_url();
        is_flag = produk.getIs_flag();
        if (is_flag.equals("ID")) {
            lin_no_kartu.setVisibility(View.VISIBLE);
            lin_no_hp.setVisibility(View.GONE);
            if (materialEditTextNoKartu.getText().length() > 0) {
                materialEditTextNoKartu.setText("");
            }
        } else {
            lin_no_kartu.setVisibility(View.GONE);
            lin_no_hp.setVisibility(View.VISIBLE);
            if (materialEditTextNoHandphone.getText().length() > 0) {
                materialEditTextNoHandphone.setText("");
            }
        }

//        if (is_flag.equals("ID")) {
//            lin_no_kartu.setVisibility(View.VISIBLE);
//            lin_no_hp.setVisibility(View.GONE);
//            // materialEditTextNoHandphone.setHint(R.string.hint_no_kartu_etoll);
//            //  materialEditTextNoHandphone.setFloatingLabelText(materialEditTextNoHandphone.getHint());
//            //  materialEditTextNoHandphone.setMaxCharacters(18);
//            //  imageViewAddressBook.setVisibility(View.GONE);
//        } else {
//            lin_no_kartu.setVisibility(View.GONE);
//            lin_no_hp.setVisibility(View.VISIBLE);
//            // materialEditTextNoHandphone.setHint(R.string.hint_no_hp_pembeli);
//            // materialEditTextNoHandphone.setFloatingLabelText(materialEditTextNoHandphone.getHint());
//            // materialEditTextNoHandphone.setMaxCharacters(13);
//            //  imageViewAddressBook.setVisibility(View.VISIBLE);
//        }
        requestNominalProduk();

    }

    @Override
    public void onClickProduk(@NonNull ProdukPulsaModel.Response_value produk, int adapterPos) {
        Log.d(TAG, "onClickProduk: " + produk.getProduct_code() + " " + produk.getProduct_name() + " " + produk.getProduct_nominal() + " " + produk.getProduct_nominal().replace("Rp", "").replace(".", ""));
        produkName = produk.getProduct_name();

        nominal = produk.getProduct_nominal().replace("Rp ", "").replace(".", "").trim();
        adapterProduk.clearSelections();
        adapterProduk.toggleSelection(adapterPos);
        kodeProdukPilihan = produk.getProduct_code();
        namaProdukPilihan = produk.getProduct_name();
        //  int ownSaldo = Integer.parseInt(PreferenceClass.getString("saldo", "0").replace(".", ""));
        int nominalFrom = Integer.parseInt(nominal.replace(".", ""));
        // int nominal_adminFrom=Integer.parseInt(nominal_admin.replace(".",""));
        //  int nominalAll = nominalFrom;

//        if(nominalAll>ownSaldo){
////            startBlinkingAnimation(true);
//            new_popup_alert_session_deposit(this, "Informasi", "Saldo Anda Tidak Mencukupi.");
//         //   return;
//        }else{
//            stopBlinkingAnimation();
        Topup();
//        }
//        appCompatButtonBayar.performClick();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            switch (reqCode) {
//                case (PICK_CONTACT):
//                    if (resultCode == Activity.RESULT_OK) {
//                        Uri contactData = data.getData();
//                        Cursor cur = managedQuery(contactData, null, null, null, null);
//                        ContentResolver contect_resolver = getContentResolver();
//
//                        if (cur.moveToFirst()) {
//                            String id = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//                            String airLineName = "";
//                            String no = "";
//
//                            Cursor phoneCur = contect_resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
//
//                            if (phoneCur.moveToFirst()) {
//                                airLineName = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                                no = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            }
//
//                            Log.d(TAG, airLineName + " : " + no);
//                            materialEditTextNoHandphone.setText("");
//                            materialEditTextNoHandphone.setCharacterDelay(50);
//                            materialEditTextNoHandphone.animateText(no.replaceAll("[^0-9]", "").replaceFirst("62", "0"));
//
//                            // txt.append(airLineName + " : " + no + "\n");
//
//                            id = null;
//                            airLineName = null;
//                            no = null;
//                            phoneCur = null;
//                        }
//                        contect_resolver = null;
//                        cur = null;
//                        //                      populateContacts();
//                    }
//            }
//        } catch (IllegalArgumentException e) {
//            // e.printStackTrace();
//            Log.d(TAG, e.toString());
//        } catch (Exception e) {
//            Log.d(TAG, e.toString());
//            //  e.printStackTrace();
//            //   Log.e("Error :: ", e.toString());
//        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ActionCode.PICK_CONTACT:
                    // final EditText phoneInput = (EditText) findViewById(R.id.phoneNumberInput);
                    Cursor cursor = null;
                    String phoneNumber = "";
                    final List<String> allNumbers = new ArrayList<>();
                    int phoneIdx = 0;
                    try {
                        Uri result = data.getData();
                        String id = result.getLastPathSegment();
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                        phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        if (cursor.moveToFirst()) {
                            while (cursor.isAfterLast() == false) {
                                phoneNumber = cursor.getString(phoneIdx);
                                allNumbers.add(phoneNumber);
                                cursor.moveToNext();
                            }
                        } else {
                            //no results actions
                        }
                    } catch (Exception e) {
                        //error actions
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        HashSet hs = new HashSet();
                        hs.addAll(allNumbers);
                        allNumbers.clear();
                        allNumbers.addAll(hs);
//                        final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
                        final String[] items = allNumbers.toArray(new String[allNumbers.size()]);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(TopUpEmoneyActivity.this);

//                        LayoutInflater inflater = this.getLayoutInflater();
                        ViewGroup parent = findViewById(R.id.contentHost);
                        View dialogView = View.inflate(this, R.layout.dialog_custom_contact, parent);

                        builder.setView(dialogView);
                        TextView textViewTitleDialog = dialogView.findViewById(R.id.textViewTitleDialog);
                        textViewTitleDialog.setText("Pilih Nomor Handphone Tujuan");
                        //  TextView textViewItemDialog=dialogView.findViewById(R.id.textViewItemDialog);

                        //Prepare ListView in dialog
                        ListView listViewPhoneNumber = (ListView) dialogView.findViewById(R.id.listViewPhoneNumber);
                        final AlertDialog alert = builder.create();

                        ArrayAdapter<String> adapter
                                = new ArrayAdapter<String>(this,
                                R.layout.layout_item_phonenumber, R.id.textViewItemPhone, items);
                        listViewPhoneNumber.setAdapter(adapter);
                        listViewPhoneNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                // TODO Auto-generated method stub
//                                Toast.makeText(AndroidCustomDialogActivity.this,
//                                        parent.getItemAtPosition(position).toString() + " clicked",
//                                        Toast.LENGTH_LONG).show();
                                //   dismissDialog(CUSTOM_DIALOG_ID);
                                String selectedNumber = items[position].toString();
                                selectedNumber = selectedNumber.replace("-", "");
                                //  phoneInput.setText(selectedNumber);
                                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                                if (selectedNumber.startsWith("62")) {
                                    if (!selectedNumber.startsWith("9")) {
                                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                                    }
                                }
                                materialEditTextNoHandphone.setText("");
//                                materialEditTextNoHandphone.setCharacterDelay(50);
//                                materialEditTextNoHandphone.animateText(selectedNumber);
                                materialEditTextNoHandphone.setText(selectedNumber);
                                // materialEditTextNoHandphone.setSelection(selectedNumber.length());
                                alert.dismiss();

                            }
                        });

                        if (allNumbers.size() > 1) {
                            alert.show();
//                            alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            alert.getWindow().setBackgroundDrawableResource(R.drawable.alert_dialog_edge);
                        } else {
                            String selectedNumber = phoneNumber.toString();
                            selectedNumber = selectedNumber.replace("-", "");
//                            phoneInput.setText(selectedNumber);
                            selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                            if (selectedNumber.startsWith("62")) {
                                if (!selectedNumber.startsWith("9")) {
                                    selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                                }
                            }
                            materialEditTextNoHandphone.setText("");
//                            materialEditTextNoHandphone.setCharacterDelay(50);
//                            materialEditTextNoHandphone.animateText(selectedNumber);
                            materialEditTextNoHandphone.setText(selectedNumber);
                            //  materialEditTextNoHandphone.setSelection(selectedNumber.length());
                        }

                        if (phoneNumber.length() == 0) {
                            //no numbers found actions
                        }
                    }
                    break;
            }
        } else {
            //activity result error actions
        }

    }

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

//    private BaseTarget target2 = new BaseTarget<BitmapDrawable>() {
//        @Override
//        public void onResourceReady(BitmapDrawable bitmap, Transition<? super BitmapDrawable> transition) {
//            // do something with the bitmap
//            // for demonstration purposes, let's set it to an imageview
//            imageViewProduk.setImageDrawable(bitmap);
//        }
//
//        @Override
//        public void getSize(SizeReadyCallback cb) {
//            cb.onSizeReady(500, 150);
//        }
//
//        @Override
//        public void removeCallback(SizeReadyCallback cb) {}
//    };

    private void startBlinkingAnimation(boolean isShowMessage) {
        //  LinearLayout linearLayout = findViewById(R.id.bottom_toolbar);

        TextViewPlus textViewPlusSaldoBottom = bottom_toolbar.findViewById(R.id.TextViewPlusSaldoBottom);
        textViewPlusSaldoBottom.setTextColor(ContextCompat.getColor(this, R.color.md_red_500));
        textViewPlusSaldoBottom.setTypeface(textViewPlusSaldoBottom.getTypeface(), Typeface.BOLD);
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
        textViewPlusSaldoBottom.startAnimation(startAnimation);
        if (isShowMessage) {
//            textViewPlusSukses.setVisibility(View.VISIBLE);
//            textViewPlusSukses.startAnimation(slideUp);
//
//            textViewPlusSukses.setText("Maaf, Saldo Anda Tidak Cukup");
            MyDynamicToast.informationMessage(this, -1, "Maaf, Saldo Anda Tidak Cukup");
        }

    }

    private void stopBlinkingAnimation() {
        //  LinearLayout linearLayout = findViewById(R.id.bottom_toolbar);

        TextViewPlus textViewPlusSaldoBottom = bottom_toolbar.findViewById(R.id.TextViewPlusSaldoBottom);
        textViewPlusSaldoBottom.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        textViewPlusSaldoBottom.setTypeface(tfLight);
        //  Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
        textViewPlusSaldoBottom.clearAnimation();

    }


    @Override
    protected void onResume() {
        super.onResume();
        shimmer_view_container.startShimmerAnimation();
//        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
//
//// Gets the clipboard as text.
//        CharSequence pasteData = item.getText();

// If the string contains data, then the paste operation is done
        Log.d(TAG, "onResume: " + new MyClipboardManager().readFromClipboard(this));

//        if (!new MyClipboardManager().readFromClipboard(this).isEmpty()) {
        if (!PreferenceClass.getString("emoney_card_number", "").equals("")) {


//            StringBuilder paramAnonymousStringBuilder = new StringBuilder(new MyClipboardManager().readFromClipboard(this));
            StringBuilder paramAnonymousStringBuilder = new StringBuilder(PreferenceClass.getString("emoney_card_number", ""));
            int i = paramAnonymousStringBuilder.length();// - 4;

            int j = i;
            while (i > 0) {
                paramAnonymousStringBuilder.insert(i, '-');
                i -= 4;
            }
            //   paramAnonymousString = paramAnonymousStringBuilder.toString();


            materialEditTextNoKartu.setText(paramAnonymousStringBuilder.toString());
            //   materialEditTextNoKartu.setText(new MyClipboardManager().readFromClipboard(this));
            //  materialEditTextNoKartu.setCharacterDelay(150);
            //  materialEditTextNoKartu.animateText(new MyClipboardManager().readFromClipboard(this));
            //if(j==16) {
            // materialEditTextNoKartu.setCu
            materialEditTextNoKartu.setSelection(19);
//            materialEditTextNoKartu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View view, boolean c) {
//                    if(c){
//                        materialEditTextNoKartu.setText(new MyClipboardManager().readFromClipboard(TopUpEmoneyActivity.this));
//                    }
//                }
//            });
            // }
        }
    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }

    private void showCaseEtoll() {
        if (PreferenceClass.getInt(TAGETOLL, 0) != 1) {

            showCaseFirst(this, "", "Untuk handphone yang mempunyai fitur NFCBrizzi, bisa tempelkan kartu pada kamera belakang Anda dan tunggu hingga handphone Anda membaca no kartu e toll pelanggan", materialEditTextNoKartu);
            // client = new GuideView.Builder(PdamActivity.this);
            mGbuilder.setGuideListener(new GuideView.GuideListener() {
                @Override
                public void onDismiss(View view) {

                    PreferenceClass.putInt(TAGETOLL, 1);


                }
            });
            mGuideView = mGbuilder.build();
            mGuideView.show();
        }
    }

    private void showCaseSaldo() {
        if (PreferenceClass.getInt(TAGSALDO, 0) != 1) {

            showCaseFirst(this, "", "Klik Icon untuk memilih Kontak yang ada di handphone", imageViewAddressBook);
            // client = new GuideView.Builder(PdamActivity.this);
            mGbuilder.setGuideListener(new GuideView.GuideListener() {
                @Override
                public void onDismiss(View view) {

                    PreferenceClass.putInt(TAGSALDO, 1);


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
//                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));

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
                new_popup_alert_two_buttonPermision(TopUpEmoneyActivity.this, "Peringatan", R.string.content_alert_kontak);
            }
        }
    }

}
