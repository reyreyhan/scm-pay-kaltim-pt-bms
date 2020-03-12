package com.bm.main.single.ftl.train.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.fpl.templates.stickylistheaders.StickyListHeadersAdapter;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.train.models.TrainStationModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//public class TrainStationAdapter extends RecyclerView.Adapter<TrainStationAdapter.ViewHolder> implements Filterable {
public class TrainStationAdapter extends BaseAdapter implements Filterable, StickyListHeadersAdapter {
    Context context;
    private SearchResultFilter mSearchResultFilter;
    private List<TrainStationModel.Data> mListOriginal;
    private List<TrainStationModel.Data> mListFilter;//= new ArrayList<FlightAirportModel>();
//   private LayoutInflater layoutInflater;
   // private final int            rowLayout;
    LayoutInflater inflater;
   private OnClickStation listener;

//    public FlightAirportAdapter(List<FlightAirportModel.Data> itemListOriginal, Context context,OnClickAirport listener) {
//        inflater = LayoutInflater.from(context);
//        this.mListOriginal = itemListOriginal;
//        this.mListFilter = itemListOriginal;
//        this.context = context;
//        this.listener=listener;
//    }

    public TrainStationAdapter(List<TrainStationModel.Data> station, @NonNull Context context, OnClickStation listener) {
        this.mListOriginal = station;
        this.mListFilter = station;
        this.context = context;
//        layoutInflater = LayoutInflater.from(context);
        this.listener=listener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.layoutInflater = layoutInflater;
   //     this.rowLayout = rowLayout;
    }

    @Override
    public int getCount() {
        //if (mListOriginal != null)
        return mListFilter.size();
        //  return 0;
    }

    @Override
    public long getItemId(int position) {
        //return 0;
        return position;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        // return null;
        return mListFilter.get(position).getNama_kota();
    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    TextView mTextGroup;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//
       final TrainStationModel.Data station = mListFilter.get(position);
        View view = inflater.inflate(R.layout.train_station_list_item,null);

        LinearLayout mLinListMain = view.findViewById(R.id.lin_list_main);
        TextView mTextCode = view.findViewById(R.id.textCode);
        TextView mTextName = view.findViewById(R.id.textName);
        mTextGroup = view.findViewById(R.id.textGroup);
        if(station.getIs_active()==1) {
            mTextCode.setText(station.getId_stasiun());
            Log.d("bind", "onBindViewHolder: " + station.getNama_stasiun());
            String[] strArrayKota = station.getNama_kota().split(" ");
            StringBuilder builderKota = new StringBuilder();
            for (String s : strArrayKota) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                builderKota.append(cap + " ");
            }
            String[] strArrayStation = station.getNama_stasiun().split(" ");
            StringBuilder builderStation = new StringBuilder();
            for (String s : strArrayStation) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                builderStation.append(cap + " ");
            }
            mTextName.setText(builderStation.toString());
//        String[] strArrayGroup = airPort.getGroup().split(" ");
//        StringBuilder builderGroup = new StringBuilder();
//        for (String s : strArrayGroup) {
//            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//            builderGroup.append(cap + " ");
//        }
//        holder.mTextGroup.setText(airPort.getGroup().toUpperCase());
            mTextGroup.setText(builderKota.toString());



        }
        mLinListMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickStation(station);
            }
        });


return view;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
//        Log.d(Tag.INFO, "getHeaderView: "+position);


        TrainStationModel.Data c = mListFilter.get(position);
        View view = inflater.inflate(R.layout.flight_header,null);

       TextView textCity_Header=view.findViewById(R.id.textCity_Header);
        String[] strArrayKota = c.getNama_kota().split(" ");
        StringBuilder builderKota = new StringBuilder();
        for (String s : strArrayKota) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            builderKota.append(cap + " ");
        }
        textCity_Header.setText(builderKota.toString());

return view;
    }

    @Override
    public long getHeaderId(int position) {
        //Route c = mListOriginal.get(position);
        if (mListFilter != null) {
            String[] strArrayKota = mListFilter.get(position).getNama_kota().split(" ");
            StringBuilder builderKota = new StringBuilder();
            for (String s : strArrayKota) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                builderKota.append(cap + " ");
            }
            //return mListOriginal.get(position).getCity_name().length();
            return builderKota.toString().compareToIgnoreCase(mTextGroup.getText().toString());//.length();
            //return c.getCity_name().lastIndexOf(String.valueOf(headerChar));
        }
        return position;

    }

//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View v = layoutInflater.inflate(R.layout.flight_airport_list_item,
////                parent,
////                false);
////        return new ViewHolder(v);
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_station_list_item,parent,false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final TrainStationModel.Data station = mListFilter.get(position);
//        if(station.getIs_active()==1) {
//            holder.mTextCode.setText(station.getId_stasiun());
//            Log.d("bind", "onBindViewHolder: " + station.getNama_stasiun());
//            String[] strArrayKota = station.getNama_kota().split(" ");
//            StringBuilder builderKota = new StringBuilder();
//            for (String s : strArrayKota) {
//                String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//                builderKota.append(cap + " ");
//            }
//            String[] strArrayStation = station.getNama_stasiun().split(" ");
//            StringBuilder builderStation = new StringBuilder();
//            for (String s : strArrayStation) {
//                String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//                builderStation.append(cap + " ");
//            }
//            holder.mTextName.setText(builderStation.toString());
////        String[] strArrayGroup = airPort.getGroup().split(" ");
////        StringBuilder builderGroup = new StringBuilder();
////        for (String s : strArrayGroup) {
////            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
////            builderGroup.append(cap + " ");
////        }
////        holder.mTextGroup.setText(airPort.getGroup().toUpperCase());
//            holder.mTextGroup.setText(builderKota.toString());
//        }
//        holder.mLinListMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClickStation(station);
//            }
//        });
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mListFilter.size();
//    }

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView mTextCode;
//        private TextView mTextName;
//        private TextView mTextGroup;
//        private LinearLayout mLinListMain;
//
//        public ViewHolder(View view) {
//            super(view);
//            mLinListMain =view.findViewById(R.id.lin_list_main);
//            mTextCode =  view.findViewById(R.id.textCode);
//            mTextName =  view.findViewById(R.id.textName);
//            mTextGroup =  view.findViewById(R.id.textGroup);
//        }
//    }
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
                ArrayList<TrainStationModel.Data> filteredList = new ArrayList<TrainStationModel.Data>();
                for (TrainStationModel.Data c : mListOriginal) {
                    if (
                            c.getNama_kota().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                    c.getNama_stasiun().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            c.getId_stasiun().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            mListFilter = (ArrayList<TrainStationModel.Data>) results.values;
                clearListFromDuplicateFirstName(mListFilter);
                notifyDataSetChanged();
//            }
        }

    }

    private void clearListFromDuplicateFirstName(@NonNull List<TrainStationModel.Data> list1) {
        Map<String, TrainStationModel.Data> cleanMap = new LinkedHashMap<String, TrainStationModel.Data>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getNama_kota(), list1.get(i));

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

    public interface OnClickStation {
        void onClickStation(TrainStationModel.Data station);
    }

}
