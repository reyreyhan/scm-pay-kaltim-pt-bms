package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.ProdukPulsaModel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class ListGridProdukPulsaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
//    public  TextView textViewPlusNominalProduk_;
    private ArrayList<ProdukPulsaModel.Response_value>data ;//= new ArrayList<>();
    private ArrayList<ProdukPulsaModel.Response_value>filterData ;//= new ArrayList<>();
    private Context context;
    private OnClickProduk listener;
    SparseBooleanArray selectedItems;
//    private static final int VIEW_TYPE_EMPTY_LIST_PLACEHOLDER = 0;
//    private static final int VIEW_TYPE_OBJECT_VIEW = 1;
   // public TextView textViewPlusNominalProduk;
//   Bitmap smallBitmap;
   @NonNull
   private final LayoutInflater inflater;
    public ListGridProdukPulsaAdapter(ArrayList<ProdukPulsaModel.Response_value> data, @NonNull Context context, OnClickProduk listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        selectedItems=new SparseBooleanArray();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    //public ListGridProdukPulsaAdapter.SolventViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      //  View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_produk_grid,viewGroup,false);
//        SolventViewHolders rcv = new SolventViewHolders(MaterialRippleLayout.on(v)
//                .rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                //.rippleColor(0xFF585858)
//                .rippleColor(R.color.colorPrimary)
//                .rippleHover(true)
//                .create()
//        );
       // return new SolventViewHolders(v);



        View v;
//        switch (i) {
//            case VIEW_TYPE_EMPTY_LIST_PLACEHOLDER:
//                // return view holder for your placeholder
//                v = inflater.inflate(R.layout.layout_data_empty, viewGroup, false);
//                return new EmptyViewHolder(v);

//            case VIEW_TYPE_OBJECT_VIEW:
                // return view holder for your normal list item
                v = inflater.inflate(R.layout.list_produk_grid, viewGroup, false);

                return new ViewHolder(v);

//        }
//        return null ;
    }

//    @Override
//    public void onBindViewHolder(RecyclerView.SolventViewHolders holder, int position) {
//
//    }

    @Override
   // public void onBindViewHolder(final ListGridProdukPulsaAdapter.SolventViewHolders viewHolder, final int i) {
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        //viewHolder.linMainListProduk.setTag(i);
        if (viewHolder instanceof ViewHolder) {

             final ViewHolder vh = (ViewHolder) viewHolder;
            final ProdukPulsaModel.Response_value produk = filterData.get(i);
            vh.linMainListProduk.setSelected(selectedItems.get(i, false));
            vh.textViewPlusNamaProduk.setText(produk.getProduct_name());
            vh.textViewPlusNominalProduk.setText(produk.getProduct_nominal());
            //vh.imageViewWatermark.setImageURI(Uri.parse(produk.getProduct_url()));
//            Picasso. with(context)
//                    .load(produk.getProduct_url()).fit().into(vh.imageViewWatermark, new Callback() {
//                @Override
//                public void onSuccess() {
//                   // imageViewPrefix.setVisibility(View.VISIBLE);
//               //     vh.imageViewWatermark.getDrawingCache(true);
//                  //  avi.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onError() {
//                  //  avi.setVisibility(View.GONE);
//                    //  imageViewPrefix.setVisibility(View.VISIBLE);
//                   // imageViewPrefix.setVisibility(View.GONE);
////
//                    //   imageViewPrefix.setImageDrawable(ContextCompat.getDrawable(PulsaActivity.this, R.drawable.ic_no_connection_small));
//
//                }
//
//            });

//            Glide.with(context).load(produk.getProduct_url()).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, Bitmap>() {
//                @Override
//                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
////                imageViewPromo.setBackground(ContextCompat.getDrawable(context, R.drawable.srikandi));                                                                              // Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
////                    smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.srikandi, options);
////                    //      viewHolder.image_notif_content.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.srikandi));
////                    viewHolder.image_notif_content.setImageBitmap(smallBitmap);
//                   // viewHolder.avi.setVisibility(View.GONE);
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//
////                Log.d(TAG, "onResourceReady: "+model+" "+isFromMemoryCache+" "+isFirstResource);
//
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    resource.compress(Bitmap.CompressFormat.PNG, 50, bos);
//                    vh.imageViewWatermark.setImageBitmap(resource);
//                   // viewHolder.avi.setVisibility(View.GONE);
//                    return true;
//                }
//            }).into(vh.imageViewWatermark);


            Glide.with(context).asBitmap().load(produk.getProduct_url()).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).override(50,50).into(new BitmapImageViewTarget(vh.imageViewWatermark) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation)  {
                    // here it's similar to RequestListener, but with less information (e.g. no model available)
                    super.onResourceReady(resource, animation);
                   // vh.avi.setVisibility(View.GONE);
                    // here you can be sure it's already set
                }
                // +++++ OR +++++
                @Override protected void setResource(Bitmap resource) {
                    // this.getView().setImageDrawable(resource); is about to be called
                    super.setResource(resource);
                    vh.imageViewWatermark.setScaleType(ImageView.ScaleType.FIT_CENTER);
                   // vh.avi.setVisibility(View.GONE);
                    // here you can be sure it's already set
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                  //  viewHolder.avi.setVisibility(View.GONE);
                 //   Glide.with(context).load( R.mipmap.ic_launcher).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(vh.imageViewWatermark);
                }
            });



