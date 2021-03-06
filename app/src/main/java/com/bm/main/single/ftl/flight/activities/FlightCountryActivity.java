package com.bm.main.single.ftl.flight.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.adapters.ListCountryAdapter;
import com.bm.main.single.ftl.models.Country;
import com.bm.main.single.ftl.utils.CountryComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class FlightCountryActivity extends BaseActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, ListCountryAdapter.OnClickCountry{

    private static final String TAG =FlightCountryActivity.class.getSimpleName() ;
    ListCountryAdapter adapter;
   // List<Country> itemList;//=new ArrayList<>();
    //    ListView routeListView;
    //  private boolean fadeHeader = true;
//    private StickyListHeadersListView stickyList;
    private RecyclerView recyclerViewAirport;
  //  private ShimmerFrameLayout mShimmerViewContainer;
    SearchView searchView;
    SearchManager searchManager ;
   // private JSONArray airPortJson;

//    Country flightAirportModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_country_activity);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //   final android.widget.SearchView searchView = (android.widget.SearchView)findViewById(R.id.searchView);
        Button buttonClonse = findViewById(R.id.buttonClose);
//        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        //  shimmer.selectPreset(0, mShimmerViewContainer);
//        linMain = findViewById(R.id.linMain);
//        layout_data_empty = findViewById(R.id.layout_data_empty);
//        txtHeader = layout_data_empty.findViewById(R.id.txtHeader);
//        txtSub = layout_data_empty.findViewById(R.id.txtSub);
//        layout_no_connection = findViewById(R.id.layout_no_connection);

        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Cari Negara");
        // searchView.setQueryHint(Html.fromHtml("<font color = #BDBDBD size=1>" + getResources().getString(R.string.cari_bandara) + "</font>"));

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setFocusable(false);

//        AutoCompleteTextView search_text = (AutoCompleteTextView) searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
//        search_text.setTextColor(getResources().getColor(R.color.md_black_1000,null));
//        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.textNormal));
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(14);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);
        getAllCountry();
        recyclerViewAirport = findViewById(R.id.recyclerViewAirport);
        buttonClonse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
//        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (recyclerViewAirport.getVisibility() == View.VISIBLE) {
//                    recyclerViewAirport.setVisibility(View.GONE);
//                }
//                if (layout_no_connection.getVisibility() == View.VISIBLE) {
//                    layout_no_connection.setVisibility(View.GONE);
//                }
//                if (linMain.getVisibility() == View.GONE) {
//                    linMain.setVisibility(View.VISIBLE);
//
//                }
//                if (mShimmerViewContainer.getVisibility() == View.GONE) {
//                    mShimmerViewContainer.setVisibility(View.VISIBLE);
//                    mShimmerViewContainer.startShimmerAnimation();
//
//                }
//                RequestAirport();
//            }
//        });






        // adapter.cityHeader.compare()

//         recyclerViewAirport.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

//        recyclerViewAirport.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        recyclerViewAirport.setVerticalScrollBarEnabled(false);
//        recyclerViewAirport.setFastScrollAlwaysVisible(false);
//        recyclerViewAirport.setAreHeadersSticky(true);
//
//        recyclerViewAirport.setOnStickyHeaderChangedListener(this);
//        recyclerViewAirport.setOnStickyHeaderOffsetChangedListener(this);

        //   fadeHeader = true;

//        recyclerViewAirport.setDrawingListUnderStickyHeader(true);
//        recyclerViewAirport.setAreHeadersSticky(true);
//        recyclerViewAirport.setFastScrollEnabled(true);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerViewAirport.setHasFixedSize(false);
//        recyclerViewAirport.setLayoutManager(linearLayoutManager);
//        RecyclerSectionItemDecoration sectionItemDecoration =
//                new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),true,getSectionCallback(itemList));
//        recyclerViewAirport.addItemDecoration(sectionItemDecoration);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewAirport.setHasFixedSize(false);
        recyclerViewAirport.setLayoutManager(linearLayoutManager);
        recyclerViewAirport.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        Collections.sort(countries, new CountryComparator());
        adapter = new ListCountryAdapter(this,countries ,this);
        recyclerViewAirport.setAdapter(adapter);
