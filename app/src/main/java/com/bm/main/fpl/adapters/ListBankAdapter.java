package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.BankModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListBankAdapter extends RecyclerView.Adapter<ListBankAdapter.ViewHolder> implements Filterable {
    ArrayList<BankModel.Response_value>data = new ArrayList<>();
    ArrayList<BankModel.Response_value>filterData = new ArrayList<>();
    Context context;
    OnClickProduk listener;
    public ListBankAdapter(ArrayList<BankModel.Response_value> data, Context context, OnClickProduk listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
      View v;
         v =inflater.inflate(R.layout.list_bank,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final BankModel.Response_value produk = filterData.get(i);
        viewHolder.textViewPlusNamaProduk.setText(produk.getBank_name());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.produkCode,produk.getBank_code());
        //viewHolder.textViewPlusNamaProduk.setTag(R.id.isGangguanProduk,produk.getIs_gangguan());
//        if (produk.getIs_promo().equalsIgnoreCase("1")) {
//            viewHolder.frame_ribbon.setVisibility(View.VISIBLE);
//        }
//        Picasso.with(context)
//                        .load(produk.getProduct_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.avi.setVisibility(View.GONE);
//             //   viewHolder.imageViewProduk.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
//
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo_color));
//
//            }
//
//        });
        Glide.with(context).asBitmap().load(produk.getProduct_url()).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(viewHolder.imageViewProduk){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                viewHolder.avi.setVisibility(View.GONE);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                viewHolder.avi.setVisibility(View.GONE);
                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                super.onResourceReady(resource, transition);

                viewHolder.avi.setVisibility(View.GONE);
            }
        });
//            @Override
//            public void onResourceReady(Bitmap resource, Transaction<? super BitmapDrawable> animation) {
//                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                super.onResourceReady(resource, animation);
//                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//            // +++++ OR +++++
//            @Override protected void setResource(Bitmap resource) {
//                // this.getView().setImageDrawable(resource); is about to be called
//                super.setResource(resource);
//               viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                viewHolder.avi.setVisibility(View.GONE);
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
//            }
//        });
//
        viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClickProduk(produk);
            }
        });
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

                    ArrayList<BankModel.Response_value> filteredList = new ArrayList<>();

                    for (BankModel.Response_value produk : data) {

                        if (produk.getBank_code().toLowerCase().contains(charString) || produk.getBank_name().toLowerCase().contains(charString) ) {

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
                filterData = (ArrayList<BankModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPlusNamaProduk;
        ImageView imageViewProduk;
        AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon;
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
            frame_ribbon =  itemView.findViewById(R.id.frame_ribbon);
            textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaProduk);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            avi=itemView.findViewById(R.id.avi);

        }
    }
    public interface OnClickProduk {
        public void onClickProduk(BankModel.Response_value produk);
    }
}
