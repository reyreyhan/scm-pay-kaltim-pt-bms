package com.bm.main.single.ftl.ship.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by sarifhidayat on 2019-06-25.
 **/
public class ShipScheduleTabsAdapter extends RecyclerView.Adapter<ShipScheduleTabsAdapter.ViewHolder> {
    private static final String TAG = ShipScheduleTabsAdapter.class.getSimpleName();
    //    ArrayList<ShipFareModel> originalData = new ArrayList<>();
//    ArrayList<ShipFareModel>filterData = new ArrayList<>();
    ArrayList<String> filterData = new ArrayList<>();
    Context context;
    OnClickScheduleTabs listener;
    @NonNull
    private final LayoutInflater inflater;
    SparseBooleanArray selectedItems;

    public ShipScheduleTabsAdapter(@NonNull Context context, ArrayList<String> data, OnClickScheduleTabs listener) {
//        this.originalData = originalData;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        // options.inJustDecodeBounds = false;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ShipScheduleTabsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.ship_schedule_tab, viewGroup, false);
//        SolventViewHolders rcv = new SolventViewHolders(MaterialRippleLayout.on(v)
//                .rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                //.rippleColor(0xFF585858)
//                .rippleColor(R.color.colorPrimary)
//                .rippleHover(true)
//                .create()
//        );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShipScheduleTabsAdapter.ViewHolder viewHolder, int i) {
//        final ShipFareModel shipFareModel = filterData.get(i);
//        final ShipModel shipModel = shipFareModel.getShipModel();
        final String shipFareModel = filterData.get(i);
        viewHolder.lin_main_item.setSelected(selectedItems.get(i, false));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", SBFApplication.config.locale);
        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);
        try {
//            viewHolder.textTab.setText(odf.format(sdf.parse(shipModel.getDEP_DATE())));
            viewHolder.textTab.setText(odf.format(sdf.parse(shipFareModel)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        viewHolder.textTab.setTag(shipModel.getDEP_DATE());
        viewHolder.textTab.setTag(shipFareModel);

        viewHolder.lin_main_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the item position.
                int adapterPos = viewHolder.getAdapterPosition();

                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (listener != null) {
//        viewHolder.lin_main_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                        //  viewHolder.lin_main_item.setBackground(ContextCompat.getDrawable(context,R.drawable.button_blue_selector));
                        listener.onClickScheduleTabs(shipFareModel, adapterPos);
//
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterData.size();
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    @NonNull
    public ArrayList<Integer> getSelectedItems() {
        ArrayList<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(){
        filterData.clear();
       // da.clear();
        notifyDataSetChanged();
    }
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String charString = charSequence.toString();
//
//                if (charString.isEmpty()) {
//
//                    filterData = originalData;
//                } else {
//
//                    ArrayList<ShipDestinationModel.Data> filteredList = new ArrayList<>();
//
//                    for (ShipDestinationModel.Data port : originalData) {
//
//                        if (port.getNama().toLowerCase().contains(charString) ) {
//
//                            filteredList.add(port);
//                        }
//                    }
//
//                    filterData = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filterData;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filterData = (ArrayList<ShipDestinationModel.Data>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTab;
        LinearLayout lin_main_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lin_main_item = itemView.findViewById(R.id.lin_main_item);
//            MaterialRippleLayout.on(lin_main_item).rippleOverlay(true)
//                    .rippleAlpha(0.2f)
//                    //.rippleColor(0xFF585858)
//                    .rippleColor(R.color.colorPrimary)
//                    .rippleHover(true)
//                    .create();
            lin_main_item.setSelected(false);
            textTab = itemView.findViewById(R.id.textTab);

        }
    }

    public void updateList(ArrayList<String> list) {

        filterData = list;

        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnClickScheduleTabs {
        void onClickScheduleTabs(String port, int adapterPos);
    }
}
