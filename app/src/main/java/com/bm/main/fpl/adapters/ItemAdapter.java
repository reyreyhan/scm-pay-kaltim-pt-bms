package com.bm.main.fpl.adapters;

/**
 * Created by sarifhidayat on 3/15/18.
 **/

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.MenuDashboardModel;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.shimmer;

import java.util.List;

//import com.bm.main.fpl.utils.SavePref;

public class ItemAdapter extends DragItemAdapter<Pair<Long, String>, ItemAdapter.SolventViewHolders> {

   // private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    private Context context;
  //  ItemAdapter(ArrayList<Pair<Long, String>> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
    public ItemAdapter(Context context, List<MenuDashboardModel> list, int grabHandleId, boolean dragOnLongPress) {
   // ItemAdapter(ArrayList<Pair<Long, MenuDashboardModel>> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
   // ItemAdapter(List<MenuDashboardModel> list, int grabHandleId, boolean dragOnLongPress) {
    //    mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        this.context = context;
        setItemList(list);
    }

    @NonNull
    @Override
    public SolventViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, parent, false);
        return new SolventViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolventViewHolders holder, int position) {
       // super.onBindViewHolder(holder, position);
     //   String text = mItemList.get(position).second;
//      //  String text = mItemList.get(position).getName();
//        holder.countryName.setText(text);
//        holder.itemView.setTag(mItemList.get(position));
        if(mItemList.get(position).getName().equalsIgnoreCase("exspedisi")||
                mItemList.get(position).getName().equalsIgnoreCase("edukasi")){
            holder.frame_ribbon.setVisibility(View.VISIBLE);
            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_new));
        }else if(mItemList.get(position).getName().equalsIgnoreCase("tiketing")){
            holder.frame_ribbon.setVisibility(View.VISIBLE);
            holder.image_ribbon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_coming_soon));
        }
        holder.countryName.setText(mItemList.get(position).getName());
        holder.countryPhoto.setImageResource(mItemList.get(position).getPhoto());
    }

    @Override
    public long getUniqueItemId(int position) {
       // return mItemList.get(position).first;
        return mItemList.get(position).getPhoto();
    }


    class SolventViewHolders extends DragItemAdapter.ViewHolder {
        public TextView countryName;
        public ImageView countryPhoto,image_ribbon;
//       SavePref savePref;
        public ShimmerFrameLayout frame_ribbon;

        SolventViewHolders(@NonNull final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);

            image_ribbon =  itemView.findViewById(R.id.image_ribbon);
            countryName =  itemView.findViewById(R.id.country_name);
            countryPhoto =itemView.findViewById(R.id.country_photo);
            frame_ribbon =  itemView.findViewById(R.id.frame_ribbon);
           frame_ribbon.stopShimmerAnimation();
            shimmer.selectPreset(0, frame_ribbon);
        }


        @Override
        public void onItemClicked(@NonNull View view) {
            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(@NonNull View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
