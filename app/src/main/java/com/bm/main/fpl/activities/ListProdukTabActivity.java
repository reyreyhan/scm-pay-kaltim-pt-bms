package com.bm.main.fpl.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.ListProdukJenisAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.ProdukJenisModel;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class ListProdukTabActivity extends BaseActivity implements ProgressResponseCallback, ListProdukJenisAdapter.OnClickProduk, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String TAG = ListProductActivity.class.getSimpleName();
    RecyclerView recyclerViewProduk;

    @NonNull
    ArrayList<ProdukJenisModel.Response_value> data = new ArrayList<>();
    ProdukJenisModel produkJenisModel;
    String product;
    SearchView searchView;
    SearchManager searchManager;
    ListProdukJenisAdapter adapterJenisProduk;
    String hint;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout layout_no_connection, layout_data_empty;
    private AppCompatButton button_coba_lagi;
    private boolean isChecked;
    int selectedTab;
    String type;
    LinearLayout linMain;
    //    private ProgressBar progressBar;
    TextView txtHeader, txtSub;
    TabLayout tabLayout;
    LinearLayout tabPostpaid, tabPrepaid;
    TextView textTabItemPostpaid, textTabItemPrepaid;
    private View viewItemTabPostpaid, viewItemTabPrepaid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk_tab);
        Intent intent = getIntent();
        product = intent.getStringExtra("product");
        hint = intent.getStringExtra("hint");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("title"));
        init(1);
        linMain = findViewById(R.id.linMain);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(0);
        ViewGroup parent = findViewById(R.id.linCustomTabProduk);
        tabPostpaid = (LinearLayout)View.inflate(this,R.layout.custom_tab_produk, parent);
        tabPrepaid = (LinearLayout) View.inflate(this,R.layout.custom_tab_produk, parent);


        textTabItemPostpaid = tabPostpaid.findViewById(R.id.textTab);
        textTabItemPrepaid = tabPrepaid.findViewById(R.id.textTab);
        viewItemTabPostpaid= tabPostpaid.findViewById(R.id.viewItemTabProduk);
        viewItemTabPrepaid = tabPrepaid.findViewById(R.id.viewItemTabProduk);


        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        layout_data_empty = findViewById(R.id.layout_data_empty);
        txtHeader = layout_data_empty.findViewById(R.id.txtHeader);
        txtSub = layout_data_empty.findViewById(R.id.txtSub);
        button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewProduk.getVisibility() == View.VISIBLE) {
                    recyclerViewProduk.setVisibility(View.GONE);
                }
                if (layout_no_connection.getVisibility() == View.VISIBLE) {
                    layout_no_connection.setVisibility(View.GONE);
                }
                if (linMain.getVisibility() == View.GONE) {
                    linMain.setVisibility(View.VISIBLE);

                }
                if (mShimmerViewContainer.getVisibility() == View.GONE) {
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.startShimmerAnimation();

                }
                requestDaftarProduk();
            }
        });

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(false);
        recyclerViewProduk = findViewById(R.id.recyclerViewProduk);
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTypeface(tfLight);
        searchAutoComplete.setTextSize(14);

        searchView.setQueryHint(hint);

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewProduk.setHasFixedSize(false);
        recyclerViewProduk.setLayoutManager(linearLayoutManager);
        recyclerViewProduk.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapterJenisProduk = new ListProdukJenisAdapter(data, this, this);
        recyclerViewProduk.setAdapter(adapterJenisProduk);

        if (PreferenceClass.getJSONObject(product).length() > 0) {
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
            if (layout_data_empty.getVisibility() == View.VISIBLE) {
                layout_data_empty.setVisibility(View.GONE);
            }
            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }
            if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmerAnimation();
            }
            produkJenisModel = gson.fromJson(PreferenceClass.getJSONObject(product).toString(), ProdukJenisModel.class);

            data.clear();
            data.addAll(produkJenisModel.getResponse_value());
            //  setData(gameModel.getData());


            adapterJenisProduk.getFilter().filter("");
            recyclerViewProduk.setVisibility(View.VISIBLE);
        }

        requestDaftarProduk();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }

    private void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: ");
        setupTab();
        tabPostpaid.setSelected(true);

    }

    private void setupTab() {


        textTabItemPostpaid.setText("Postpaid");
        //imageTabItemRegular.setVisibility(View.GONE);
        viewItemTabPostpaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.selector_indicator_tab_btn_group_home));
        tabPostpaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.selector_tab_btn_first));
        textTabItemPostpaid.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_ppob));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabPostpaid));

        textTabItemPrepaid.setText("Prepaid");
        //imageTabItemInternet.setVisibility(View.GONE);
        viewItemTabPrepaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.unselector_indicator_tab_btn_group_home));
        tabPrepaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.unselector_tab_btn_last));
        textTabItemPrepaid.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabPrepaid));


    }

    private void bindWidgetsWithAnEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView textTab;
                // ImageView imageTab;
                switch (position) {
                    case 0:
                        viewItemTabPostpaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.selector_indicator_tab_btn_group_home));
                        tabPostpaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.selector_tab_btn_first));
                        textTabItemPostpaid.setTextColor(ContextCompat.getColor(ListProdukTabActivity.this, R.color.colorPrimary_ppob));
