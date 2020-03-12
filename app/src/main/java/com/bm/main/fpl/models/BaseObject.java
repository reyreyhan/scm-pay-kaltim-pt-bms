package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by papahnakal on 10/11/17.
 */

public class BaseObject {
    @SerializedName("response_code")
    private String response_code;
    @SerializedName("response_desc")
    private String response_desc;

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_desc() {
        return response_desc;
    }

    public void setResponse_desc(String response_desc) {
        this.response_desc = response_desc;
    }
}
