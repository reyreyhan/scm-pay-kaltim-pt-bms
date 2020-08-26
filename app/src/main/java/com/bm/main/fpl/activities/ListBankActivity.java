package com.bm.main.fpl.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bm.main.scm.R;
import com.bm.main.fpl.adapters.ListBankAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.BankModel;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.fpl.utils.RequestUtils;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListBankActivity extends BaseActivity implements JsonObjectResponseCallback, View.OnClickListener, ListBankAdapter.OnClickProduk {
    private static final String TAG = ListBankActivity.class.getSimpleName();
    RecyclerView recyclerViewProduk;

    @NonNull
    ArrayList<BankModel.Response_value> data = new ArrayList<>();
    BankModel bankModel;
    String product;
    SearchView searchView;
    SearchManager searchManager;
    ListBankAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bank);
        Intent intent = getIntent();
        // product=intent.getStringExtra("product");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("title"));
        init(1);
//        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = findViewById(R.id.searchView);
        recyclerViewProduk = findViewById(R.id.recyclerViewProduk);

//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setQueryHint(this.getString(R.string.hint_pilih_pdam));
//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(this);
//        searchView.setOnCloseListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewProduk.setHasFixedSize(false);
        recyclerViewProduk.setLayoutManager(linearLayoutManager);
        recyclerViewProduk.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ListBankAdapter(data, this, this);
        recyclerViewProduk.setAdapter(adapter);


        requestListBank24();
    }

    private void requestListBank24() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestBank("24JAM"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_PRODUK, this);
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
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {

//        try {
//            if (response.getString("response_code").equals("03")) {
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {

                if (actionCode == ActionCode.LIST_PRODUK) {
                    Log.d(TAG, "onSuccess: " + response.toString());
                    bankModel = gson.fromJson(response.toString(), BankModel.class);
                    data.addAll(bankModel.getResponse_value());
                    //  setData(gameModel.getData());
                    adapter.notifyDataSetChanged();
                }

//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }

    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {

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
    public void onClickProduk(@NonNull BankModel.Response_value produk) {

        Intent intent = new Intent();
        intent.putExtra("kodeBank", produk.getBank_code());
        intent.putExtra("namaBank", produk.getBank_name());
        intent.putExtra("productUrl", produk.getProduct_url());
        setResult(RESULT_OK, intent);
        finish();
        //overridePendingTransition(R.anim.popup_show,R.anim.popup_hide);

    }

//    @Override
//    public boolean onClose() {
//        new ListBankAdapter(data,this,this).getFilter().filter("");
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        adapter.getFilter().filter(query);
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        adapter.getFilter().filter(newText);
//        return true;
//    }


    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }
}
