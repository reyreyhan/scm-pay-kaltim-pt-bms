package com.bm.main.single.ftl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.fpl.templates.AutoResizeTextView;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.single.ftl.models.EtiketShipModel;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sarifhidayat on 2019-08-29.
 **/
public class EtiketShipAdapter extends RecyclerView.Adapter<EtiketShipAdapter.ViewHolder> implements Filterable{

    Context context;
    LayoutInflater inflater;
    @NonNull
//        List<JSONModel> data = new ArrayList<>();
            ArrayList<EtiketShipModel.Data> dataShip = new ArrayList<>();
    ArrayList<EtiketShipModel.Data> filterDataShip =  new ArrayList<>();
OnClickProdukShip listener;

    private static final String TAG = EtiketShipAdapter.class.getSimpleName() ;


    public EtiketShipAdapter(Context context, ArrayList<EtiketShipModel.Data> data, OnClickProdukShip listener){
        this.context = context;
        this.dataShip=data;
        this.filterDataShip=data;
        this.listener=listener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
         itemView = inflater.inflate(R.layout.travel_etiket_ship_list_item,viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
//        if (filterDataShip.size() > 0 && i > 0) {



        final EtiketShipModel.Data pesananKapal=filterDataShip.get(i);
//        viewHolder.dataView.setVisibility(View.VISIBLE);
//        viewHolder.emptyView.setVisibility(View.GONE);
        viewHolder.tvInfo2.setVisibility(View.VISIBLE);
        viewHolder.tvInfo1.setVisibility(View.VISIBLE);
        viewHolder.ivIcon.setVisibility(View.VISIBLE);
        viewHolder.ivInfoSeparation.setVisibility(View.VISIBLE);
            viewHolder.tvInfo1.setText(pesananKapal.getOrigin());
            viewHolder.tvInfo2.setText(pesananKapal.getDestination());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);
            try {
                Date checkInDate = sdf.parse(pesananKapal.getTanggal_keberangkatan());
                viewHolder.tvTanggalWaktuBerangkat.setText(pesananKapal.getHari_keberangkatan() + ", " + odf.format(checkInDate) + " - " + pesananKapal.getJam_keberangkatan());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            viewHolder.tvNama.setText(pesananKapal.getNama_kapal());
           // viewHolder.tvNama.setText("GARUDA INDONESIA sdgdsgdsgsdgdsgs");
            viewHolder.tvCode.setText(pesananKapal.getKode_booking());
//                        holder.ivIcon.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.ic_pelni).into(viewHolder.ivIcon);
//        transaction_id = String.valueOf(pesananPesawat.getId_transaksi());
//        url_struk = pesananPesawat.getUrl_struk();
//        finalUrl_struk =pesananPesawat.getUrl_struk();
//        url_pdf = pesananPesawat.getUrl_etiket();
//        produk = "Pesawat";
//        namaProduk = pesananPesawat.getNama_maskapai();
//        kode = pesananPesawat.getKode_booking();
//        finalkode=pesananPesawat.getKode_booking();
//        for(int i = 0; i < pesananPesawat.getPenumpang().length; i++) {
//            nama = pesananPesawat.getPenumpang()[0].getTitle() + " " + pesananPesawat.getPenumpang()[0].getNama();
//        }

        //  nama = pesananPesawat.getPenumpangList().get(0).getTitle() + " " + pesananPesawat.getPenumpangList().get(0).getNama();
//                        nama = pesananPesawat.getPenumpang().get(0).getTitle() + " " + pesananPesawat.getPenumpangList().get(0).getNama();
        //  jsonObject = pesananPesawat.getJsonObject();
        // jsonObject= String.valueOf(pesananPesawat);//new GsonBuilder().create().toJson(this, EtiketFlightModel.class);
        //  pesananPesawatModel = pesananPesawat;
//        finalPesananPesawatModel=pesananPesawat;
        //jsonObject=gson.toJson(pesananPesawat);


//                        Log.d(TAG, "onBindViewHolder: " + pesananPesawat.getPenumpangList().get(0).getTitle());
//                        Log.d(TAG, "onBindViewHolder: " + pesananPesawat.getPenumpangList().get(0).getNama());
        //  searchView.setVisibility(View.VISIBLE);

        viewHolder.ivMenu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(@NonNull final View view) {
            listener.onClickProdukPopupMenu(pesananKapal,viewHolder);
//            subMenuView = view;
//            PopupMenu popup = new PopupMenu(context, viewHolder.ivMenu);
//            popup.getMenuInflater().inflate(R.menu.core_popup_menu_list_pesanan, popup.getMenu());
//            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                public boolean onMenuItemClick(@NonNull MenuItem item) {
//                    int id = item.getItemId();
//                    if (id == R.id.action_pop1) {
//                        Intent intent = new Intent(TravelDaftarPesananActivity.this, TravelTiketPesananActivity.class);
//                        intent.putExtra("url_struk", finalUrl_struk);
//                        intent.putExtra("url_pdf", finalUrl_pdf);
//                        intent.putExtra("id_transaksi", finalTransaction_id);
//                        intent.putExtra("data", finalJsonObject.toString());
//                        intent.putExtra("product", selectedProduct);
//                        startActivity(intent);
//                    } else if (id == R.id.action_pop2) {
//
////                        isSendETiket = 1;
////                        checkPermisionStorage();
//
//                    } else if (id == R.id.action_pop3) {
//                   //     kodeBooking = finalkode;
//                   //     showToast("Printing......"+finalPesananPesawatModel.getKode_booking());
//                        // getStruk(finalUrl_struk, 1, TravelDaftarPesananActivity.this);
//                    }
//                    return true;
//                }
//            });
//            popup.show();
        }
    });

