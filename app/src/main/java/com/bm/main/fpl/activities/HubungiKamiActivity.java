package com.bm.main.fpl.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.CollapsingToolbarLayout;
//import android.support.design.widget.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//import androidx.appcompat.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.Utils;
import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.ListGridSaranAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.SaranKategoriModel;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.MyClipboardManager;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HubungiKamiActivity extends BaseActivity implements JsonObjectResponseCallback, ListGridSaranAdapter.OnClickProduk {
    private static final String TAG = HubungiKamiActivity.class.getSimpleName();
    LinearLayout linHunting, linCallOnly, linEmailSupport, linEmailMarketing;
    TextView textViewEmailSupport, textViewEmailMarketing;
    //    FrameLayout fabLiveChat;
    FloatingActionButton fabLiveChat;


    RecyclerView recycler_list_komplain;
    ListGridSaranAdapter listGridSaranAdapter;
    @NonNull
    ArrayList<SaranKategoriModel.Response_value> data = new ArrayList<>();
    SaranKategoriModel saranKategoriModel;
    AppCompatButton button_lanjutkan;
    MaterialEditText materialEditTextSaran;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    String kategoriSaran = null;
    LinearLayout lin_content;
    FrameLayout loading_bar;
//    ImageView imageViewBack;

    AppBarLayout appBarLayout;
    private Menu collapsedMenu;
    private boolean appBarExpanded = true;
    NestedScrollView nestedScrollView;
    private ImageView imageViewCustomerService;
    private int heightDp;
    CollapsingToolbarLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hubungi_kami);
        overridePendingTransition(0, 0);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Hubungi Kami");
        init(0);

//        imageViewBack=findViewById(R.id.imageViewCancel);
//        imageViewBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        nestedScrollView = findViewById(R.id.nestedScrollView);
        imageViewCustomerService = findViewById(R.id.imageViewCustomerService);
        heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 6;
        Log.d(TAG, "onCreateView: " + heightDp);
        lp = (CollapsingToolbarLayout.LayoutParams) imageViewCustomerService.getLayoutParams();
//        CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) lin_header.getLayoutParams();

        lp.height = (int) heightDp;
