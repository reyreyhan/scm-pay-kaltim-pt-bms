package com.bm.main.single.ftl.flight.models;

import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class SectionDataPassagerModel {



    private String headerTitle;
    private ArrayList<SingleItemBaggageModel> allItemsInSection;


    public SectionDataPassagerModel() {

    }
    public SectionDataPassagerModel(String headerTitle, ArrayList<SingleItemBaggageModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItemBaggageModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemBaggageModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
