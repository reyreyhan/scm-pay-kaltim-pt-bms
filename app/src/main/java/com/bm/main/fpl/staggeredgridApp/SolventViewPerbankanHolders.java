package com.bm.main.fpl.staggeredgridApp;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.activities.PerbankanActivity;
import com.bm.main.fpl.constants.MenuActionCode;
import com.bm.main.fpl.listener.ShowModulePerbankanOnClickListener;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.shimmer;

//import com.bm.main.fpl.utils.SavePref;


public class SolventViewPerbankanHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView countryName;
     ImageView countryPhoto, image_ribbon;
//    static SavePref savePref;
    public ShimmerFrameLayout frame_ribbon;


    public SolventViewPerbankanHolders(@NonNull View itemView) {
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
//        savePref = SavePref.getInstance(view.getContext());

        PerbankanActivity x = (PerbankanActivity) view.getContext();

        Intent intent;

        switch (getAdapterPosition()) {
            case 0:
                // intent = new Intent(view.getContext(), PlnActivity.class);
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    new ShowModulePerbankanOnClickListener(x, MenuActionCode.BNI).onClick(view);
                    break;
                }

            case 1:
                // intent = new Intent(view.getContext(), PulsaActivity.class);
                break;

//
//                case 2:
//
//                intent = new Intent(view.getContext(), ToursTiketingActivity.class);
//                break;
//            case 3:
//
//                intent = new Intent(view.getContext(), PdamActivity.class);
//            case 4:
//
//                intent = new Intent(view.getContext(), BpjsActivity.class);
//                break;
//
//
//            case 5:
//                intent = new Intent(view.getContext(), IndiHomeActivity.class);
//                break;
//
//            case 6:
//
//                intent = new Intent(view.getContext(), ToursTiketingActivity.class);
//                break;
//
//
//
//            default:
//                    intent = new Intent(view.getContext(), ToursTiketingActivity.class);
//
//                break;
        }
//
//        x.startActivity(intent);
//        x.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
