package com.bm.main.fpl.staggeredgridApp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bm.main.fpl.activities.GasActivity;
import com.bm.main.fpl.constants.MenuActionCode;
import com.bm.main.fpl.listener.ShowModuleGasOnClickListener;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.shimmer;
import com.bm.main.scm.R;

public class SolventViewGasHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView countryName;
    ImageView countryPhoto, image_ribbon;
    public ShimmerFrameLayout frame_ribbon;


    public SolventViewGasHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        image_ribbon = itemView.findViewById(R.id.image_ribbon);
        countryName = itemView.findViewById(R.id.country_name);
        countryPhoto = itemView.findViewById(R.id.country_photo);
        frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
        frame_ribbon.stopShimmerAnimation();
        shimmer.selectPreset(0, frame_ribbon);
    }

    @Override
    public void onClick(@NonNull View view) {
        GasActivity x = (GasActivity) view.getContext();

        switch (getAdapterPosition()) {
            case 0:
                new ShowModuleGasOnClickListener(x, MenuActionCode.PGN).onClick(view);
                break;
            case 1:
                new ShowModuleGasOnClickListener(x, MenuActionCode.PERTAGAS).onClick(view);
                break;
            default:
        }
    }
}
