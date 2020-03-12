package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.PelangganModel;
import com.bm.main.fpl.templates.TextViewPlus;

import java.util.ArrayList;

/**
 * Created by papahnakal on 13/11/17.
 */

public class PelangganAdapter extends RecyclerView.Adapter<PelangganAdapter.ViewHolder> implements Filterable {
    ArrayList<PelangganModel.Response_value>data = new ArrayList<>();
    ArrayList<PelangganModel.Response_value>filterData = new ArrayList<>();
    Context context;
    OnClickNotification listener;
    public PelangganAdapter(ArrayList<PelangganModel.Response_value> data, Context context, OnClickNotification listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public PelangganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pelanggan,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PelangganAdapter.ViewHolder viewHolder, int i) {
        final PelangganModel.Response_value notif = filterData.get(i);
        viewHolder.textViewPlusIdPel_1.setText(notif.getId_pelanggan_1());
        viewHolder.textViewPlusIdPel_2.setText(notif.getId_pelanggan_2());
        viewHolder.textViewPlusNamaPel.setText(notif.getNama_pelanggan());
        viewHolder.textViewPlusAdditional.setText(notif.getAdditional_data());
//
        viewHolder.linearLayoutItemPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNotif(notif);
            }
        });
        viewHolder.imageViewTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNotifTrash(notif);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterData.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @NonNull
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filterData = data;
                } else {

                    ArrayList<PelangganModel.Response_value> filteredList = new ArrayList<>();

                    for (PelangganModel.Response_value pelanggan : data) {

                        if (pelanggan.getId_pelanggan_1().toLowerCase().contains(charString) || pelanggan.getId_pelanggan_2().toLowerCase().contains(charString) || pelanggan.getNama_pelanggan().toLowerCase().contains(charString)) {

                            filteredList.add(pelanggan);
                        }
                    }

                    filterData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
                filterData = (ArrayList<PelangganModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextViewPlus textViewPlusIdPel_1, textViewPlusIdPel_2,textViewPlusNamaPel,textViewPlusAdditional;
ImageView imageViewTrash;
        LinearLayout linearLayoutItemPelanggan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutItemPelanggan = itemView.findViewById(R.id.linearLayoutItemPelanggan);
            textViewPlusIdPel_1 = itemView.findViewById(R.id.imageViewProduk);
            textViewPlusIdPel_2 = itemView.findViewById(R.id.textViewPlusNamaProduk);
            textViewPlusNamaPel = itemView.findViewById(R.id.textViewPlusNamaPel);
            textViewPlusAdditional = itemView.findViewById(R.id.textViewPlusAdditional);
            imageViewTrash = itemView.findViewById(R.id.imageViewTrash);

        }
    }
    public interface OnClickNotification{
        public void onClickNotif(PelangganModel.Response_value notif);

        void onClickNotifTrash(PelangganModel.Response_value notif);
    }
}
