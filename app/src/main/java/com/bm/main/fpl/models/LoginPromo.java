package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by papahnakal on 08/11/17.
 */

public class LoginPromo extends BaseObject implements Parcelable{
    /*@SerializedName("response_code")
    private String response_code;
    @SerializedName("response_desc")
    private String response_desc;*/
    @SerializedName("response_value")
    private List<Response_value> response_value;

    protected LoginPromo(Parcel in) {
    }

    public static final Creator<LoginPromo> CREATOR = new Creator<LoginPromo>() {
        @NonNull
        @Override
        public LoginPromo createFromParcel(Parcel in) {
            return new LoginPromo(in);
        }

        @NonNull
        @Override
        public LoginPromo[] newArray(int size) {
            return new LoginPromo[size];
        }
    };

    public List<Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(List<Response_value> response_value) {
        this.response_value = response_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Response_value {
        @SerializedName("url_image")
        private String url_image;
        @SerializedName("target_link")
        private String target_link;

        public String getUrl_image() {
            return url_image;
        }

        public void setUrl_image(String url_image) {
            this.url_image = url_image;
        }

        public String getTarget_link() {
            return target_link;
        }

        public void setTarget_link(String target_link) {
            this.target_link = target_link;
        }
    }
}
