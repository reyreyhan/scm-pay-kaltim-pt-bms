package com.bm.main.fpl.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bm.main.pos.R;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.models.TiketHistoryModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.TextViewPlus;
import com.bm.main.fpl.utils.FormatString;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListTiketHistoryAdapter extends RecyclerView.Adapter<ListTiketHistoryAdapter.ViewHolder>{
    @NonNull
    private final LayoutInflater inflater;
    ArrayList<TiketHistoryModel.Response_value>data = new ArrayList<>();
  //  ArrayList<TiketHistoryModel.Response_value>filterData = new ArrayList<>();
    Context context;

    public ListTiketHistoryAdapter(ArrayList<TiketHistoryModel.Response_value> data, @NonNull Context context){
        this.data = data;
       // this.filterData = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public ListTiketHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.list_tiket_history,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ListTiketHistoryAdapter.ViewHolder viewHolder, int i) {
        final TiketHistoryModel.Response_value produk = data.get(i);
        viewHolder.textViewPlusNamaBank.setText(produk.getBank());


String keterangan=produk.getKeterangan();


        String[] arrKeterangan ;
        String rek;
        String rekNo;
        String []rekArr;
try {
    arrKeterangan = keterangan.split(",");
     rek = arrKeterangan[1];
     rekArr = rek.split(" ");
     rekNo = rekArr[1].replace("rek=", "");
    Log.d("list", "onBindViewHolder: "+rekNo.replace("rek=", ""));
    viewHolder.textViewPlusNoRek.setText(rekNo);
    viewHolder.textViewPlusKeterangan.setText(produk.getKeterangan());
}catch (Exception e){
//    arrKeterangan = keterangan.split("=");
////
//           rek = arrKeterangan[1];
//    viewHolder.textViewPlusKeterangan.setText(produk.getKeterangan());
//    viewHolder.textViewPlusNoRek.setText(rek);
    rekNo="";
    viewHolder.textViewPlusNoRek.setText(rekNo);
    viewHolder.textViewPlusKeterangan.setText(produk.getKeterangan());
}
//finally {
//    rek="";
//    viewHolder.textViewPlusNoRek.setText(rek);
//    viewHolder.textViewPlusKeterangan.setText(produk.getKeterangan());
//    //arrKeterangan="";
//}
//        if(rek.equals(null)){
//            arrKeterangan = keterangan.split("=");
//
//           rek = arrKeterangan[1];
//        }


        viewHolder.textViewPlusNonimal.setText(FormatString.CurencyIDR(produk.getNominal()));

        if (produk.getStatus().equalsIgnoreCase("1")) {//sukses
            viewHolder.frame_ribbon_sukses.setVisibility(View.VISIBLE);
            viewHolder.frame_ribbon_aktif.setVisibility(View.GONE);
            viewHolder.frame_ribbon_expired.setVisibility(View.GONE);
        }

        if (produk.getStatus().equalsIgnoreCase("2")) {//sukses
            viewHolder.frame_ribbon_sukses.setVisibility(View.GONE);
            viewHolder.frame_ribbon_aktif.setVisibility(View.GONE);
            viewHolder.frame_ribbon_expired.setVisibility(View.VISIBLE);
        }

        if (produk.getStatus().equalsIgnoreCase("0")) {//sukses
            viewHolder.frame_ribbon_sukses.setVisibility(View.GONE);
            viewHolder.frame_ribbon_aktif.setVisibility(View.VISIBLE);
            viewHolder.frame_ribbon_expired.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextViewPlus textViewPlusNamaBank,textViewPlusNoRek,textViewPlusNonimal,textViewPlusKeterangan;
        ImageView imageViewCopyNoRek,imageViewNominal,imageViewKeterangan;
        FrameLayout frame_ribbon_aktif;
        FrameLayout frame_ribbon_sukses;
        FrameLayout frame_ribbon_expired;
       LinearLayout linMain;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           linMain=itemView.findViewById(R.id.linMain);
           frame_ribbon_aktif=itemView.findViewById(R.id.frame_ribbon_aktif);
           frame_ribbon_sukses=itemView.findViewById(R.id.frame_ribbon_sukses);
           frame_ribbon_expired=itemView.findViewById(R.id.frame_ribbon_expired);
            MaterialRippleLayout.on(linMain).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
            textViewPlusNamaBank=itemView.findViewById(R.id.textViewPlusNamaBank);
            textViewPlusNoRek=itemView.findViewById(R.id.textViewPlusNoRek);
            textViewPlusNonimal=itemView.findViewById(R.id.textViewPlusNonimal);
            textViewPlusKeterangan=itemView.findViewById(R.id.textViewPlusKeterangan);

            imageViewCopyNoRek = itemView.findViewById(R.id.imageViewCopyNoRek);
            imageViewCopyNoRek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textViewPlusNoRek.getText().toString().replaceAll("-",""));
                    clipboard.setPrimaryClip(clip);
                    ((BaseActivity)context).showToast("No Rek Telah Disalin");
                }
            });

            imageViewNominal = itemView.findViewById(R.id.imageViewNominal);
            imageViewNominal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textViewPlusNonimal.getText().toString().replace(".","").replace("Rp ",""));
                    clipboard.setPrimaryClip(clip);
                    ((BaseActivity)context).showToast("Nominal Telah Disalin");
                }
            });
            imageViewKeterangan = itemView.findViewById(R.id.imageViewKeterangan);
            imageViewKeterangan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textViewPlusKeterangan.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    ((BaseActivity)context).showToast("Keterangan Telah Disalin");
                }
            });




        }
    }

}
