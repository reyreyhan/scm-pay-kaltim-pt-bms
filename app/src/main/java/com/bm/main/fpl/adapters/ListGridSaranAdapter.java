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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.SaranKategoriModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;

import java.util.ArrayList;

public class ListGridSaranAdapter extends RecyclerView.Adapter<ListGridSaranAdapter.ViewHolder> implements Filterable {
    private ArrayList<SaranKategoriModel.Response_value>data;// = new ArrayList<>();
    private ArrayList<SaranKategoriModel.Response_value>filterData;// = new ArrayList<>();
    private Context context;
    private OnClickProduk listener;
    SparseBooleanArray selectedItems;

    public ListGridSaranAdapter(ArrayList<SaranKategoriModel.Response_value> data, Context context, OnClickProduk listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        selectedItems=new SparseBooleanArray();
    }
    @NonNull
    @Override
    public ListGridSaranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
         v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_saran_grid,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ListGridSaranAdapter.ViewHolder viewHolder, final int i) {
        final SaranKategoriModel.Response_value produk = filterData.get(i);
        //viewHolder.linMainListProduk.setTag(i);
        viewHolder.linMainListProduk.setSelected(selectedItems.get(i, false));
        viewHolder.textViewNamaKategori.setText(produk.getNama_kategori());
        viewHolder.textViewNamaKategori.setTag(R.id.produkCode,produk.getId_kategori());



            viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // get the item position.
                 int adapterPos = viewHolder.getAdapterPosition();

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

                    ArrayList<SaranKategoriModel.Response_value> filteredList = new ArrayList<>();

                    for (SaranKategoriModel.Response_value produk : data) {

                        if (produk.getNama_kategori().toLowerCase().contains(charString)  ) {

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
                filterData = (ArrayList<SaranKategoriModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaKategori;

       public  RelativeLayout linMainListProduk;
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

            textViewNamaKategori = itemView.findViewById(R.id.textViewNamaKategori);




        }
    }
    public interface OnClickProduk {
        void onClickProduk(SaranKategoriModel.Response_value produk);
    }
}
