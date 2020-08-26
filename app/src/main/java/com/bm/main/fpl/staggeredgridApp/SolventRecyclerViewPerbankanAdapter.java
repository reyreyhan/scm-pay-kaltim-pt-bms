package com.bm.main.fpl.staggeredgridApp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.main.scm.R;
import com.bm.main.fpl.templates.MaterialRippleLayout;

import java.util.List;

public class SolventRecyclerViewPerbankanAdapter extends RecyclerView.Adapter<SolventViewPerbankanHolders> {

    private List<MenuPerbankanModel> itemList;
    private Context context;

    public SolventRecyclerViewPerbankanAdapter(Context context, List<MenuPerbankanModel> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SolventViewPerbankanHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //SolventViewHolders rcv = new SolventViewHolders(layoutView);
        SolventViewPerbankanHolders rcv = new SolventViewPerbankanHolders(MaterialRippleLayout.on(inflater.inflate(R.layout.solvent_list_perbankan, parent, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                //.rippleColor(0xFF585858)
                .rippleColor(R.color.colorPrimary_ppob)
                .rippleHover(true)
                .create()
         );

        return rcv;

    }



    @Override
    public void onBindViewHolder(@NonNull SolventViewPerbankanHolders holder, int position) {
        if(itemList.get(position).getName().equalsIgnoreCase("Laku Pandai")){
            holder.frame_ribbon.setVisibility(View.GONE);
          //  holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_new));
        }else{
            holder.frame_ribbon.setVisibility(View.VISIBLE);
            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_coming_soon));
        }
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
       // Log.d("adapter", "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
