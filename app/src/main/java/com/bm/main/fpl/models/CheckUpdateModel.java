package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

public class CheckUpdateModel extends BaseObject  {




        @SerializedName("version_code")
        private int version_code;
        @SerializedName("urgent")
        private String urgent;
    @SerializedName("info")
    private String info;


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getVersion_code() {
            return version_code;
        }

        public void setVersion_code(int version_code) {
            this.version_code = version_code;
        }

        public String getUrgent() {
            return urgent;
        }

        public void setUrgent(String urgent) {
            this.urgent = urgent;
        }



}
