package com.bm.main.single.ftl.flight.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.flight.adapters.FlightBaggageAdapter;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.models.FlightBaggageDetailModel;
import com.bm.main.single.ftl.flight.models.FlightBaggageModel;
import com.bm.main.single.ftl.flight.models.SectionDataPassagerModel;
import com.bm.main.single.ftl.flight.models.SingleItemBaggageModel;
import com.bm.main.single.ftl.utils.MemoryStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlightBaggageActivity extends BaseActivity {

    int countAdult = 1, countChild = 0, allCount = 1;

    //    ArrayList<FlightBaggageModel.Data> flightBaggageModels;
//    FlightBaggageModel flightBaggageModel;
    String[] adultPassenger;
    String[] childPassenger;

    //    FlightSubBaggageAdapter adapter;
    RecyclerView my_recycler_view;

    FlightBaggageAdapter flightBaggageAdapter;
    @NonNull
    ArrayList<FlightBaggageDetailModel> data = new ArrayList<>();
//    ArrayList<SectionDataPassagerModel> data = new ArrayList<>();
//    FlightBaggageDetailModel flightBaggageDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_baggage_activity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bagasi");
        init(1);
        Intent intent = this.getIntent();
        if (intent != null) {
            adultPassenger = intent.getStringArrayExtra("adultPassanger");
            childPassenger = intent.getStringArrayExtra("childPassanger");
        }

        countAdult = PreferenceClass.getInt(FlightKeyPreference.countAdultFlight, 1);
        countChild = PreferenceClass.getInt(FlightKeyPreference.countChildFlight, 0);
        allCount = countAdult + countChild;
        // allSectionDataPassagerModels = new ArrayList<SectionDataPassagerModel>();
        data = new ArrayList<FlightBaggageDetailModel>();
//        data = new ArrayList<SectionDataPassagerModel>();

        showData();
       // showDataBaggage();
        my_recycler_view = findViewById(R.id.baggage_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        //adapter = new FlightSubBaggageAdapter(this, allSectionDataPassagerModels);
        flightBaggageAdapter = new FlightBaggageAdapter(this, data);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        my_recycler_view.setAdapter(adapter);
        my_recycler_view.setAdapter(flightBaggageAdapter);


//        if (produkBank != null) {
//            produkBank = null;
//            //  adapterBank.clearSelections();
//            if (0 != RecyclerView.NO_POSITION) {
//                if (FlightSubBaggageAdapter.ItemRowHolder.listener != null) {
//                    FlightSubBaggageAdapter.clearSelections();
//                    FlightSubBaggageAdapter.toggleSelection(PreferenceClass.getInt("deposit_bank_non",0));
//
//                    FlightSubBaggageAdapter.listener.onClickProduk(bankModel.getResponse_value().get(PreferenceClass.getInt("deposit_bank_non",0)), PreferenceClass.getInt("deposit_bank_non",0));
//                }
//            }
//        }

//flightBaggageAdapter.flightForm_adult_sectionBaggageAdapter.itemListDataAdapter.SingleItemRowHolder
    }

    private void showData() {
        JSONArray detailTitleArr = PreferenceClass.getJSONArray(FlightKeyPreference.detailTitle);
        List<FlightBaggageModel.Data> itemList = (List<FlightBaggageModel.Data>) MemoryStore.get(FlightKeyPreference.baggage);
//        JSONArray jsonArrayAdults = new JSONArray();
//        JSONArray jsonArrayChilds = new JSONArray();

        for (int i = 0; i < detailTitleArr.length(); i++) {
            FlightBaggageDetailModel flightBaggageDetailModel = new FlightBaggageDetailModel();
            Log.d("BAGGAGE", "showData: "+i);
//            data.add(flightBaggageDetailModel);
            try {
                JSONObject data = detailTitleArr.getJSONObject(i);

                flightBaggageDetailModel.setFlightIcon(data.getString("flightIcon"));
                flightBaggageDetailModel.setFlightName(data.getString("flightName"));
                flightBaggageDetailModel.setFlightCode(data.getString("flightCode"));
                flightBaggageDetailModel.setOrigin(data.getString("origin"));
                flightBaggageDetailModel.setOriginName(data.getString("originName"));
                flightBaggageDetailModel.setDestination(data.getString("destination"));
                flightBaggageDetailModel.setDestinationName(data.getString("destinationName"));
                flightBaggageDetailModel.setDurationDetail(data.getString("durationDetail"));
                flightBaggageDetailModel.setTransitTime(data.getString("transitTime"));
                flightBaggageDetailModel.setArrival(data.getString("arrival"));
                flightBaggageDetailModel.setDepart(data.getString("depart"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<SectionDataPassagerModel> allSectionDataPassagerModels = new ArrayList<>();
            for (String adult : adultPassenger) {
//                ArrayList<SectionDataPassagerModel> allSectionDataPassagerModels = new ArrayList<>();
                //  jsonArrayAdults.put(adult);

                SectionDataPassagerModel dm = new SectionDataPassagerModel();

                String[] arrChild = adult.split(";");
                dm.setHeaderTitle(arrChild[1] + " " + arrChild[2] + " " + arrChild[3]);
                Log.d("BAGGAGE", "showData: adultPassanger "+arrChild[1] + " " + arrChild[2] + " " + arrChild[3]);
                ArrayList<SingleItemBaggageModel> singleItem = new ArrayList<>();
                singleItem.add(new SingleItemBaggageModel("0", "0", "No Baggage",flightBaggageDetailModel.getFlightCode(),true));
                for (int j = 0; j < itemList.size(); j++) {
                    String[] arrA = itemList.get(j).getBaggage_key().split("\\|");
                    Log.d("BAGGAGE", "showData: "+arrA[1]);
                    try {
                        if (arrA[1].equals(detailTitleArr.getJSONObject(i).getString("origin") + "-" + detailTitleArr.getJSONObject(i).getString("destination"))){
                            singleItem.add(new SingleItemBaggageModel(itemList.get(j).getPrice(), itemList.get(j).getWeight(), itemList.get(j).getBaggage_key(),flightBaggageDetailModel.getFlightCode(),false));
                        Log.d("BAGGAGE", itemList.get(j).getPrice() + " " + itemList.get(j).getWeight() + " " + itemList.get(j).getBaggage_key()+" "+flightBaggageDetailModel.getFlightCode());
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                dm.setAllItemsInSection(singleItem);

                allSectionDataPassagerModels.add(dm);

                flightBaggageDetailModel.setSectionDataPassagerModels(allSectionDataPassagerModels);
               // data.add(flightBaggageDetailModel);
            }


            if (childPassenger.length > 0) {
                for (String child : childPassenger) {
                //    ArrayList<SectionDataPassagerModel> allSectionDataPassagerModels = new ArrayList<>();
                    // jsonArrayChilds.put(child);
                    SectionDataPassagerModel dm = new SectionDataPassagerModel();
                    String[] arrChild = child.split(";");
                    dm.setHeaderTitle(arrChild[1] + " " + arrChild[2] + " " + arrChild[3]);

                    ArrayList<SingleItemBaggageModel> singleItem = new ArrayList<>();
                    singleItem.add(new SingleItemBaggageModel("0", "0", "No Baggage",flightBaggageDetailModel.getFlightCode(),true));
                    for (int j = 0; j < itemList.size(); j++) {

                        String[] arrA = itemList.get(j).getBaggage_key().split("|");
                        try {
                            if (arrA[1].equals(detailTitleArr.getJSONObject(i).getString("origin") + "-" + detailTitleArr.getJSONObject(i).getString("destination"))){
                        singleItem.add(new SingleItemBaggageModel(itemList.get(j).getPrice(), itemList.get(j).getWeight(), itemList.get(j).getBaggage_key(),flightBaggageDetailModel.getFlightCode(),false));
                                Log.d("BAGGAGE CHILD", itemList.get(j).getPrice() + " " + itemList.get(j).getWeight() + " " + itemList.get(j).getBaggage_key()+" "+flightBaggageDetailModel.getFlightCode());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    dm.setAllItemsInSection(singleItem);

                    allSectionDataPassagerModels.add(dm);

                    flightBaggageDetailModel.setSectionDataPassagerModels(allSectionDataPassagerModels);
                }

            }




           // flightBaggageDetailModel.setSectionDataPassagerModels(allSectionDataPassagerModels);
            data.add(flightBaggageDetailModel);

        }


//        for (int i = 1; i <= jsonArrayAdults.length(); i++) {
//
//           // x=jsonArrayAdults.get(i).toString().split(";");
//            SectionDataPassagerModel dm = new SectionDataPassagerModel();
//
//            dm.setHeaderTitle(.);
//
//            ArrayList<SingleItemBaggageModel> singleItem = new ArrayList<>();
//            for (int j = 0; j <= flightBaggageModels.size(); j++) {
//                singleItem.add(new SingleItemBaggageModel(flightBaggageModels.get(j).getPrice(),flightBaggageModels.get(j).getWeight(),flightBaggageModels.get(j).getBaggage_key()));
//            }
//
//            dm.setAllItemsInSection(singleItem);
//
//            allSectionDataPassagerModels.add(dm);
//
//        }
    }


//    private void showDataBaggage(){
//        JSONArray detailTitleArr = PreferenceClass.getJSONArray(FlightKeyPreference.detailTitle);
//        List<FlightBaggageModel.Data> itemList = (List<FlightBaggageModel.Data>) MemoryStore.get(FlightKeyPreference.baggage);
//        for (String adult : adultPassanger) {
//
//            SectionDataPassagerModel dm = new SectionDataPassagerModel();
//
//            String[] arrChild = adult.split(";");
//            dm.setHeaderTitle(arrChild[1] + " " + arrChild[2] + " " + arrChild[3]);
//
//            ArrayList<SectionDataPassagerModel> allSectionDataPassagerModels = new ArrayList<>();
//
//            for (int i = 0; i < detailTitleArr.length(); i++) {
//                FlightBaggageDetailModel flightBaggageDetailModel =new FlightBaggageDetailModel();
//             //  flightBaggageDetailModel = new FlightBaggageDetailModel();
//                Log.d("BAGGAGE", "showData: " + i);
////            data.add(flightBaggageDetailModel);
//                try {
//                    JSONObject data = detailTitleArr.getJSONObject(i);
//
//                    flightBaggageDetailModel.setFlightIcon(data.getString("flightIcon"));
//                    flightBaggageDetailModel.setFlightName(data.getString("flightName"));
//                    flightBaggageDetailModel.setFlightCode(data.getString("flightCode"));
//                    flightBaggageDetailModel.setOrigin(data.getString("origin"));
//                    flightBaggageDetailModel.setOriginName(data.getString("originName"));
//                    flightBaggageDetailModel.setDestination(data.getString("destination"));
//                    flightBaggageDetailModel.setDestinationName(data.getString("destinationName"));
//                    flightBaggageDetailModel.setDurationDetail(data.getString("durationDetail"));
//                    flightBaggageDetailModel.setTransitTime(data.getString("transitTime"));
//                    flightBaggageDetailModel.setArrival(data.getString("arrival"));
//                    flightBaggageDetailModel.setDepart(data.getString("depart"));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                ArrayList<SingleItemBaggageModel> singleItem = new ArrayList<>();
//                singleItem.add(new SingleItemBaggageModel("0", "0", "No Baggage"));
//                for (int j = 0; j < itemList.size(); j++) {
//                    String[] arrA = itemList.get(j).getBaggage_key().split("\\|");
//                    Log.d("BAGGAGE", "showData: "+arrA[1]);
//                    try {
//                        if (arrA[1].equals(detailTitleArr.getJSONObject(i).getString("origin") + "-" + detailTitleArr.getJSONObject(i).getString("destination"))){
//                            singleItem.add(new SingleItemBaggageModel(itemList.get(j).getPrice(), itemList.get(j).getWeight(), itemList.get(j).getBaggage_key()));
//                            Log.d("BAGGAGE", itemList.get(j).getPrice() + " " + itemList.get(j).getWeight() + " " + itemList.get(j).getBaggage_key());
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    dm.setAllItemsInSection(singleItem);
//                }
//
//                flightBaggageDetailModel.setSectionDataPassagerModels(allSectionDataPassagerModels);
//
//                allSectionDataPassagerModels.add(dm);
//
//
//            }
//            data.add(dm);
//        }
//
//    }
}
