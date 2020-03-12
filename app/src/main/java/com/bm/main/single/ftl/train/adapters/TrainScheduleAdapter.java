package com.bm.main.single.ftl.train.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.train.models.TrainDataModel;
import com.bm.main.single.ftl.utils.utilBand;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

/**
 * Created by sarifhidayat on 2019-06-19.
 **/
public class TrainScheduleAdapter extends RecyclerView.Adapter<TrainScheduleAdapter.ViewHolder>  {
    public static final String TAG = TrainScheduleAdapter.class.getSimpleName();
    public ArrayList<TrainDataModel> mDisplayList = new ArrayList<>();//= Collections.emptyList();;
    public ArrayList<TrainDataModel> mDisplayListFilter = new ArrayList<>();
    LayoutInflater inflater;
    private ViewHolder mHolder;
Context context;

    private OnClickSchedule listener;

    public TrainScheduleAdapter(Context context, ArrayList<TrainDataModel> itemList,OnClickSchedule listener) {
        this.context = context;
        this.mDisplayList = itemList;
        this.mDisplayListFilter = itemList;
this.listener=listener;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = inflater.inflate(R.layout.train_schedule_list_item, parent, false);

        return new ViewHolder(itemView);


    }


    boolean check;

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        final TrainDataModel a;
        a = mDisplayListFilter.get(position);

        String str = a.getTrainName();
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            builder.append(cap + " ");
        }

        viewHolder.textViewKereta.setText(builder.toString());
        viewHolder.textViewKelas.setText("("+a.getClasses()+")");
        //viewHolder.textViewGrade.setText("("+a.getGrade()+")");
        if (a.getGrade().equals("E")) {
            viewHolder.textViewGrade.setText("Eksekutif");
        }
        if (a.getGrade().equals("K")) {
            viewHolder.textViewGrade.setText("Ekonomi");
        }
        if (a.getGrade().equals("B")) {
            viewHolder.textViewGrade.setText("Bisnis");
        }

        viewHolder.textViewDurasi.setText(a.getDuration());
        viewHolder.textViewOriginCode.setText(a.getOriginCode());
        viewHolder.textViewDestinationCode.setText(a.getDestinationCode());
        viewHolder.textViewJamBrngkt.setText(a.getDepartureTime());
        viewHolder.textViewJamTiba.setText(a.getArrivalTime());
//        viewHolder.tglBrngkt.setText(a.getDepartureDate());
//        viewHolder.tglTiba.setText(a.getArrivalDate());

        // String rego = String.valueOf(a.getPriceAdult());

//        int hargatiketperorang = a.getPriceAdult();
//        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
//        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
//
//        formatRp.setCurrencySymbol("Rp. ");
//        formatRp.setMonetaryDecimalSeparator(',');
//        formatRp.setGroupingSeparator('.');
//
//        kursIndonesia.setDecimalFormatSymbols(formatRp);
//        viewHolder.textViewHargaKereta.setText("" + kursIndonesia.format(hargatiketperorang).replace(",00",""));
        viewHolder.textViewHargaKereta.setText(utilBand.formatRupiah(a.getPriceAdult()).replace(",00", ""));

        viewHolder.textViewJmlKursi.setText(a.getAvailability() + " kursi");

        if(a.getAvailability()==0) {

            viewHolder.textViewJmlKursi.setText("Habis");

           // viewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.md_grey_200));
            viewHolder.lin_main_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //  ((TrainScheduleActivity) context).showToastCustom((context),3,"Tidak tersedia kursi");
                    listener.onClickScheduleMsg("Tidak tersedia kursi");
                }


            });
        }else if(a.getAvailability()==999999){
            viewHolder.textViewJmlKursi.setText("Tersedia");

         //   viewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.md_white_1000));

            viewHolder.lin_main_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
//
                    listener.onClickSchedule(a);
                }


            });
        }else if(a.getAvailability()>50){
            viewHolder.textViewJmlKursi.setText("Tersedia");

           // viewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.md_white_1000));

            viewHolder.lin_main_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
//
                    listener.onClickSchedule(a);
                }


            });
        } else {
           // viewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.md_white_1000));

            viewHolder.lin_main_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
//                    ((TrainScheduleActivity) context).getFare(a.getTrainNumber(), a.getGrade(), a.getClasses(),
//                            a.getTrainName(),
//                            a.getDepartureTime(), a.getArrivalTime(), a.getDepartureDate(), a.getPriceAdult(),
//                            a.getArrivalDate());
                    listener.onClickSchedule(a);
                }


            });
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewKereta;


        public TextView  textViewGrade;
        public TextView textViewKelas;
        public TextView textViewJamBrngkt;
        public TextView textViewJamTiba;
        public TextView textViewJmlKursi;
        public TextView textViewHargaKereta;
        //  public TextView textViewSisaKursi;
        public TextView textViewLabelHarga;
        public TextView textViewDurasi;
        public TextView textViewOriginCode;
        public TextView textViewDestinationCode;



        public LinearLayout lin_main_item;
        public RelativeLayout cv;

        //int posisi;
        public ViewHolder(@NonNull final View view) {
            super(view);

            cv = view.findViewById(R.id.cv);
            lin_main_item = view.findViewById(R.id.lin_main_item);
//            MaterialRippleLayout.on(lin_main_item).rippleOverlay(true).rippleDelayClick(false)
//                    .rippleAlpha(0.2f)
//                    //.rippleColor(0xFF585858)
//                    .rippleColor(R.color.colorPrimary)
//                    .rippleHover(true)
//                    .create();
            textViewKereta = view.findViewById(R.id.textViewNamaKereta);
            textViewGrade = view.findViewById(R.id.textViewGrade);
            textViewKelas = view.findViewById(R.id.textViewKelas);
            textViewJamBrngkt = view.findViewById(R.id.textViewJamBerangkat);
            textViewJamTiba = view.findViewById(R.id.textViewJamTiba);

            textViewJmlKursi = view.findViewById(R.id.textViewJmlKursi);
            textViewHargaKereta = view.findViewById(R.id.textViewHargaKereta);
            textViewLabelHarga = view.findViewById(R.id.labelHarga);
            textViewDurasi= view.findViewById(R.id.textViewDurasi);
            textViewOriginCode= view.findViewById(R.id.textViewOriginCode);
            textViewDestinationCode= view.findViewById(R.id.textViewDestinationCode);

        }
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void updateList(ArrayList<TrainDataModel> list) {

        mDisplayListFilter = list;

        notifyDataSetChanged();
    }

    public ArrayList<TrainDataModel> getData() {


        return this.mDisplayList;
    }

    public ArrayList<TrainDataModel> getDataFiter() {


        return this.mDisplayListFilter;
    }

    @Override
    public int getItemCount() {

        if (mDisplayListFilter != null) {
            return mDisplayListFilter.size();
        }
        return 0;
    }

    public interface OnClickSchedule {
        void onClickSchedule(TrainDataModel dataSchedule);
        void onClickScheduleMsg(String msg);
    }

}
