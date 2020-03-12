package com.bm.main.fpl.models;

import java.io.Serializable;

public class FeeFlightModel implements Serializable{
    private String isCaptcha;
    private String customAdmin;
    private String newsUrl;
    private String isInfant;
    private String icon;
    private String switcherId;
    private String airline;
    private String isActive;
    private String airlineName;
    private String isChild;

    public FeeFlightModel(){
        super();

    }
    public String getIsCaptcha() {
        return this.isCaptcha;
    }

    public void setIsCaptcha(String isCaptcha) {
        this.isCaptcha = isCaptcha;
    }

    public String getCustomAdmin() {
        return this.customAdmin;
    }

    public void setCustomAdmin(String customAdmin) {
        this.customAdmin = customAdmin;
    }

    public String getNewsUrl() {
        return this.newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getIsInfant() {
        return this.isInfant;
    }

    public void setIsInfant(String isInfant) {
        this.isInfant = isInfant;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSwitcherId() {
        return this.switcherId;
    }

    public void setSwitcherId(String switcherId) {
        this.switcherId = switcherId;
    }

    public String getAirline() {
        return this.airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getIsActive() {
        return this.isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getAirlineName() {
        return this.airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getIsChild() {
        return this.isChild;
    }

    public void setIsChild(String isChild) {
        this.isChild = isChild;
    }
}
