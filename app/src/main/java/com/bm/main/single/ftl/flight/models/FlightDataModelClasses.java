package com.bm.main.single.ftl.flight.models;

import org.json.JSONArray;

public class FlightDataModelClasses {
    private String title;
    private JSONArray detailTitle;
    private String cityTransit;
    private String departureTimeZoneText;
    private String flightCode;
    private String arrival;
    private String arrivalName;
    private String classeses;
    private String seatKey;
    private String seat;
    private int availability;
    private String duration;
    private int durationInt;
    private int price;
    private int priceTemp;
    private String arrivalTime;
    private String arrivalDate;
    private String arrivalTimeZoneText;
    private String departure;
    private String departureName;
    private String departureDate;
    private String departureTime;
    private boolean isTransit;
    private String airlineIcon;
    private String airlineName;
    private String airlineCode;
    private String waktuBerangkat;
    private int isInternational;
    private JSONArray classArr;

    public FlightDataModelClasses() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartureTimeZoneText() {
        return this.departureTimeZoneText;
    }

    public void setDepartureTimeZoneText(String departureTimeZoneText) {
        this.departureTimeZoneText = departureTimeZoneText;
    }

    public String getFlightCode() {
        return this.flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getArrivalName() {
        return arrivalName;
    }

    public void setArrivalName(String arrivalName) {
        this.arrivalName = arrivalName;
    }

    public String getArrival() {
        return this.arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getClasseses() {
        return this.classeses;
    }

    public void setClasseses(String classeses) {
        this.classeses = classeses;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getSeatKey() {
        return this.seatKey;
    }

    public void setSeatKey(String seatKey) {
        this.seatKey = seatKey;
    }

    public int getAvailability() {
        return this.availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceTemp() {
        return priceTemp;
    }

    public void setPriceTemp(int priceTemp) {
        this.priceTemp = priceTemp;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalTimeZoneText() {
        return this.arrivalTimeZoneText;
    }

    public void setArrivalTimeZoneText(String arrivalTimeZoneText) {
        this.arrivalTimeZoneText = arrivalTimeZoneText;
    }

    public String getDepartureName() {
        return departureName;
    }

    public void setDepartureName(String departureName) {
        this.departureName = departureName;
    }

    public String getDeparture() {
        return this.departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public boolean getIsTransit() {
        return this.isTransit;
    }

    public void setIsTransit(boolean isTransit) {
        this.isTransit = isTransit;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getAirlineIcon() {
        return airlineIcon;
    }

    public void setAirlineIcon(String airlineIcon) {
        this.airlineIcon = airlineIcon;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public void setClassArr(JSONArray classArr) {
        this.classArr = classArr;
    }

    public JSONArray getClassArr() {
        return classArr;
    }

    public String getWaktuBerangkat() {
        return waktuBerangkat;
    }

    public void setWaktuBerangkat(String waktuBerangkat) {
        this.waktuBerangkat = waktuBerangkat;
    }

    public boolean isTransit() {
        return isTransit;
    }

    public void setTransit(boolean transit) {
        isTransit = transit;
    }

    public int getIsInternational() {
        return isInternational;
    }

    public void setIsInternational(int isInternational) {
        this.isInternational = isInternational;
    }

    public JSONArray getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(JSONArray detailTitle) {
        this.detailTitle = detailTitle;
    }

    public String getCityTransit() {
        return cityTransit;
    }

    public void setCityTransit(String cityTransit) {
        this.cityTransit = cityTransit;
    }

    public int getDurationInt() {
        return durationInt;
    }

    public void setDurationInt(int durationInt) {
        this.durationInt = durationInt;
    }
}
