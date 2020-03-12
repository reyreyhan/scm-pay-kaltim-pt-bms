package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
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

public class ListGridProdukAdapter extends RecyclerView.Adapter<ListGridProdukAdapter.ViewHolder> implements Filterable {
    private ArrayList<ProdukModel.Response_value>data;// = new ArrayList<>();
    private ArrayList<ProdukModel.Response_value>filterData;// = new ArrayList<>();
    private Context context;
    private OnClickProduk listener;
    SparseBooleanArray selectedItems;
   public String product;
     Bitmap smallBitmap;
    @NonNull
    private final LayoutInflater inflater;
    private final BitmapFactory.Options options = new BitmapFactory.Options();
    public ListGridProdukAdapter(ArrayList<ProdukModel.Response_value> data, @NonNull Context context, OnClickProduk listener, String product){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        this.product=product;
        selectedItems=new SparseBooleanArray();

        options.inJustDecodeBounds = true;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @NonNull
    @Override
    public ListGridProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
         v = inflater.inflate(R.layout.list_produk_grid_image,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ListGridProdukAdapter.ViewHolder viewHolder, final int i) {
        final ProdukModel.Response_value produk = filterData.get(i);
        //viewHolder.linMainListProduk.setTag(i);
        viewHolder.linMainListProduk.setSelected(selectedItems.get(i, false));
        viewHolder.textViewPlusNamaProduk.setText(produk.getProduct_name());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.produkCode,produk.getProduct_code());
        viewHolder.textViewPlusNamaProduk.setTag(R.id.isGangguanProduk,produk.getIs_gangguan());

        if (produk.getIs_promo().equalsIgnoreCase("1")) {
            viewHolder.frame_ribbon.setVisibility(View.VISIBLE);
        }

//        Picasso.with(context)
//                .load(produk.getProduct_url()).into(viewHolder.imageViewProduk, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.avi.setVisibility(View.GONE);
//             //   viewHolder.imageViewProduk.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//                viewHolder.avi.setVisibility(View.GONE);
////                if (produk.getProduct_code().startsWith("WA")) {
////                    viewHolder.imageViewProduk.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_menu_pdam));
////                } else {
//                if(viewHolder.flagProduct.equals("TVKABEL")) {
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_tv_kabel));
//                }else  if(viewHolder.flagProduct.equals("GAME")){
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_game_online));
//                }else{
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//                }
//
//              //  }
//            }
//
//        });


//        Glide.with(context).load(produk.getProduct_url()).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, Bitmap>() {
//
//
//            @Override
//            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                viewHolder.avi.setVisibility(View.GONE);
//                switch (viewHolder.flagProduct) {
//                    case "TVKABEL":
//                         smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_tv_kabel, options);
//                     //   viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_tv_kabel));
//                       // viewHolder.imageViewProduk.setImageBitmap(smallBitmap);
//                        break;
//                    case "GAME":
//                         smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_game_online, options);
//                      //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_game_online));
//                       // viewHolder.imageViewProduk.setImageBitmap(smallBitmap);
//                        break;
//                    default:
//                        smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logofcm, options);
//                      //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//                        break;
//                }
//                viewHolder.imageViewProduk.setImageBitmap(smallBitmap);
//                return false;
//            }
//
//
//
//            @Override
//            public boolean onResourceReady(Bitmap resource, String model,Target<Bitmap> target, boolean isFromMemoryCache,boolean isFirstResource) {
//
//
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                resource.compress(Bitmap.CompressFormat.PNG, 50, bos);
//                viewHolder.imageViewProduk.setImageBitmap(resource);
//                viewHolder.avi.setVisibility(View.GONE);
//                return true;
//            }
//
//        }).into(viewHolder.imageViewProduk);
//



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
//                viewHolder.avi.setVisibility(View.GONE);
//                Glide.with(context).load( R.mipmap.ic_launcher).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
                viewHolder.avi.setVisibility(View.GONE);
                switch (viewHolder.flagProduct) {
                    case "TVKABEL":
                        smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_tv_kabel, options);
                        //   viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_tv_kabel));
                        // viewHolder.imageViewProduk.setImageBitmap(smallBitmap);
                        break;
                    case "GAME":
                        smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_game_online, options);
                        //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_game_online));
                        // viewHolder.imageViewProduk.setImageBitmap(smallBitmap);
                        break;
                    default:
                        smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logofcm, options);
                        //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
                        break;
                }
                viewHolder.imageViewProduk.setImageBitmap(smallBitmap);

            }


        });



//
//
//
//                .into(new GlideDrawableImageViewTarget(viewHolder.imageViewProduk) {
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
//                if(viewHolder.flagProduct.equals("TVKABEL")) {
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_tv_kabel));
//                }else  if(viewHolder.flagProduct.equals("GAME")){
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_menu_game_online));
//                }else{
//                    viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//                }
//            }
//        });
      //  viewHolder.linMainListProduk.setSelected(false);
      //  viewHolder.linMainListProduk.setSelected(false);



            viewHolder.linMainListProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // get the item position.
                 int adapterPos = viewHolder.getAdapterPosition();
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
                        clearSelections();
                        toggleSelection(adapterPos);

                        listener.onClickProduk(produk);
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
        TextView textViewPlusNamaProduk;
        ImageView imageViewProduk;
        AVLoadingIndicatorView avi;
        FrameLayout frame_ribbon;
        String flagProduct=ListGridProdukAdapter.this.product;
       public LinearLayout linMainListProduk;
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
            textViewPlusNamaProduk = itemView.findViewById(R.id.textViewPlusNamaProduk);
            imageViewProduk = itemView.findViewById(R.id.imageViewProduk);
            avi=itemView.findViewById(R.id.avi);



        }
    }
    public interface OnClickProduk {
        void onClickProduk(ProdukModel.Response_value produk);
    }
}