//
//
//
//                    .into(new GlideDrawableImageViewTarget(vh.imageViewWatermark) {
//                @Override
//                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                    // here it's similar to RequestListener, but with less information (e.g. no model available)
//                    super.onResourceReady(resource, animation);
//                    //viewHolder.avi.setVisibility(View.GONE);
//                    // here you can be sure it's already set
//                }
//                // +++++ OR +++++
//                @Override protected void setResource(GlideDrawable resource) {
//                    // this.getView().setImageDrawable(resource); is about to be called
//                    super.setResource(resource);
//                  //  viewHolder.avi.setVisibility(View.GONE);
//                    // here you can be sure it's already set
//                }
//
//                @Override
//                public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                    super.onLoadFailed(e, errorDrawable);
////                    viewHolder.avi.setVisibility(View.GONE);
////                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_topup_emoney));
//                }
//            });


            if(PreferenceClass.getBoolean("switchHarga", true)==true){
              //  Log.d("pulsa adapter", "onBindViewHolder true: "+PreferenceClass.getBoolean("switchHargaPulsa",true));
                vh.textViewPlusNominalProduk.setVisibility(View.VISIBLE);
                vh.textViewPlusNominalProduk.refreshDrawableState();
            }else{
              //  Log.d("pulsa adapter", "onBindViewHolder false: "+PreferenceClass.getBoolean("switchHargaPulsa",true));
                vh.textViewPlusNominalProduk.setVisibility(View.GONE);
                vh.textViewPlusNominalProduk.refreshDrawableState();
            }
//
//            if(PreferenceClass.getString("switchHargaGame", "").equals("show")){
//                vh.textViewPlusNominalProduk.setVisibility(View.GONE);
//            }else{
//                vh.textViewPlusNominalProduk.setVisibility(View.VISIBLE);
//            }
////
//            if(PreferenceClass.getString("switchHargaTopup", "").equals("show")){
//                vh.textViewPlusNominalProduk.setVisibility(View.GONE);
//            }else{
//                vh.textViewPlusNominalProduk.setVisibility(View.VISIBLE);
//            }


            vh.textViewPlusNamaProduk.setTag(R.id.produkCode, produk.getProduct_code());
            vh.textViewPlusNamaProduk.setTag(R.id.isGangguanProduk, produk.getIs_gangguan());

            if (produk.getIs_promo().equalsIgnoreCase("1")) {
                vh.frame_ribbon.setVisibility(View.VISIBLE);
            }

//        Picasso.with(context).load(produk.getProduct_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
            //  viewHolder.avi.setVisibility(View.GONE);
            //  viewHolder.imageViewProduk.setVisibility(View.GONE);
            //  viewHolder.frameIcon.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