//                        textTab = tabPostpaid.findViewById(R.id.textTab);
//
//                        textTab.setTextColor(ContextCompat.getColor(ListProdukTabActivity.this, R.color.md_white_1000));
//
//                        textTab.setTypeface(tfRegular, Typeface.NORMAL);

                        selectedTab = 0;
                        type = "POSTPAID";
                        adapterJenisProduk.jenis = type;

                        break;
                    case 1:
                        viewItemTabPrepaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.selector_indicator_tab_btn_group_home));
                        tabPrepaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.selector_tab_btn_last));
                        textTabItemPrepaid.setTextColor(ContextCompat.getColor(ListProdukTabActivity.this, R.color.colorPrimary_ppob));
//                        textTab = tabPrepaid.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(ListProdukTabActivity.this, R.color.md_white_1000));
//
//                        textTab.setTypeface(tfRegular, Typeface.NORMAL);

                        selectedTab = 1;
                        type = "PREPAID";
                        adapterJenisProduk.jenis = type;
                        break;


                }
                requestLayout();// call activity here for daftar id pelanggan

            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {

//                Log.d(TAG, "onTabUnselected: " + tab.getPosition());
                int position = tab.getPosition();
                TextView textTab;
//                ImageView imageTab;
                switch (position) {
                    case 0:
                        viewItemTabPostpaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.unselector_indicator_tab_btn_group_home));
                        tabPostpaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.unselector_tab_btn_first));
                        textTabItemPostpaid.setTextColor(ContextCompat.getColor(ListProdukTabActivity.this, R.color.md_white_1000));
//                        textTab = tabPostpaid.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(ListProdukTabActivity.this, R.color.md_blue_200));
//                        textTab.setTypeface(tfLight, Typeface.NORMAL);
//                        if(isChecked){
//                            adapterProduk.clearSelections();
//                            kodeProdukPilihan ="";
//                            namaProdukPilihan ="";
//                            isChecked=false;
//                        }
                        break;

                    case 1:
                        viewItemTabPrepaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.unselector_indicator_tab_btn_group_home));
                        tabPrepaid.setBackground(ContextCompat.getDrawable(ListProdukTabActivity.this,R.drawable.unselector_tab_btn_last));
                        textTabItemPrepaid.setTextColor(ContextCompat.getColor(ListProdukTabActivity.this, R.color.md_white_1000));
//                        textTab = tabPrepaid.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(ListProdukTabActivity.this, R.color.md_blue_100));
//                        textTab.setTypeface(tfLight, Typeface.NORMAL);
//                        if(isChecked){
//                            adapterProduk.clearSelections();
//                            kodeProdukPilihan ="";
//                            namaProdukPilihan ="";
//                            isChecked=false;
//                        }
                        break;


                }


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //       Log.d(TAG, "onTabReselected: "+tab.getPosition());
            }
        });
    }

    private void requestLayout() {

        // Log.d(TAG, "requestLayout: " + selectedTab);
        //FragmentManager fm = getSupportFragmentManager();
        // FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // mLayoutManagerProduk.removeAllViews();
        switch (selectedTab) {
            case 0:
                // recyclerViewNominalProduk.getRecycledViewPool().clear();
                // if (materialEditTextNoHandphone.getText().length() >= 4) {
                // adapterProduk.clearSelections();

                //  adapterJenisProduk.removeData();
                adapterJenisProduk.getFilter().filter("");
                recyclerViewProduk.invalidate();
                // adapterJenisProduk.notifyDataSetChanged();
//                    if(!isChecked){
//                        adapterProduk.clearSelections();
//                        isChecked=false;
//                    }

//                } else if (materialEditTextNoHandphone.getText().length() < 4) {
//                    if (produkPulsaModel != null) {
//                        adapterProduk.getFilter().filter("");
//                    }
//                }

            case 1:

                //recyclerViewNominalProduk.getRecycledViewPool().clear();
                // if (materialEditTextNoHandphone.getText().length() >= 4) {
                //     adapterProduk.clearSelections();
                // adapterJenisProduk.jenis=type;
                adapterJenisProduk.getFilter().filter("");
                recyclerViewProduk.invalidate();
//                    if(!isChecked){
//                        adapterProduk.clearSelections();
//                        isChecked=false;
//                    }

//                } else if (materialEditTextNoHandphone.getText().length() < 4) {
//                    if (produkPulsaModel != null) {
//                        adapterProduk.getFilter().filter("");
//                    }
//                }

//
                break;


        }

        Log.d(TAG, "requestLayout: " + selectedTab);


    }


    private void requestDaftarProduk() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestListProduct(product));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PRODUK, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_right_voice) {
            startVoice();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startVoice() {

        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        Intent intent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Fastpay Voice Command");
            startActivityForResult(intent, RequestCode.ActionCode_VOICE_RECOGNITION);
        } else {
            showToast("Maaf,Fastpay Command Voice tidak di support di Handphone Anda");
        }


    }

    @Override
    public void onBackPressed() {
        if (selectedTab != 0) {
            tabLayout.getTabAt(0).select();
            selectedTab = 0;
            tabPostpaid.setSelected(true);

        } else {
            Intent intent = new Intent();

            setResult(RESULT_CANCELED, intent);

            finish();
            //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);

        }
        closeKeyboard(this);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());


        if (layout_no_connection.getVisibility() == View.VISIBLE) {
            layout_no_connection.setVisibility(View.GONE);
        }
        if (linMain.getVisibility() == View.GONE) {
            linMain.setVisibility(View.VISIBLE);
        }
        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
        }

