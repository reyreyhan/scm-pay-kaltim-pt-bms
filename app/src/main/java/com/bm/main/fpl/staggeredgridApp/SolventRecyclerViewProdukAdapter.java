package com.bm.main.fpl.staggeredgridApp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.models.ProdukTomoModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class SolventRecyclerViewProdukAdapter extends RecyclerView.Adapter<SolventRecyclerViewProdukAdapter.ViewHolder> {

    private ArrayList<ProdukTomoModel.Response_value> itemList;
    private Context context;
    private OnClickProduk listener;

    public SolventRecyclerViewProdukAdapter(Context context, ArrayList<ProdukTomoModel.Response_value> itemList, OnClickProduk listener
    ) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public SolventRecyclerViewProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //SolventViewHolders rcv = new SolventViewHolders(layoutView);

        return new ViewHolder(MaterialRippleLayout.on(inflater.inflate(R.layout.solvent_list_produk, parent, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                //.rippleColor(0xFF585858)
                .rippleColor(R.color.colorPrimary_ppob)
                .rippleHover(true)
                .create()
        );

    }

    @Override
    public void onBindViewHolder(@NonNull final SolventRecyclerViewProdukAdapter.ViewHolder viewHolder, int position) {
        final ProdukTomoModel.Response_value produk = itemList.get(position);

        //  Log.d("produk", "onBindViewHolder: "+produk.getNama());
        viewHolder.textViewProduk.setText(produk.getNama());
        viewHolder.harga.setText("Rp. " + FormatString.CurencyIDR(produk.getHarga()));
        viewHolder.harga_coret.setPaintFlags(viewHolder.harga_coret.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        try {
            viewHolder.harga_coret.setText("Rp. " + FormatString.CurencyIDR(produk.getHarga_coret()));
        } catch (Exception e) {
            viewHolder.harga_coret.setVisibility(View.INVISIBLE);
        }
        String komisi = produk.getKomisi_outlet()==null?"0":produk.getKomisi_outlet();
        viewHolder.komisi.setText("Rp. " + FormatString.CurencyIDR(komisi));
        // holder.imageViewProduk.setImageResource(itemList.get(position).getFoto());
        //  Log.v("Produk Tomo", "onBindViewHolder: "+produk.getKomisi_outlet()+" "+produk.getHarga());
//        Picasso.with(context)
//                .load(produk.getFoto()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.avi.setVisibility(View.GONE);
//                viewHolder.imageViewProduk.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
////                if (produk.getProduct_code().startsWith("WA")) {
////                    viewHolder.imageViewProduk.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
////                } else {
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_tomo_gray_big));
//                //  }
//            }
//
//        });

//        Glide.with(context).load(produk.getFoto()).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().error(R.drawable.logo_tomo_gray_big).placeholder(ContextCompat.getDrawable(context, R.drawable.logo_tomo_gray_big)).listener(new RequestListener<String, Bitmap>() {
//            @Override
//            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
////                imageViewPromo.setBackground(ContextCompat.getDrawable(context, R.drawable.srikandi));                                                                              // Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                viewHolder.avi.setVisibility(View.GONE);
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//
////                Log.d(TAG, "onResourceReady: "+model+" "+isFromMemoryCache+" "+isFirstResource);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                resource.compress(Bitmap.CompressFormat.JPEG, 50, bos);
//                viewHolder.imageViewProduk.setImageBitmap(resource);
//                viewHolder.avi.setVisibility(View.GONE);
//                return true;
//            }
//        }).into(viewHolder.imageViewProduk);

        Glide.with(context).asBitmap().load(produk.getFoto())
               // .dontAnimate().skipMemoryCache(true).
                .fitCenter().encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).placeholder(ContextCompat.getDrawable(context,R.drawable.logo_tomo_gray_big)).error(ContextCompat.getDrawable(context, R.drawable.logo_tomo_gray_big))
                .diskCacheStrategy(DiskCacheStrategy.NONE).override(100,75).into(new BitmapImageViewTarget(viewHolder.imageViewProduk) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation)  {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                viewHolder.avi.setVisibility(View.GONE);
                viewHolder.imageViewProduk.refreshDrawableState();
                // here you can be sure it's already set
            }
            // +++++ OR +++++
            @Override protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                viewHolder.avi.setVisibility(View.GONE);
               // viewHolder.imageViewProduk.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_tomo_gray_big));
                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                viewHolder.avi.setVisibility(View.GONE);
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_tomo_gray_big));
//                viewHolder.imageViewProduk.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });


//                into(new GlideDrawableImageViewTarget(viewHolder.imageViewProduk) {
//            @Override
//            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                super.onResourceReady(resource, animation);
//                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//            // +++++ OR +++++
//            @Override protected void setResource(GlideDrawable resource) {
//                // this.getView().setImageDrawable(resource); is about to be called
//                super.setResource(resource);
//                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                viewHolder.avi.setVisibility(View.GONE);
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.logo_tomo_gray_big));
//            }
//        });

        if (PreferenceClass.getBoolean("switchKomisiProdukTomo", true)) {

            viewHolder.linKomisiOutlet.setVisibility(View.VISIBLE);
            // viewHolder.labelKomisi.setVisibility(View.VISIBLE);
            viewHolder.linKomisiOutlet.refreshDrawableState();
        } else {
            //  Log.d("pulsa adapter", "onBindViewHolder false: "+PreferenceClass.getBoolean("switchHargaPulsa",true));
            viewHolder.linKomisiOutlet.setVisibility(View.GONE);
            //  viewHolder.labelKomisi.setVisibility(View.GONE);
            viewHolder.linKomisiOutlet.refreshDrawableState();
        }

        viewHolder.lin_view_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                if(PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    ((BaseActivity)context).new_popup_alert(v.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");

                }else {
                    listener.onClickProduk(produk);
                }
            }
        });

        // Log.d("adapter", "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return itemList== null ? 0 : itemList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProduk, url, harga_coret;
        TextView komisi;
        TextView harga, labelKomisi;
        ImageView imageViewProduk;
        AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon, frameIcon;
        CardView card_view;
        LinearLayout linKomisiOutlet,lin_view_produk;
        //LinearLayout linKomisiOutlet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lin_view_produk = itemView.findViewById(R.id.lin_view_produk);
            card_view = itemView.findViewById(R.id.card_view);
            frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
            frameIcon = itemView.findViewById(R.id.frameIcon);
            textViewProduk = itemView.findViewById(R.id.textViewNamaProduk);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            harga = itemView.findViewById(R.id.textViewHargaTomo);
            harga_coret = itemView.findViewById(R.id.textViewHargaCoretTomo);

            komisi = itemView.findViewById(R.id.textViewKomisiTomo);
            labelKomisi = itemView.findViewById(R.id.textViewlabelKomisi);
            linKomisiOutlet = itemView.findViewById(R.id.linKomisiOutlet);

            avi = itemView.findViewById(R.id.avi);

        }
    }

    public interface OnClickProduk {
        void onClickProduk(ProdukTomoModel.Response_value produk);
    }
}
