package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.LaporanKomisiModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.utils.FormatString;

import java.util.ArrayList;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListLaporanKomisiAdapter extends RecyclerView.Adapter<ListLaporanKomisiAdapter.ViewHolder> implements Filterable {
    ArrayList<LaporanKomisiModel.Response_value>data = new ArrayList<>();
    ArrayList<LaporanKomisiModel.Response_value>filterData = new ArrayList<>();
    Context context;

    public ListLaporanKomisiAdapter(ArrayList<LaporanKomisiModel.Response_value> data, Context context){
        this.data = data;
        this.filterData = data;
        this.context = context;

    }
    @NonNull
    @Override
    public ListLaporanKomisiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_laporan_komisi,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListLaporanKomisiAdapter.ViewHolder viewHolder, int i) {
        final LaporanKomisiModel.Response_value produk = filterData.get(i);
        viewHolder.textViewKeteranganKomisi.setText(produk.getKeterangan());
//
//
        if(produk.getInitstatus().equals("1")){
            viewHolder.textViewStatusKomisi.setTextColor(ContextCompat.getColor(context,R.color.md_blue_500));
        }else{
            viewHolder.textViewStatusKomisi.setTextColor(ContextCompat.getColor(context,R.color.md_red_500));
        }
        viewHolder.textViewStatusKomisi.setText(produk.getStatus());
        viewHolder.textViewNominalKomisi.setText("Rp "+ FormatString.CurencyIDR(produk.getJumlah()));
//
//
//
//        if (produk.getKredit().equalsIgnoreCase("0") ) {
//            viewHolder.textViewNominalKomisi.setText("Rp "+FormatString.CurencyIDR(produk.getDebet()));
//            viewHolder.imageViewIndikator.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_kredit));
//        }else  if (produk.getDebet().equalsIgnoreCase("0") ) {
//            viewHolder.textViewNominalKomisi.setText("Rp "+FormatString.CurencyIDR(produk.getKredit()));
//            viewHolder.imageViewIndikator.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_debet));
//
//        }
//
//        viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (produk.getStatus_transaksi().equalsIgnoreCase("SUKSES")  && produk.getIs_pulsa().equalsIgnoreCase("0")) {
//                    listener.onClickProduk(produk);
//                }
//            }
//        });
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

                    ArrayList<LaporanKomisiModel.Response_value> filteredList = new ArrayList<>();

                    for (LaporanKomisiModel.Response_value produk : data) {

                        if (produk.getKeterangan().toLowerCase().contains(charString) ) {

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
                filterData = (ArrayList<LaporanKomisiModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewKeteranganKomisi, textViewStatusKomisi, textViewNominalKomisi;
       // ImageView imageViewIndikator;

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


            textViewKeteranganKomisi = itemView.findViewById(R.id.textViewKeteranganKomisi);
            textViewStatusKomisi = itemView.findViewById(R.id.textViewStatusKomisi);
            textViewNominalKomisi = itemView.findViewById(R.id.textViewNominalKomisi);

          //  imageViewIndikator = itemView.findViewById(R.id.imageViewIndikator);


        }
    }
//    public interface OnClickProduk {
//        public void onClickProduk(LaporanMutasiModel.Response_value produk);
//    }
}
