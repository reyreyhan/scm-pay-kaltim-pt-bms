package com.bm.main.fpl.staggeredgridApp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bm.main.pos.R;
import com.bm.main.fpl.templates.MaterialRippleLayout;

import java.util.List;

public class SolventRecyclerViewAdminAdapter extends RecyclerView.Adapter<SolventViewHoldersAdmin> {

    private List<ItemObjects> itemList;

    public SolventRecyclerViewAdminAdapter(Context context, List<ItemObjects> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SolventViewHoldersAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new SolventViewHoldersAdmin(MaterialRippleLayout.on(inflater.inflate(R.layout.solvent_list_admin, parent, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(R.color.colorPrimary_ppob)
                .rippleHover(true)
                .create()
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SolventViewHoldersAdmin holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
