package com.bm.main.fpl.staggeredgridApp;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.shimmer;


public class SolventViewTravelHolders extends RecyclerView.ViewHolder {

    TextView countryName;
    ImageView countryPhoto, image_ribbon;

    public ShimmerFrameLayout frame_ribbon;
    public CardView card_view;

    public SolventViewTravelHolders(@NonNull View itemView) {
        super(itemView);

        card_view = itemView.findViewById(R.id.card_view);
        image_ribbon = itemView.findViewById(R.id.image_ribbon);
        countryName = itemView.findViewById(R.id.country_name);
        countryPhoto = itemView.findViewById(R.id.country_photo);
        frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
        frame_ribbon.stopShimmerAnimation();
        shimmer.selectPreset(0, frame_ribbon);

    }

}
