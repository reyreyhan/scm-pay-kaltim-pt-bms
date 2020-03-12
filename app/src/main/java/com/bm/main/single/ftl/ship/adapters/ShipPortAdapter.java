package com.bm.main.single.ftl.ship.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.ship.models.ShipDestinationModel;

import java.util.ArrayList;

/**
 * Created by sarifhidayat on 2019-06-25.
 **/
public class ShipPortAdapter extends RecyclerView.Adapter<ShipPortAdapter.ViewHolder> implements Filterable {
    private static final String TAG = ShipPortAdapter.class.getSimpleName();
    ArrayList<ShipDestinationModel.Data> originalData ;//= new ArrayList<>();
    ArrayList<ShipDestinationModel.Data>filterData ;//= new ArrayList<>();
    Context context;
    OnClickPort listener;
    @NonNull
    private final LayoutInflater inflater;

    public ShipPortAdapter(ArrayList<ShipDestinationModel.Data> data, @NonNull Context context, OnClickPort listener){
        this.originalData = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
       // options.inJustDecodeBounds = false;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ShipPortAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =inflater.inflate(R.layout.ship_item_destination,viewGroup,false);
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
    public void onBindViewHolder(@NonNull ShipPortAdapter.ViewHolder viewHolder, int i) {
        final ShipDestinationModel.Data port = filterData.get(i);
        viewHolder.textName.setText(port.getNama());

        viewHolder.linMainListPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickPort(port);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterData.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @NonNull
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filterData = originalData;
                } else {

                    ArrayList<ShipDestinationModel.Data> filteredList = new ArrayList<>();

                    for (ShipDestinationModel.Data port : originalData) {

                        if (port.getNama().toLowerCase().contains(charString) ) {

                            filteredList.add(port);
                        }
                    }

                    filterData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
                filterData = (ArrayList<ShipDestinationModel.Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        LinearLayout linMainListPort;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListPort = itemView.findViewById(R.id.linMainListPort);
//            MaterialRippleLayout.on(linMainListPort).rippleOverlay(true)
//                    .rippleAlpha(0.2f)
//                    //.rippleColor(0xFF585858)
//                    .rippleColor(R.color.colorPrimary)
//                    .rippleHover(true)
//                    .create();
             textName = itemView.findViewById(R.id.textName);

        }
    }

    public interface OnClickPort {
        public void onClickPort(ShipDestinationModel.Data port);
    }
}
