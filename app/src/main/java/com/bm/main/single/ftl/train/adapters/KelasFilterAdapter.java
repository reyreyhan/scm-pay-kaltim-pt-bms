package com.bm.main.single.ftl.train.adapters;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.single.ftl.train.models.TrainKelasFilterModel;

import java.util.ArrayList;

/**
 * Created by sarifhidayat on 9/14/17.
 */

public class KelasFilterAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<TrainKelasFilterModel> data;
    private ArrayList<TrainKelasFilterModel> dataFilter;
    @Nullable
    private static LayoutInflater inflater = null;
    @Nullable
    private View vi;
    private ViewHolder viewHolder;

    public KelasFilterAdapter(Context context, ArrayList<TrainKelasFilterModel> items) {
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

        if(view == null) {
            vi = inflater.inflate(R.layout.train_kelas_list_item_filter, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = vi.findViewById(R.id.checkBoxKelasFilter);
            viewHolder.name = vi.findViewById(R.id.textViewKelasFilter);
            vi.setTag(viewHolder);
            viewHolder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    TrainKelasFilterModel country = (TrainKelasFilterModel) cb.getTag();

                    country.setCheckbox(cb.isChecked());
                }
            });
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TrainKelasFilterModel items = dataFilter.get(position);
        viewHolder.name.setText(items.getKelasShow());
        viewHolder.checkBox.setChecked(items.isCheckbox());
        viewHolder.checkBox.setTag(items);

        return vi;
    }
    public ArrayList<TrainKelasFilterModel> getAllData(){
        return data;
    }
    public void setCheckBox(int position){
        //Update status of checkbox
        TrainKelasFilterModel items = dataFilter.get(position);
        items.setCheckbox(!items.isCheckbox());
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView name;
        CheckBox checkBox;
    }

    public void updateList(ArrayList<TrainKelasFilterModel> item){
        this.dataFilter=item;
        notifyDataSetChanged();
    }
}