//        recyclerViewAirport.setStickyHeaderTopOffset(-5);
        // recyclerViewAirport.addItemDecoration(new StickHeaderItemDecoration(adapter));
//        if (PreferenceClass.getJSONObject(FlightKeyPreference.airPortListData).length() > 0) {
//            if (layout_no_connection.getVisibility() == View.VISIBLE) {
//                layout_no_connection.setVisibility(View.GONE);
//            }
//            if (layout_data_empty.getVisibility() == View.VISIBLE) {
//                layout_data_empty.setVisibility(View.GONE);
//            }
//            if (linMain.getVisibility() == View.GONE) {
//                linMain.setVisibility(View.VISIBLE);
//            }
//            if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
//                mShimmerViewContainer.setVisibility(View.GONE);
//                mShimmerViewContainer.stopShimmerAnimation();
//            }
//            flightAirportModel = gson.fromJson(PreferenceClass.getJSONObject(FlightKeyPreference.airPortListData).toString(), FlightAirportModel.class);
//
//            itemList.clear();
//            itemList.addAll(flightAirportModel.getData());

            //  setData(gameModel.getData());
            adapter.notifyDataSetChanged();
          //  recyclerViewAirport.setVisibility(View.VISIBLE);
//        }


//        RequestAirport();

    }
//    private void RequestAirport() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("token", PreferenceClass.getToken());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //Log.d(TAG, "RequestAirport: " + jsonObject);
//        RequestUtilsTravel.transportWithProgressResponse(FlightAirPortActivity.this, FlightPath.AIRPORTLIST, jsonObject, TravelActionCode.AIRPORTLIST,this);
//    }
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

