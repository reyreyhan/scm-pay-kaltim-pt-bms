package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SettingFlightModel extends BaseObject  implements Parcelable {
    @SerializedName("settings")
    private ArrayList<Settings> settings;
    private SettingFlightModel(Parcel in) {
    }

    public static final Creator<SettingFlightModel> CREATOR = new Creator<SettingFlightModel>() {
        @NonNull
        @Override
        public SettingFlightModel createFromParcel(Parcel in) {
            return new SettingFlightModel(in);
        }

        @NonNull
        @Override
        public SettingFlightModel[] newArray(int size) {
            return new SettingFlightModel[size];
        }
    };

    public ArrayList<SettingFlightModel.Settings> getSettings() {
        return settings;
    }

    public void setData(ArrayList<SettingFlightModel.Settings> settings) {
        this.settings = settings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Settings implements Parcelable {

        @Nullable
        @SerializedName("airline")
        private String airline;
        @Nullable
        @SerializedName("isActive")
        private String isActive;
        @Nullable
        @SerializedName("customAdmin")
        private String customAdmin;
        @Nullable
        @SerializedName("airlineName")
        private String airlineName;
        @Nullable
        @SerializedName("isCaptcha")
        private String isCaptcha;
        @Nullable
        @SerializedName("isInfant")
        private String isInfant;
        @Nullable
        @SerializedName("isChild")
        private String isChild;
        @Nullable
        @SerializedName("icon")
        private String icon;
        @Nullable
        @SerializedName("switcherId")
        private String switcherId;
        @Nullable
        @SerializedName("newsUrl")
        private String newsUrl;




        protected Settings(@NonNull Parcel in) {
            airline = in.readString();
            isActive = in.readString();
            customAdmin= in.readString();
            airlineName = in.readString();
            isCaptcha = in.readString();
            isInfant = in.readString();
            isChild = in.readString();
            icon = in.readString();
            switcherId = in.readString();
            newsUrl = in.readString();


        }

        public static final Creator<SettingFlightModel.Settings> CREATOR = new Creator<Settings>() {
            @NonNull
            @Override
            public SettingFlightModel.Settings createFromParcel(@NonNull Parcel in) {
                return new SettingFlightModel.Settings(in);
            }

            @NonNull
            @Override
            public SettingFlightModel.Settings[] newArray(int size) {
                return new SettingFlightModel.Settings[size];
            }
        };


        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.airline);
            dest.writeString(this.isActive);
            dest.writeString(this.customAdmin);
            dest.writeString(this.airlineName);
            dest.writeString(this.isCaptcha);
            dest.writeString(this.isInfant);
            dest.writeString(this.isChild);
            dest.writeString(this.icon);
            dest.writeString(this.switcherId);
            dest.writeString(this.newsUrl);




        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
