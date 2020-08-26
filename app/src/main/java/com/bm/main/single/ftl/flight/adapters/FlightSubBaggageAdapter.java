package com.bm.main.single.ftl.flight.adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.flight.models.SectionDataPassagerModel;
import com.bm.main.single.ftl.flight.models.SingleItemBaggageModel;

import java.util.ArrayList;

public class FlightSubBaggageAdapter extends RecyclerView.Adapter<FlightSubBaggageAdapter.ItemRowHolder> {

    private ArrayList<SectionDataPassagerModel> dataList;
    private Context mContext;
    public FlightSectionListDataPassagerAdapter itemListDataAdapter;
    public FlightSubBaggageAdapter(Context context, ArrayList<SectionDataPassagerModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

         itemListDataAdapter = new FlightSectionListDataPassagerAdapter(mContext, singleSectionItems,itemRowHolder);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.addItemDecoration(new DividerItemDecoration(mContext,1));

        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);


         itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);


       /*  itemRowHolder.recycler_view_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        //Allow ScrollView to intercept touch events once again.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle RecyclerView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/

//        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Toast.makeText(v.getContext(), "click event on more, "+sectionName , Toast.LENGTH_SHORT).show();
//
//
//
//            }
//        });







       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder implements FlightSectionListDataPassagerAdapter.OnClickBaggage {

        protected TextView itemTitle,itemWeight;

        protected RecyclerView recycler_view_list;


      //  protected Button btnMore;



        public ItemRowHolder(@NonNull View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.itemWeight = (TextView) view.findViewById(R.id.itemWeight);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
          //  this.btnMore= (Button) view.findViewById(R.id.btnMore);


        }

        public void onClickBaggage(@NonNull SingleItemBaggageModel baggage, @NonNull FlightSectionListDataPassagerAdapter.SingleItemRowHolder holder, int adapterPos){
            Log.d("BAGGAGE", "onClickBaggage: "+holder.radioButtonBaggage.getTag(R.id.radioGroupKey)+" "+adapterPos);
            this.itemWeight.setText(baggage.getWeight()+" kg");

//            holder.linMainListBaggage.setBackground(null);
//            holder.linMainListBaggage.refreshDrawableState();
//            holder.linMainListBaggage.setBackgroundResource(R.drawable.button_blue_white_selector);
//            itemListDataAdapter.clearSelections();
//            itemListDataAdapter.toggleSelection(adapterPos);

//            itemListDataAdapter.clearSelections();
//            itemListDataAdapter.toggleSelection(adapterPos);
        }

    }

}