package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.bm.main.scm.R;
import com.bm.main.fpl.models.NotificationModel;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
//import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

//import android.util.Log;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

/**
 * Created by papahnakal on 13/11/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> implements Filterable {
    @NonNull
    private final LayoutInflater inflater;
    ArrayList<NotificationModel.Response_value>data ;//= new ArrayList<>();
    public ArrayList<NotificationModel.Response_value> filterData;// = new ArrayList<>();
    Context context;
    OnClickNotification listener;
//    public int isFlag;
private Bitmap smallBitmap;
    private final BitmapFactory.Options options = new BitmapFactory.Options();
    public NotificationAdapter(ArrayList<NotificationModel.Response_value> data, @NonNull Context context, OnClickNotification listener){
        this.data = data;
        this.filterData = data;
        this.context = context;
        this.listener = listener;
        options.inJustDecodeBounds = true;
         inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
         v = inflater.inflate(R.layout.list_notification,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolder viewHolder, int i) {
        final NotificationModel.Response_value notif = filterData.get(i);
        viewHolder.text_notif_title.setText(notif.getSubject());
        viewHolder.text_notif_content.setText(notif.getContent());
        viewHolder.text_notif_date.setText(notif.getDate_created());
//        if(notif.getThumbnail().equalsIgnoreCase("")) {
//            viewHolder.image_notif_content.setImageResource(R.mipmap.ic_launcher);
//        }else{
           // Picasso.with(context).load(notif.getThumbnail()).fit().into(viewHolder.image_notif_content);
        if(!notif.getThumbnail().isEmpty()) {

//            Picasso.with(context)
//                    .load(notif.getThumbnail()).into(viewHolder.image_notif_content, new Callback() {
//                @Override
//                public void onSuccess() {
//                    viewHolder.avi.setVisibility(View.GONE);
//                  //  viewHolder.image_notif_content.getDrawingCache(true);
//                }
//
//                @Override
//                public void onError() {
//                    viewHolder.avi.setVisibility(View.GONE);
////
//
//                    viewHolder.image_notif_content.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.srikandi));
//
//
//                    //  }
//                }
//
//            });


//            Glide.with(context).load(notif.getThumbnail()).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .listener(new RequestListener<String, Bitmap>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
////                imageViewPromo.setBackground(ContextCompat.getDrawable(context, R.drawable.srikandi));                                                                              // Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.srikandi, options);
//                      //      viewHolder.image_notif_content.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.srikandi));
//                            viewHolder.image_notif_content.setImageBitmap(smallBitmap);
//                            viewHolder.avi.setVisibility(View.GONE);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//
////                Log.d(TAG, "onResourceReady: "+model+" "+isFromMemoryCache+" "+isFirstResource);
//
//                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                            resource.compress(Bitmap.CompressFormat.PNG, 50, bos);
//                            viewHolder.image_notif_content.setImageBitmap(resource);
//                            viewHolder.avi.setVisibility(View.GONE);
//                            return true;
//                        }
//                    }).into(viewHolder.image_notif_content);
//

            Glide.with(context).asBitmap().load(notif.getThumbnail()).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(viewHolder.image_notif_content) {
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
//                    Glide.with(context).load( R.mipmap.ic_launcher).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
                    smallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.srikandi, options);
                    //      viewHolder.image_notif_content.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.srikandi));
                    viewHolder.image_notif_content.setImageBitmap(smallBitmap);
                }
            });


//
//
//                    .into(new GlideDrawableImageViewTarget(viewHolder.image_notif_content) {
//                @Override
//                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                    // here it's similar to RequestListener, but with less information (e.g. no model available)
//                    super.onResourceReady(resource, animation);
//                    viewHolder.avi.setVisibility(View.GONE);
//                    // here you can be sure it's already set
//                }
//                // +++++ OR +++++
//                @Override protected void setResource(GlideDrawable resource) {
//                    // this.getView().setImageDrawable(resource); is about to be called
//                    super.setResource(resource);
//                    viewHolder.avi.setVisibility(View.GONE);
//                    // here you can be sure it's already set
//                }
//
//                @Override
//                public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                    super.onLoadFailed(e, errorDrawable);
//                    viewHolder.avi.setVisibility(View.GONE);
//                    viewHolder.image_notif_content.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.srikandi));
//                }
//            });


        }else{
            viewHolder.frameIcon.setVisibility(View.GONE);
        }
      //  }
//        if(notif.getUrl_icon().equalsIgnoreCase("")) {
//            viewHolder.image_notif_type.setImageResource(R.mipmap.ic_launcher);
//        }else {
           // Picasso.with(context).load(notif.getUrl_icon()).fit().into(viewHolder.image_notif_type);
//            Picasso. with(context)
//                            .load(notif.getUrl_icon()).into(viewHolder.image_notif_type, new Callback() {
//                @Override
//                public void onSuccess() {
//                    viewHolder.avi1.setVisibility(View.GONE);
//               //     viewHolder.image_notif_type.getDrawingCache(true);
//                }
//
//                @Override
//                public void onError() {
//                    viewHolder.avi1.setVisibility(View.GONE);
////
//
//                    viewHolder.image_notif_type.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
//
//
//                    //  }
//                }
//
//            });


        Glide.with(context).asBitmap().load(notif.getUrl_icon()).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(viewHolder.image_notif_type) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> animation) {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                viewHolder.avi1.setVisibility(View.GONE);
                // here you can be sure it's already set
            }
            // +++++ OR +++++
            @Override protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
//                Resources res;
                Drawable drawable=new BitmapDrawable(context.getResources(), resource);
                viewHolder.avi1.setVisibility(View.GONE);
                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                viewHolder.avi1.setVisibility(View.GONE);
                viewHolder.image_notif_type.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logofcm));
            }
        });



//        }
        viewHolder.lin_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNotif(notif);
            }
        });
    }

    @Override
    public int getItemCount() {
       // if(filterData!=null) {
            return filterData.size();
      //  }
       // return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_notif_title,text_notif_content,text_notif_date;
        ImageView image_notif_type,image_notif_content;
        LinearLayout lin_notif;
        AVLoadingIndicatorView avi;
        AVLoadingIndicatorView avi1;
        FrameLayout frameIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avi=itemView.findViewById(R.id.avi);
           avi1=itemView.findViewById(R.id.avi1);
            frameIcon = itemView.findViewById(R.id.frameIcon);
            lin_notif = itemView.findViewById(R.id.lin_notification);
            text_notif_title = itemView.findViewById(R.id.text_notif_title);
            text_notif_content = itemView.findViewById(R.id.text_notif_content);
            text_notif_date = itemView.findViewById(R.id.text_notif_date);
            image_notif_content = itemView.findViewById(R.id.img_notif_content);
            image_notif_type = itemView.findViewById(R.id.img_notif_type);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @NonNull
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {

                String charString = charSequence.toString();
//filterData.clear();
                if (charString.isEmpty()) {

                    filterData = data;
                   // isFlag=1;
                 //   filterData.addAll(data);
                } else {

                    ArrayList<NotificationModel.Response_value> filteredList = new ArrayList<>();

                    for (NotificationModel.Response_value produk : data) {

                        if (produk.getId_tipe_notif().toLowerCase().equals(charString) ) {

                            filteredList.add(produk);
                         //   isFlag=1;
                        }
//                        else{
//                            isFlag=0;
//                        }
                    }
                   // Log.d("filter", "performFiltering: "+filteredList.size());
//                    if(filteredList.size()>0) {
                        filterData = filteredList;
//                    }else{
//                        filterData=null;
//                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterData;
                filterResults.count = filterData.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
             //   filterData.clear();
             //   filterData.addAll((ArrayList<NotificationModel.Response_value>) filterResults.values);
                filterData = (ArrayList<NotificationModel.Response_value>) filterResults.values;
            //    Log.d("publishResults", "publishResults: "+filterData.size());

                notifyDataSetChanged();
            }


        };
    }
    public interface OnClickNotification{
         void onClickNotif(NotificationModel.Response_value notif);
    }

    public ArrayList<NotificationModel.Response_value> getData() {
        //if(isFiltered)
        //     return this.shipModelFilteredArrayList;

        return this.filterData;
    }
}
