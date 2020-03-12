package com.bm.main.single.ftl.train.models;



import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rizabudiprasetya on 8/8/17.
 */

public class TrainSeatModel extends TrainJSONModel {
    private String wagonCode;
    private int wagonNumber;
    private List<TrainSeatLayoutModel> modelLayoutList;
    private boolean is4Row = true;

    public TrainSeatModel(@NonNull JSONObject jsonObject) throws JSONException {
        wagonCode = getStringValue(jsonObject, "wagonCode");
        wagonNumber = getIntValue(jsonObject, "wagonNumber");

        modelLayoutList = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONArray("layout");
        for(int i = 0; i < jsonArray.length(); i++) {
            TrainSeatLayoutItemModel seatModelLayoutItem = new TrainSeatLayoutItemModel(jsonArray.getJSONObject(i));
            TrainSeatLayoutModel selectedSeatModelLayout = new TrainSeatLayoutModel();

            boolean isNew = true;

            for(TrainSeatLayoutModel seatModelLayout : modelLayoutList) {
                if(seatModelLayout.getRow() == seatModelLayoutItem.getRow()) {
                    selectedSeatModelLayout = seatModelLayout;
                    isNew = false;
                    break;
                }
            }

            selectedSeatModelLayout.setLayout(seatModelLayoutItem);
            if(isNew) {
                modelLayoutList.add(selectedSeatModelLayout);
            }

            if(seatModelLayoutItem.getColumn().equals("E")) {
                is4Row = false;
            }
        }
    }

    public boolean is4Row() {
        return is4Row;
    }

    public void setIs4Row(boolean is4Row) {
        this.is4Row = is4Row;
    }

    public String getWagonCode() {
        return wagonCode;
    }

    public void setWagonCode(String wagonCode) {
        this.wagonCode = wagonCode;
    }

    public int getWagonNumber() {
        return wagonNumber;
    }

    public void setWagonNumber(int wagonNumber) {
        this.wagonNumber = wagonNumber;
    }

    public List<TrainSeatLayoutModel> getModelLayoutList() {
        return modelLayoutList;
    }

    public void setModelLayoutList(List<TrainSeatLayoutModel> modelLayoutList) {
        this.modelLayoutList = modelLayoutList;
    }
}
