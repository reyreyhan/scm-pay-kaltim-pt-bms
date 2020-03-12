package com.bm.main.single.ftl.train.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.bm.main.pos.R;
import com.bm.main.single.ftl.train.models.TrainTimeFilterModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sarifhidayat on 9/14/17.
 */

public class TimeFilterAdapter extends BaseAdapter {

    private Context activity;
     ArrayList<TrainTimeFilterModel> data;
     ArrayList<TrainTimeFilterModel> dataFilter;
    @Nullable
    private static LayoutInflater inflater = null;
     @Nullable
     View vi;
     ViewHolder viewHolder;

    public TimeFilterAdapter(Context context, ArrayList<TrainTimeFilterModel> items) {
        this.activity = context;
        this.data = items;
        this.dataFilter = items;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataFilter.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View view, ViewGroup viewGroup) {
        vi = view;
        //Populate the Listview
       // final int pos = position;
//        TrainTimeFilterModel items = data.get(pos);
        if(view == null) {
            vi = inflater.inflate(R.layout.train_time_list_item_filter, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = vi.findViewById(R.id.checkBoxTimeFilter);
            viewHolder.name = vi.findViewById(R.id.textViewTimeFilter);
            vi.setTag(viewHolder);

            viewHolder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    TrainTimeFilterModel country = (TrainTimeFilterModel) cb.getTag();

                    country.setCheckbox(cb.isChecked());
                }
            });
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TrainTimeFilterModel items = dataFilter.get(position);
        viewHolder.name.setText(items.getTimeShow());

        viewHolder.checkBox.setChecked(items.isCheckbox());
        viewHolder.checkBox.setTag(items);
        Log.d("time filter", "getView: "+items.getTimeKey());

        return vi;
    }
    public ArrayList<TrainTimeFilterModel> getAllData(){
        return data;
    }
    public void setCheckBox(int position){
        //Update status of checkbox
        TrainTimeFilterModel items = dataFilter.get(position);
        items.setCheckbox(!items.isCheckbox());
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView name;
        CheckBox checkBox;
    }

    public void updateList(ArrayList<TrainTimeFilterModel> item){
        this.dataFilter=item;
        this.data=item;
        notifyDataSetChanged();
    }

    @NonNull
    private ArrayList<TrainTimeFilterModel> clearListFromDuplicateTime(@NonNull ArrayList<TrainTimeFilterModel> list1) {

        Map<String, TrainTimeFilterModel> cleanMap = new LinkedHashMap<String, TrainTimeFilterModel>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(String.valueOf(list1.get(i).getTimeKey()), list1.get(i));

        }
        ArrayList<TrainTimeFilterModel> list = new ArrayList<TrainTimeFilterModel>(cleanMap.values());
        return list;
    }
}