//        try {
//            if (response.getString("response_code").equals("03")) {
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {
                if (actionCode == ActionCode.LIST_PRODUK) {

                    produkJenisModel = gson.fromJson(response.toString(), ProdukJenisModel.class);
                    // data.clear();
                    if (produkJenisModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                        JSONObject obj = PreferenceClass.getJSONObject(product);
                        JSONArray array = new JSONArray();
                        try {
                            array = obj.getJSONArray("response_value");
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }

                        if (array.length() != produkJenisModel.getResponse_value().size()) {
                            PreferenceClass.putJSONObject(product, response);
                            data.clear();
                            data.addAll(produkJenisModel.getResponse_value());

                            adapterJenisProduk.getFilter().filter("");
                        }
                        recyclerViewProduk.setVisibility(View.VISIBLE);
                        // data.addAll(produkJenisModel.getData());
                        //  setData(gameModel.getData());
                        // adapterJenisProduk.notifyDataSetChanged();

                        // stop animating Shimmer and hide the layout
                    }else if (produkJenisModel.getResponse_code().equals("03")) {
                        new_popup_alert_session(this, "Informasi", produkJenisModel.getResponse_desc());
                    } else {
                        layout_data_empty.setVisibility(View.VISIBLE);
                        txtHeader.setText("Data Produk");
                        txtSub.setText("Tidak Ditemukan");
                    }
                }

//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }

    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        //  Log.d(TAG, "onFailure: "+responseCode+" "+responseDescription+" "+throwable.toString());
        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.LIST_PRODUK) {
                if (PreferenceClass.getJSONObject(product).length() > 0) {

                } else {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    linMain.setVisibility(View.GONE);
                    layout_data_empty.setVisibility(View.GONE);
                    layout_no_connection.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {
//        System.out.println(bytesRead);
//                System.out.println(totalSize);
//                System.out.println(done);
//                System.out.format("%d%% done\n", (100 * bytesRead) / totalSize);
        int progress = (int) ((100 * bytesRead) / totalSize);

        // Enable if you want to see the progress with logcat
        Log.d(TAG, "Progress: " + progress + "%");
        // progressBar.setProgress(progress);
        if (done) {
            Log.d(TAG, "Done loading");
        }
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
    public void onClickProduk(@NonNull ProdukJenisModel.Response_value produk) {
        closeKeyboard(this);
        Intent intent = new Intent();
        intent.putExtra("kodeProduk", produk.getProduct_code());
        intent.putExtra("namaProduk", produk.getProduct_name());
        setResult(RESULT_OK, intent);
        finish();
        //overridePendingTransition(R.anim.popup_show,R.anim.popup_hide);

    }

    @Override
    public boolean onClose() {
        adapterJenisProduk.getFilter().filter("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapterJenisProduk.getFilter().filter(query);
        closeKeyboard(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterJenisProduk.getFilter().filter(newText);
        return false;
    }

    @Override
    protected void onDestroy() {
        mShimmerViewContainer.stopShimmerAnimation();

        mShimmerViewContainer.removeAllViews();
        recyclerViewProduk.removeAllViews();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        if (requestCode == RequestCode.ActionCode_VOICE_RECOGNITION && resultCode == RESULT_OK) {
            ArrayList matches = dataIntent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayList<ProdukJenisModel.Response_value> filteredList = new ArrayList<>();
            Log.d(TAG, "onActivityResult: " + Arrays.asList(matches));
            for (int i = 0; i < matches.size(); i++) {
                String voiceNumber = matches.get(i).toString().trim();


                for (ProdukJenisModel.Response_value produk : data) {

                    if (produk.getProduct_code().toLowerCase().contains(voiceNumber.toLowerCase()) || produk.getProduct_name().toLowerCase().contains(voiceNumber.toLowerCase()) ) {
                        Log.d(TAG, "onActivityResult: "+voiceNumber.toLowerCase());
                        searchView.setQuery(voiceNumber.toLowerCase(), true);
                    }
                }
                // searchView..setText("");
//                if (!matches.contains(adapter.getOriginalData().get(i).getProduct_name())) {
//                    searchView.setQuery(voiceNumber.toLowerCase(), true);
//                }

//                if (adapter.getFilter().filter(voiceNumber)) {


//                }

            }
        }
    }

}
