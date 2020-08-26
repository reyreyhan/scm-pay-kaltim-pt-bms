package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.PaketModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class ListGridPaketAdapter extends RecyclerView.Adapter<ListGridPaketAdapter.ViewHolder>  {
    private ArrayList<PaketModel.Response_value>data ;//= new ArrayList<>();
   // private ArrayList<PaketModel.Response_value>filterData = new ArrayList<>();
     Context context;
    private OnClickProduk listener;
    SparseBooleanArray selectedItems;
public int mSelectedItem = -1;

    public ListGridPaketAdapter(ArrayList<PaketModel.Response_value> data, Context context, OnClickProduk listener){
        this.data = data;
      //  this.filterData = data;
        this.context = context;
        this.listener = listener;
        selectedItems=new SparseBooleanArray();
    }
    @NonNull
    @Override
    public ListGridPaketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v;
         v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_paket_grid,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ListGridPaketAdapter.ViewHolder viewHolder, final int i) {
        final PaketModel.Response_value produk = data.get(i);
        if(produk.getTipe_loket_alias().equals("PROFESSIONAL")){
            viewHolder.mText.setText("PRO");
        }else {
            viewHolder.mText.setText(produk.getTipe_loket_alias());
        }
        viewHolder.mRadio.setChecked(i == mSelectedItem);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedItem = viewHolder.getAdapterPosition();
                notifyItemRangeChanged(0, data.size());
                listener.onClickProduk(produk);
            }
        };

        viewHolder.linMainListProduk.setOnClickListener(clickListener);
        viewHolder.mRadio.setOnClickListener(clickListener);



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
//
    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }
//
//    public int getSelectedItemCount() {
//        return selectedItems.size();
//    }
//
//    public ArrayList<Integer> getSelectedItems() {
//        ArrayList<Integer> items = new ArrayList<Integer>(selectedItems.size());
//        for (int i = 0; i < selectedItems.size(); i++) {
//            items.add(selectedItems.keyAt(i));
//        }
//        return items;
//    }



    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
       // TextViewPlus textViewPlusNamaProduk;
       private RadioButton mRadio;
        private TextView mText;
       public LinearLayout linMainListProduk;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
            mText =  itemView.findViewById(R.id.text);
            mRadio =itemView.findViewById(R.id.radio);


        }
    }
    public interface OnClickProduk {
        void onClickProduk(PaketModel.Response_value produk);
    }
}
