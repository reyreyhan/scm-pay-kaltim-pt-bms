package com.bm.main.single.ftl.utils;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.pos.SBFApplication;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by sarifhidayat on 6/16/17.
 */

public class utilBand {


        public static String formatRupiah(int uang){
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(SBFApplication.config.locale);
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols(SBFApplication.config.locale);
            formatRp.setCurrencySymbol("Rp ");
            formatRp.setGroupingSeparator('.');
            kursIndonesia.setDecimalFormatSymbols(formatRp);
            kursIndonesia.setDecimalSeparatorAlwaysShown(false);
            kursIndonesia.setMinimumFractionDigits(0);
            kursIndonesia.setMaximumFractionDigits(2);
            return kursIndonesia.format(uang);
        }

        public static String rupiahFormate(int money){
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(SBFApplication.config.locale);
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols(SBFApplication.config.locale);
            formatRp.setCurrencySymbol("Rp ");
            formatRp.setGroupingSeparator('.');
            kursIndonesia.setDecimalFormatSymbols(formatRp);

            kursIndonesia.setDecimalSeparatorAlwaysShown(false);
            kursIndonesia.setMinimumFractionDigits(0);
            kursIndonesia.setMaximumFractionDigits(2);

            return kursIndonesia.format(money);
        }


}
