package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.ArrayList;

public class FlightDataModel extends BaseObject implements Parcelable {
    @SerializedName("data")
    private ArrayList<FlightDataModel.Data> data;
    private FlightDataModel(Parcel in) {
    }

    public static final Creator<FlightDataModel> CREATOR = new Creator<FlightDataModel>() {
        @NonNull
        @Override
        public FlightDataModel createFromParcel(Parcel in) {
            return new FlightDataModel(in);
        }

        @NonNull
        @Override
        public FlightDataModel[] newArray(int size) {
            return new FlightDataModel[size];
        }
    };

    public ArrayList<FlightDataModel.Data> getData() {
        return data;
    }

    public void setData(ArrayList<FlightDataModel.Data> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
    public static class Data implements Parcelable {
        @Nullable
        @SerializedName("title")
        private String title;
        @SerializedName("detailTitle")
        private JSONArray detailTitle;
        @Nullable
        @SerializedName("cityTransit")
        private String cityTransit;

        @Nullable
        @SerializedName("duration")
        private String duration;


        @Nullable
        @SerializedName("arrivalDate")
        private String arrivalDate;

        @Nullable
        @SerializedName("departureDate")
        private String departureDate;

        @SerializedName("isTransit")
        private boolean isTransit;
        @Nullable
        @SerializedName("airlineIcon")
        private String airlineIcon;
        @Nullable
        @SerializedName("airlineName")
        private String airlineName;
        @Nullable
        @SerializedName("airlineCode")
        private String airlineCode;

        @SerializedName("classes")
        private JSONArray classes;


        protected Data(@NonNull Parcel in) {
            title = in.readString();
           // detailTitle = in.createTypedArray(FlightDetailTitleModel.CREATOR);
           // detailTitle = in.readArray();
            cityTransit= in.readString();

            duration = in.readString();


            arrivalDate = in.readString();

            departureDate = in.readString();

            isTransit = Boolean.parseBoolean(in.readString());

            airlineIcon = in.readString();
            airlineName = in.readString();
            airlineCode = in.readString();

          //  classes=in.createTypedArray(FlightClassesModel.CREATOR);

//            int numberOfArrays = in.readInt();
//            classes = new FlightClassesModel[numberOfArrays][];
//            for (int i = 0; i < numberOfArrays; i++) {
//                classes[i] = (FlightClassesModel[]) in.readParcelableArray(FlightClassesModel.class.getClassLoader());
//            }


        }

        public static final Creator<FlightAirportModel.Data> CREATOR = new Creator<FlightAirportModel.Data>() {
            @NonNull
            @Override
            public FlightAirportModel.Data createFromParcel(@NonNull Parcel in) {
                return new FlightAirportModel.Data(in);
            }

            @NonNull
            @Override
            public FlightAirportModel.Data[] newArray(int size) {
                return new FlightAirportModel.Data[size];
            }
        };

        @Nullable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public JSONArray getDetailTitle() {
            return detailTitle;
        }

        public void setDetailTitle(JSONArray detailTitle) {
            this.detailTitle = detailTitle;
        }

        @Nullable
        public String getCityTransit() {
            return cityTransit;
        }

        public void setCityTransit(String cityTransit) {
            this.cityTransit = cityTransit;
        }

        @Nullable
        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        @Nullable
        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        @Nullable
        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public boolean isTransit() {
            return isTransit;
        }

        public void setTransit(boolean transit) {
            isTransit = transit;
        }

        @Nullable
        public String getAirlineIcon() {
            return airlineIcon;
        }

        public void setAirlineIcon(String airlineIcon) {
            this.airlineIcon = airlineIcon;
        }

        @Nullable
        public String getAirlineName() {
            return airlineName;
        }

        public void setAirlineName(String airlineName) {
            this.airlineName = airlineName;
        }

        @Nullable
        public String getAirlineCode() {
            return airlineCode;
        }

        public void setAirlineCode(String airlineCode) {
            this.airlineCode = airlineCode;
        }

        public JSONArray getClasses() {
            return classes;
        }

        public void setClasses(JSONArray classes) {
            this.classes = classes;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.title);
          //  dest.writeTypedArray(this.detailTitle, flags);
            dest.writeString(this.cityTransit);

            dest.writeValue(this.isTransit);
            dest.writeString(this.duration);
            dest.writeString(this.cityTransit);
            dest.writeString(this.departureDate);
            dest.writeString(this.arrivalDate);
            dest.writeString(this.airlineIcon);
            dest.writeString(this.airlineCode);
            dest.writeString(this.airlineName);
//            int numOfArrays = classes.length;
//            dest.writeInt(numOfArrays);
//            for (int i = 0; i < numOfArrays; i++) {
//                dest.writeParcelableArray(classes[i], flags);
//            }

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }


//    public FlightDataModel() {
//    }


}
