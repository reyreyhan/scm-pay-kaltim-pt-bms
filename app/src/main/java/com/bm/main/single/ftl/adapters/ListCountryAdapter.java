package com.bm.main.single.ftl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.scm.R;
import com.bm.main.single.ftl.models.Country;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

/**
 * Created by papahnakal on 13/11/17.
 */

public class ListCountryAdapter extends RecyclerView.Adapter<ListCountryAdapter.ViewHolder> implements Filterable {
    private static final String TAG = ListCountryAdapter.class.getSimpleName();
//    private final int layoutResourceId;
    public  ArrayList<Country>data;// = new ArrayList<>();
    public ArrayList<Country>filterData;// = new ArrayList<>();
    Context context;
    OnClickCountry listener;
    @NonNull
    private final LayoutInflater inflater;
    Bitmap smallBitmap;
    final BitmapFactory.Options options = new BitmapFactory.Options();
    public ListCountryAdapter(@NonNull Context context, ArrayList<Country> data, OnClickCountry listener){


       // this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
//        options.inJustDecodeBounds = false;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        try{
//
//            /*
//             * The convertView argument is essentially a "ScrapView" as described is Lucas post
//             * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
//             * It will have a non-null value when ListView is asking you recycle the row layout.
//             * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
//             */
//            if(convertView==null){
//                // inflate the layout
//           //     LayoutInflater inflater = ((MainActivity) mContext).getLayoutInflater();
//                convertView = View.inflate(context,layoutResourceId, parent);
//            }
//
//            // object item based on the position
//            Country objectItem = filterData.get(position);
//
//            // get the TextView and then set the text (item name) and tag (item ID) values
//            final ImageView imageViewCountryFlag =  convertView.findViewById(R.id.imageViewCountryFlag);
//            TextView textViewItem =  convertView.findViewById(R.id.textViewNama);
//            final AVLoadingIndicatorView avi = convertView.findViewById(R.id.avi);
//            textViewItem.setText(objectItem.name);
//
//            // in case you want to add some style, you can do something like:
////            textViewItem.setBackgroundColor(Color.CYAN);
//
//            Glide.with(context).load(objectItem.flag).asBitmap().encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.RESULT).into(new BitmapImageViewTarget(imageViewCountryFlag) {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
//                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                super.onResourceReady(resource, animation);
//                avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//            // +++++ OR +++++
//            @Override protected void setResource(Bitmap resource) {
//                // this.getView().setImageDrawable(resource); is about to be called
//                super.setResource(resource);
//                avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                avi.setVisibility(View.GONE);
//
//                    //    smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logofcm, options);
//
//                    //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//                    Glide.with(context).load(R.drawable.ic_logofcm).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageViewCountryFlag);
//
//            }
//        });
//
//listener.onClickCountry(objectItem);
//
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return convertView;
//
//    }
    @NonNull
    @Override
    public ListCountryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v =inflater.inflate(R.layout.list_country,viewGroup,false);
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
    public void onBindViewHolder(@NonNull final ListCountryAdapter.ViewHolder viewHolder, int i) {
        final Country produk = filterData.get(i);
        viewHolder.textViewNama.setText(produk.getName());


        Glide.with(context).asBitmap().load(produk.getFlag()).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(viewHolder.imageViewCountryFlag) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
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

                    //    smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logofcm, options);

                    //  viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
                    Glide.with(context).load(R.drawable.ic_logofcm).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewCountryFlag);

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
        viewHolder.linMainListCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCountry(produk);
            }
        });
    }

//    @Nullable
//    @Override
//    public Country getItem(int position) {
//          return filterData.get(position);
//    }
////
//    @Override
//    public int getCount() {
//        return filterData.size();
//    }
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

                    ArrayList<Country> filteredList = new ArrayList<>();

                    for (Country produk : data) {

                        if (produk.getCode().toLowerCase().contains(charString) || produk.getName().toLowerCase().contains(charString) ) {

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
                filterData = (ArrayList<Country>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNama;
        ImageView imageViewCountryFlag;
        AVLoadingIndicatorView avi;

        LinearLayout linMainListCountry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linMainListCountry = itemView.findViewById(R.id.linMainListCountry);
            MaterialRippleLayout.on(linMainListCountry).rippleOverlay(true)
                    .rippleAlpha(0.2f)
                    //.rippleColor(0xFF585858)
                    .rippleColor(R.color.colorPrimary)
                    .rippleHover(true)
                    .create();

            textViewNama = itemView.findViewById(R.id.textViewNama);
            imageViewCountryFlag = itemView.findViewById(R.id.imageViewCountryFlag);

            avi=itemView.findViewById(R.id.avi);

        }

//        public  boolean isNumeric(String str)
//        {
//            try
//            {
//                int d = Integer.parseInt(str);
//            }
//            catch(NumberFormatException nfe)
//            {
//                return false;
//            }
//            return true;
//        }
    }
    public interface OnClickCountry {
        public void onClickCountry(Country produk);
    }

//    public ArrayList<Country> getOriginalData(){
//        return data;
//    }
}