////                if (produk.getProduct_code().startsWith("WA")) {
////                    viewHolder.imageViewProduk.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
////                } else {
//                    viewHolder.imageViewProduk.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_logo));
//              //  }
//            }
//
//        });
            //  viewHolder.linMainListProduk.setSelected(false);
            //  viewHolder.linMainListProduk.setSelected(false);


            vh.linMainListProduk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get the item position.
                    int adapterPos = vh.getAdapterPosition();
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
                    if (adapterPos != RecyclerView.NO_POSITION) {
                        if (listener != null) {


                            listener.onClickProduk(produk, adapterPos);
                        }
                    }

                }
            });
        }else{
            Log.d("pulsa", "onBindViewHolder: masuk else");
        }

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

//    public int getSelectedItemCount() {
//        return selectedItems.size();
//    }

    @NonNull
    public ArrayList<Integer> getSelectedItems() {
        ArrayList<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

//    public int getItemViewType(int position) {
//        return filterData.get(position).getType();
//   /*this returns the view type of each element in the list*/
//    }

    @Override
    public int getItemCount() {
        return filterData == null ? 0 : filterData.size();
        //return filterData.size();
//        Log.d("Size", "" + filterData.size());
//        if (filterData.size() > 0) {
//            Log.d("Size", "" + filterData.size());
//            return filterData.size();
//        }

//        else if(removeModel.size()==0){
//            Log.d("Size remove", "" + removeModel.size());
//            Log.d("","");
//            return 1;
//        }
       // return 0;
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

                    ArrayList<ProdukPulsaModel.Response_value> filteredList = new ArrayList<>();

                    for (ProdukPulsaModel.Response_value produk : data) {

                        if (produk.getType().equals(charString)) {

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
                filterData = (ArrayList<ProdukPulsaModel.Response_value>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         public TextView textViewPlusNamaProduk,textViewPlusNominalProduk;
      //  ImageView imageViewProduk;
       // AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon;
        ImageView imageViewWatermark;
     //   FrameLayout frameIcon;
       public RelativeLayout linMainListProduk;
        public ViewHolder(@NonNull View itemView) {
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
          //  frameIcon =  itemView.findViewById(R.id.frameIcon);
            textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaProduk);

            textViewPlusNominalProduk = itemView.findViewById(R.id.textViewPlusNominalProduk);
          //  textViewPlusNominalProduk_ = itemView.findViewById(R.id.textViewPlusNominalProduk);

            imageViewWatermark=itemView.findViewById(R.id.imageViewWatermark);
//            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
//            imageViewProduk.setVisibility(View.GONE);
           // avi=itemView.findViewById(R.id.avi);
          //  avi.setVisibility(View.GONE);



        }
    }

    public void removeData(){
        filterData.clear();
        data.clear();
        notifyDataSetChanged();
    }

    public interface OnClickProduk {
        void onClickProduk(ProdukPulsaModel.Response_value produk, int adapterPos);
    }

//    @Override
//    public int getItemViewType(int position) {
//        Log.d("pulsa adapater", "getItemViewType: "+position);
////        if (filterData.size() == 0) {
////            return VIEW_TYPE_EMPTY_LIST_PLACEHOLDER;
////        } else {
////            return VIEW_TYPE_OBJECT_VIEW;
////        }
//
//        return filterData.get(position) == null ? VIEW_TYPE_EMPTY_LIST_PLACEHOLDER : VIEW_TYPE_OBJECT_VIEW;
//    }
//    public class EmptyViewHolder extends RecyclerView.ViewHolder {
//        TextView txtHeader,txtSub;
//
//        EmptyViewHolder(View itemView) {
//            super(itemView);
//            txtHeader = itemView.findViewById(R.id.txtHeader);
//            txtHeader.setText("Produk SMS");
//            txtSub = itemView.findViewById(R.id.txtSub);
//            txtSub.setText("Belum Tersedia");
//        }
//    }
}
