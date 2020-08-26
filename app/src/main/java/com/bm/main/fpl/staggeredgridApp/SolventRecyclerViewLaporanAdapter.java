package com.bm.main.fpl.staggeredgridApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.shimmer;
import com.bm.main.scm.R;

import java.util.List;

public class SolventRecyclerViewLaporanAdapter extends RecyclerView.Adapter<SolventRecyclerViewLaporanAdapter.ViewLaporanHolders> {

    private List<MenuLaporanModel> itemList;
    private Context context;

    public SolventRecyclerViewLaporanAdapter(Context context, List<MenuLaporanModel> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SolventRecyclerViewLaporanAdapter.ViewLaporanHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new ViewLaporanHolders(inflater.inflate(R.layout.solvent_list_perbankan, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull SolventRecyclerViewLaporanAdapter.ViewLaporanHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


    class ViewLaporanHolders extends RecyclerView.ViewHolder {
        TextView countryName;
        ImageView countryPhoto, image_ribbon;
        public ShimmerFrameLayout frame_ribbon;

        public ViewLaporanHolders(@NonNull View itemView) {
            super(itemView);

            image_ribbon = itemView.findViewById(R.id.image_ribbon);
            countryName = itemView.findViewById(R.id.country_name);
            countryPhoto = itemView.findViewById(R.id.country_photo);
            frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
            frame_ribbon.stopShimmerAnimation();
            shimmer.selectPreset(0, frame_ribbon);
        }
    }
}