                    viewHolder.linMain.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

           // checkPermisionStorage();
            listener.onClickProdukDetail(pesananKapal);
//

        }
    });
                    viewHolder.btnETiket.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

          //  checkPermisionStorage();
            listener.onClickProdukDetail(pesananKapal);
//

        }
    });
//        } else {
//            //   searchView.setVisibility(View.GONE);
//            viewHolder.dataView.setVisibility(View.GONE);
//            viewHolder.emptyView.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return filterDataShip.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            // FilterResults filterResults = new FilterResults();

            @NonNull
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {
                Log.d(TAG, "performFiltering: "+charSequence);
                String charString = charSequence.toString().toLowerCase();

                if (charString.isEmpty()) {

                        filterDataShip = dataShip;


                } else {



                        ArrayList<EtiketShipModel.Data> filteredList = new ArrayList<>();
//                                PesananPesawat pesananPesawat = (PesananPesawat)
                        for (EtiketShipModel.Data produk : dataShip) {

                            if (produk.getKode_booking().toLowerCase().contains(charString)) {

                                filteredList.add(produk);
                            }
                        }
                        filterDataShip = filteredList;
                        // filterResults.values = filterDataFlight;
                        // return filterResults;



                }

                FilterResults filterResults = new FilterResults();

                    filterResults.values = filterDataShip;




                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults
                    filterResults) {

                    filterDataShip = (ArrayList<EtiketShipModel.Data>) filterResults.values;



                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout dataView, linMain;
        RelativeLayout emptyView;
        TextView tvInfo1, tvInfo2, tvTanggalWaktuBerangkat, tvCode;
        AutoResizeTextView tvNama;
        ImageView ivInfoSeparation, ivIcon;
        public ImageView  ivMenu;
        AppCompatButton btnETiket;

        public ViewHolder(@NonNull View view) {
            super(view);
            linMain = view.findViewById(R.id.linMain);
            dataView = view.findViewById(R.id.rel_dataView);
            emptyView = view.findViewById(R.id.rel_EmptyViewItem1);
            tvInfo1 = view.findViewById(R.id.tvInfo1);
            tvInfo2 = view.findViewById(R.id.tvInfo2);
            tvTanggalWaktuBerangkat = view.findViewById(R.id.tvTanggalWaktuBerangkat);
            tvNama = view.findViewById(R.id.tvNama);
            tvCode = view.findViewById(R.id.tvCode);
            ivInfoSeparation = view.findViewById(R.id.ivInfoSeparation);
            ivIcon = view.findViewById(R.id.ivIcon);
            ivMenu = view.findViewById(R.id.ivMenu);
            btnETiket = view.findViewById(R.id.btnETiket);
        }
    }

    public interface OnClickProdukShip {
        public void onClickProdukDetail(EtiketShipModel.Data produk);
        public void onClickProdukPopupMenu(EtiketShipModel.Data produk, ViewHolder viewHolder);
    }
}
