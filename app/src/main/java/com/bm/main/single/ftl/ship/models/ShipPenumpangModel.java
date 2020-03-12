package com.bm.main.single.ftl.ship.models;

import android.content.res.Configuration;
import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rizabudiprasetya on 7/31/17.
 */

public class ShipPenumpangModel extends ShipJSONModel {
    private String Nama = "", Identitas = "", TanggalLahir = "", Seat = "";
    private int Type = -1;

    public ShipPenumpangModel() {
    }

    public ShipPenumpangModel(String nama, String identitas, String tanggalLahir, int type) {
        Nama = nama;
        Identitas = identitas;
        TanggalLahir = tanggalLahir;
        Type = type;
    }

    public ShipPenumpangModel(@NonNull JSONObject jsonObject) throws JSONException {
        Nama = getStringValue(jsonObject, "Nama");
        Identitas = getStringValue(jsonObject, "Identitas");
        TanggalLahir = getStringValue(jsonObject, "TanggalLahir");
        Type = getIntValue(jsonObject, "Type");
    }

    @NonNull
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Nama", Nama);
        jsonObject.put("Identitas", Identitas);
        jsonObject.put("TanggalLahir", TanggalLahir);
        jsonObject.put("Type", Type);

        return jsonObject;
    }

    @NonNull
    public JSONObject getJSONObject(String gender) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", Nama);
        jsonObject.put("identityNumber", Identitas);
        jsonObject.put("birthDate", TanggalLahir);
        jsonObject.put("gender", gender);

        return jsonObject;
    }

    @NonNull
    public String isValid(@NonNull Configuration config) {
        if(Type == -1) {
            return "Mohon isi kelengkapan Tipe Penumpang";
        }
        if(Nama.equals("")) {
            return "Mohon isi kelepngkapan Nama Penumpang";
        }
        if(TanggalLahir.equals("")) {
            return "Mohon isi kelengkapan Tanggal Lahir Penumpang " + Nama;
        }
        if(Type == 0 && Identitas.equals("")) {
            return "Mohon isi kelengkapan Identitas Penumpang " + Nama;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", config.locale);
        SimpleDateFormat odf = new SimpleDateFormat("dd-MM-yyyy", config.locale);
        Date tglLahir;
        try {
            tglLahir = sdf.parse(TanggalLahir);

            Calendar tgl = Calendar.getInstance(config.locale);
            tgl.setTime(tglLahir);
            tgl.set(Calendar.HOUR_OF_DAY, 0);
            tgl.set(Calendar.MINUTE, 0);
            tgl.set(Calendar.SECOND, 0);
            tgl.set(Calendar.HOUR, 0);
            tgl.set(Calendar.MILLISECOND, 0);

            tglLahir = tgl.getTime();

        } catch (ParseException e) {
            return "Tanggal lahir Penumpang " + Nama + " tidak valid!";
        }

        Calendar c = Calendar.getInstance(config.locale);
        c.setTime(new Date());
        if(Type == 0) {
            c.add(Calendar.YEAR, -2);
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 59);
            Date maximumDate = c.getTime();

            if(tglLahir.compareTo(maximumDate) == 1) {
                return "Tanggal lahir Penumpang " + Nama + " tidak boleh lebih besar dari " + odf.format(maximumDate);
            }

        }else if(Type == 1) {

            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 59);
            Date maximumDate = c.getTime();

            c.add(Calendar.YEAR, -2);
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date minimumDate = c.getTime();

            /*c.add(Calendar.YEAR, -2);
            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 59);
            Date maximumDate = c.getTime();

            c = Calendar.getInstance(config.locale);
            c.add(Calendar.YEAR, -12);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date minimumDate = c.getTime();*/

            if(tglLahir.compareTo(maximumDate) == 1) {
                return "Tanggal lahir Penumpang " + Nama + " tidak boleh lebih besar dari " + odf.format(maximumDate);
            }else if(tglLahir.compareTo(minimumDate) == -1) {
                return "Tanggal lahir Penumpang " + Nama + " tidak boleh lebih kecil dari " + odf.format(minimumDate);
            }
        }else if(Type == 2) {

            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 59);
            Date maximumDate = c.getTime();

            c.add(Calendar.YEAR, -2);
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date minimumDate = c.getTime();

            if(tglLahir.compareTo(maximumDate) == 1) {
                return "Tanggal lahir Penumpang " + Nama + " tidak boleh lebih besar dari " + odf.format(maximumDate);
            }else if(tglLahir.compareTo(minimumDate) == -1) {
                return "Tanggal lahir Penumpang " + Nama + " tidak boleh lebih kecil dari " + odf.format(minimumDate);
            }
        }

        return "";
    }

    public String getSeat() {
        return Seat;
    }

    public void setSeat(String seat) {
        Seat = seat;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getIdentitas() {
        return Identitas;
    }

    public void setIdentitas(String identitas) {
        Identitas = identitas;
    }

    public String getTanggalLahir() {
        return TanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        TanggalLahir = tanggalLahir;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
