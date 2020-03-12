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
import com.bm.main.single.ftl.train.models.TrainFilterModel;

import java.util.ArrayList;

/**
 * Created by sarifhidayat on 9/14/17.
 */

public class KeretaFilterAdapter extends BaseAdapter {
@NonNull
private static String TAG= KeretaFilterAdapter.class.getSimpleName();
    private Context activity;
    private ArrayList<TrainFilterModel> data;
    private ArrayList<TrainFilterModel> dataFilter;
    @Nullable
    private static LayoutInflater inflater = null;
    @Nullable
    private View vi;
    private ViewHolder viewHolder;

    public KeretaFilterAdapter(Context context, ArrayList<TrainFilterModel> items) {
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

        if(view == null) {
            vi = inflater.inflate(R.layout.train_list_item_filter, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = vi.findViewById(R.id.checkBoxKeretaFilter);
            viewHolder.name = vi.findViewById(R.id.textViewKeretaFilter);
            vi.setTag(viewHolder);

            viewHolder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    TrainFilterModel country = (TrainFilterModel) cb.getTag();

                    country.setCheckbox(cb.isChecked());
                }
            });


        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        TrainFilterModel country = dataFilter.get(position);
        //holder.code.setText(" (" +  country.getCode() + ")");
        viewHolder.name.setText(country.getKeretaShow());
        viewHolder.checkBox.setChecked(country.isCheckbox());
        viewHolder.checkBox.setTag(country);

        return vi;
    }
    public ArrayList<TrainFilterModel> getAllData(){
        return data;
    }
    public void setCheckBox(int position){
        //Update status of checkbox
        TrainFilterModel items = data.get(position);
        items.setCheckbox(!items.isCheckbox());
        Log.d(TAG, "setCheckBox: "+items.getKeretaKey());
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView name;
        CheckBox checkBox;

    }

    public void updateList(ArrayList<TrainFilterModel> item){
        this.dataFilter=item;
        notifyDataSetChanged();
    }
}
