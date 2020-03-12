package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConfigFlightModel extends BaseObject  implements Parcelable {
    @SerializedName("configurations")
    private ArrayList<Configurations> configurations;
    private ConfigFlightModel(Parcel in) {
    }

    public static final Creator<ConfigFlightModel> CREATOR = new Creator<ConfigFlightModel>() {
        @NonNull
        @Override
        public ConfigFlightModel createFromParcel(Parcel in) {
            return new ConfigFlightModel(in);
        }

        @NonNull
        @Override
        public ConfigFlightModel[] newArray(int size) {
            return new ConfigFlightModel[size];
        }
    };

    public ArrayList<ConfigFlightModel.Configurations> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(ArrayList<ConfigFlightModel.Configurations> data) {
        this.configurations = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Configurations implements Parcelable {

        @Nullable
        @SerializedName("isAddress")
        private String isAddress;
        @Nullable
        @SerializedName("isPhone")
        private String isPhone;
        @Nullable
        @SerializedName("isPostalCode")
        private String isPostalCode;
        @Nullable
        @SerializedName("isActive")
        private String isActive;
        @Nullable
        @SerializedName("isIdentityNumber")
        private String isIdentityNumber;
        @Nullable
        @SerializedName("isMobilePhone")
        private String isMobilePhone;
        @Nullable
        @SerializedName("isNationality")
        private String isNationality;
        @Nullable
        @SerializedName("isTitle")
        private String isTitle;
        @Nullable
        @SerializedName("isBirthDay")
        private String isBirthDay;
        @Nullable
        @SerializedName("isEmail")
        private String isEmail;
        @Nullable
        @SerializedName("isFirstName")
        private String isFirstName;
        @Nullable
        @SerializedName("airline")
        private String airline;
        @Nullable
        @SerializedName("isLastName")
        private String isLastName;



        protected Configurations(@NonNull Parcel in) {
            isAddress = in.readString();
            isPhone = in.readString();
            isPostalCode= in.readString();
            isActive = in.readString();
            isIdentityNumber = in.readString();
            isMobilePhone = in.readString();
            isNationality = in.readString();
            isTitle = in.readString();
            isBirthDay = in.readString();
            isEmail = in.readString();
            isFirstName = in.readString();
            airline = in.readString();
            isLastName = in.readString();

        }

        public static final Creator<ConfigFlightModel.Configurations> CREATOR = new Creator<ConfigFlightModel.Configurations>() {
            @NonNull
            @Override
            public ConfigFlightModel.Configurations createFromParcel(@NonNull Parcel in) {
                return new ConfigFlightModel.Configurations(in);
            }

            @NonNull
            @Override
            public ConfigFlightModel.Configurations[] newArray(int size) {
                return new ConfigFlightModel.Configurations[size];
            }
        };


        @Nullable
        public String getIsAddress() {
            return isAddress;
        }

        public void setIsAddress(String isAddress) {
            this.isAddress = isAddress;
        }

        @Nullable
        public String getIsPhone() {
            return isPhone;
        }

        public void setIsPhone(String isPhone) {
            this.isPhone = isPhone;
        }

        @Nullable
        public String getIsPostalCode() {
            return isPostalCode;
        }

        public void setIsPostalCode(String isPostalCode) {
            this.isPostalCode = isPostalCode;
        }

        @Nullable
        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        @Nullable
        public String getIsIdentityNumber() {
            return isIdentityNumber;
        }

        public void setIsIdentityNumber(String isIdentityNumber) {
            this.isIdentityNumber = isIdentityNumber;
        }

        @Nullable
        public String getIsMobilePhone() {
            return isMobilePhone;
        }

        public void setIsMobilePhone(String isMobilePhone) {
            this.isMobilePhone = isMobilePhone;
        }

        @Nullable
        public String getIsNationality() {
            return isNationality;
        }

        public void setIsNationality(String isNationality) {
            this.isNationality = isNationality;
        }

        @Nullable
        public String getIsTitle() {
            return isTitle;
        }

        public void setIsTitle(String isTitle) {
            this.isTitle = isTitle;
        }

        @Nullable
        public String getIsBirthDay() {
            return isBirthDay;
        }

        public void setIsBirthDay(String isBirthDay) {
            this.isBirthDay = isBirthDay;
        }

        @Nullable
        public String getIsEmail() {
            return isEmail;
        }

        public void setIsEmail(String isEmail) {
            this.isEmail = isEmail;
        }

        @Nullable
        public String getIsFirstName() {
            return isFirstName;
        }

        public void setIsFirstName(String isFirstName) {
            this.isFirstName = isFirstName;
        }

        @Nullable
        public String getAirline() {
            return airline;
        }

        public void setAirline(String airline) {
            this.airline = airline;
        }

        @Nullable
        public String getIsLastName() {
            return isLastName;
        }

        public void setIsLastName(String isLastName) {
            this.isLastName = isLastName;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.isAddress);
            dest.writeString(this.isPhone);
            dest.writeString(this.isPostalCode);
            dest.writeString(this.isActive);
            dest.writeString(this.isIdentityNumber);
            dest.writeString(this.isMobilePhone);
            dest.writeString(this.isNationality);
            dest.writeString(this.isTitle);
            dest.writeString(this.isBirthDay);
            dest.writeString(this.isEmail);
            dest.writeString(this.isFirstName);
            dest.writeString(this.airline);
            dest.writeString(this.isLastName);



        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
