package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FlightResponseBookModelData implements Parcelable {
    public static final Creator<FlightResponseBookModelData> CREATOR = new Creator<FlightResponseBookModelData>() {
        @NonNull
        @Override
        public FlightResponseBookModelData createFromParcel(@NonNull Parcel source) {
            FlightResponseBookModelData var = new FlightResponseBookModelData();
            var.passengers = source.readString();
            var.reservationDate = source.readString();
            var.flightInfoGo = source.readString();
            var.transactionId = source.readString();
            var.timeLimit = source.readString();
            var.nominalAdmin = source.readString();
            var.komisi = source.readInt();
            var.paymentCode = source.readString();
            var.timeLimitYMD = source.readString();
            var.nominal = source.readString();
            var.departureTime1 = source.readString();
            var.flightCode1 = source.readString();
            var.comission = source.readString();
            var.details = source.readString();
            var.bookingCode = source.readString();
            var.arrivalTime1 = source.readString();
            return var;
        }

        @NonNull
        @Override
        public FlightResponseBookModelData[] newArray(int size) {
            return new FlightResponseBookModelData[size];
        }
    };
    @Nullable
    private String passengers;
    @Nullable
    private String reservationDate;
    @Nullable
    private String flightInfoGo;
    @Nullable
    private String transactionId;
    @Nullable
    private String timeLimit;
    @Nullable
    private String nominalAdmin;
    private int komisi;
    @Nullable
    private String paymentCode;
    @Nullable
    private String timeLimitYMD;
    @Nullable
    private String nominal;
    @Nullable
    private String departureTime1;
    @Nullable
    private String flightCode1;
    @Nullable
    private String comission;
    @Nullable
    private String details;
    @Nullable
    private String bookingCode;
    @Nullable
    private String arrivalTime1;

    @Nullable
    public String getPassengers() {
        return this.passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    @Nullable
    public String getReservationDate() {
        return this.reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Nullable
    public String getFlightInfoGo() {
        return this.flightInfoGo;
    }

    public void setFlightInfoGo(String flightInfoGo) {
        this.flightInfoGo = flightInfoGo;
    }

    @Nullable
    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Nullable
    public String getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Nullable
    public String getNominalAdmin() {
        return this.nominalAdmin;
    }

    public void setNominalAdmin(String nominalAdmin) {
        this.nominalAdmin = nominalAdmin;
    }

    public int getKomisi() {
        return this.komisi;
    }

    public void setKomisi(int komisi) {
        this.komisi = komisi;
    }

    @Nullable
    public String getPaymentCode() {
        return this.paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    @Nullable
    public String getTimeLimitYMD() {
        return this.timeLimitYMD;
    }

    public void setTimeLimitYMD(String timeLimitYMD) {
        this.timeLimitYMD = timeLimitYMD;
    }

    @Nullable
    public String getNominal() {
        return this.nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    @Nullable
    public String getDepartureTime1() {
        return this.departureTime1;
    }

    public void setDepartureTime1(String departureTime1) {
        this.departureTime1 = departureTime1;
    }

    @Nullable
    public String getFlightCode1() {
        return this.flightCode1;
    }

    public void setFlightCode1(String flightCode1) {
        this.flightCode1 = flightCode1;
    }

    @Nullable
    public String getComission() {
        return this.comission;
    }

    public void setComission(String comission) {
        this.comission = comission;
    }

    @Nullable
    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Nullable
    public String getBookingCode() {
        return this.bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    @Nullable
    public String getArrivalTime1() {
        return this.arrivalTime1;
    }

    public void setArrivalTime1(String arrivalTime1) {
        this.arrivalTime1 = arrivalTime1;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.passengers);
        dest.writeString(this.reservationDate);
        dest.writeString(this.flightInfoGo);
        dest.writeString(this.transactionId);
        dest.writeString(this.timeLimit);
        dest.writeString(this.nominalAdmin);
        dest.writeInt(this.komisi);
        dest.writeString(this.paymentCode);
        dest.writeString(this.timeLimitYMD);
        dest.writeString(this.nominal);
        dest.writeString(this.departureTime1);
        dest.writeString(this.flightCode1);
        dest.writeString(this.comission);
        dest.writeString(this.details);
        dest.writeString(this.bookingCode);
        dest.writeString(this.arrivalTime1);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
