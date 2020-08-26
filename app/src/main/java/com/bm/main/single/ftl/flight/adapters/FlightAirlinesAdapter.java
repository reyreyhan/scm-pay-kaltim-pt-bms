package com.bm.main.single.ftl.flight.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.bm.main.scm.R;
import com.bm.main.single.ftl.flight.models.FlightAirlinesModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FlightAirlinesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    Context context;
    public AirlinesViewHolder viewHolder;
     private ArrayList<FlightAirlinesModel> mListOriginal;// = new ArrayList<>();
     private ArrayList<FlightAirlinesModel> mListFilter;// = new ArrayList<>();

    public FlightAirlinesAdapter(@NonNull Context context, ArrayList<FlightAirlinesModel> airLinesList) {
        this.context = context;
        this.mListOriginal = airLinesList;
        this.mListFilter = airLinesList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListFilter.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.flight_airline_list_item, null);
            viewHolder = new AirlinesViewHolder();
            viewHolder.textViewCode = convertView.findViewById(R.id.textViewAirlineCodeFilter);
            viewHolder.textViewPrice = convertView.findViewById(R.id.textViewAirlinePriceFilter);
            viewHolder.textViewName = convertView.findViewById(R.id.textViewAirlineNameFilter);
            viewHolder.imageViewIcon = convertView.findViewById(R.id.imageViewIconAirlineFilter);
            viewHolder.checkBox = convertView.findViewById(R.id.checkBoxAirlineFilter);
            convertView.setTag(viewHolder);
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    FlightAirlinesModel airlines = (FlightAirlinesModel) cb.getTag();
                    airlines.setChecked(cb.isChecked());
                }
            });
        }
        else {
            viewHolder = (AirlinesViewHolder) convertView.getTag();
        }
        FlightAirlinesModel airlinesModel = mListFilter.get(position);
        viewHolder.checkBox.setChecked(airlinesModel.isChecked());
        viewHolder.checkBox.setTag(airlinesModel);
        viewHolder.textViewCode.setText(airlinesModel.getAirLineCode());
        viewHolder.textViewPrice.setText(String.valueOf(airlinesModel.getAirLinePrice()));
        viewHolder.textViewName.setText(airlinesModel.getAirLineNama());
        Glide.with(context).load(airlinesModel.getAirLineIcon())
                .into(viewHolder.imageViewIcon);
        return convertView;
    }

    public class AirlinesViewHolder {
        CheckBox checkBox;
         TextView textViewCode;
         TextView textViewPrice;
         TextView textViewName;
         ImageView imageViewIcon;

    }

    public ArrayList<FlightAirlinesModel> getAllData() {
        return mListOriginal;
    }
    public ArrayList<FlightAirlinesModel> getDataFilter() {
        return mListFilter;
    }

    public void setCheckBox(int position) {
        FlightAirlinesModel items = mListOriginal.get(position);
        items.setChecked(!items.isChecked());
        notifyDataSetChanged();
    }

}



