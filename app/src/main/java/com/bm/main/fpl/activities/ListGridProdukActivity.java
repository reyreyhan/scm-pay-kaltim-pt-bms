package com.bm.main.fpl.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.annotation.NonNull;
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
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.adapters.ListGridProdukAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.ProdukModel;
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

public class ListGridProdukActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener, ListGridProdukAdapter.OnClickProduk, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String TAG = ListGridProdukActivity.class.getSimpleName();
    RecyclerView recyclerViewProduk;

    @NonNull
    ArrayList<ProdukModel.Response_value> data = new ArrayList<>();
    ProdukModel produkModel;
    String product, hint;
    SearchView searchView;
    SearchManager searchManager;
    ListGridProdukAdapter adapter;
//    RecyclerView.LayoutManager mLayoutManager;
StaggeredGridLayoutManager mLayoutManager;
    private LinearLayout linMain, layout_no_connection, layout_data_empty;

    ShimmerFrameLayout shimmer_view_container;
    Intent intent;
     NestedScrollView nestedScrollView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grid_produk);
      //  overridePendingTransition(0,0);
        intent = getIntent();
        product = intent.getStringExtra("product");
        hint = intent.getStringExtra("hint");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("title"));
        init(1);
        shimmer_view_container = findViewById(R.id.shimmer_view_container);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        layout_data_empty = findViewById(R.id.layout_data_empty);
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nestedScrollView.getVisibility() == View.VISIBLE) {
                    nestedScrollView.setVisibility(View.GONE);
                }
//                if (nestedScrollViewProduk.getVisibility() == View.VISIBLE) {
//                    nestedScrollViewProduk.setVisibility(View.GONE);
//                }
                if (layout_no_connection.getVisibility() == View.VISIBLE) {
                    layout_no_connection.setVisibility(View.GONE);
                }
                if (layout_data_empty.getVisibility() == View.VISIBLE) {
                    layout_data_empty.setVisibility(View.GONE);
                }
                if (linMain.getVisibility() == View.GONE) {
                    linMain.setVisibility(View.VISIBLE);

                }
                if (shimmer_view_container.getVisibility() == View.GONE) {
                    shimmer_view_container.setVisibility(View.VISIBLE);
                    shimmer_view_container.startShimmerAnimation();

                }
                requestDaftarProduk();
            }
        });
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setFocusable(false);
        searchView.setQueryHint(hint);
        recyclerViewProduk = findViewById(R.id.recyclerViewProduk);

        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(14);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        textView = searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);

        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewProduk.setHasFixedSize(false);
//        recyclerViewProduk.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewProduk, false);
        //recyclerViewPropinsi.setItemViewCacheSize(1024);
        recyclerViewProduk.setLayoutManager(mLayoutManager);
        //recyclerViewPropinsi.setLayoutManager(linearLayoutManager);
