package com.bm.main.single.ftl.flight.models;

import java.util.ArrayList;

public class FlightBaggageDetailModel implements java.io.Serializable {
    private static final long serialVersionUID = 2297123451532078626L;
    private String durationDetail;
    private String flightCode;
    private String arrival;
    private String origin;
    private String destinationName;
    private String destination;
    private String depart;
    private String originName;
    private String flightIcon;
    private String flightName;
    private String transitTime;
    private boolean isTransit;
    private String initTransit;

    private ArrayList<SectionDataPassagerModel> sectionDataPassagerModels;


    public FlightBaggageDetailModel(){

    }

    public FlightBaggageDetailModel(String flightIcon,String flightName,String flightCode,String arrival,String destination,  ArrayList<SectionDataPassagerModel> sectionDataPassagerModels) {
        this.flightIcon = flightIcon;
        this.flightName = flightName;
        this.flightCode = flightCode;
        this.arrival = arrival;
        this.destination = destination;
        this.sectionDataPassagerModels = sectionDataPassagerModels;
    }

    public ArrayList<SectionDataPassagerModel> getSectionDataPassagerModels() {
        return sectionDataPassagerModels;
    }

    public void setSectionDataPassagerModels(ArrayList<SectionDataPassagerModel> sectionDataPassagerModels) {
        this.sectionDataPassagerModels = sectionDataPassagerModels;
    }


    public String getDurationDetail() {
        return this.durationDetail;
    }

    public void setDurationDetail(String duration) {
        this.durationDetail = duration;
    }

    public String getFlightCode() {
        return this.flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getArrival() {
        return this.arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestinationName() {
        return this.destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepart() {
        return this.depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getOriginName() {
        return this.originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getFlightIcon() {
        return flightIcon;
    }

    public void setFlightIcon(String flightIcon) {
        this.flightIcon = flightIcon;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(String transitTime) {
        this.transitTime = transitTime;
    }

    public boolean isTransit() {
        return isTransit;
    }

    public void setTransit(boolean transit) {
        isTransit = transit;
    }

    public String getInitTransit() {
        return initTransit;
    }

    public void setInitTransit(String initTransit) {
        this.initTransit = initTransit;
    }
}
