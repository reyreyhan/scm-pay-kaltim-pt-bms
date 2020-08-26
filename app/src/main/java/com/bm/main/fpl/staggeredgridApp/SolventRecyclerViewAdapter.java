package com.bm.main.fpl.staggeredgridApp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.shimmer;
import com.bm.main.scm.R;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class SolventRecyclerViewAdapter extends RecyclerView.Adapter<SolventRecyclerViewAdapter.SolventViewHolders> {

    private List<ItemObjectsMenuBeranda> itemList;
    private Context context;

    public SolventRecyclerViewAdapter(Context context, List<ItemObjectsMenuBeranda> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SolventRecyclerViewAdapter.SolventViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new SolventViewHolders(MaterialRippleLayout.on(inflater.inflate(R.layout.solvent_list, parent, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(R.color.colorPrimary_ppob)
                .rippleHover(true)
                .create()
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SolventRecyclerViewAdapter.SolventViewHolders holder, int position) {
        ItemObjectsMenuBeranda item = itemList.get(position);
        if (item.isComingSoon) {
            holder.frame_ribbon.setVisibility(View.VISIBLE);
            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_coming_soon));
        } else if (item.isPromo) {
            holder.frame_ribbon.setVisibility(View.VISIBLE);
            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_promo));
        } else if (item.isNew) {
            holder.frame_ribbon.setVisibility(View.VISIBLE);
            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_new));
        } else {
            holder.frame_ribbon.setVisibility(View.GONE);
        }
        holder.countryName.setText(itemList.get(position).getName());

        String urlPhoto = itemList.get(position).getUrlPhoto();
        if (urlPhoto != null && !urlPhoto.isEmpty()) {
            Glide.with(holder.countryPhoto).load(urlPhoto).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.countryPhoto);
        } else {
            holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class SolventViewHolders extends RecyclerView.ViewHolder {

        TextView countryName;
        ImageView countryPhoto, image_ribbon;
        public ShimmerFrameLayout frame_ribbon;


        public SolventViewHolders(@NonNull View itemView) {
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
