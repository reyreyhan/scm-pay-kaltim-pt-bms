package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

public class JumlahDownlineModel extends BaseObject {





        @SerializedName("count_level1")
        private String count_level1;
        @SerializedName("count_level2")
        private String count_level2;

    public String getCount_level1() {
        return count_level1;
    }

    public void setCount_level1(String count_level1) {
        this.count_level1 = count_level1;
    }

    public String getCount_level2() {
        return count_level2;
    }

    public void setCount_level2(String count_level2) {
        this.count_level2 = count_level2;
    }
}
