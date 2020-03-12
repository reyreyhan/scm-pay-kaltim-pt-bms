package com.bm.main.fpl.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bm.main.fpl.models.KodePosModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.pos.R;

import java.util.ArrayList;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListKodePosAdapter extends RecyclerView.Adapter<ListKodePosAdapter.ViewHolder> implements Filterable {
    ArrayList<KodePosModel.Response_value>data = new ArrayList<>();
    ArrayList<KodePosModel.Response_value>filterData = new ArrayList<>();
    Context context;
    OnClickProduk listener;
    @NonNull
    private final LayoutInflater inflater;
    public ListKodePosAdapter(ArrayList<KodePosModel.Response_value> data, @NonNull Context context, OnClickProduk listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public ListKodePosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v;
         v = inflater.inflate(R.layout.list_wilayah,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ListKodePosAdapter.ViewHolder viewHolder, int i) {
        final KodePosModel.Response_value produk = filterData.get(i);
        viewHolder.textViewPlusNamaProduk.setText(produk.getDetail());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.produkCode,produk.getKode_pos());


//
        viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickProduk(produk);
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

                    filterData = data;
                } else {

                    ArrayList<KodePosModel.Response_value> filteredList = new ArrayList<>();

                    for (KodePosModel.Response_value produk : data) {

                        if (produk.getDetail().toLowerCase().contains(charString)  ) {

                            filteredList.add(produk);
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
                filterData = (ArrayList<KodePosModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPlusNamaProduk;

        LinearLayout linMainListProduk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
          //  frame_ribbon =  itemView.findViewById(R.id.frame_ribbon);
            textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaProduk);
           // imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
          //  avi=itemView.findViewById(R.id.avi);

        }
    }
    public interface OnClickProduk {
        public void onClickProduk(KodePosModel.Response_value produk);
    }
}