//        recyclerViewPropinsi.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // mLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);

        adapter = new ListGridProdukAdapter(data, this, this, product);
        recyclerViewProduk.setAdapter(adapter);

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
            if (shimmer_view_container.getVisibility() == View.GONE) {
                shimmer_view_container.setVisibility(View.VISIBLE);
                shimmer_view_container.startShimmerAnimation();
            }
            produkModel = gson.fromJson(PreferenceClass.getJSONObject(product).toString(), ProdukModel.class);
            if (produkModel.getResponse_value().size() > 0) {
                data.clear();
                data.addAll(produkModel.getResponse_value());
                //  setData(gameModel.getData());
                adapter.notifyDataSetChanged();
                if (shimmer_view_container.getVisibility() == View.VISIBLE) {
                    shimmer_view_container.setVisibility(View.GONE);
                    shimmer_view_container.stopShimmerAnimation();
                }
                nestedScrollView.setVisibility(View.VISIBLE);
//                nestedScrollViewProduk.setVisibility(View.VISIBLE);
            }
        }


        requestDaftarProduk();
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        shimmer_view_container.stopShimmerAnimation();
        super.onPause();
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
        closeKeyboard(this);
        //Intent intent = new Intent();

        setResult(RESULT_CANCELED);

        finish();
     //   overridePendingTransition(0,0);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        if (layout_no_connection.getVisibility() == View.VISIBLE) {
            layout_no_connection.setVisibility(View.GONE);
        }
        if (layout_data_empty.getVisibility() == View.VISIBLE) {
            layout_data_empty.setVisibility(View.GONE);
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
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {

                if (actionCode == ActionCode.LIST_PRODUK) {
                    produkModel = gson.fromJson(response.toString(), ProdukModel.class);

//            data.clear();
                    if (produkModel.getResponse_code().equals(ResponseCode.SUCCESS)) {

                        JSONObject obj = PreferenceClass.getJSONObject(product);
                        JSONArray array = new JSONArray();
                        try {
                            array = obj.getJSONArray("response_value");
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }

                        if (array.length() != produkModel.getResponse_value().size()) {
                            PreferenceClass.putJSONObject(product, response);
                            data.clear();
                            data.addAll(produkModel.getResponse_value());
                            adapter.notifyDataSetChanged();
                        }
                        Log.d(TAG, "onSuccess: " + response.toString());
                        if (nestedScrollView.getVisibility() == View.GONE) {
                            nestedScrollView.setVisibility(View.VISIBLE);
                        }
//                        nestedScrollViewProduk.setVisibility(View.VISIBLE);
//                    if(data.size()==0) {
//
//                    }
                        //  setData(gameModel.getData());
                       // try {
                           // Crashlytics.getInstance().crash();
                       // }catch (Exception e) {
                        //    throw new RuntimeException("This is a crash");
                       // }

                    }else if (produkModel.getResponse_code().equals("03")) {
                        new_popup_alert_session(this, "Informasi", produkModel.getResponse_desc());
                    } else {
                        layout_data_empty.setVisibility(View.VISIBLE);
                    }
                }
//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        // Log.d(TAG, "onFailure: " + responseCode + " " + responseDescription);

        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.LIST_PRODUK) {
                if (PreferenceClass.getJSONObject(product).length() > 0) {

                } else {
                    shimmer_view_container.setVisibility(View.GONE);
                    shimmer_view_container.stopShimmerAnimation();
                    linMain.setVisibility(View.GONE);
                    layout_data_empty.setVisibility(View.GONE);
                    layout_no_connection.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }
//    public void setData(ArrayList<ProdukModel.Response_value> data) {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerViewPropinsi.setHasFixedSize(false);
//        recyclerViewPropinsi.setLayoutManager(linearLayoutManager);
//        recyclerViewPropinsi.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recyclerViewPropinsi.setAdapter(new ListProdukAdapter(data,this,this));
//
//    }

    @Override
    public void onClickProduk(@NonNull ProdukModel.Response_value produk) {

        Intent intent = new Intent();
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("kodeProduk", produk.getProduct_code());
        intent.putExtra("namaProduk", produk.getProduct_name());
        intent.putExtra("urlProduk", produk.getProduct_url());
        setResult(RESULT_OK, intent);
        finish();
      //  overridePendingTransition(0,0);

    }

    @Override
    public boolean onClose() {
        new ListGridProdukAdapter(data, this, this, product).getFilter().filter("");
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
    protected void onDestroy() {
        shimmer_view_container.stopShimmerAnimation();
        shimmer_view_container.removeAllViews();
        recyclerViewProduk.removeAllViews();
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if (requestCode == RequestCode.ActionCode_VOICE_RECOGNITION && resultCode == RESULT_OK) {
            // onClose();
            ArrayList matches = dataIntent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayList<ProdukModel.Response_value> filteredList = new ArrayList<>();
            Log.d(TAG, "onActivityResult: " + Arrays.asList(matches));
            for (int i = 0; i < matches.size(); i++) {
                String voiceNumber = matches.get(i).toString().trim();


                for (ProdukModel.Response_value produk : data) {

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






            //  onQueryTextChange(voiceNumber.toLowerCase());

        }
    }
}
