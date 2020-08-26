package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.AdminModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListAdminMenuAdapter extends RecyclerView.Adapter<ListAdminMenuAdapter.ViewHolder> {
    List<AdminModel> data = new ArrayList<>();

    Context context;
    OnClickProduk listener;
    public ListAdminMenuAdapter(List<AdminModel> data, Context context, OnClickProduk listener){
        this.data = data;

        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ListAdminMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_admin,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdminMenuAdapter.ViewHolder viewHolder, int i) {
        final AdminModel produk = data.get(i);
        viewHolder.textViewPlusNamaProduk.setText(produk.getNama());


//
        viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = viewHolder.getAdapterPosition();
                listener.onClickProduk(adapterPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPlusNamaProduk;

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

            textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaMenu);


        }
    }
    public interface OnClickProduk {
        public void onClickProduk(int adminModel);
    }
}
