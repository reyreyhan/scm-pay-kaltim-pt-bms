package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.bm.main.fpl.models.ProdukModel;
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

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListProdukAdapter extends RecyclerView.Adapter<ListProdukAdapter.ViewHolder> implements Filterable {
    private static final String TAG = ListProdukAdapter.class.getSimpleName();
   public  ArrayList<ProdukModel.Response_value>data = new ArrayList<>();
    ArrayList<ProdukModel.Response_value>filterData = new ArrayList<>();
    Context context;
    OnClickProduk listener;
    @NonNull
    private final LayoutInflater inflater;
    Bitmap smallBitmap;
    final BitmapFactory.Options options = new BitmapFactory.Options();
    public ListProdukAdapter(ArrayList<ProdukModel.Response_value> data, @NonNull Context context, OnClickProduk listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        options.inJustDecodeBounds = false;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public ListProdukAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       View v;
         v =inflater.inflate(R.layout.list_produk,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ListProdukAdapter.ViewHolder viewHolder, int i) {
        final ProdukModel.Response_value produk = filterData.get(i);
        viewHolder.textViewPlusNamaProduk.setText(produk.getProduct_name());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.produkCode,produk.getProduct_code());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.isGangguanProduk,produk.getIs_gangguan());
        if (produk.getIs_promo().equalsIgnoreCase("1")) {
            Glide.with(context).load(R.drawable.ic_promo).into(viewHolder.imageViewRibbonPromo);
            viewHolder.frame_ribbon.setVisibility(View.VISIBLE);
        }
//        Picasso. with(context)
//                        .load(produk.getProduct_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.avi.setVisibility(View.GONE);
//           //     viewHolder.imageViewProduk.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
//                if (produk.getProduct_code().startsWith("WA")) {
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
//                } else {
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//                }
//                viewHolder.imageViewProduk.getDrawingCache(true);
//            }
//
//        });

//        Glide.with(context).load(produk.getProduct_url()).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).listener(new RequestListener<String, Bitmap>() {
//
//
//            @Override
//            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                Log.d(TAG, "onException: "+e.toString()+" model="+model+" "+target+" "+isFirstResource);
//                viewHolder.avi.setVisibility(View.GONE);
//                if (produk.getProduct_code().startsWith("WA")) {
//                  //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
//                  //  smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_pdam, options);
//                    Glide.with(context).load(R.drawable.ic_menu_pdam).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
//                } else {
//                //    smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logofcm, options);
//
//                  //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//                    Glide.with(context).load(R.drawable.ic_logofcm).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
//                }
//               // Glide.with(context).load(smallBitmap).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
//              //  viewHolder.imageViewProduk.setImageBitmap(smallBitmap);
//
//                return false;
//            }
//
//
//
//            @Override
//            public boolean onResourceReady(Bitmap resource, String model,Target<Bitmap> target, boolean isFromMemoryCache,boolean isFirstResource) {
//
//                Log.d(TAG, "onResourceReady: "+model+" "+isFromMemoryCache+" "+isFirstResource);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                resource.compress(Bitmap.CompressFormat.PNG, 50, bos);
//                    viewHolder.imageViewProduk.setImageBitmap(resource);
//
////                    if (produk.getProduct_code().startsWith("WA")) {
////                        viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
////                    } else {
////                        viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
////                    }
////                }
//
//                viewHolder.avi.setVisibility(View.GONE);
//                return true;
//            }
//        }).into(viewHolder.imageViewProduk);


        Glide.with(context).asBitmap().load(produk.getProduct_url()).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(viewHolder.imageViewProduk) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation)  {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                viewHolder.avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }
            // +++++ OR +++++
            @Override protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                viewHolder.avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                viewHolder.avi.setVisibility(View.GONE);
                if (produk.getProduct_code().startsWith("WA")) {
                      viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
                    //  smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_pdam, options);
//                    Glide.with(context).load(R.drawable.ic_menu_pdam).into(viewHolder.imageViewProduk);
                }else if (viewHolder.isNumeric(produk.getProduct_code())) {
                   // Glide.with(context).load(R.drawable.ic_menu_perbankan).into(viewHolder.imageViewProduk);
                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_perbankan));
                } else {
                    //    smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logofcm, options);

                    //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
                   // Glide.with(context).load(R.drawable.ic_logofcm).into(viewHolder.imageViewProduk);
                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
                }
            }
        });




//                .into(new GlideDrawableImageViewTarget(viewHolder.imageViewProduk) {
//            @Override
//            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                super.onResourceReady(resource, animation);
//
//                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//            // +++++ OR +++++
//            @Override protected void setResource(GlideDrawable resource) {
//                // this.getView().setImageDrawable(resource); is about to be called
//                super.setResource(resource);
//
//                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                viewHolder.avi.setVisibility(View.GONE);
//                if (produk.getProduct_code().startsWith("WA")) {
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
//                } else {
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//                }
//                viewHolder.imageViewProduk.getDrawingCache(true);
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

                    ArrayList<ProdukModel.Response_value> filteredList = new ArrayList<>();

                    for (ProdukModel.Response_value produk : data) {

                        if (produk.getProduct_code().toLowerCase().contains(charString) || produk.getProduct_name().toLowerCase().contains(charString) ) {

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
                filterData = (ArrayList<ProdukModel.Response_value>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPlusNamaProduk;
        ImageView imageViewProduk,imageViewRibbonPromo;
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
            imageViewRibbonPromo = itemView.findViewById(R.id.imageViewRibbonPromo);
            avi=itemView.findViewById(R.id.avi);

        }

        public  boolean isNumeric(@NonNull String str)
        {
            try
            {
                int d = Integer.parseInt(str);
            }
            catch(NumberFormatException nfe)
            {
                return false;
            }
            return true;
        }
    }
    public interface OnClickProduk {
        public void onClickProduk(ProdukModel.Response_value produk);
    }

    public ArrayList<ProdukModel.Response_value> getOriginalData(){
        return data;
    }
}
