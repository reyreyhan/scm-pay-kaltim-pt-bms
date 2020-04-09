package com.bm.main.fpl.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.ListKabupatenAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.KabupatenModel;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class ListKabupatenActivity extends BaseActivity implements ProgressResponseCallback, ListKabupatenAdapter.OnClickProduk, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String TAG = ListKabupatenActivity.class.getSimpleName();
    RecyclerView recyclerViewKabupaten;

    @NonNull
    ArrayList<KabupatenModel.Response_value> data = new ArrayList<>();
    KabupatenModel kabupatenModel;
    //  String product="kabupaten";
    SearchView searchView;
    SearchManager searchManager;
    ListKabupatenAdapter adapter;
    private String propCode;

    ShimmerFrameLayout mShimmerViewContainer;
    LinearLayout linMain, layout_data_empty, layout_no_connection;
    private AppCompatButton button_coba_lagi;

    TextView txtHeader, txtSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_list_kabupaten);
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Kabupaten/Kota");
        init(0);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        layout_data_empty = findViewById(R.id.layout_data_empty);
        txtHeader = layout_data_empty.findViewById(R.id.txtHeader);
        txtSub = layout_data_empty.findViewById(R.id.txtSub);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        linMain = findViewById(R.id.linMain);
        button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewKabupaten.getVisibility() == View.VISIBLE) {
                    recyclerViewKabupaten.setVisibility(View.GONE);
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
                requestDaftarKabupaten();
            }
        });
        propCode = intent.getStringExtra("propCode");
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(false);
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTypeface(tfLight);
        searchAutoComplete.setTextSize(14);
        recyclerViewKabupaten = findViewById(R.id.recyclerViewKabupaten);

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(this.getString(R.string.hint_cari_kabupaten));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewKabupaten.setHasFixedSize(false);
        recyclerViewKabupaten.setLayoutManager(linearLayoutManager);
        recyclerViewKabupaten.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ListKabupatenAdapter(data, this, this);
        recyclerViewKabupaten.setAdapter(adapter);

        if (PreferenceClass.getJSONObject(propCode).length() > 0) {
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
            kabupatenModel = gson.fromJson(PreferenceClass.getJSONObject(propCode).toString(), KabupatenModel.class);

            data.clear();
            data.addAll(kabupatenModel.getResponse_value());
            //  setData(gameModel.getData());
            adapter.notifyDataSetChanged();
            recyclerViewKabupaten.setVisibility(View.VISIBLE);
        }
        requestDaftarKabupaten();
    }

    private void requestDaftarKabupaten() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestKota(propCode));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PRODUK, this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeKeyboard(this);
        Intent intent = new Intent();

        setResult(RESULT_CANCELED, intent);

        finish();
        //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);


    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
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
                    Log.d(TAG, "onSuccess: " + response.toString());
                    kabupatenModel = gson.fromJson(response.toString(), KabupatenModel.class);
                    //  data.clear();
                    if (kabupatenModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                        JSONObject obj = PreferenceClass.getJSONObject(propCode);
                        JSONArray array = new JSONArray();
                        try {
                            array = obj.getJSONArray("response_value");
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }

                        if (array.length() != kabupatenModel.getResponse_value().size()) {
                            PreferenceClass.putJSONObject(propCode, response);
                            data.clear();
                            data.addAll(kabupatenModel.getResponse_value());
                            adapter.notifyDataSetChanged();
                        }


//                if(kabupatenModel.getData().size()>0) {
                        //  data.addAll(kabupatenModel.getData());
                        //  setData(gameModel.getData());
                        // adapter.notifyDataSetChanged();
                        recyclerViewKabupaten.setVisibility(View.VISIBLE);

//                }else {
//                    if (layout_no_connection.getVisibility() == View.VISIBLE) {
//                        layout_no_connection.setVisibility(View.GONE);
//                    }
//                    if (linMain.getVisibility() == View.VISIBLE) {
//                        linMain.setVisibility(View.GONE);
//                    }
//                    layout_data_empty.setVisibility(View.VISIBLE);
//                    txtHeader.setText("Data Kabupaten");
//                    txtSub.setText("Tidak Ditemukan");
//                    searchView.setVisibility(View.GONE);
//                }
                    } else {
                        if (layout_no_connection.getVisibility() == View.VISIBLE) {
                            layout_no_connection.setVisibility(View.GONE);
                        }
                        if (linMain.getVisibility() == View.VISIBLE) {
                            linMain.setVisibility(View.GONE);
                        }
                        layout_data_empty.setVisibility(View.VISIBLE);
                        txtHeader.setText("Data Kabupaten");
                        txtSub.setText("Tidak Ditemukan");
                        searchView.setVisibility(View.GONE);
                    }


                }

//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }

    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        if (responseCode.equals(NETWORK_ERROR)) {
            if (PreferenceClass.getJSONObject(propCode).length() > 0) {

            } else {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                linMain.setVisibility(View.GONE);
                layout_no_connection.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

    @Override
    public void onClickProduk(@NonNull KabupatenModel.Response_value produk) {

        Intent intent = new Intent();
        intent.putExtra("kodeKabupaten", produk.getCity_code());
        intent.putExtra("namaKabupaten", produk.getCity_name());
        setResult(RESULT_OK, intent);
        closeKeyboard(this);
        finish();
        //overridePendingTransition(R.anim.popup_show,R.anim.popup_hide);

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
        super.onDestroy();
    }
}
