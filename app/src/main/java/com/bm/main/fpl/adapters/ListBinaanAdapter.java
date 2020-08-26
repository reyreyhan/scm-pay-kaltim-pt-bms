package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.DownlineModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;

import java.util.ArrayList;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListBinaanAdapter extends RecyclerView.Adapter<ListBinaanAdapter.ViewHolder> implements Filterable {
    ArrayList<DownlineModel.Response_value>data = new ArrayList<>();
    ArrayList<DownlineModel.Response_value>filterData = new ArrayList<>();
    Context context;
    CursorAdapter mCursorAdapter;
    OnClickProduk listener;
    public ListBinaanAdapter(ArrayList<DownlineModel.Response_value> data, Context context, OnClickProduk listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ListBinaanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_binaan,viewGroup,false);
//        SolventViewHolders rcv = new SolventViewHolders(MaterialRippleLayout.on(v)
//                .rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                //.rippleColor(0xFF585858)
//                .rippleColor(R.color.colorPrimary)
//                .rippleHover(true)
//                .create()
//        );
        return new ViewHolder(v);

//        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
//        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListBinaanAdapter.ViewHolder viewHolder, int i) {
      //  mCursorAdapter.getCursor().moveToPosition(i); //EDITED: added this line as suggested in the comments below, thanks :)
        //mCursorAdapter.bindView(viewHolder.itemView, context, mCursorAdapter.getCursor());

        final DownlineModel.Response_value produk = filterData.get(i);
        viewHolder.textViewNamaBinaan1.setText(produk.getNama_pemilik());
        viewHolder.textViewJumlahBinaan1.setText("Jumlah Binaan : "+produk.getJumlah_downline());
        viewHolder.textViewIdBinaan1.setText(produk.getId_outlet());
for(int x=0;x<produk.getLevel2().length;x++) {
    viewHolder.textViewNamaBinaan2.setText(produk.getLevel2()[x].getNama_pemilik2());
    viewHolder.textViewJumlahBinaan2.setText("Jumlah Binaan : " + produk.getLevel2()[x].getJumlah_downline2());
    viewHolder.textViewIdBinaan2.setText(produk.getLevel2()[x].getId_outlet2());
}

//
//        viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClickProduk(produk);
//            }
//        });
        if(!produk.getJumlah_downline().equals("0")) {
            viewHolder.imageViewDrop1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.linMainListBinaan2.getVisibility() == View.GONE) {
                        viewHolder.linMainListBinaan2.setVisibility(View.VISIBLE);
                        viewHolder.imageViewDrop1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_down_arrow));
                        viewHolder.imageViewDrop1.getDrawingCache(true);
                    } else {
                        viewHolder.linMainListBinaan2.setVisibility(View.GONE);
                        viewHolder.imageViewDrop1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_right_arrow));
                        viewHolder.imageViewDrop1.getDrawingCache(true);
                    }
                }
            });
            viewHolder.imageViewDrop1Empty.setVisibility(View.GONE);
            viewHolder.imageViewDrop1.setVisibility(View.VISIBLE);
        }else{
            viewHolder.linMainListBinaan2.setVisibility(View.GONE);
            viewHolder.imageViewDrop1Empty.setVisibility(View.VISIBLE);
            viewHolder.imageViewDrop1.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return filterData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaBinaan1,textViewJumlahBinaan1,textViewIdBinaan1,textViewNamaBinaan2,textViewJumlahBinaan2,textViewIdBinaan2;

        LinearLayout linMainListProduk,linMainListBinaan1,linearInfoMitra1,linMainListBinaan2,linearInfoMitra2;
        ImageView imageViewDrop1,imageViewDrop1Empty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            linMainListBinaan1 = itemView.findViewById(R.id.linMainListBinaan1);
            linearInfoMitra1 = itemView.findViewById(R.id.linearInfoMitra1);
            linMainListBinaan2 = itemView.findViewById(R.id.linMainListBinaan2);
            linearInfoMitra2 = itemView.findViewById(R.id.linearInfoMitra2);
            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
          //  frame_ribbon =  itemView.findViewById(R.id.frame_ribbon);
            textViewNamaBinaan1 = itemView.findViewById(R.id.textViewNamaBinaan1);
            textViewJumlahBinaan1 = itemView.findViewById(R.id.textViewJumlahBinaan1);
            textViewIdBinaan1 = itemView.findViewById(R.id.textViewIdBinaan1);

            textViewNamaBinaan2 = itemView.findViewById(R.id.textViewNamaBinaan2);
            textViewJumlahBinaan2 = itemView.findViewById(R.id.textViewJumlahBinaan2);
            textViewIdBinaan2 = itemView.findViewById(R.id.textViewIdBinaan2);
           // imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
          //  avi=itemView.findViewById(R.id.avi);

            imageViewDrop1= itemView.findViewById(R.id.imageViewDrop1);
            imageViewDrop1Empty= itemView.findViewById(R.id.imageViewDrop1Empty);


        }
    }
    public interface OnClickProduk {
        public void onClickProduk(DownlineModel.Response_value produk);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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

                    ArrayList<DownlineModel.Response_value> filteredList = new ArrayList<>();

                    for (DownlineModel.Response_value produk : data) {

                        if (produk.getId_outlet().toLowerCase().contains(charString) || produk.getLevel2()[data.size()].getId_outlet2().toLowerCase().contains(charString)) {

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
                filterData = (ArrayList<DownlineModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