//        lin_header.setMinimumHeight(lp.height);
        imageViewCustomerService.setMinimumHeight(lp.height);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully expanded.
                float alpha = (float) Math.abs(verticalOffset) / (lp.height - toolbar.getHeight());
                //  Log.d(TAG, "onOffsetChanged: "+alpha);
                if ((Math.abs(verticalOffset)) > 150) {
                    toolbar.setBackgroundColor(Utils.getColorWithAlpha(ContextCompat.getColor(HubungiKamiActivity.this, R.color.colorPrimary_ppob), alpha));
                    if (alpha >= 1.0) {
                        toolbar.setBackground(ContextCompat.getDrawable(HubungiKamiActivity.this, R.drawable.toolbar));
                    }
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    toolbar.setBackgroundColor(0);
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
        fabLiveChat = findViewById(R.id.fab_live_chat);
        fabLiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(HubungiKamiActivity.this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    liveChat();
                }
            }
        });
        textViewEmailSupport = findViewById(R.id.textViewEmailSupport);
        textViewEmailMarketing = findViewById(R.id.textViewEmailMarketing);
        linHunting = findViewById(R.id.linHunting);
        linHunting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(HubungiKamiActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    String phone = "+62318535799";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            }
        });
        linCallOnly = findViewById(R.id.linCallOnly);
        linCallOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(HubungiKamiActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    String phone = "+6281385785799";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            }
        });
        linEmailSupport = findViewById(R.id.linEmailSupport);
        linEmailSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(HubungiKamiActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
//                    sendGmail("support@fastpay.co.id", "marketing@fastpay.co.id", "");
                    String paramAnonymousString = textViewEmailSupport.getText().toString();

                    if (new MyClipboardManager().copyToClipboard(HubungiKamiActivity.this, paramAnonymousString)) {
                        showToast("Email Support Telah Disalin");
                    }
                }
            }
        });

        linEmailMarketing = findViewById(R.id.linEmailMarketing);
        linEmailMarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(HubungiKamiActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    Device.vibrate(HubungiKamiActivity.this);
                    String paramAnonymousString = textViewEmailMarketing.getText().toString();
                    if (new MyClipboardManager().copyToClipboard(HubungiKamiActivity.this, paramAnonymousString)) {
                        showToast("Email Marketing Telah Disalin");
                    }
                }
            }
        });

        loading_bar = findViewById(R.id.loading_bar);
        lin_content = findViewById(R.id.lin_content);
        materialEditTextSaran = findViewById(R.id.materialEditTextSaran);
        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(HubungiKamiActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    if (kategoriSaran == null) {

                        recycler_list_komplain.setAnimation(animShake);
                        recycler_list_komplain.startAnimation(animShake);
                        Device.vibrate(HubungiKamiActivity.this);

                        // showToastCustom(KritikSaranActivity.this, 1, "Kategori Saran & Kritik Belum Dipilih");
                        //     snackBarCustomAction(findViewById(R.id.button_lanjutkan), 0, "Kategori Saran & Kritik Belum Dipilih", R.string.tutup, WARNING);
                        MyDynamicToast.warningMessage(HubungiKamiActivity.this, "Kategori Saran & Kritik Belum Dipilih");
                        return;
                    }

                    if (materialEditTextSaran.getText().toString().isEmpty()) {

                        materialEditTextSaran.setAnimation(animShake);
                        materialEditTextSaran.startAnimation(animShake);

                        materialEditTextSaran.setError("Saran & Kritik Tidak Boleh Kosong");
                        Device.vibrate(HubungiKamiActivity.this);
                        return;

                    }
                    requestKomplain();
                }
            }
        });

        recycler_list_komplain = findViewById(R.id.recycler_list_komplain);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recycler_list_komplain.setHasFixedSize(false);
        recycler_list_komplain.setLayoutManager(mLayoutManager);
        ViewCompat.setNestedScrollingEnabled(recycler_list_komplain, false);
        listGridSaranAdapter = new ListGridSaranAdapter(data, this, this);
        recycler_list_komplain.setAdapter(listGridSaranAdapter);
        requestListKategori();
    }

    private void requestKomplain() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestInfo(materialEditTextSaran.getText().toString(), kategoriSaran));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_SARAN, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void requestListKategori() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestTypeKomplain());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_KATEGORI_SARAN, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        collapsedMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_right_drawer:
                openTopDialog(false);
                return true;
        }
        if (item.getTitle() == "Live Chat") {
            //Toast.makeText(this, "clicked add", Toast.LENGTH_SHORT).show();
            fabLiveChat.performClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        finish();
        // overridePendingTransition(0,0);


    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());


//        try {
//            if (response.getString("response_code").equals("03")) {
//                if (actionCode == ActionCode.LIST_KATEGORI_SARAN) {
//                    loading_bar.setVisibility(View.GONE);
//                } else if (actionCode == ActionCode.REQUEST_SARAN) {
//                    closeProgressBarDialog();
//                }
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {


        if (actionCode == ActionCode.LIST_KATEGORI_SARAN) {
            loading_bar.setVisibility(View.GONE);
            recycler_list_komplain.setVisibility(View.VISIBLE);
            data.clear();
            saranKategoriModel = gson.fromJson(response.toString(), SaranKategoriModel.class);
            if (saranKategoriModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                //ArrayList<ProdukPulsaRegularModel.Response_value> dataProduk = new ArrayList<>();
                data.addAll(saranKategoriModel.getResponse_value());


                listGridSaranAdapter.notifyDataSetChanged();


            }
        } else if (actionCode == ActionCode.REQUEST_SARAN) {
            closeProgressBarDialog();
            try {
                if (response.getString("response_code").equals(ResponseCode.SUCCESS)) {
                    new_popup_alert(HubungiKamiActivity.this, "SUKSES", response.getString("response_desc"));

                } else {
                    new_popup_alert(HubungiKamiActivity.this, "INFO", response.getString("response_desc"));
                }
            } catch (Exception e) {
                Log.d(TAG, "JSONException: " + e.toString());
            }
            listGridSaranAdapter.clearSelections();
            materialEditTextSaran.setText("");
        }
//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }

    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        if (actionCode == ActionCode.REQUEST_SARAN) {
            closeProgressBarDialog();
        }
    }

    @Override
    public void onClickProduk(@NonNull SaranKategoriModel.Response_value produk) {
        Log.d(TAG, "onClickProduk: " + produk.getId_kategori());
        kategoriSaran = produk.getId_kategori();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
            //collapsed
            collapsedMenu.add("Live Chat")
                    .setIcon(R.drawable.ic_live_chat)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            //expanded

        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }
}
