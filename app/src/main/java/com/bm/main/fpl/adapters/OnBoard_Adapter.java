package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
//import androidx.core.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.OnBoardModel;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ThumbnailImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.SimpleTimeZone;

import io.reactivex.annotations.NonNull;

/**
 * Author: sarifhidayat
 * Created on 2/14/19
 */
public class OnBoard_Adapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<OnBoardModel.Response_value> onBoardItems;//=new ArrayList<>();


    public OnBoard_Adapter(Context mContext, ArrayList<OnBoardModel.Response_value> items) {
        this.mContext = mContext;
        this.onBoardItems = items;
    }

    @Override
    public int getCount() {
        return onBoardItems.size();
    }
    @NonNull
    @Override
    public boolean isViewFromObject(@androidx.annotation.NonNull View view, @androidx.annotation.NonNull Object object) {
        return view == object;
    }
@androidx.annotation.NonNull
@NonNull
    @Override
    public Object instantiateItem(@androidx.annotation.NonNull ViewGroup container, int position) {
        RelativeLayout itemView;
         itemView =(RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.onboard_item, container, false);

        OnBoardModel.Response_value onBoardModel=onBoardItems.get(position);
   final AVLoadingIndicatorView avi=itemView.findViewById(R.id.avi);
//LinearLayout linMainOnBoarding=itemView.findViewById(R.id.linMainOnBoarding);
//if(position%2 ==0){
//    linMainOnBoarding.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_batik_blue));
//}else {
//    linMainOnBoarding.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_batik_orange));
//}
//        final ScaleImageView imageView =  itemView.findViewById(R.id.imageViewOnBoard);
////        imageView.setImageResource(item.getImageID());
//        Glide.with(mContext).load(onBoardModel.getOnboardUrl()).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50))
//                .override(OnBoardingActivity.config.screenWidthDp,OnBoardingActivity.config.screenHeightDp)
//                .diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(imageView) {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> animation) {
//                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                super.onResourceReady(resource, animation);
////                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//            // +++++ OR +++++
//            @Override protected void setResource(Bitmap resource) {
//                // this.getView().setImageDrawable(resource); is about to be called
//                super.setResource(resource);
//                avi.setVisibility(View.GONE);
//
//
//                // here you can be sure it's already set
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                avi.setVisibility(View.GONE);
////                viewHolder.avi.setVisibility(View.GONE);
//               // imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.srikandi));
//            }
//        });
    final RelativeLayout linOnBoardItem=itemView.findViewById(R.id.linOnBoardItem);

//    float aspectRatio = 6/3f;
//    int newWidth = OnBoardingActivity.displayMetrics.widthPixels;
//    int newHeight = (int) (newWidth / aspectRatio);
//    Log.d("Onboard", "instantiateItem: "+newWidth+" "+newHeight);
    avi.setVisibility(View.VISIBLE);

//    Glide.with(mContext).asBitmap().load(onBoardModel.getOnboardUrl()).sizeMultiplier(1).centerCrop().encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE)
    Glide.with(mContext).asBitmap().load(onBoardModel.getOnboardUrl()).optionalCenterCrop().encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {



        @Override
        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
            avi.setVisibility(View.GONE);
            Drawable drawable = new BitmapDrawable(mContext.getResources(), resource);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                linOnBoardItem.setBackground(drawable);

            }
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
            avi.setVisibility(View.GONE);
        }
    });

        container.addView(itemView);

        return itemView;
    }
    @NonNull
    @Override
    public void destroyItem(@androidx.annotation.NonNull ViewGroup container, int position, @androidx.annotation.NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

//    private void changeBackgroundDrawable(int resourceId, final Object object){
//        Glide.with(mContext).load(resourceId).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(object) {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> animation) {
//                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                super.onResourceReady(resource, animation);
////                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//            // +++++ OR +++++
//            @Override protected void setResource(Bitmap resource) {
//                // this.getView().setImageDrawable(resource); is about to be called
//                super.setResource(resource);
////                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
////                viewHolder.avi.setVisibility(View.GONE);
//                ((LinearLayout)object).setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_batik_blue));
//            }
//        });
//    }

}
