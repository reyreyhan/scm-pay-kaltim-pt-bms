package com.bm.main.single.ftl.flight.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.bm.main.fpl.templates.stickylistheaders.StickyListHeadersAdapter;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.flight.models.FlightAirportModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AirportAdapterOld extends BaseAdapter implements Filterable, StickyListHeadersAdapter {
    Context context;
    SearchResultFilter mSearchResultFilter;
    List<FlightAirportModel.Data> mListOriginal;
    List<FlightAirportModel.Data> mListstoredRowItems ;//= new ArrayList<FlightAirportModel>();
    LayoutInflater inflater;
    OnClickAirport listener;

    public AirportAdapterOld(List<FlightAirportModel.Data> itemListOriginal, Context context, OnClickAirport listener) {
        inflater = LayoutInflater.from(context);
        this.mListOriginal = itemListOriginal;
        this.mListstoredRowItems = itemListOriginal;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return mListOriginal.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return mListOriginal.get(position).getGroup();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    TextView text3;

    @Nullable
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
     final   FlightAirportModel.Data c = mListOriginal.get(position);
        View v;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.flight_airport_list_item, null);
        }
        else {
            v = convertView;
        }
       // TextView text1 = v.findViewById(R.id.textCode);
        TextView text2 = v.findViewById(R.id.textName);
        text3 = v.findViewById(R.id.textGroup);
       // text1.setText(c.getCode());
        text2.setText(c.getNama());
        text3.setText(c.getGroup());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAirport(c);
            }
        });
        return v;
    }

    TextView textHeader;

    @Nullable
    @Override
    public View getHeaderView(int position, @Nullable View convertView, ViewGroup parent) {
        View v;
        FlightAirportModel.Data c = mListOriginal.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.flight_header, parent, false);
        }
        else {
            v = convertView;
        }
        textHeader = v.findViewById(R.id.textCity_Header);
        textHeader.setText(c.getGroup());
        return v;
    }

    @Override
    public long getHeaderId(int position) {
        if (mListOriginal != null)
            return mListOriginal.get(position).getGroup().compareTo(text3.getText().toString());//.length();
        return position;
    }


    class ViewHolder {

    }

    @Override
    public Filter getFilter() {
        if (mSearchResultFilter == null)
            mSearchResultFilter = new SearchResultFilter();
        return mSearchResultFilter;
    }

    private class SearchResultFilter extends Filter {
        @NonNull
        @Override
        protected FilterResults performFiltering(@Nullable CharSequence constraint) {
            FilterResults result = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                result.values = mListstoredRowItems;
                result.count = mListstoredRowItems.size();
            }
            else {
                ArrayList<FlightAirportModel.Data> filteredList = new ArrayList<FlightAirportModel.Data>();
                for (FlightAirportModel.Data c : mListstoredRowItems) {
                    if (c.getCode().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            c.getNama().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(c);
                    }
                }
                result.values = filteredList;
                result.count = filteredList.size();
            }

            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, @NonNull FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            }
            else {
                mListOriginal = (ArrayList<FlightAirportModel.Data>) results.values;
                clearListFromDuplicateFirstName(mListOriginal);
                notifyDataSetChanged();
            }
        }

    }

    @NonNull
    private List<FlightAirportModel.Data> clearListFromDuplicateFirstName(@NonNull List<FlightAirportModel.Data> list1) {
        Map<String, FlightAirportModel.Data> cleanMap = new LinkedHashMap<String, FlightAirportModel.Data>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getCode(), list1.get(i));

        }
        List<FlightAirportModel.Data> list = new ArrayList<FlightAirportModel.Data>(cleanMap.values());
        return list;
    }
//    private List<FlightAirportModel.Data> clearListFromDuplicateGroup(List<FlightAirportModel.Data> list1) {
//        Map<String, FlightAirportModel.Data> cleanMap = new LinkedHashMap<String, FlightAirportModel.Data>();
//        for (int i = 0; i < list1.size(); i++) {
//            cleanMap.put(list1.get(i).getGroup(), list1.get(i));
//        }
//        List<FlightAirportModel.Data> list = new ArrayList<FlightAirportModel.Data>(cleanMap.values());
//        return list;
//    }

    public interface OnClickAirport {
         void onClickAirport(FlightAirportModel.Data airport);
    }

}
