package com.bm.main.fpl.staggeredgridApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bm.main.pos.R;

import java.util.List;

public class SolventRecyclerViewGasAdapter extends RecyclerView.Adapter<SolventViewGasHolders> {

    private List<MenuGasModel> itemList;

    public SolventRecyclerViewGasAdapter(Context context, List<MenuGasModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SolventViewGasHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SolventViewGasHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list_perbankan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SolventViewGasHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
