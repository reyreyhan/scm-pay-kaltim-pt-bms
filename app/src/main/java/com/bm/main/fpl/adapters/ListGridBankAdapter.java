package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.BankModel;
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

public class ListGridBankAdapter extends RecyclerView.Adapter<ListGridBankAdapter.ViewHolder> implements Filterable {
    private ArrayList<BankModel.Response_value>data ;//= new ArrayList<>();
    private ArrayList<BankModel.Response_value>filterData ;//= new ArrayList<>();
    private Context context;
    public OnClickProduk listener;
    SparseBooleanArray selectedItems;

    public ListGridBankAdapter(ArrayList<BankModel.Response_value> data, Context context, OnClickProduk listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        selectedItems=new SparseBooleanArray();

    }
    @NonNull
    @Override
    public ListGridBankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_bank_grid,viewGroup,false);

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
    public void onBindViewHolder(@NonNull final ListGridBankAdapter.ViewHolder viewHolder, final int i) {
        final BankModel.Response_value produk = filterData.get(i);
        //viewHolder.linMainListProduk.setTag(i);
        viewHolder.linMainListProduk.setSelected(selectedItems.get(i, false));
       // viewHolder.textViewPlusNamaProduk.setText(produk.getBank_name());
      //  viewHolder.textViewPlusNamaProduk.setTag(R.id.produkCode,produk.getBank_code());


//        if (produk.getIs_promo().equalsIgnoreCase("1")) {
//            viewHolder.frame_ribbon.setVisibility(View.VISIBLE);
//        }

//        Picasso.with(context)
//                .load(produk.getProduct_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.avi.setVisibility(View.GONE);
////                viewHolder.imageViewProduk.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
////                if (produk.getProduct_code().startsWith("WA")) {
////                    viewHolder.imageViewProduk.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
////                } else {
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo_color));
//              //  }
//            }
//
//        });
//        Glide.clear(viewHolder.imageViewProduk);
//        Glide.with(context).load(produk.getProduct_url()).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).error(R.mipmap.ic_launcher).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, Bitmap>() {
//
//
//            @Override
//            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//               // Log.d(TAG, "onResourceReady: "+e.toString()+" model="+model+" "+target+" "+isFirstResource);
//
//                Glide.with(context).load( R.mipmap.ic_launcher).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
//
//                viewHolder.avi.setVisibility(View.GONE);
//                return false;
//            }
//
//
//
//            @Override
//            public boolean onResourceReady(Bitmap resource, String model,Target<Bitmap> target, boolean isFromMemoryCache,boolean isFirstResource) {
//
//              //  Log.d(TAG, "onResourceReady: "+model+" "+isFromMemoryCache+" "+isFirstResource);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                resource.compress(Bitmap.CompressFormat.PNG, 50, bos);
//                viewHolder.imageViewProduk.setImageBitmap(resource);
//
//                viewHolder.avi.setVisibility(View.GONE);
//                return true;
//            }
//        }).into(viewHolder.imageViewProduk);

        Glide.with(context).asBitmap().load(produk.getProduct_url()).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).override(50,50).into(new BitmapImageViewTarget(viewHolder.imageViewProduk) {
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
                viewHolder.imageViewProduk.setScaleType(ImageView.ScaleType.FIT_CENTER);
                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                viewHolder.avi.setVisibility(View.GONE);
//                Glide.with(context).load( R.mipmap.ic_launcher).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_launcher));
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
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo_color));
//            }
//        });

      //  viewHolder.linMainListProduk.setSelected(false);
      //  viewHolder.linMainListProduk.setSelected(false);



            viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // get the item position.

             //   Integer pos;
             //   pos = (Integer) v.getTag();

//                if(i==adapterPos){
//                    viewHolder.linMainListProduk.setSelected(true);
//                    // selectedItems.put(adapterPos, true);
//                }else{
//                    //  selectedItems.delete(adapterPos);
//                    viewHolder.linMainListProduk.setSelected(false);
//                }
               // notifyDataSetChanged();
                int adapterPos = viewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (listener != null) {
                        clearSelections();
                        toggleSelection(adapterPos);

                        listener.onClickProduk(produk,adapterPos);
                    }
                }

            }
        });

    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    @NonNull
    public ArrayList<Integer> getSelectedItems() {
        ArrayList<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
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
       // TextViewPlus textViewPlusNamaProduk;
        ImageView imageViewProduk;
        AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon;
       public  RelativeLayout linMainListProduk;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListProduk = itemView.findViewById(R.id.linMainListProduk);
            MaterialRippleLayout.on(linMainListProduk).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary_ppob)
                    .rippleHover(true)
                    .create();
            linMainListProduk.setSelected(false);
            frame_ribbon =  itemView.findViewById(R.id.frame_ribbon);
           // textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaProduk);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            avi=itemView.findViewById(R.id.avi);
            avi.setVisibility(View.VISIBLE);



        }
    }
    public interface OnClickProduk {
        void onClickProduk(BankModel.Response_value produk, int adapterPos);
    }
}
