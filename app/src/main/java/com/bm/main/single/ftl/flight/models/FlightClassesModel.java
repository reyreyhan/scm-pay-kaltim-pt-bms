package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlightClassesModel extends BaseObject implements Parcelable {
    @SerializedName("classes")
    private ArrayList<Classes> classes;

    private FlightClassesModel(Parcel in) {
    }

    public static final Creator<FlightClassesModel> CREATOR = new Creator<FlightClassesModel>() {
        @NonNull
        @Override
        public FlightClassesModel createFromParcel(Parcel in) {
            return new FlightClassesModel(in);
        }

        @NonNull
        @Override
        public FlightClassesModel[] newArray(int size) {
            return new FlightClassesModel[size];
        }
    };

    public ArrayList<Classes> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Classes> classes) {
        this.classes = classes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Classes implements Parcelable {

        @Nullable
        @SerializedName("availability")
        private String availability;
        @Nullable
        @SerializedName("seatKey")
        private String seatKey;
        @Nullable
        @SerializedName("class")
        private String mclass;
        @SerializedName("price")
        private int price;
        @Nullable
        @SerializedName("depatureTime")
        private String departureTime;
        @Nullable
        @SerializedName("arrivalTime")
        private String arrivalTime;
        @Nullable
        @SerializedName("flightCode")
        private String flightCode;
        @Nullable
        @SerializedName("departure")
        private String departure;
        @Nullable
        @SerializedName("departureName")
        private String departureName;
        @Nullable
        @SerializedName("arrival")
        private String arrival;
        @Nullable
        @SerializedName("arrivalName")
        private String arrivalName;
        @SerializedName("isInternasional")
        private int isInternasional;
        @Nullable
        @SerializedName("depatureTimeZone")
        private String depatureTimeZone;
        @Nullable
        @SerializedName("arrivalTimeZone")
        private String arrivalTimeZone;
        @Nullable
        @SerializedName("departureTimeZoneText")
        private String departureTimeZoneText;
        @Nullable
        @SerializedName("arrivalTimeZoneText")
        private String arrivalTimeZoneText;
        @Nullable
        @SerializedName("seat")
        private String seat;
        @Nullable
        @SerializedName("duration")
        private String duration;
 @Nullable
 @SerializedName("departureDate")
        private String departureDate;
 @Nullable
 @SerializedName("arrivalDate")
        private String arrivalDate;



        private int durationInt;
        private int priceTemp;


        protected Classes(@NonNull Parcel in) {
            availability = in.readString();
            seatKey = in.readString();
            mclass = in.readString();
            price = in.readInt();
            departureTime = in.readString();
            arrivalTime = in.readString();
            flightCode = in.readString();
            departure = in.readString();
            departureName = in.readString();
            arrival = in.readString();
            arrivalName = in.readString();
            isInternasional = in.readInt();
            depatureTimeZone = in.readString();
            arrivalTimeZone = in.readString();
            departureTimeZoneText = in.readString();
            arrivalTimeZoneText = in.readString();
            seat = in.readString();
            duration = in.readString();
            departureDate = in.readString();
            arrivalDate = in.readString();
            durationInt = in.readInt();
            priceTemp = in.readInt();

        }

        public static final Creator<Classes> CREATOR = new Creator<Classes>() {
            @NonNull
            @Override
            public Classes createFromParcel(@NonNull Parcel in) {
                return new Classes(in);
            }

            @NonNull
            @Override
            public Classes[] newArray(int size) {
                return new Classes[size];
            }
        };

        @Nullable
        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        @Nullable
        public String getSeatKey() {
            return seatKey;
        }

        public void setSeatKey(String seatKey) {
            this.seatKey = seatKey;
        }

        @Nullable
        public String getMclass() {
            return mclass;
        }

        public void setMclass(String mclass) {
            this.mclass = mclass;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            if (price > 0) {
//                priceList.add(price);
//                flightDataModelClasses.setPriceTemp(price);
//                airLinesPriceModel.setAirLinesPriceModel(price);
//                airLinesModel.setAirLinePrice(price);
                setPriceTemp(price);

            }
            else {
//                flightDataModelClasses.setPriceTemp(999999999);
//                airLinesPriceModel.setAirLinesPriceModel(999999999);
//                airLinesModel.setAirLinePrice(999999999);
                setPriceTemp(999999999);
            }
            this.price = price;
        }

        @Nullable
        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        @Nullable
        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        @Nullable
        public String getFlightCode() {
            return flightCode;
        }

        public void setFlightCode(String flightCode) {
            this.flightCode = flightCode;
        }

        @Nullable
        public String getDeparture() {
            return departure;
        }

        public void setDeparture(String departure) {
            this.departure = departure;
        }

        @Nullable
        public String getDepartureName() {
            return departureName;
        }

        public void setDepartureName(String departureName) {
            this.departureName = departureName;
        }

        @Nullable
        public String getArrival() {
            return arrival;
        }

        public void setArrival(String arrival) {
            this.arrival = arrival;
        }

        @Nullable
        public String getArrivalName() {
            return arrivalName;
        }

        public void setArrivalName(String arrivalName) {
            this.arrivalName = arrivalName;
        }

        public int getIsInternasional() {
            return isInternasional;
        }

        public void setIsInternasional(int isInternasional) {
            this.isInternasional = isInternasional;
        }

        @Nullable
        public String getDepatureTimeZone() {
            return depatureTimeZone;
        }

        public void setDepatureTimeZone(String depatureTimeZone) {
            this.depatureTimeZone = depatureTimeZone;
        }

        @Nullable
        public String getArrivalTimeZone() {
            return arrivalTimeZone;
        }

        public void setArrivalTimeZone(String arrivalTimeZone) {
            this.arrivalTimeZone = arrivalTimeZone;
        }

        @Nullable
        public String getDepartureTimeZoneText() {
            return departureTimeZoneText;
        }

        public void setDepartureTimeZoneText(String departureTimeZoneText) {
            this.departureTimeZoneText = departureTimeZoneText;
        }

        @Nullable
        public String getArrivalTimeZoneText() {
            return arrivalTimeZoneText;
        }

        public void setArrivalTimeZoneText(String arrivalTimeZoneText) {
            this.arrivalTimeZoneText = arrivalTimeZoneText;
        }

        @Nullable
        public String getSeat() {
            return seat;
        }

        public void setSeat(String seat) {
            this.seat = seat;
        }

        @Nullable
        public String getDuration() {
            return duration;
        }

        public void setDuration(@NonNull String duration) {
            String arrDuration[] = duration.split("j");
            String jam;
            String mnt;
            if (arrDuration[0].length() == 1) {
                jam = "0" + arrDuration[0];
            }
            else {
                jam = arrDuration[0];
            }
            if (arrDuration[1].length() == 2) {
                mnt = "0" + arrDuration[1];
            }
            else {
                mnt = arrDuration[1];
            }
            String durationInt = jam + "j" + mnt;
            String dur = jam + mnt.substring(0, 2);
            setDurationInt(Integer.parseInt(dur));


            this.duration = duration;
        }

        @Nullable
        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        @Nullable
        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }


        public int getDurationInt() {
            return durationInt;
        }

        public void setDurationInt(int durationInt) {

            this.durationInt = durationInt;
        }


        public int getPriceTemp() {
            return priceTemp;
        }

        public void setPriceTemp(int priceTemp) {

            this.priceTemp = priceTemp;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.availability);
            dest.writeString(this.seatKey);
            dest.writeString(this.mclass);
            dest.writeInt(this.price);

            dest.writeString(this.departureTime);
            dest.writeString(this.arrivalTime);
            dest.writeString(this.flightCode);
            dest.writeString(this.departure);
            dest.writeString(this.arrival);
            dest.writeString(this.arrivalName);

            dest.writeInt(this.isInternasional);
            dest.writeString(this.depatureTimeZone);
            dest.writeString(this.arrivalTimeZone);
            dest.writeString(this.departureTimeZoneText);
            dest.writeString(this.arrivalTimeZoneText);

            dest.writeString(this.seat);
            dest.writeString(this.duration);
            dest.writeString(this.departureDate);
            dest.writeString(this.arrivalDate);



            dest.writeInt(this.durationInt);
            dest.writeInt(this.priceTemp);





        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
