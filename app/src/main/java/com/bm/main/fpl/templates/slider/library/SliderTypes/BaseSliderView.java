package com.bm.main.fpl.templates.slider.library.SliderTypes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bm.main.pos.R;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;


/**
 * When you want to make your own slider view, you must extends from this class.
 * BaseSliderView provides some useful methods.
 * I provide two example: {@link com.bm.main.fpl.templates.slider.library.SliderTypes.DefaultSliderView} and
 * {@link com.bm.main.fpl.templates.slider.library.SliderTypes.TextSliderView}
 * if you want to show progressbar, you just need to set a progressbar id as @+id/loading_bar.
 */
public abstract class BaseSliderView {

    public Context mContext;

    private Bundle mBundle;

    private String mUrl;
    private File mFile;
    private int mRes;

    public OnSliderClickListener mOnSliderClickListener;

    public ImageLoadListener mLoadListener;

    private String mDescription;

    //private RequestOptions mRequestOptions;

    private boolean isProgressBarVisible = false;

    private int mBackgroundColor = Color.BLACK;

    /**
     * Ctor
     *
     * @param context
     */
    public BaseSliderView(Context context) {
        mContext = context;
    }

    /**
     * the description of a slider image.
     *
     * @param description
     * @return
     */
    @NonNull
    public BaseSliderView description(String description) {
        mDescription = description;
        return this;
    }

    /**
     * set a url as a image that preparing to load
     *
     * @param url
     * @return
     */
    @NonNull
    public BaseSliderView image(String url) {
        if (mFile != null || mRes != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mUrl = url;
        return this;
    }

    /**
     * set a file as a image that will to load
     *
     * @param file
     * @return
     */
    @NonNull
    public BaseSliderView image(File file) {
        if (mUrl != null || mRes != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mFile = file;
        return this;
    }

    @NonNull
    public BaseSliderView image(int res) {
        if (mUrl != null || mFile != null) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mRes = res;
        return this;
    }

    /**
     * lets users add a bundle of additional information
     *
     * @param bundle
     * @return
     */
    @NonNull
    public BaseSliderView bundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * set a slider image click listener
     *
     * @param l
     * @return
     */
    @NonNull
    public BaseSliderView setOnSliderClickListener(OnSliderClickListener l) {
        mOnSliderClickListener = l;
        return this;
    }

    /**
     * set Glide RequestOption
     *
     * @param requestOption
     * @return self
     */
//    public BaseSliderView setRequestOption(RequestOptions requestOption) {
//        mRequestOptions = requestOption;
//        return this;
//    }

    /**
     * set Progressbar visible or gone
     *
     * @param isVisible
     */
    @NonNull
    public BaseSliderView setProgressBarVisible(boolean isVisible) {
        isProgressBarVisible = isVisible;
        return this;
    }

    /**
     * set Background Color
     *
     * @param color
     */
    @NonNull
    public BaseSliderView setBackgroundColor(int color) {
        mBackgroundColor = color;
        return this;
    }

    /**
     * When you want to implement your own slider view, please call this method in the end in `getView()` method
     *
     * @param v               the whole view
     * @param targetImageView where to place image
     */
    protected void bindEventAndShow(@NonNull final View v, @Nullable final ImageView targetImageView) {
        final BaseSliderView me = this;

//        try {
//            v.findViewById(R.id.glide_slider_background).setBackgroundColor(mBackgroundColor);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(me);
                }
            }
        });

        if (targetImageView == null)
            return;

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }

        final AVLoadingIndicatorView mProgressBar = v.findViewById(R.id.avi);
        if (isProgressBarVisible) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }

        Object imageToLoad = null;
        if (mUrl != null) {
            imageToLoad = mUrl;
        } else if (mFile != null) {
            imageToLoad = mFile;
        } else if (mRes != 0) {
            imageToLoad = mRes;
        }

        final RequestManager requestBuilder = Glide.with(mContext);//.as(Drawable.class);

        if (imageToLoad != null) {
            requestBuilder.asBitmap().load(imageToLoad)
//                        .error(R.drawable.ic_slide_no_connection)
//                        .fitCenter().dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(targetImageView) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation)  {
                    // here it's similar to RequestListener, but with less information (e.g. no model available)
                    super.onResourceReady(resource, animation);
                    mProgressBar.setVisibility(View.GONE);
                    targetImageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    // here you can be sure it's already set
                }

                // +++++ OR +++++
                @Override
                protected void setResource(Bitmap resource) {
                    // this.getView().setImageDrawable(resource); is about to be called
                    super.setResource(resource);
                    mProgressBar.setVisibility(View.GONE);

                    // here you can be sure it's already set
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                    mProgressBar.setVisibility(View.GONE);
                    targetImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    targetImageView.setImageResource(R.drawable.ic_slide_no_connection);
                    // viewHolder.avi.setVisibility(View.GONE);
                    //  requestBuilder.load(R.drawable.ic_slide_no_connection).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(targetImageView);
                }
            });

        }

    }

    /**
     * the extended class have to implement getView(), which is called by the adapter,
     * every extended class response to render their own view.
     *
     * @return
     */
    public abstract View getView();

    /**
     * set a listener to get a message , if load error.
     *
     * @param l
     */
    public void setOnImageLoadListener(ImageLoadListener l) {
        mLoadListener = l;
    }

    public interface OnSliderClickListener {
        public void onSliderClick(BaseSliderView slider);
    }

    /**
     * when you have some extra information, please put it in this bundle.
     *
     * @return
     */
    public Bundle getBundle() {
        return mBundle;
    }

    public interface ImageLoadListener {
        public void onStart(BaseSliderView target);

        public void onEnd(boolean result, BaseSliderView target);
    }
}
