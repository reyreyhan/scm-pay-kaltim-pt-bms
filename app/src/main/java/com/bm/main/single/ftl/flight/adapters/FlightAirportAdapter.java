package com.bm.main.single.ftl.flight.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bm.main.scm.R;
import com.bm.main.single.ftl.flight.models.FlightAirportModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlightAirportAdapter extends RecyclerView.Adapter<FlightAirportAdapter.ViewHolder> implements Filterable {
    Context context;
    private SearchResultFilter mSearchResultFilter;
    private List<FlightAirportModel.Data> mListOriginal;
    private List<FlightAirportModel.Data> mListFilter;//= new ArrayList<FlightAirportModel>();
//   private LayoutInflater layoutInflater;
   // private final int            rowLayout;
    //LayoutInflater inflater;
   private OnClickAirport listener;

//    public FlightAirportAdapter(List<FlightAirportModel.Data> itemListOriginal, Context context,OnClickAirport listener) {
//        inflater = LayoutInflater.from(context);
//        this.mListOriginal = itemListOriginal;
//        this.mListFilter = itemListOriginal;
//        this.context = context;
//        this.listener=listener;
//    }

    public FlightAirportAdapter(List<FlightAirportModel.Data> airPort, Context context, OnClickAirport listener) {
        this.mListOriginal = airPort;
        this.mListFilter = airPort;
        this.context = context;
//        layoutInflater = LayoutInflater.from(context);
        this.listener=listener;
        //this.layoutInflater = layoutInflater;
   //     this.rowLayout = rowLayout;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = layoutInflater.inflate(R.layout.flight_airport_list_item,
//                parent,
//                false);
//        return new ViewHolder(v);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_airport_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FlightAirportModel.Data airPort = mListFilter.get(position);
        holder.mTextCode.setText(airPort.getCode());
        Log.d("bind", "onBindViewHolder: "+airPort.getNama());
        String[] strArrayBandara = airPort.getBandara().split(" ");
        StringBuilder builderBandara = new StringBuilder();
        for (String s : strArrayBandara) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            builderBandara.append(cap + " ");
        }

        holder.mTextName.setText(builderBandara.toString()+" - "+airPort.getNama());
//        String[] strArrayGroup = airPort.getGroup().split(" ");
//        StringBuilder builderGroup = new StringBuilder();
//        for (String s : strArrayGroup) {
//            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//            builderGroup.append(cap + " ");
//        }
//        holder.mTextGroup.setText(airPort.getGroup().toUpperCase());
        holder.mTextGroup.setText(airPort.getGroup());
        holder.mLinListMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAirport(airPort);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextCode;
        private TextView mTextName;
        private TextView mTextGroup;
        private LinearLayout mLinListMain;

        public ViewHolder(@NonNull View view) {
            super(view);
            mLinListMain =view.findViewById(R.id.lin_list_main);
            mTextCode =  view.findViewById(R.id.textCode);
            mTextName =  view.findViewById(R.id.textName);
            mTextGroup =  view.findViewById(R.id.textGroup);
        }
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
                result.values = mListOriginal;
                result.count = mListOriginal.size();
            }
            else {
                ArrayList<FlightAirportModel.Data> filteredList = new ArrayList<FlightAirportModel.Data>();
                for (FlightAirportModel.Data c : mListOriginal) {
                    if (c.getCode().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                    c.getBandara().toLowerCase().contains(constraint.toString().toLowerCase()) ||
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
//            if (results.count == 0) {
//                notifyDataSetInvalidated();
//            }
//            else {
            mListFilter = (ArrayList<FlightAirportModel.Data>) results.values;
                clearListFromDuplicateFirstName(mListFilter);
                notifyDataSetChanged();
//            }
        }

    }

    private void clearListFromDuplicateFirstName(@NonNull List<FlightAirportModel.Data> list1) {
        Map<String, FlightAirportModel.Data> cleanMap = new LinkedHashMap<String, FlightAirportModel.Data>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getCode(), list1.get(i));

        }
        cleanMap.values();
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