//    @Override
//    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l, View header, int offset) {
//        if (fadeHeader) {
//            header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
//        }
//    }
//
//    @Override
//    public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition, long headerId) {
//        header.setAlpha(1);
//
//    }

    protected void searchAction(String query) {


        adapter.getFilter().filter(query);
    }

    @Override
    public void onResume() {
        super.onResume();
    //    mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
     //   mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
      //  mShimmerViewContainer.stopShimmerAnimation();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }

    @Override
    public void onClickCountry(@NonNull Country airport) {
        closeKeyboard(this);
        Intent i = new Intent();
        i.putExtra("countryKode", airport.getCode());
        i.putExtra("countryFlag", airport.getFlag());
        i.putExtra("countryNama", airport.getName());
        setResult(Activity.RESULT_OK, i);
        finish();
        overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
    }

//    @Override
//    public void onSuccess(int actionCode, JSONObject response) {
//
//        if (layout_no_connection.getVisibility() == View.VISIBLE) {
//            layout_no_connection.setVisibility(View.GONE);
//        }
//        if (linMain.getVisibility() == View.GONE) {
//            linMain.setVisibility(View.VISIBLE);
//        }
//        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
//            mShimmerViewContainer.setVisibility(View.GONE);
//            mShimmerViewContainer.stopShimmerAnimation();
//        }
//
//        if (actionCode == TravelActionCode.AIRPORTLIST) {
//
//            flightAirportModel = gson.fromJson(response.toString(), FlightAirportModel.class);
//            // data.clear();
//            if (flightAirportModel.getRc().equals(ResponseCode.SUCCESS)) {
//
////                data.addAll(produkModel.getData());
////                //  setData(gameModel.getData());
////                adapter.notifyDataSetChanged();
//                // stop animating Shimmer and hide the layout
//                JSONObject obj = PreferenceClass.getJSONObject(FlightKeyPreference.airPortListData);
//
//                JSONArray array = new JSONArray();
//
//                try {
//
//                    array = obj.getJSONArray("data");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                if (array.length() != flightAirportModel.getData().size()) {
//                    Log.d(TAG, "onSuccess: "+response.toString());
//                    itemList.clear();
//
//                    PreferenceClass.putJSONObject(FlightKeyPreference.airPortListData, response);
//                    itemList.addAll(flightAirportModel.getData());
//                    Collections.sort(itemList);
//                    adapter.notifyDataSetChanged();
//                }
//                recyclerViewAirport.setVisibility(View.VISIBLE);
////                if (flightAirportModel.getData().size() == 0) {
////                    layout_data_empty.setVisibility(View.VISIBLE);
////                    txtHeader.setText("Data Airport");
////                    txtSub.setText("Tidak Ditemukan");
////                    recyclerViewAirport.setVisibility(View.GONE);
////                }
//            } else {
//                layout_data_empty.setVisibility(View.VISIBLE);
//                txtHeader.setText("Data Airport");
//                txtSub.setText("Tidak Ditemukan");
//            }
//        }
//
//    }
//
//    @Override
//    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
//
//        if (responseCode.equals(ResponseCode.NETWORK_ERROR)) {
//            if (actionCode == TravelActionCode.AIRPORTLIST) {
//                if (PreferenceClass.getJSONObject(FlightKeyPreference.airPortListData).length() > 0) {
//
//                } else {
//                    mShimmerViewContainer.stopShimmerAnimation();
//                    mShimmerViewContainer.setVisibility(View.GONE);
//                    linMain.setVisibility(View.GONE);
//                    layout_data_empty.setVisibility(View.GONE);
//                    layout_no_connection.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//
//    }

//    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<FlightAirportModel.Data> airport) {
//        return new RecyclerSectionItemDecoration.SectionCallback() {
//            @Override
//            public boolean isSection(int position) {
//                Log.d(TAG, "isSection: "+airport.get(position).getGroup());
////                String[] strArrayGroup = airport.get(position).getGroup().split(" ");
////                StringBuilder builderGroup = new StringBuilder();
////                for (String s : strArrayGroup) {
////                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
////                    builderGroup.append(cap + " ");
////                }
////
////
////                String[] strArrayGroup1 = airport.get(position - 1).getGroup().split(" ");
////                StringBuilder builderGroup1 = new StringBuilder();
////                for (String s : strArrayGroup1) {
////                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
////                    builderGroup1.append(cap + " ");
////                }
//                // return position == 0|| !airport.get(position).getGroup().toUpperCase().equals(airport.get(position - 1).getGroup().toUpperCase());
//                return position == 0|| !airport.get(position).getGroup().equals(airport.get(position - 1).getGroup());
//                //  return position == 0|| !builderGroup.toString().equals(builderGroup1.toString());
//            }
//
//            @Override
//            public CharSequence getSectionHeader(int position) {
////                String[] strArrayGroup = airport.get(position).getGroup().split(" ");
////                StringBuilder builderGroup = new StringBuilder();
////                for (String s : strArrayGroup) {
////                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
////                    builderGroup.append(cap + " ");
////                }
//                // return airport.get(position).getGroup().toUpperCase();
//                return airport.get(position).getGroup();
//                // return builderGroup.toString();
//            }
//        };
//    }

    @NonNull
    public ArrayList<Country> countries = new ArrayList<>();
    public void getAllCountry(){


        // Get ISO countries, create Country object and
        // store in the collection.
        String[] isoCountries = Locale.getISOCountries();
        for (String country : isoCountries) {
            Locale locale = new Locale("id", country);
            String iso = locale.getISO3Country();
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();
            String flag = "https://www.countryflags.io/"+locale.getCountry()+"/shiny/64.png";

            if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                countries.add(new Country(iso, code, name,flag));
            }
        }

        // Sort the country by their name and then display the content
        // of countries collection object.
        Collections.sort(countries, new CountryComparator());

        for (Country country : countries) {
            System.out.println(country.getCode()+" "+country.getName());
        }
    }
}
