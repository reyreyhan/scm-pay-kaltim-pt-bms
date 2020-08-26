package com.bm.main.single.ftl.train.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.templates.stickylistheaders.StickyListHeadersListView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.train.adapters.TrainStationAdapter;
import com.bm.main.single.ftl.train.constants.TrainKeyPreference;
import com.bm.main.single.ftl.train.constants.TrainPath;
import com.bm.main.single.ftl.train.models.TrainStationModel;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainStationActivity extends BaseActivity implements StickyListHeadersListView.OnStickyHeaderOffsetChangedListener, StickyListHeadersListView.OnStickyHeaderChangedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener,  ProgressResponseCallback, TrainStationAdapter.OnClickStation {

    private static final String TAG = TrainStationActivity.class.getSimpleName();

    TrainStationAdapter adapter;
    @NonNull
    List<TrainStationModel.Data> itemList=new ArrayList<>();
//    private RecyclerView recyclerViewStation;
    private StickyListHeadersListView stickyList;
    private ShimmerFrameLayout mShimmerViewContainer;
    SearchView searchView;
    SearchManager searchManager ;

    TrainStationModel trainStationModel;

    private LinearLayout layout_no_connection,linMain;
    private LinearLayout layout_data_empty;

    TextView txtHeader, txtSub;

    private boolean fadeHeader = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_station_activity);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        Button buttonClonse = findViewById(R.id.buttonClose);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        //  shimmer.selectPreset(0, mShimmerViewContainer);
        linMain = findViewById(R.id.linMain);
        layout_data_empty = findViewById(R.id.layout_data_empty);
        txtHeader = layout_data_empty.findViewById(R.id.txtHeader);
        txtSub = layout_data_empty.findViewById(R.id.txtSub);
        layout_no_connection = findViewById(R.id.layout_no_connection);

        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(this.getString(R.string.cari_station));
        // searchView.setQueryHint(Html.fromHtml("<font color = #BDBDBD size=1>" + getResources().getString(R.string.cari_bandara) + "</font>"));

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setFocusable(false);

        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(14);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);

        adapter = new TrainStationAdapter(itemList , this,this);
        stickyList = findViewById(R.id.recyclerViewStation);

        stickyList.setVerticalScrollBarEnabled(false);
        stickyList.setFastScrollAlwaysVisible(false);
        stickyList.setAreHeadersSticky(true);

        stickyList.setOnStickyHeaderChangedListener(this);
        stickyList.setOnStickyHeaderOffsetChangedListener(this);

        fadeHeader = true;

        stickyList.setDrawingListUnderStickyHeader(true);
        stickyList.setAreHeadersSticky(true);
        stickyList.setFastScrollEnabled(true);

        stickyList.setAdapter(adapter);
        stickyList.setStickyHeaderTopOffset(-5);
        buttonClonse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stickyList.getVisibility() == View.VISIBLE) {
                    stickyList.setVisibility(View.GONE);
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
                RequestStation();
            }
        });


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerViewStation.setHasFixedSize(false);
//        recyclerViewStation.setLayoutManager(linearLayoutManager);
//        RecyclerSectionItemDecoration sectionItemDecoration =
//                new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),true,getSectionCallback(itemList));
//        recyclerViewStation.addItemDecoration(sectionItemDecoration);
//        adapter = new TrainStationAdapter(itemList , this,this);
//        recyclerViewStation.setAdapter(adapter);
//        recyclerViewAirport.setStickyHeaderTopOffset(-5);
        // recyclerViewAirport.addItemDecoration(new StickHeaderItemDecoration(adapter));
        if (PreferenceClass.getJSONObject(TrainKeyPreference.stationListData).length() > 0) {
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
            trainStationModel = gson.fromJson(PreferenceClass.getJSONObject(TrainKeyPreference.stationListData).toString(), TrainStationModel.class);

            itemList.clear();
            itemList.addAll(trainStationModel.getData());
            Collections.sort(itemList);
            //  setData(gameModel.getData());
            adapter.notifyDataSetChanged();
            stickyList.setVisibility(View.VISIBLE);
        }


        RequestStation();
    }

    @Override
    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l, @NonNull View header, int offset) {
        if (fadeHeader) {
            header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
        }
    }

    @Override
    public void onStickyHeaderChanged(StickyListHeadersListView l, @NonNull View header, int itemPosition, long headerId) {
        header.setAlpha(1);
    }

    private void RequestStation() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "RequestStation: "+jsonObject.toString());
        RequestUtilsTravel.transportWithProgressResponse(TrainStationActivity.this, TrainPath.STATIONLIST, jsonObject, TravelActionCode.STATIONLIST,this);
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
    public void onClickStation(@NonNull TrainStationModel.Data station) {
        closeKeyboard(this);
        Intent i = new Intent();

        String[] strArrayKota = station.getNama_kota().split(" ");
        StringBuilder builderKota = new StringBuilder();
        for (String s : strArrayKota) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            builderKota.append(cap + " ");
        }

        String[] strArrayStation = station.getNama_stasiun().split(" ");
        StringBuilder builderStation = new StringBuilder();
        for (String s : strArrayStation) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            builderStation.append(cap + " ");
        }

        i.putExtra("stationKode", station.getId_stasiun());
        i.putExtra("stationKota", builderKota.toString());
        i.putExtra("stationNama", builderStation.toString());
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

        if (actionCode == TravelActionCode.STATIONLIST) {

            trainStationModel = gson.fromJson(response.toString(), TrainStationModel.class);
            // data.clear();
            if (trainStationModel.getRc().equals(ResponseCode.SUCCESS)) {

//                data.addAll(produkModel.getData());
//                //  setData(gameModel.getData());
//                adapter.notifyDataSetChanged();
                // stop animating Shimmer and hide the layout
                JSONObject obj = PreferenceClass.getJSONObject(TrainKeyPreference.stationListData);

                JSONArray array = new JSONArray();

                try {

                    array = obj.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (array.length() != trainStationModel.getData().size()) {
                    Log.d(TAG, "onSuccess: "+response.toString());
                    itemList.clear();

                    PreferenceClass.putJSONObject(TrainKeyPreference.stationListData, response);
                    itemList.addAll(trainStationModel.getData());
                    Collections.sort(itemList);
                    adapter.notifyDataSetChanged();
                }
                stickyList.setVisibility(View.VISIBLE);
//                if (flightAirportModel.getData().size() == 0) {
//                    layout_data_empty.setVisibility(View.VISIBLE);
//                    txtHeader.setText("Data Airport");
//                    txtSub.setText("Tidak Ditemukan");
//                    recyclerViewAirport.setVisibility(View.GONE);
//                }
            } else {
                layout_data_empty.setVisibility(View.VISIBLE);
                txtHeader.setText("Data Station");
                txtSub.setText("Tidak Ditemukan");
            }
        }

    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {

        if (responseCode.equals(ResponseCode.NETWORK_ERROR)) {
            if (actionCode == TravelActionCode.STATIONLIST) {
                if (PreferenceClass.getJSONObject(TrainKeyPreference.stationListData).length() > 0) {

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



//    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<TrainStationModel.Data> station) {
//        return new RecyclerSectionItemDecoration.SectionCallback() {
//            @Override
//            public boolean isSection(int position) {
//                Log.d(TAG, "isSection: "+station.get(position).getNama_kota()+" "+station.get(position).getNama_stasiun());
////                String[] strArrayGroup = station.get(position).getNama_kota().split(" ");
////                StringBuilder builderGroup = new StringBuilder();
////                for (String s : strArrayGroup) {
////                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
////                    builderGroup.append(cap + " ");
////                }
////
////
////                String[] strArrayGroup1 = station.get(position - 1).getNama_kota().split(" ");
////                StringBuilder builderGroup1 = new StringBuilder();
////                for (String s : strArrayGroup1) {
////                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
////                    builderGroup1.append(cap + " ");
////                }
//                // return position == 0|| !airport.get(position).getGroup().toUpperCase().equals(airport.get(position - 1).getGroup().toUpperCase());
//                return position == 0|| !station.get(position).getNama_kota().equals(station.get(position - 1).getNama_kota());
////                  return position == 0|| !builderGroup.toString().equals(builderGroup1.toString());
//            }
//
//            @Override
//            public CharSequence getSectionHeader(int position) {
//                String[] strArrayGroup = station.get(position).getNama_kota().split(" ");
//                StringBuilder builderGroup = new StringBuilder();
//                for (String s : strArrayGroup) {
//                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//                    builderGroup.append(cap + " ");
//                }
//                // return airport.get(position).getGroup().toUpperCase();
//                //return station.get(position).builderGroup;
//                 return builderGroup.toString();
//            }
//        };
//    }
}
