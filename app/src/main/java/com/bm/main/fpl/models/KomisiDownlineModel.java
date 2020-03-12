package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

public class KomisiDownlineModel extends BaseObject {





        @SerializedName("sum_level1")
        private String sum_level1;
        @SerializedName("sum_level2")
        private String sum_level2;

    public String getSum_level1() {
        return sum_level1;
    }

    public void setSum_level1(String sum_level1) {
        this.sum_level1 = sum_level1;
    }

    public String getSum_level2() {
        return sum_level2;
    }

    public void setSum_level2(String sum_level2) {
        this.sum_level2 = sum_level2;
    }
}
