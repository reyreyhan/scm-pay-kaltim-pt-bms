package com.bm.main.single.ftl.flight.adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;


import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.flight.models.FlightFormBaggageModel;
import com.bm.main.single.ftl.flight.models.SingleItemBaggageModel;

import java.util.ArrayList;

public class FlightFormSectionListDataPassagerAdapter extends RecyclerView.Adapter<FlightFormSectionListDataPassagerAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemBaggageModel> itemsList;
    private Context mContext;
    public OnClickBaggage listener;
    public SparseBooleanArray selectedItems;
//    private int lastSelectedPosition = -1;
    FlightFormBaggageModel flightFormBaggageModel;
     int position;

    public FlightFormSectionListDataPassagerAdapter(Context context, ArrayList<SingleItemBaggageModel> itemsList, OnClickBaggage listener, FlightFormBaggageModel c, int position) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.listener = listener;
        this.flightFormBaggageModel=c;
        this.position=position;
        selectedItems=new SparseBooleanArray();
    }

    public FlightFormSectionListDataPassagerAdapter(Context context, ArrayList<SingleItemBaggageModel> itemsList, int position) {
        this.itemsList = itemsList;
        this.mContext = context;
//        this.listener = listener;
//        this.flightFormBaggageModel=c;
        this.position=position;
        selectedItems=new SparseBooleanArray();
    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card_text, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }
//     SingleItemBaggageModel singleItem;
    @Override
    public void onBindViewHolder(@NonNull final SingleItemRowHolder holder, int i) {

       final SingleItemBaggageModel singleItem = itemsList.get(i);
//FormatString.CurencyIDR(singleItem.getPrice());

//        holder.radioButtonBaggage.setText(singleItem.getWeight()+" kg \n Rp "+FormatString.CurencyIDR(singleItem.getPrice()));
        holder.linMainListBaggage.setSelected(selectedItems.get(i, false));
        holder.linMainListBaggage.setTag(R.id.baggage_posisi,singleItem.getBaggage_key());
//       if(i==0){
//           holder.linMainListBaggage.setSelected(selectedItems.get(0, true));
//
//       }
        holder.tvTitleHeader.setText(singleItem.getWeight()+" kg");
        holder.tvTitleContent.setText("Rp "+FormatString.CurencyIDR(singleItem.getPrice()));


        holder.linMainListBaggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the item position.
                int adapterPos = holder.getAdapterPosition();

                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (listener != null) {
                        clearSelections();
                        toggleSelection(adapterPos);
                        setSelected(position,adapterPos);

                        Log.d("FormBaggage", "onClick: "+adapterPos+" "+position);
                        Log.d("FormBaggage", "onClick: "+holder.linMainListBaggage.getTag(R.id.baggage_posisi));
                       // Log.d("FormBaggage", "onClick: "+holder.linMainListBaggage.getLayoutPosition());
//                        listener.onClickBaggage(singleItem,holder,adapterPos,flightFormBaggageModel);
                        listener.onClickBaggage(flightFormBaggageModel,position,adapterPos);
                    }
                }

            }
        });




//        holder.radioButtonBaggage.setTag(R.id.radioGroupKey,singleItem.getBaggage_key());
//        holder.radioButtonBaggage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                lastSelectedPosition = holder.getAdapterPosition();
//////                lastSelectedPosition = holder.radioButtonBaggage.getTag(R.id.radioGroupKey);
////                notifyDataSetChanged();
////                listener.onClickBaggage(singleItem,holder,lastSelectedPosition,flightFormBaggageModel);
//                //  Toast.makeText(OffersRecyclerViewAdapter.this.context,
////                            "selected offer is " + offerName.getText(),
////                            Toast.LENGTH_LONG).show();
//                int adapterPos = holder.getAdapterPosition();
////
//                if (adapterPos != RecyclerView.NO_POSITION) {
//                    if (listener != null) {
//                        clearSelections();
//                        toggleSelection(adapterPos);
//
//                        listener.onClickBaggage(singleItem,holder,adapterPos,flightFormBaggageModel);
//                    }
//                }
//
//            }
//        });
//        holder.tvTitleHeader.setText(singleItem.getWeight()+" kg");
//        holder.tvTitleContent.setText("Rp "+FormatString.CurencyIDR(singleItem.getPrice()));

