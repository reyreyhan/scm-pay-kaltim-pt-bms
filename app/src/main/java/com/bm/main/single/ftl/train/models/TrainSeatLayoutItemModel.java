package com.bm.main.single.ftl.train.models;



import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rizabudiprasetya on 8/8/17.
 */

public class TrainSeatLayoutItemModel extends TrainJSONModel {
    private String column, kelas;
    private int row;
    private boolean isFillled;

    public TrainSeatLayoutItemModel(@NonNull JSONObject jsonObject) throws JSONException {
        column = getStringValue(jsonObject, "column");
        row = getIntValue(jsonObject, "row");
        kelas = getStringValue(jsonObject, "class");
        isFillled = getIntValue(jsonObject, "isFilled") == 1;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isFillled() {
        return isFillled;
    }

    public void setFillled(boolean fillled) {
        isFillled = fillled;
    }
}
