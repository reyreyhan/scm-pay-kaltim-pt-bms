package com.bm.main.single.ftl.flight.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bm.main.scm.R;
import com.bm.main.single.ftl.flight.models.FlightDetailModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FlightDetailAdapter extends RecyclerView.Adapter<FlightDetailAdapter.ViewHolder> {
    public static final String TAG = FlightDetailAdapter.class.getSimpleName();
    private Context context;
    public ArrayList<FlightDetailModel> mDisplayList = new ArrayList<>();//= Collections.emptyList();;
    public ArrayList<FlightDetailModel> mDisplayListFilter = new ArrayList<>();
    LayoutInflater inflater;

    public FlightDetailAdapter(Context context, ArrayList<FlightDetailModel> itemList) {
        this.context = context;
        this.mDisplayList = itemList;
        this.mDisplayListFilter = itemList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = inflater.inflate(R.layout.flight_detail_title_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final FlightDetailModel c;
        c = mDisplayListFilter.get(position);
        Glide.with(context).load(c.getFlightIcon())
                .into(holder.imageAirlineIcon);
        holder.textViewAirlineNameCode.setText(c.getFlightName() + " " + c.getFlightCode());
        holder.textViewOrigin.setText(c.getOriginName());
        holder.textViewDeptTime.setText(c.getDepart());
        holder.textViewDurasi.setText(c.getDurationDetail());
        holder.textViewDest.setText(c.getDestinationName());
        holder.textViewTransitArrTime.setText(c.getArrival());
        holder.textViewDurasiTransit.setText(c.getTransitTime());

        if (c.getInitTransit().equals("Tiba")) {
            holder.textViewFlagTransitArr.setText(c.getInitTransit());
            holder.textViewFlagTransitArr.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        else if (c.getInitTransit().equals("Transit")) {

//            String code1=c.getFlightCode();
//            String code2=c.getFlightCode();
//            Log.d(TAG, "onBindViewHolder: "+code1+" "+code2);

            holder.textViewFlagTransitArr.setText(c.getInitTransit()+" "+mDisplayListFilter.get(position+1).getTransitTime());
            holder.textViewFlagTransitArr.setTextColor(context.getResources().getColor(R.color.md_red_500));
            holder.imageViewTransitArr.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.jp_book_transit));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageAirlineIcon, imageViewTransitArr;
        public TextView textViewAirlineNameCode;
        public TextView textViewOrigin;
        public TextView textViewDest;
        public TextView textViewFlagTransitArr;
        public TextView textViewDeptDate;
        public TextView textViewTransitArrDate;
        public TextView textViewDeptTime, textViewTransitArrTime;
        public TextView textViewDurasi, textViewDurasiTransit;
        LinearLayout linLamaTransit;

        public ViewHolder(@NonNull final View view) {
            super(view);
            linLamaTransit = view.findViewById(R.id.linLamaTransit);
            imageAirlineIcon = view.findViewById(R.id.imageAirlineIcon);
            imageViewTransitArr = view.findViewById(R.id.imageViewTransitArr);
            textViewAirlineNameCode = view.findViewById(R.id.textViewAirlineNameCode);
            textViewOrigin = view.findViewById(R.id.textViewOrigin);
            textViewDest = view.findViewById(R.id.textViewDest);
            textViewFlagTransitArr = view.findViewById(R.id.textViewFlagTransitArr);
            textViewDeptDate = view.findViewById(R.id.textViewDeptDate);
            textViewTransitArrDate = view.findViewById(R.id.textViewTransitArrDate);
            textViewDeptTime = view.findViewById(R.id.textViewDeptTime);
            textViewTransitArrTime = view.findViewById(R.id.textViewTransitArrTime);
            textViewDurasi = view.findViewById(R.id.textViewDurasi);
            textViewDurasiTransit = view.findViewById(R.id.textViewDurasiTransit);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ArrayList<FlightDetailModel> getData() {
        return this.mDisplayList;
    }

    @Override
    public int getItemCount() {
        if (mDisplayListFilter != null) {
            return mDisplayListFilter.size();
        }
        return 0;
    }

}
