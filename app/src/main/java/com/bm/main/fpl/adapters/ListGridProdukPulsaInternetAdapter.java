package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.ProdukPulsaInternetModel;
import com.bm.main.fpl.templates.AutoResizeTextView;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class ListGridProdukPulsaInternetAdapter extends RecyclerView.Adapter<ListGridProdukPulsaInternetAdapter.ViewHolder> implements Filterable {
    private ArrayList<ProdukPulsaInternetModel.Response_value>data = new ArrayList<>();
    private ArrayList<ProdukPulsaInternetModel.Response_value>filterData = new ArrayList<>();
    private Context context;
    private OnClickProduk listener;
    SparseBooleanArray selectedItems;

    public ListGridProdukPulsaInternetAdapter(ArrayList<ProdukPulsaInternetModel.Response_value> data, Context context, OnClickProduk listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        selectedItems=new SparseBooleanArray();
    }
    @NonNull
    @Override
    public ListGridProdukPulsaInternetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_produk_grid,viewGroup,false);
//        ViewHolder rcv = new ViewHolder(MaterialRippleLayout.on(v)
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
    public void onBindViewHolder(@NonNull final ListGridProdukPulsaInternetAdapter.ViewHolder viewHolder, final int i) {
        final ProdukPulsaInternetModel.Response_value produk = filterData.get(i);
        //viewHolder.linMainListProduk.setTag(i);
        viewHolder.linMainListProduk.setSelected(selectedItems.get(i, false));
        viewHolder.textViewPlusNamaProduk.setText(produk.getProduct_name());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.produkCode,produk.getProduct_code());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.isGangguanProduk,produk.getIs_gangguan());

//        Picasso. with(context)
//                        .load(produk.getProduct_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.avi.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
////                if (produk.getProduct_code().startsWith("WA")) {
////                    viewHolder.imageViewProduk.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
////                } else {
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo_color));
//              //  }
//            }
//
//        });




      //  viewHolder.linMainListProduk.setSelected(false);
      //  viewHolder.linMainListProduk.setSelected(false);



            viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // get the item position.
                 int adapterPos = viewHolder.getAdapterPosition();
             //   Integer pos;
             //   pos = (Integer) v.getTag();

//                if(i==adapterPos){
//                    viewHolder.linMainListProduk.setSelected(true);
//                    // selectedItems.put(adapterPos, true);
//                }else{
//                    //  selectedItems.delete(adapterPos);
//                    viewHolder.linMainListProduk.setSelected(false);
//                }
               // notifyDataSetChanged();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (listener != null) {
                        clearSelections();
                        toggleSelection(adapterPos);

                        listener.onClickProduk(produk);
                    }
                }

            }
        });

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

                    ArrayList<ProdukPulsaInternetModel.Response_value> filteredList = new ArrayList<>();

                    for (ProdukPulsaInternetModel.Response_value produk : data) {

                        if (produk.getProduct_code().toLowerCase().contains(charString) || produk.getProduct_name().toLowerCase().contains(charString) ) {

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
                filterData = (ArrayList<ProdukPulsaInternetModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AutoResizeTextView textViewPlusNamaProduk;
        ImageView imageViewProduk;
        AVLoadingIndicatorView avi;
       public RelativeLayout linMainListProduk;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
            linMainListProduk.setSelected(false);
            textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaProduk);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            avi=itemView.findViewById(R.id.avi);



        }
    }
    public interface OnClickProduk {
        void onClickProduk(ProdukPulsaInternetModel.Response_value produk);
    }
}
