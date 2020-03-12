package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlightDetailTitleModel  implements Parcelable {
    @SerializedName("detailTitle")
    private ArrayList<DetailTitle> detailTitles;
    private FlightDetailTitleModel(Parcel in) {
    }

    public static final Creator<FlightDetailTitleModel> CREATOR = new Creator<FlightDetailTitleModel>() {
        @NonNull
        @Override
        public FlightDetailTitleModel createFromParcel(Parcel in) {
            return new FlightDetailTitleModel(in);
        }

        @NonNull
        @Override
        public FlightDetailTitleModel[] newArray(int size) {
            return new FlightDetailTitleModel[size];
        }
    };

    public ArrayList<DetailTitle> getDetailTitles() {
        return detailTitles;
    }

    public void setDetailTitles(ArrayList<DetailTitle> detailTitles) {
        this.detailTitles = detailTitles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class DetailTitle implements Parcelable {

        @Nullable
        @SerializedName("flightIcon")
        private String flightIcon;
        @Nullable
        @SerializedName("flightName")
        private String flightName;
        @Nullable
        @SerializedName("transitTime")
        private String transitTime;
        @Nullable
        @SerializedName("flightCode")
        private String flightCode;

        @Nullable
        @SerializedName("origin")
        private String origin;
        @Nullable
        @SerializedName("originName")
        private String originName;
        @Nullable
        @SerializedName("destination")
        private String destination;
        @Nullable
        @SerializedName("destinationName")
        private String destinationName;
        @Nullable
        @SerializedName("depart")
        private String depart;
        @Nullable
        @SerializedName("arrival")
        private String arrival;
        @Nullable
        @SerializedName("departureDate")
        private String departureDate;
        @Nullable
        @SerializedName("durationDetail")
        private String durationDetail;




        protected DetailTitle(@NonNull Parcel in) {
            flightIcon = in.readString();
            flightName = in.readString();
            flightCode= in.readString();
            transitTime = in.readString();
            origin = in.readString();
            originName = in.readString();
            destination = in.readString();
            destinationName = in.readString();
            depart = in.readString();
            arrival = in.readString();
            departureDate = in.readString();
            durationDetail = in.readString();

        }

        public static final Creator<DetailTitle> CREATOR = new Creator<DetailTitle>() {
            @NonNull
            @Override
            public DetailTitle createFromParcel(@NonNull Parcel in) {
                return new DetailTitle(in);
            }

            @NonNull
            @Override
            public DetailTitle[] newArray(int size) {
                return new DetailTitle[size];
            }
        };

        @Nullable
        public String getFlightIcon() {
            return flightIcon;
        }

        public void setFlightIcon(String flightIcon) {
            this.flightIcon = flightIcon;
        }

        @Nullable
        public String getFlightName() {
            return flightName;
        }

        public void setFlightName(String flightName) {
            this.flightName = flightName;
        }

        @Nullable
        public String getTransitTime() {
            return transitTime;
        }

        public void setTransitTime(String transitTime) {
            this.transitTime = transitTime;
        }

        @Nullable
        public String getFlightCode() {
            return flightCode;
        }

        public void setFlightCode(String flightCode) {
            this.flightCode = flightCode;
        }

        @Nullable
        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        @Nullable
        public String getOriginName() {
            return originName;
        }

        public void setOriginName(String originName) {
            this.originName = originName;
        }

        @Nullable
        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        @Nullable
        public String getDestinationName() {
            return destinationName;
        }

        public void setDestinationName(String destinationName) {
            this.destinationName = destinationName;
        }

        @Nullable
        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        @Nullable
        public String getArrival() {
            return arrival;
        }

        public void setArrival(String arrival) {
            this.arrival = arrival;
        }

        @Nullable
        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        @Nullable
        public String getDurationDetail() {
            return durationDetail;
        }

        public void setDurationDetail(String durationDetail) {
            this.durationDetail = durationDetail;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.flightIcon);
            dest.writeString(this.flightName);
            dest.writeString(this.flightCode);
            dest.writeString(this.transitTime);
            dest.writeString(this.origin);
            dest.writeString(this.originName);
            dest.writeString(this.destination);
            dest.writeString(this.destinationName);
            dest.writeString(this.depart);
            dest.writeString(this.arrival);
            dest.writeString(this.departureDate);
            dest.writeString(this.durationDetail);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