//        holder.linMainListBaggage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // get the item position.
//                int adapterPos = holder.getAdapterPosition();
//
//                if (adapterPos != RecyclerView.NO_POSITION) {
//                    if (listener != null) {
//                        clearSelections();
//                        toggleSelection(adapterPos);
//
////                        listener.onClickBaggage(singleItem,holder,adapterPos,flightFormBaggageModel);
//                        listener.onClickBaggage(singleItem,flightFormBaggageModel);
//                    }
//                }
//
//            }
//        });

        //holder.radioButtonBaggage.setChecked(lastSelectedPosition == i);

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }
    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    @NonNull
    public ArrayList<Integer> getSelectedItems() {
        ArrayList<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }


    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
   // public LinearLayout linMainListBaggage;
    public  class SingleItemRowHolder extends RecyclerView.ViewHolder {


        protected TextView tvTitleHeader,tvTitleContent;

//        protected ImageView itemImage;
public LinearLayout linMainListBaggage;
public RadioButton radioButtonBaggage;
        public SingleItemRowHolder(@NonNull View view) {
            super(view);

            this.radioButtonBaggage =view.findViewById(R.id.radioButtonBaggage);
            this.tvTitleHeader = (TextView) view.findViewById(R.id.tvTitleHeader);
            this.tvTitleContent = (TextView) view.findViewById(R.id.tvTitleContent);
            this.linMainListBaggage =  view.findViewById(R.id.linMainListBaggage);
//            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            MaterialRippleLayout.on(linMainListBaggage).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary)
                    .rippleHover(true)
                    .create();

//            radioButtonBaggage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    lastSelectedPosition = getAdapterPosition();
//                    notifyDataSetChanged();
//
//                  //  Toast.makeText(OffersRecyclerViewAdapter.this.context,
////                            "selected offer is " + offerName.getText(),
////                            Toast.LENGTH_LONG).show();
//                }
//            });
//            linMainListBaggage.setSelected(false);
          //  linMainListBaggage.setSelected(selectedItems.get(0, true));

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    Toast.makeText(v.getContext(), tvTitleContent.getText(), Toast.LENGTH_SHORT).show();
//
//                }
//            });

//            linMainListBaggage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // get the item position.
//                    int adapterPos = getAdapterPosition();
//
//                    if (adapterPos != RecyclerView.NO_POSITION) {
//                        if (listener != null) {
//                            clearSelections();
//                            toggleSelection(adapterPos);
//
//
//                            Log.d("FormBaggage", "onClick: "+adapterPos);
//                            Log.d("FormBaggage", "onClick: "+linMainListBaggage.getTag(R.id.baggage_posisi));
//                            Log.d("FormBaggage", "onClick: "+getLayoutPosition());
////                        listener.onClickBaggage(singleItem,holder,adapterPos,flightFormBaggageModel);
//                            listener.onClickBaggage(singleItem,flightFormBaggageModel);
//                        }
//                    }
//
//                }
//            });

//            if(0!=RecyclerView.NO_POSITION){
//              //  clearSelections();
//                toggleSelection(0);
//
//
////                Log.d("FormBaggage", "onClick ELSE: "+adapterPos);
//                Log.d("FormBaggage", "onClick ELSE: "+linMainListBaggage.getTag(R.id.baggage_posisi));
//                Log.d("FormBaggage", "onClick ELSE: "+getLayoutPosition());
////                        listener.onClickBaggage(singleItem,holder,adapterPos,flightFormBaggageModel);
//                listener.onClickBaggage(singleItem,flightFormBaggageModel);
//            }



        }

    }

     public interface OnClickBaggage {
//        void onClickBaggage(SingleItemBaggageModel baggage, SingleItemRowHolder holder, int adapterPos, FlightFormBaggageModel flightFormBaggageModel);
        void onClickBaggage(FlightFormBaggageModel flightFormBaggageModel, int row, int col);
    }

    public void setSelected(int row,int col) {
        SingleItemBaggageModel items = itemsList.get(col);
        items.setChecked(!items.isChecked());

     //   linMainListBaggage.setSelected(selectedItems.get(0, false));
//        toggleSelection(row);
//        listener.onClickBaggage(flightFormBaggageModel,row,col);
       // notifyDataSetChanged();
      //  notifyDataSetChanged();
    }

}