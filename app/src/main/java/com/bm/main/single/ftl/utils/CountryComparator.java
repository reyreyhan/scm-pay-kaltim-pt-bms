package com.bm.main.single.ftl.utils;

import androidx.annotation.NonNull;

import com.bm.main.single.ftl.models.Country;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by sarifhidayat on 2019-08-13.
 **/
public class CountryComparator implements Comparator<Country> {
    private Comparator<Object> comparator;

    public CountryComparator() {
        comparator = Collator.getInstance();
    }

    public int compare(@NonNull Country c1, @NonNull Country c2) {
        return comparator.compare(c1.name, c2.name);
    }
}


