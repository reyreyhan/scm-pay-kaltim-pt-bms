package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.NotifTypeModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by papahnakal on 16/11/17.
 */

public class FilterTypeAdapter extends RecyclerView.Adapter<FilterTypeAdapter.Holder> {
    List<NotifTypeModel.Response_value> list;
    String checked;
    RadicoClickedListener listener;
    Context context;

    public FilterTypeAdapter(String checked,List<NotifTypeModel.Response_value> list, RadicoClickedListener listener, Context context) {
        this.list = list;
        this.listener = listener;
        this.context = context;
        this.checked =checked;
    }

    @NonNull
    @Override
    public FilterTypeAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_type_notif,viewGroup,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterTypeAdapter.Holder holder, int i) {
        NotifTypeModel.Response_value data = list.get(i);
        final String id_type = data.getId_type();
        holder.text_type_notif.setText(data.getNama_type());
//        Picasso.with(context)
//                        .load(data.getUrl_icon()).fit().centerCrop().into(holder.img_type);
        Glide.with(context).load(data.getUrl_icon()).fitCenter().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.img_type);
        if(i+1 == Integer.parseInt(checked)){
            holder.radio_type_notif.setChecked(true);
        }else{
            holder.radio_type_notif.setChecked(false);
        }
        holder.radio_type_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.radioClicked(id_type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img_type;
        TextView text_type_notif;
        RadioButton radio_type_notif;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img_type = itemView.findViewById(R.id.img_type_list);
            img_type.getDrawingCache(true);
            text_type_notif = itemView.findViewById(R.id.text_type_list);
            radio_type_notif = itemView.findViewById(R.id.radio_type_list);
        }
    }
    public interface RadicoClickedListener{
        void radioClicked(String type);
    }
}
