package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.PaketModelModelResponse_valueTipe_loket_detail;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListPaketOutletAdapter extends RecyclerView.Adapter<ListPaketOutletAdapter.ViewHolder> {
    ArrayList<PaketModelModelResponse_valueTipe_loket_detail>data = new ArrayList<>();
    ArrayList<PaketModelModelResponse_valueTipe_loket_detail>filterData = new ArrayList<>();
    Context context;
   // OnClickProduk listener;
    public ListPaketOutletAdapter(ArrayList<PaketModelModelResponse_valueTipe_loket_detail> data, Context context ){
        this.data = data;
        this.filterData = data;
        this.context = context;
       // this.listener = listener;
    }
    @NonNull
    @Override
    public ListPaketOutletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_paket_detail,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ListPaketOutletAdapter.ViewHolder viewHolder, int i) {
        final PaketModelModelResponse_valueTipe_loket_detail produk = filterData.get(i);
        viewHolder.textViewDesc.setText(Html.fromHtml(produk.getDesc_name()));
        switch (produk.getAvail()) {
            case "1":
                viewHolder.imageViewAvail.setVisibility(View.VISIBLE);
                viewHolder.imageViewNotAvail.setVisibility(View.GONE);
                break;
            case "0":
                viewHolder.imageViewAvail.setVisibility(View.GONE);
                viewHolder.imageViewNotAvail.setVisibility(View.VISIBLE);
                break;
            default:
                viewHolder.imageViewAvail.setVisibility(View.GONE);
                viewHolder.imageViewNotAvail.setVisibility(View.GONE);
                break;
        }

//        viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                listener.onClickProduk(produk);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return filterData.size();
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
//                    filterData = data;
//                } else {
//
//                    ArrayList<BankModel.Response_value> filteredList = new ArrayList<>();
//
//                    for (BankModel.Response_value produk : data) {
//
//                        if (produk.getBank_code().toLowerCase().contains(charString) || produk.getBank_name().toLowerCase().contains(charString) ) {
//
//                            filteredList.add(produk);
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
//                filterData = (ArrayList<BankModel.Response_value>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDesc;
        ImageView imageViewAvail,imageViewNotAvail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            imageViewAvail = itemView.findViewById(R.id.imageViewAvail);
            imageViewNotAvail = itemView.findViewById(R.id.imageViewNotAvail);


        }
    }
//    public interface OnClickProduk {
//        public void onClickProduk(BankModel.Response_value produk);
//    }
}
