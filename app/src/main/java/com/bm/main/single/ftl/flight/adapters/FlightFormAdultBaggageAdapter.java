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


import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.flight.models.FlightFormBaggageModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FlightFormAdultBaggageAdapter extends RecyclerView.Adapter<FlightFormAdultBaggageAdapter.ViewHolder> {
    public static final String TAG = FlightFormAdultBaggageAdapter.class.getSimpleName();
    private Context context;
    public ArrayList<FlightFormBaggageModel> mDisplayList = new ArrayList<>();//= Collections.emptyList();;
    public ArrayList<FlightFormBaggageModel> mDisplayListFilter = new ArrayList<>();
    LayoutInflater inflater;
    public OnClickFormBaggage listener;
   // public FlightForm_Adult_SectionBaggageAdapter flightForm_adult_sectionBaggageAdapter;
   public FlightFormSectionListDataPassagerAdapter itemListDataAdapter;

    public FlightFormAdultBaggageAdapter(Context context, ArrayList<FlightFormBaggageModel> itemList,OnClickFormBaggage listener) {
        this.context = context;
        this.mDisplayList = itemList;
        this.mDisplayListFilter = itemList;
        this.listener=listener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = inflater.inflate(R.layout.flight_form_adult_baggage_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final FlightFormBaggageModel c= mDisplayListFilter.get(position);
        Glide.with(context).load(c.getFlightIcon()).into(holder.imageAirlineIcon);
        holder.textViewAirlineNameCode.setText(c.getFlightName() + " " + c.getFlightCode()+" "+c.getOrigin()+"->"+c.getDestination());

        ArrayList singleSectionItems = mDisplayListFilter.get(position).getSectionDataPassagerModels();
//        flightForm_adult_sectionBaggageAdapter = new FlightForm_Adult_SectionBaggageAdapter(context, singleSectionItems);

        itemListDataAdapter = new FlightFormSectionListDataPassagerAdapter(context, singleSectionItems,holder,c,position);
//        FlightFormSectionListDataPassagerAdapter.SingleItemRowHolder x = new FlightFormSectionListDataPassagerAdapter.SingleItemRowHolder();
//        if(x.linMainListBaggage() )

        holder.recyclerFlightDetailBaggage.setHasFixedSize(true);
        holder.recyclerFlightDetailBaggage.addItemDecoration(new DividerItemDecoration(context,1));

        holder.recyclerFlightDetailBaggage.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerFlightDetailBaggage.setAdapter(itemListDataAdapter);


        holder.recyclerFlightDetailBaggage.setNestedScrollingEnabled(false);

//        holder.recyclerFlightDetailBaggage.setHasFixedSize(true);
////        holder.recycler_view_list.addItemDecoration(new DividerItemDecoration(mContext,1));
//
//        holder.recyclerFlightDetailBaggage.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        holder.recyclerFlightDetailBaggage.setAdapter(flightForm_adult_sectionBaggageAdapter);
//
//
//        holder.recyclerFlightDetailBaggage.setNestedScrollingEnabled(false);


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

    public class ViewHolder extends RecyclerView.ViewHolder implements FlightFormSectionListDataPassagerAdapter.OnClickBaggage{
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

        TextView itemWeight;


        protected RecyclerView recyclerFlightDetailBaggage;

        public ViewHolder(@NonNull final View view) {
            super(view);
//            linLamaTransit = view.findViewById(R.id.linLamaTransit);
            imageAirlineIcon = view.findViewById(R.id.imageAirlineIcon);
//            imageViewTransitArr = view.findViewById(R.id.imageViewTransitArr);
            textViewAirlineNameCode = view.findViewById(R.id.textViewAirlineNameCode);
            itemWeight = view.findViewById(R.id.itemWeight);
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

        @Override
//        public void onClickBaggage(SingleItemBaggageModel baggage, FlightFormSectionListDataPassagerAdapter.SingleItemRowHolder holder, int adapterPos, FlightFormBaggageModel flightFormBaggageModel) {
        public void onClickBaggage(@NonNull FlightFormBaggageModel flightFormBaggageModel, int row, int col) {
          //  Log.d("BAGGAGE", "onClickBaggage: "+holder.radioButtonBaggage.getTag(R.id.radioGroupKey)+" "+adapterPos);
            this.itemWeight.setText(flightFormBaggageModel.getSectionDataPassagerModels().get(col).getWeight()+" kg");
//            listener.onClickFormBaggage(baggage, holder, adapterPos,flightFormBaggageModel);
            listener.onClickFormBaggage(flightFormBaggageModel,row,col);
        }


    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ArrayList<FlightFormBaggageModel> getData() {
        return this.mDisplayList;
    }

    @Override
    public int getItemCount() {
        if (mDisplayListFilter != null) {
            return mDisplayListFilter.size();
        }
        return 0;
    }

    public interface OnClickFormBaggage {
//        void onClickFormBaggage(SingleItemBaggageModel baggage, FlightFormSectionListDataPassagerAdapter.SingleItemRowHolder holder, int adapterPos, FlightFormBaggageModel flightFormBaggageModel);
        void onClickFormBaggage(FlightFormBaggageModel flightFormBaggageModel, int row, int col);
    }


}
