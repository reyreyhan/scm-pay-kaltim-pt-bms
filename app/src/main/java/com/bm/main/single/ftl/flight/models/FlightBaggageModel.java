package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlightBaggageModel extends BaseObject implements Parcelable {
    @SerializedName("data")
    private ArrayList<Data> data;
    private FlightBaggageModel(Parcel in) {
    }

    public static final Creator<FlightBaggageModel> CREATOR = new Creator<FlightBaggageModel>() {
        @NonNull
        @Override
        public FlightBaggageModel createFromParcel(Parcel in) {
            return new FlightBaggageModel(in);
        }

        @NonNull
        @Override
        public FlightBaggageModel[] newArray(int size) {
            return new FlightBaggageModel[size];
        }
    };

    public ArrayList<FlightBaggageModel.Data> getData() {
        return data;
    }

    public void setData(ArrayList<FlightBaggageModel.Data> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }




    public static class Data implements Parcelable,Comparable<Data> {

        @Nullable
        @SerializedName("price")
        private String price;
        @Nullable
        @SerializedName("weight")
        private String weight;
        @Nullable
        @SerializedName("baggage_key")
        private String baggage_key;


        protected Data(@NonNull Parcel in) {
            price = in.readString();
            weight = in.readString();
            baggage_key= in.readString();


        }

        public static final Creator<FlightBaggageModel.Data> CREATOR = new Creator<Data>() {
            @NonNull
            @Override
            public FlightBaggageModel.Data createFromParcel(@NonNull Parcel in) {
                return new FlightBaggageModel.Data(in);
            }

            @NonNull
            @Override
            public FlightBaggageModel.Data[] newArray(int size) {
                return new FlightBaggageModel.Data[size];
            }
        };


        @Nullable
        public String getPrice() {
            return this.price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Nullable
        public String getWeight() {
            return this.weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        @Nullable
        public String getBaggage_key() {
            return this.baggage_key;
        }

        public void setBaggage_key(String baggage_key) {
            this.baggage_key = baggage_key;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.price);
            dest.writeString(this.weight);
            dest.writeString(this.baggage_key);
        }

        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public int compareTo(@NonNull Data o) {
            String []arrA=getBaggage_key().split("|");
            String []arrB=o.getBaggage_key().split("|");


            return arrA[1].compareTo(arrB[1]);
        }
    }
}
