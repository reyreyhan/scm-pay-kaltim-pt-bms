package com.bm.main.single.ftl.utils;

import android.content.Context;
import androidx.annotation.NonNull;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.constants.TravelKeyPreference;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * Created by sarif on 18/02/16.
 */
public class FormatUtil {


    public static String FormatMoneySymbolVoucher(@NonNull String _money) {
       String money="0";
        Long price=0L;
        try {
          // .. money=_money;
           price= Long.valueOf(_money);
//            MemoryStore.set( "voucher", _money);
            PreferenceClass.putString(TravelKeyPreference.voucher, _money);
        } catch (NumberFormatException e) {
          //  price=0L;
//            MemoryStore.set( "voucher", "0");
            PreferenceClass.putString(TravelKeyPreference.voucher, "0");
        }

       // Long price=Long.valueOf(money);
//        Locale locale = new Locale("id", "ID");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(SBFApplication.config.locale);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(price);
    }
    public static String FormatMoneySymbol(@NonNull String _money) {
       String money="0";
        Long price=0L;
        try {
          // .. money=_money;
           price= Long.valueOf(_money);
        } catch (NumberFormatException e) {
          //  price=0L;
        }

       // Long price=Long.valueOf(money);
//        Locale locale = new Locale("id", "ID");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(SBFApplication.config.locale);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(price);
    }
public static String FormatMoneySymbolInputVoucher(@NonNull String _money) {
       String money="0";
        Long price=0L;
        try {
          // .. money=_money;
           price= Long.valueOf(_money);
        } catch (NumberFormatException e) {
          //  price=0L;
        }

       // Long price=Long.valueOf(money);
//        Locale locale = new Locale("id", "ID");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(SBFApplication.config.locale);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setGroupingSeparator('.');
        formatRp.setMinusSign('-');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(price);
    }

    public static String FormatMoney(@NonNull String money) {
        Long price = 0L;
        try {
            price = Long.valueOf(money);
        } catch (Exception e) {
            //price=0L;
        }
//        Locale locale = new Locale("id", "ID");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(SBFApplication.config.locale);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        //formatRp.setCurrencySymbol("Rp. ");
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(price);
    }

    public static int getInt(Context context, String key) {
        return (PreferenceClass.getString(key, "") != null ? Integer.parseInt(PreferenceClass.getString( key,"").toString()) : PreferenceClass.getInt( key,0));
       // return (MemoryStore.get( key) instanceof String ? Integer.parseInt(MemoryStore.get( key).toString()) : (int) MemoryStore.get( key));
    }



    @NonNull
    public static <K extends Comparable,V extends Comparable> Map<K,V> sortByKeys(@NonNull Map<K,V> map){
        List<K> keys = new LinkedList<K>(map.keySet());
        Collections.sort(keys);

        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K,V> sortedMap = new LinkedHashMap<>();
        for(K key: keys){
            sortedMap.put(key, map.get(key));
        }

        return sortedMap;
    }


    public static boolean checkNumberFromString(String msg){
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        //String msg = "What is the square of 10?";
        return  pattern.matcher(msg).matches();
    }



}
