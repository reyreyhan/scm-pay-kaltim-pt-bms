package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.main.pos.R;
import com.bm.main.fpl.fragments.BerandaFragment;
import com.bm.main.fpl.templates.banner.BannerLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

/**
 * Created by test on 2017/11/22.
 */


public class WebBannerAdapter extends RecyclerView.Adapter<WebBannerAdapter.MzViewHolder> {

    private Context context;
    private List<String> urlList;
    private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;
    // private BannerLayout.OnBannerScrollListener onBannerScrollListener;
    BerandaFragment fragment;


    public WebBannerAdapter(BerandaFragment berandaFragment, Context context, List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
        this.fragment = berandaFragment;
        //BannerLayout.OnBannerItemClickListener(context);
    }


    public void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }


    @NonNull
    @Override
    public WebBannerAdapter.MzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final WebBannerAdapter.MzViewHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty())
            return;
        final int P = position % urlList.size();
        String url = urlList.get(P);
        final ImageView img = holder.imageView;
        // Glide.with(context).load(url).into(img);
        Glide.with(context).asBitmap().load(url)

//                        .fitCenter().dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.logo_tomo_gray_big).error(R.drawable.ic_slide_no_connection).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(img) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                holder.avi.setVisibility(View.GONE);
//                        (resource).getBackground().setAlpha(int alpha);
//                        Drawable x = new BitmapDrawable(context.getResources(),resource);
//                        x.setAlpha(100);
                //fragment.lin_header.setBackground(x);
//                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                // here you can be sure it's already set
            }

            // +++++ OR +++++
            @Override
            protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                holder.avi.setVisibility(View.GONE);

                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                holder.avi.setVisibility(View.GONE);
                // viewHolder.avi.setVisibility(View.GONE);
                // img.setImageResource(R.drawable.ic_slide_no_connection);
                // fragment.lin_header.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_slide_no_connection));
//                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //  Glide.with(context).load(R.drawable.ic_slide_no_connection).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(P);
                }

            }
        });
//        img.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (onBannerScrollListener != null) {
//                    onBannerScrollListener.onBannerScrollListener(P);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (urlList != null) {
            return urlList.size();
        }
        return 0;
    }


    class MzViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        AVLoadingIndicatorView avi;

        MzViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            avi = itemView.findViewById(R.id.avi);
        }
    }
}
