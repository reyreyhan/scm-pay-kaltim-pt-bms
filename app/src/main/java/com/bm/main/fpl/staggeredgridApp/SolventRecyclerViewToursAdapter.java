package com.bm.main.fpl.staggeredgridApp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.main.scm.R;
import com.bm.main.fpl.templates.MaterialRippleLayout;

import java.util.List;

public class SolventRecyclerViewToursAdapter extends RecyclerView.Adapter<SolventViewTravelHolders> {

    private List<MenuToursModel> itemList;
    private Context context;
    OnClickMenuTravel listener;
   // LayoutInflater layoutInflater;
    public SolventRecyclerViewToursAdapter(Context context, List<MenuToursModel> itemList,OnClickMenuTravel listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener=listener;
      //  layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public SolventViewTravelHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //SolventViewHolders rcv = new SolventViewHolders(layoutView);

        return new SolventViewTravelHolders(MaterialRippleLayout.on(inflater.inflate(R.layout.solvent_list, parent, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                //.rippleColor(0xFF585858)
                .rippleColor(R.color.colorPrimary_ppob)
                .rippleHover(true)
                .create()
         );
//        View v = inflater.inflate(R.layout.solvent_list,parent,false);
//        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull SolventViewTravelHolders holder, final int position) {
//        if(itemList.get(position).getName().equalsIgnoreCase("exspedisi")||
//                itemList.get(position).getName().equalsIgnoreCase("edukasi")){
//            holder.frame_ribbon.setVisibility(View.VISIBLE);
//            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_new));
//        }else if(itemList.get(position).getName().equalsIgnoreCase("tiketing")){
//            holder.frame_ribbon.setVisibility(View.VISIBLE);
//            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_coming_soon));
//        }
        holder.frame_ribbon.setVisibility(View.GONE);
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
//        int[] number = {2,4,6,7};
//
//        if(contains(number, position)){
//            holder.card_view.setVisibility(View.GONE);
//        }
//        if(position==4){
//            holder.frame_ribbon.setVisibility(View.VISIBLE);
//            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_coming_soon));
//        }
       // Log.d("adapter", "onBindViewHolder: "+position);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickMenuTravel(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView countryName;
//        ImageView countryPhoto, image_ribbon;
//
//        public ShimmerFrameLayout frame_ribbon;
//        public CardView card_view;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            card_view = itemView.findViewById(R.id.card_view);
//            image_ribbon = itemView.findViewById(R.id.image_ribbon);
//            countryName = itemView.findViewById(R.id.country_name);
//            countryPhoto = itemView.findViewById(R.id.country_photo);
//            frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
//            frame_ribbon.stopShimmerAnimation();
//            shimmer.selectPreset(0, frame_ribbon);
////            MaterialRippleLayout.on(card_view).rippleOverlay(true)
////                    .rippleAlpha(0.2f)
////                    .rippleColor(R.color.colorPrimary)
////                    .rippleHover(true)
////                    .create();
//        }
//
//
//    }
        public static boolean contains(@NonNull final int[] array, final int v) {

        boolean result = false;

        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }

        return result;
    }

    public interface OnClickMenuTravel {
        void onClickMenuTravel(int position);
    }
}
