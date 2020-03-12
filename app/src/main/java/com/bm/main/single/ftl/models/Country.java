package com.bm.main.single.ftl.models;

/**
 * Created by sarifhidayat on 2019-08-13.
 **/

import java.text.Collator;
import java.util.*;
public class Country {
    private String iso;
    private String code;
    public String name;
    public String flag;

    public Country(String iso, String code, String name,String flag) {
        this.iso = iso;
        this.code = code;
        this.name = name;
        this.flag = flag;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

   // public String toString() {
   //     return flag+" "+iso + " - " + code + " - " + name.toUpperCase();
   // }
}



