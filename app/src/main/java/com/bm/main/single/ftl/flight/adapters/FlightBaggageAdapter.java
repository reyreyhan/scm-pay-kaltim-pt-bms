package com.bm.main.single.ftl.flight.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bm.main.scm.R;
import com.bm.main.single.ftl.flight.models.FlightBaggageDetailModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FlightBaggageAdapter extends RecyclerView.Adapter<FlightBaggageAdapter.ViewHolder> {
    public static final String TAG = FlightBaggageAdapter.class.getSimpleName();
    private Context context;
    public ArrayList<FlightBaggageDetailModel> mDisplayList = new ArrayList<>();//= Collections.emptyList();;
    public ArrayList<FlightBaggageDetailModel> mDisplayListFilter = new ArrayList<>();
    LayoutInflater inflater;

    FlightSubBaggageAdapter flightSubBaggageAdapter;

    public FlightBaggageAdapter(Context context, ArrayList<FlightBaggageDetailModel> itemList) {
        this.context = context;
        this.mDisplayList = itemList;
        this.mDisplayListFilter = itemList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = inflater.inflate(R.layout.flight_detail_baggage_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final FlightBaggageDetailModel c= mDisplayListFilter.get(position);
        Glide.with(context).load(c.getFlightIcon()).into(holder.imageAirlineIcon);
        holder.textViewAirlineNameCode.setText(c.getFlightName() + " " + c.getFlightCode()+" "+c.getOrigin()+"->"+c.getDestination());

        ArrayList singleSectionItems = mDisplayListFilter.get(position).getSectionDataPassagerModels();
        flightSubBaggageAdapter = new FlightSubBaggageAdapter(context, singleSectionItems);

        holder.recyclerFlightDetailBaggage.setHasFixedSize(true);
//        holder.recycler_view_list.addItemDecoration(new DividerItemDecoration(mContext,1));

        holder.recyclerFlightDetailBaggage.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.recyclerFlightDetailBaggage.setAdapter(flightSubBaggageAdapter);


        holder.recyclerFlightDetailBaggage.setNestedScrollingEnabled(false);


//        holder.textViewOrigin.setText(c.getOriginName());
//        holder.textViewDeptTime.setText(c.getDepart());
//        holder.textViewDurasi.setText(c.getDurationDetail());
//        holder.textViewDest.setText(c.getDestinationName());
//        holder.textViewTransitArrTime.setText(c.getArrival());
//        holder.textViewDurasiTransit.setText(c.getTransitTime());
//        holder.textViewFlagTransitArr.setText(c.getInitTransit());
//        if (c.getInitTransit().equals("Tiba")) {
//            holder.textViewFlagTransitArr.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//        }
//        else if (c.getInitTransit().equals("Transit")) {
//            holder.textViewFlagTransitArr.setTextColor(context.getResources().getColor(R.color.md_red_500));
//            holder.imageViewTransitArr.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.jp_book_transit));
//        }
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


        protected RecyclerView recyclerFlightDetailBaggage;

        public ViewHolder(@NonNull final View view) {
            super(view);
//            linLamaTransit = view.findViewById(R.id.linLamaTransit);
            imageAirlineIcon = view.findViewById(R.id.imageAirlineIcon);
//            imageViewTransitArr = view.findViewById(R.id.imageViewTransitArr);
            textViewAirlineNameCode = view.findViewById(R.id.textViewAirlineNameCode);
//            textViewOrigin = view.findViewById(R.id.textViewOrigin);
//            textViewDest = view.findViewById(R.id.textViewDest);
//            textViewFlagTransitArr = view.findViewById(R.id.textViewFlagTransitArr);
//            textViewDeptDate = view.findViewById(R.id.textViewDeptDate);
//            textViewTransitArrDate = view.findViewById(R.id.textViewTransitArrDate);
//            textViewDeptTime = view.findViewById(R.id.textViewDeptTime);
//            textViewTransitArrTime = view.findViewById(R.id.textViewTransitArrTime);
//            textViewDurasi = view.findViewById(R.id.textViewDurasi);
//            textViewDurasiTransit = view.findViewById(R.id.textViewDurasiTransit);

            recyclerFlightDetailBaggage=view.findViewById(R.id.recyclerFlightDetailBaggage);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ArrayList<FlightBaggageDetailModel> getData() {
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
