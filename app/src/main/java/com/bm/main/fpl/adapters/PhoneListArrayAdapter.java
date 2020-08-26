package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bm.main.scm.R;

import java.util.List;

public class PhoneListArrayAdapter extends ArrayAdapter<String> {
     @NonNull
     final List<String> list;
    Context context;

    static class ViewHolder {
        protected TextView name;
//        protected ImageView flag;
    }

    public PhoneListArrayAdapter(@NonNull Context context, @NonNull List<String> list) {

        super(context, R.layout.layout_item_phonenumber, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (convertView == null) {
//            LayoutInflater inflator = context.getLayoutInflater();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phonenumber, parent);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.textViewItemPhone);
//            viewHolder.flag = (ImageView) view.findViewById(R.id.flag);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position));
//        holder.flag.setImageDrawable(list.get(position).getFlag());
        return view;
    }

}
