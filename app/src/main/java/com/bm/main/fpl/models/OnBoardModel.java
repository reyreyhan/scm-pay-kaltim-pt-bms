package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OnBoardModel implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private OnBoardModel(Parcel in) {
    }

    public static final Creator<OnBoardModel> CREATOR = new Creator<OnBoardModel>() {
        @NonNull
        @Override
        public OnBoardModel createFromParcel(Parcel in) {
            return new OnBoardModel(in);
        }

        @NonNull
        @Override
        public OnBoardModel[] newArray(int size) {
            return new OnBoardModel[size];
        }
    };

    public ArrayList<OnBoardModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<OnBoardModel.Response_value> response_value) {
        this.response_value = response_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Response_value implements Parcelable {

        @Nullable
        @SerializedName("onboard")
        private String onboardUrl;





        protected Response_value(@NonNull Parcel in) {
            onboardUrl = in.readString();




        }

        public static final Creator<OnBoardModel.Response_value> CREATOR = new Creator<OnBoardModel.Response_value>() {
            @NonNull
            @Override
            public OnBoardModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new OnBoardModel.Response_value(in);
            }

            @NonNull
            @Override
            public OnBoardModel.Response_value[] newArray(int size) {
                return new OnBoardModel.Response_value[size];
            }
        };


        @Nullable
        public String getOnboardUrl() {
            return onboardUrl;
        }

        public void setOnboardUrl(String onboardUrl) {
            this.onboardUrl = onboardUrl;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.onboardUrl);



        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
