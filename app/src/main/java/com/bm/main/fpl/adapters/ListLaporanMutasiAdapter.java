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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.LaporanMutasiModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.utils.FormatString;

import java.util.ArrayList;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListLaporanMutasiAdapter extends RecyclerView.Adapter<ListLaporanMutasiAdapter.ViewHolder> implements Filterable {
    ArrayList<LaporanMutasiModel.Response_value>data = new ArrayList<>();
    public ArrayList<LaporanMutasiModel.Response_value>filterData = new ArrayList<>();
    Context context;

    public ListLaporanMutasiAdapter(ArrayList<LaporanMutasiModel.Response_value> data, Context context){
        this.data = data;
        this.filterData = data;
        this.context = context;

    }
    @NonNull
    @Override
    public ListLaporanMutasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_laporan_mutasi,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListLaporanMutasiAdapter.ViewHolder viewHolder, int i) {
        final LaporanMutasiModel.Response_value produk = filterData.get(i);
        viewHolder.textViewKeteranganMutasi.setText(produk.getKeterangan());


        viewHolder.textViewTanggalMutasi.setText(produk.getTanggal_mutasi_toshow());



        if (produk.getKredit().equalsIgnoreCase("0") ) {
            viewHolder.textViewNominalMutasi.setText("Rp "+FormatString.CurencyIDR(produk.getDebet()));
            viewHolder.imageViewIndikator.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_kredit));
        }else  if (produk.getDebet().equalsIgnoreCase("0") ) {
            viewHolder.textViewNominalMutasi.setText("Rp "+FormatString.CurencyIDR(produk.getKredit()));
            viewHolder.imageViewIndikator.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_debet));

        }
        viewHolder.textViewSaldoMutasi.setText("Rp "+FormatString.CurencyIDR(produk.getCurrent_balance()));
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

                    ArrayList<LaporanMutasiModel.Response_value> filteredList = new ArrayList<>();

                    for (LaporanMutasiModel.Response_value produk : data) {

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
                filterData = (ArrayList<LaporanMutasiModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewKeteranganMutasi,textViewTanggalMutasi,textViewNominalMutasi,textViewSaldoMutasi;
        ImageView imageViewIndikator;

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


            textViewSaldoMutasi = itemView.findViewById(R.id.textViewSaldoMutasi);
            textViewKeteranganMutasi = itemView.findViewById(R.id.textViewKeteranganMutasi);
            textViewTanggalMutasi = itemView.findViewById(R.id.textViewTanggalMutasi);
            textViewNominalMutasi = itemView.findViewById(R.id.textViewNominalMutasi);

            imageViewIndikator = itemView.findViewById(R.id.imageViewIndikator);


        }
    }

    public void updateList(ArrayList<LaporanMutasiModel.Response_value> list) {
        filterData = list;

        notifyDataSetChanged();
    }


//    public interface OnClickProduk {
//        public void onClickProduk(LaporanMutasiModel.Response_value produk);
//    }
}
