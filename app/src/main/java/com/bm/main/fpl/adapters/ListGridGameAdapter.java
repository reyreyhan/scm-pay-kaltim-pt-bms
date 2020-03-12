package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.GameModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class ListGridGameAdapter extends RecyclerView.Adapter<ListGridGameAdapter.ViewHolder> implements Filterable {
    private static final String TAG = ListGridGameAdapter.class.getSimpleName();
    private ArrayList<GameModel.Response_value> data ;//= new ArrayList<>();
    private ArrayList<GameModel.Response_value> filterData ;//= new ArrayList<>();
    private Context context;
    private OnClickProduk listener;
    private int flag=0;

    public ListGridGameAdapter(ArrayList<GameModel.Response_value> data, Context context, OnClickProduk listener) {
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListGridGameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_produk_grid_image, viewGroup, false);
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
    public void onBindViewHolder(@NonNull final ListGridGameAdapter.ViewHolder viewHolder, int i) {
        final GameModel.Response_value produk = filterData.get(i);
      //  viewHolder.url=produk.getGame_url();
        viewHolder.textViewPlusNamaProduk.setText(produk.getGame_name());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.produkCode, produk.getGame_code());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.isGangguanProduk, produk.getIs_gangguan());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.urlProduk, produk.getGame_url());
        if (produk.getIs_promo().equalsIgnoreCase("1")) {
            viewHolder.frame_ribbon.setVisibility(View.VISIBLE);
        }

//        Picasso.with(context)
//                        .load(produk.getGame_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                // Log.d("Picasso", "Image loaded from cache>>>" + produk.getGame_url());
//                viewHolder.avi.setVisibility(View.GONE);
////                viewHolder.imageViewProduk.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//
//
//                // Log.d("Picasso", "Failed to load image online and offline, make sure you enabled INTERNET permission for your app and the url is correct>>>>>>>" + produk.getGame_url());
//                viewHolder.avi.setVisibility(View.GONE);
//
//
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.gameonline_default));
//            }
//
//
//
//
//
//        });


        Glide.with(context).asBitmap().load(produk.getGame_url()).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(50,50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(viewHolder.imageViewProduk) {
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
                viewHolder.imageViewProduk.setScaleType(ImageView.ScaleType.FIT_CENTER);

                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                viewHolder.avi.setVisibility(View.GONE);
               // viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.gameonline_default));
                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.gameonline_default));
            }
        });


//        Picasso.with(context).load(produk.getGame_url()).resize(40,40).error(R.drawable.gameonline_default).into(viewHolder.imageViewProduk);
//        if(viewHolder.imageViewProduk.getDrawable()==null){
//            viewHolder.imageViewProduk.setBackground(ContextCompat.getDrawable(context, R.drawable.gameonline_default));
//            viewHolder.avi.setVisibility(View.GONE);
//        }else{
//            viewHolder.avi.setVisibility(View.VISIBLE);
//        }
//        Log.d(TAG, "onBindViewHolder: "+viewHolder.imageViewProduk.getDrawable());
////
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

                    ArrayList<GameModel.Response_value> filteredList = new ArrayList<>();

                    for (GameModel.Response_value produk : data) {

                        if (produk.getGame_code().toLowerCase().contains(charString) || produk.getGame_name().toLowerCase().contains(charString)) {

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
                filterData = (ArrayList<GameModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPlusNamaProduk;
        ImageView imageViewProduk;
        AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon;
        LinearLayout linMainListProduk;
//String url;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
           // final GameModel.Response_value produk = filterData.get(i);
            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
            frame_ribbon = itemView.findViewById(R.id.frame_ribbon);
            textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaProduk);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            avi = itemView.findViewById(R.id.avi);
         //   url= (String) textViewPlusNamaProduk.getTag(R.id.urlProduk);

        }
    }

    public interface OnClickProduk {
        void onClickProduk(GameModel.Response_value produk);
    }
}
