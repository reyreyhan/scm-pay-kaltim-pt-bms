package com.bm.main.fpl.models;

/**
 * Created by skyshi on 19/01/17.
 */

public class SignOn extends BaseObject{

    private String session_id;
    private String expired_time;
    private String id;
    private String pin;
    private String key;
    private String nama_pemilik;
    private String notelp_pemilik;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getNotelp_pemilik() {
        return notelp_pemilik;
    }

    public void setNotelp_pemilik(String notelp_pemilik) {
        this.notelp_pemilik = notelp_pemilik;
    }
}
