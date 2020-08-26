package com.bm.main.fpl.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.models.LaporanTransaksiModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.utils.FormatString;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListLaporanTransaksiAdapter extends RecyclerView.Adapter<ListLaporanTransaksiAdapter.ViewHolder> implements Filterable {
    ArrayList<LaporanTransaksiModel.Response_value> data ;//= new ArrayList<>();
   public ArrayList<LaporanTransaksiModel.Response_value> filterData;// = new ArrayList<>();
    Context context;
    OnClickProduk listener;
    @NonNull
    private final LayoutInflater inflater;
    public ListLaporanTransaksiAdapter(ArrayList<LaporanTransaksiModel.Response_value> data, @NonNull Context context, OnClickProduk listener) {
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ListLaporanTransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.list_laporan_trx, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListLaporanTransaksiAdapter.ViewHolder viewHolder, int i) {
        final LaporanTransaksiModel.Response_value produk = filterData.get(i);
        viewHolder.textViewProduk.setText(produk.getProduk());
        viewHolder.textViewProduk.setTag(R.id.id_trx, produk.getId_transaksi());
        viewHolder.textViewKeterangan.setText(produk.getKeterangan());
        viewHolder.textViewTanggalTrx.setText(produk.getTanggal_trx());
        String nominal=produk.getNominal()==null?"0":produk.getNominal();
        viewHolder.textViewNominal.setText("Rp " + FormatString.CurencyIDR(nominal));
        viewHolder.textViewStatus.setText(produk.getStatus_transaksi());
        viewHolder.textViewStatus.setText(produk.getStatus_transaksi());
        viewHolder.textViewProdukInfo.setText(produk.getInfo());
        viewHolder.textViewSerialNumber.setText(produk.getSn());
        if (viewHolder.linMainListProdukInfo.getVisibility() == View.VISIBLE) {
            viewHolder.linMainListProduk.setVisibility(View.VISIBLE);
            viewHolder.linMainListProdukInfo.setVisibility(View.GONE);
        }


        if (produk.getStatus_transaksi().equalsIgnoreCase("SUKSES")) {
            viewHolder.imageViewCetak.setVisibility(View.VISIBLE);
            if (produk.getIs_pulsa().equalsIgnoreCase("0")) {
                // viewHolder.imageViewCetak.setVisibility(View.VISIBLE);
                //    viewHolder.imageViewCetak.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_print_blue));
                Glide.with(context).load(R.drawable.ic_print_blue).override(30,30).into(viewHolder.imageViewCetak);
            } else {
                // viewHolder.imageViewCetak.setVisibility(View.VISIBLE);
                // viewHolder.imageViewCetak.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_info_blue));
                Glide.with(context).load(R.drawable.ic_info_blue).override(30,30).into(viewHolder.imageViewCetak);
            }
            viewHolder.textViewStatus.setTextColor(ContextCompat.getColor(context, R.color.md_green_500));
        } else {
            viewHolder.imageViewCetak.setVisibility(View.GONE);

            viewHolder.textViewStatus.setTextColor(ContextCompat.getColor(context, R.color.md_red_500));

        }


//
        viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (produk.getStatus_transaksi().equalsIgnoreCase("SUKSES") && produk.getIs_pulsa().equalsIgnoreCase("0")) {
                    listener.onClickProduk(produk);
                } else if (produk.getStatus_transaksi().equalsIgnoreCase("SUKSES") && produk.getIs_pulsa().equalsIgnoreCase("1")) {
                    //   v.animate().translationX(v.getWidth());

                    //    viewHolder.linMainListProduk.setVisibility(View.GONE);
                    viewHolder.linMainListProdukInfo.setVisibility(View.VISIBLE);
                }
            }
        });
        viewHolder.imageViewClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // v.animate().translationX(v.getWidth());
                //   viewHolder.linMainListProduk.setVisibility(View.VISIBLE);
                viewHolder.linMainListProdukInfo.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterData == null ? 0 : filterData.size();
//        return filterData.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
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

                    ArrayList<LaporanTransaksiModel.Response_value> filteredList = new ArrayList<>();

                    for (LaporanTransaksiModel.Response_value produk : data) {

                        if (produk.getProduk().toLowerCase().contains(charString) || produk.getKeterangan().toLowerCase().contains(charString) || produk.getStatus_transaksi().toLowerCase().contains(charString)) {

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
                filterData = (ArrayList<LaporanTransaksiModel.Response_value>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProduk, textViewKeterangan, textViewTanggalTrx, textViewNominal, textViewStatus, textViewProdukInfo, textViewSerialNumber;
        ImageView imageViewCetak, imageViewCopySn, imageViewClosed;

        LinearLayout linMainListProduk, linMainListProdukInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            linMainListProdukInfo = itemView.findViewById(R.id.linMainListProdukInfo);
            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();

            textViewProduk = itemView.findViewById(R.id.textViewProduk);
            textViewKeterangan = itemView.findViewById(R.id.textViewKeterangan);
            textViewTanggalTrx = itemView.findViewById(R.id.textViewTanggalTrx);
            textViewNominal = itemView.findViewById(R.id.textViewNominal);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewProdukInfo = itemView.findViewById(R.id.textViewProdukInfo);
            textViewSerialNumber = itemView.findViewById(R.id.textViewSerialNumber);
            imageViewCetak = itemView.findViewById(R.id.imageViewCetak);
            imageViewCopySn = itemView.findViewById(R.id.imageViewCopySn);
            imageViewClosed = itemView.findViewById(R.id.imageViewClosed);


            imageViewCopySn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textViewSerialNumber.getText().toString().replaceAll("-", ""));
                    clipboard.setPrimaryClip(clip);
                    ((BaseActivity) context).showToast("Kode Voucher/SN Telah Disalin");
                }
            });

            textViewSerialNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textViewSerialNumber.getText().toString().replaceAll("-", ""));
                    clipboard.setPrimaryClip(clip);
                    ((BaseActivity) context).showToast("Kode Voucher/SN Telah Disalin");
                }
            });

        }
    }
    public void updateList(ArrayList<LaporanTransaksiModel.Response_value> list) {
        filterData = list;

        notifyDataSetChanged();
    }
    public interface OnClickProduk {
        void onClickProduk(LaporanTransaksiModel.Response_value produk);
    }
}
