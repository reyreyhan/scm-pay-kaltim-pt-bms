package com.bm.main.single.ftl.ship.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.ship.adapters.ShipPortAdapter;
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;
import com.bm.main.single.ftl.ship.constants.ShipPath;
import com.bm.main.single.ftl.ship.models.ShipDestinationModel;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ShipPortActivity extends BaseActivity implements ProgressResponseCallback, ShipPortAdapter.OnClickPort, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String TAG =ShipPortActivity.class.getSimpleName() ;

    ShipPortAdapter adapter;
    @NonNull
    ArrayList<ShipDestinationModel.Data> itemList=new ArrayList<>();

    private ShimmerFrameLayout mShimmerViewContainer;
    SearchView searchView;
    SearchManager searchManager ;
    private JSONArray portJson;

    ShipDestinationModel shipDestinationModel;
    private LinearLayout layout_no_connection,linMain;
    private LinearLayout layout_data_empty;

RecyclerView recyclerViewPort;
    TextView txtHeader, txtSub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_port_activity);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //   final android.widget.SearchView searchView = (android.widget.SearchView)findViewById(R.id.searchView);
        Button buttonClose = findViewById(R.id.buttonClose);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        //  shimmer.selectPreset(0, mShimmerViewContainer);
        linMain = findViewById(R.id.linMain);
        layout_data_empty = findViewById(R.id.layout_data_empty);
        txtHeader = layout_data_empty.findViewById(R.id.txtHeader);
        txtSub = layout_data_empty.findViewById(R.id.txtSub);
        layout_no_connection = findViewById(R.id.layout_no_connection);

        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(this.getString(R.string.cari_pelabuhan));
        // searchView.setQueryHint(Html.fromHtml("<font color = #BDBDBD size=1>" + getResources().getString(R.string.cari_bandara) + "</font>"));

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setFocusable(false);

//        AutoCompleteTextView search_text = (AutoCompleteTextView) searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
//        search_text.setTextColor(getResources().getColor(R.color.md_black_1000,null));
//        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.textNormal));

        recyclerViewPort = findViewById(R.id.recyclerViewPort);
//        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        searchAutoComplete.setTypeface(tfLight);
//        searchAutoComplete.setTextSize(14);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewPort.getVisibility() == View.VISIBLE) {
                    recyclerViewPort.setVisibility(View.GONE);
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
                RequestPort();
            }
        });




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPort.setHasFixedSize(false);
        recyclerViewPort.setLayoutManager(linearLayoutManager);
        recyclerViewPort.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ShipPortAdapter(itemList , this,this);
        recyclerViewPort.setAdapter(adapter);
//        recyclerViewAirport.setStickyHeaderTopOffset(-5);
        // recyclerViewAirport.addItemDecoration(new StickHeaderItemDecoration(adapter));
        if (PreferenceClass.getJSONObject(ShipKeyPreference.portListData).length() > 0) {
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
            shipDestinationModel = gson.fromJson(PreferenceClass.getJSONObject(ShipKeyPreference.portListData).toString(), ShipDestinationModel.class);

            itemList.clear();
            itemList.addAll(shipDestinationModel.getData());
            Collections.sort(itemList);
            //  setData(gameModel.getData());
            adapter.notifyDataSetChanged();
            recyclerViewPort.setVisibility(View.VISIBLE);
        }


        RequestPort();


        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(14);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);
    }
    private void RequestPort() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d(TAG, "RequestAirport: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponse(ShipPortActivity.this, ShipPath.PORTLIST, jsonObject, TravelActionCode.PORTLIST,this);
    }



    @Override
    public boolean onClose() {
        adapter.getFilter().filter("");
        return false;

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchAction(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        searchAction(s);
        return false;
    }

    protected void searchAction(String query) {


        adapter.getFilter().filter(query);
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
    @Override
    public void onClickPort(@NonNull ShipDestinationModel.Data port) {
    closeKeyboard(this);
    Intent i = new Intent();
        i.putExtra("pelabuhanNama", port.getNama());
        i.putExtra("pelabuhanKode", port.getCode());
//        i.putExtra("airportNama", airport.getBandara());
    setResult(Activity.RESULT_OK, i);
    finish();
    overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
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
            mShimmerViewContainer.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmerAnimation();
        }

        if (actionCode == TravelActionCode.PORTLIST) {

            shipDestinationModel = gson.fromJson(response.toString(), ShipDestinationModel.class);
            // data.clear();
            if (shipDestinationModel.getRc().equals(ResponseCode.SUCCESS)) {

//                data.addAll(produkModel.getData());
//                //  setData(gameModel.getData());
//                adapter.notifyDataSetChanged();
                // stop animating Shimmer and hide the layout
                JSONObject obj = PreferenceClass.getJSONObject(ShipKeyPreference.portListData);

                JSONArray array = new JSONArray();

                try {

                    array = obj.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (array.length() != shipDestinationModel.getData().size()) {
                    Log.d(TAG, "onSuccess: "+response.toString());
                    itemList.clear();

                    PreferenceClass.putJSONObject(ShipKeyPreference.portListData, response);
                    itemList.addAll(shipDestinationModel.getData());
                    Collections.sort(itemList);
                    adapter.notifyDataSetChanged();
                }
                recyclerViewPort.setVisibility(View.VISIBLE);
//                if (flightAirportModel.getData().size() == 0) {
//                    layout_data_empty.setVisibility(View.VISIBLE);
//                    txtHeader.setText("Data Airport");
//                    txtSub.setText("Tidak Ditemukan");
//                    recyclerViewAirport.setVisibility(View.GONE);
//                }
            } else {
                layout_data_empty.setVisibility(View.VISIBLE);
                txtHeader.setText("Data Pelabuhan");
                txtSub.setText("Tidak Ditemukan");
            }
        }

    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {

        if (responseCode.equals(ResponseCode.NETWORK_ERROR)) {
            if (actionCode == TravelActionCode.PORTLIST) {
                if (PreferenceClass.getJSONObject(ShipKeyPreference.portListData).length() > 0) {

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
}
