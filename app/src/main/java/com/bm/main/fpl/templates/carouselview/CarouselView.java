package com.bm.main.fpl.templates.carouselview;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews.RemoteView;

import com.bm.main.scm.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sayyam on 11/25/15.
 *
 * @attr ref R.styleable#CarouselView_pageTransformer
 */
@RemoteView
public class CarouselView extends FrameLayout {

//    private static final String TAG =CarouselView.class.getSimpleName() ;
    private final int DEFAULT_GRAVITY = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private static final int DEFAULT_SLIDE_INTERVAL = 3500;
    private static final int DEFAULT_SLIDE_VELOCITY = 400;


    private int mPageCount;
    private int slideInterval = DEFAULT_SLIDE_INTERVAL;
    private int mIndicatorGravity = DEFAULT_GRAVITY;
    private int indicatorMarginVertical;
    private int indicatorMarginHorizontal;
    private int pageTransformInterval = DEFAULT_SLIDE_VELOCITY;

    private CarouselViewPager containerViewPager;
    private CirclePageIndicator mIndicator;
    @Nullable
    private ViewListener mViewListener = null;
    @Nullable
    private ImageListener mImageListener = null;
    @Nullable
    private ImageClickListener imageClickListener = null;

    private Timer swipeTimer;
    private SwipeTask swipeTask;

    private boolean autoPlay;
    private boolean disableAutoPlayOnUserInteraction;
    private boolean animateOnBoundary = true;

    private int previousState;

    private ViewPager.PageTransformer pageTransformer;
    private Bitmap mBitmap;
Context mContext;
    public CarouselView(@NonNull Context context) {
        super(context);
        mContext=context;
    }

    public CarouselView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView(context, attrs, 0, 0);
    }

    public CarouselView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CarouselView(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext=context;
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mContext=context;
        if (isInEditMode()) {
            return;
        } else {
//            ViewGroup parent =findViewById(R.id.contentHost);
//            View view = LayoutInflater.from(context).inflate(R.layout.view_carousel, this, true);
            View view =View.inflate(context,R.layout.view_carousel, this);
            containerViewPager = (CarouselViewPager) view.findViewById(R.id.containerViewPager);
            mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

            containerViewPager.addOnPageChangeListener(carouselOnPageChangeListener);


            //Retrieve styles attributes
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CarouselView, defStyleAttr, 0);
            try {
                indicatorMarginVertical = a.getDimensionPixelSize(R.styleable.CarouselView_indicatorMarginVertical, getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_vertical));
                indicatorMarginHorizontal = a.getDimensionPixelSize(R.styleable.CarouselView_indicatorMarginHorizontal, getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_horizontal));
                setPageTransformInterval(a.getInt(R.styleable.CarouselView_pageTransformInterval, DEFAULT_SLIDE_VELOCITY));
                setSlideInterval(a.getInt(R.styleable.CarouselView_slideInterval, DEFAULT_SLIDE_INTERVAL));
                setOrientation(a.getInt(R.styleable.CarouselView_indicatorOrientation, LinearLayout.HORIZONTAL));
                setIndicatorGravity(a.getInt(R.styleable.CarouselView_indicatorGravity, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL));
                setAutoPlay(a.getBoolean(R.styleable.CarouselView_autoPlay, true));
                setDisableAutoPlayOnUserInteraction(a.getBoolean(R.styleable.CarouselView_disableAutoPlayOnUserInteraction, false));
                setAnimateOnBoundary(a.getBoolean(R.styleable.CarouselView_animateOnBoundary, true));

                setPageTransformer(a.getInt(R.styleable.CarouselView_pageTransformer, CarouselViewPagerTransformer.DEFAULT));

                int fillColor = a.getColor(R.styleable.CarouselView_fillColor, 0);
                if (fillColor != 0) {
                    setFillColor(fillColor);
                }
                int pageColor = a.getColor(R.styleable.CarouselView_pageColor, 0);
                if (pageColor != 0) {
                    setPageColor(pageColor);
                }
                float radius = a.getDimensionPixelSize(R.styleable.CarouselView_radius, 0);
                if (radius != 0) {
                    setRadius(radius);
                }
                setSnap(a.getBoolean(R.styleable.CarouselView_snap, getResources().getBoolean(R.bool.default_circle_indicator_snap)));
                int strokeColor = a.getColor(R.styleable.CarouselView_strokeColor, 0);
                if (strokeColor != 0) {
                    setStrokeColor(strokeColor);
                }
                float strokeWidth = a.getDimensionPixelSize(R.styleable.CarouselView_strokeWidth, 0);
                if (strokeWidth != 0) {
                    setStrokeWidth(strokeWidth);
                }

            } finally {
                a.recycle();
            }
        }
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        swipeTimer.cancel();
    }

    public int getSlideInterval() {
        return slideInterval;
    }

    /**
     * Set interval for one slide in milliseconds.
     *
     * @param slideInterval integer
     */
    public void setSlideInterval(int slideInterval) {
        this.slideInterval = slideInterval;

        if (null != containerViewPager) {
            playCarousel();
        }
    }

    /**
     * Set interval for one slide in milliseconds.
     *
     * @param slideInterval integer
     */
    public void reSetSlideInterval(int slideInterval) {
        setSlideInterval(slideInterval);

        if (null != containerViewPager) {
            playCarousel();
        }
    }

    /**
     * Sets speed at which page will slide from one to another in milliseconds
     *
     * @param pageTransformInterval integer
     */
    public void setPageTransformInterval(int pageTransformInterval) {
        if (pageTransformInterval > 0) {
            this.pageTransformInterval = pageTransformInterval;
        } else {
            this.pageTransformInterval = DEFAULT_SLIDE_VELOCITY;
        }

        containerViewPager.setTransitionVelocity(pageTransformInterval);
    }

    public ViewPager.PageTransformer getPageTransformer() {
        return pageTransformer;
    }

    /**
     * Sets page transition animation.
     *
     * @param pageTransformer Choose from zoom, flow, depth, slide_over .
     */
    public void setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;
        containerViewPager.setPageTransformer(true, pageTransformer);
    }

    /**
     * Sets page transition animation.
     *
     * @param transformer Pass {@link CarouselViewPagerTransformer#FLOW}, {@link CarouselViewPagerTransformer#ZOOM}, {@link CarouselViewPagerTransformer#DEPTH} or {@link CarouselViewPagerTransformer#SLIDE_OVER}
     * @attr
     */
    public void setPageTransformer(@CarouselViewPagerTransformer.Transformer int transformer) {
        setPageTransformer(new CarouselViewPagerTransformer(transformer));

    }

    /**
     * Sets whether to animate transition from last position to first or not.
     *
     * @param animateOnBoundary .
     */
    public void setAnimateOnBoundary(boolean animateOnBoundary) {
        this.animateOnBoundary = animateOnBoundary;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    private void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public boolean isDisableAutoPlayOnUserInteraction() {
        return disableAutoPlayOnUserInteraction;
    }

    private void setDisableAutoPlayOnUserInteraction(boolean disableAutoPlayOnUserInteraction) {
        this.disableAutoPlayOnUserInteraction = disableAutoPlayOnUserInteraction;
    }

    private void setData() {
        CarouselPagerAdapter carouselPagerAdapter = new CarouselPagerAdapter(getContext());
        containerViewPager.setAdapter(carouselPagerAdapter);
        if(getPageCount() > 1) {
            mIndicator.setViewPager(containerViewPager);
            mIndicator.requestLayout();
            mIndicator.invalidate();
            containerViewPager.setOffscreenPageLimit(getPageCount());
            playCarousel();
        }
    }

    private void stopScrollTimer() {

        if (null != swipeTimer) {
            swipeTimer.cancel();
        }

        if (null != swipeTask) {
            swipeTask.cancel();
        }
    }


    private void resetScrollTimer() {

        stopScrollTimer();

        swipeTask = new SwipeTask();
        swipeTimer = new Timer();

    }

    /**
     * Starts auto scrolling if
     */
    public void playCarousel() {

        resetScrollTimer();

        if (autoPlay && slideInterval > 0 && containerViewPager.getAdapter() != null && containerViewPager.getAdapter().getCount() > 1) {

            swipeTimer.schedule(swipeTask, slideInterval, slideInterval);
        }
    }

    /**
     * Pause auto scrolling unless user interacts provided autoPlay is enabled.
     */
    public void pauseCarousel() {

        resetScrollTimer();
    }

    /**
     * Stops auto scrolling.
     */
    public void stopCarousel() {

        this.autoPlay = false;
    }


    private class CarouselPagerAdapter extends PagerAdapter {
        private Context mContext;
        private View customTopNav;
       // private LayoutInflater inflater;
        public CarouselPagerAdapter(Context context) {
            mContext = context;
          //  inflater = LayoutInflater.from(mContext);
////

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup collection, int position) {

            Object objectToReturn;
         //   customTopNav = inflater.inflate(R.layout.layout_image_caraouselview, collection);
            //Either let user set image to ImageView
            if (mImageListener != null) {
                float cornerRadius = 50.0f;
                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));  //setting image position

       // ImageView imageView=mContext.findViewById(R.id.imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //imageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.blue_rounded_corners));

//                if(mBitmap != null) {
//                    mBitmap.recycle();
//                    mBitmap = null;
//                }
//                Runtime.getRuntime().gc();

                // Initialize da new RoundedBitmapDrawable object to make ImageView rounded corners
//                mBitmap = BitmapFactory.decodeResource(mContext.getResources(), position);
//                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(),mBitmap);
//
//                // Set the RoundedBitmapDrawable corners radius
//                roundedBitmapDrawable.setCornerRadius(50f);
//                roundedBitmapDrawable.setAntiAlias(true);
//
//                // Set the ImageView image as drawable object
//                imageView.setImageBitmap(mBitmap);
                objectToReturn = imageView;
                mImageListener.setImageForPosition(position, imageView);

                collection.addView(imageView);

                //Or let user add his own ViewGroup
            } else if (mViewListener != null) {

                View view = mViewListener.setViewForPosition(position);
//                Log.d(TAG, "instantiateItem: ");
                if (null != view) {
                    objectToReturn = view;
                    collection.addView(view);
                } else {
                    throw new RuntimeException("View can not be null for position " + position);
                }

            } else {
                throw new RuntimeException("View must set " + ImageListener.class.getSimpleName() + " or " + ViewListener.class.getSimpleName() + ".");
            }

            return objectToReturn;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup collection, int position, Object view) {
           // Log.d("CAROUSELVIEW", "destroyItem: ");
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return getPageCount();
        }
    }

    @NonNull
    ViewPager.OnPageChangeListener carouselOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //Programmatic scroll
           // Log.d("CAROUSELVIEW", "onPageScrolled: ");

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

            //User initiated scroll

            if (previousState == ViewPager.SCROLL_STATE_DRAGGING
                    && state == ViewPager.SCROLL_STATE_SETTLING) {

                if (disableAutoPlayOnUserInteraction) {
                    pauseCarousel();
                } else {
                    playCarousel();
                }

            } else if (previousState == ViewPager.SCROLL_STATE_SETTLING
                    && state == ViewPager.SCROLL_STATE_IDLE) {
            }

            previousState = state;

        }
    };

    private class SwipeTask extends TimerTask {
        public void run() {
            containerViewPager.post(new Runnable() {
                public void run() {
//                    Log.d(TAG, "run: "+getFileSize((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())));
//                    Log.d(TAG, "run free: "+getFileSize(Runtime.getRuntime().freeMemory()));
//                    Log.d(TAG, "run total: "+getFileSize(Runtime.getRuntime().totalMemory()));

                   // if(Runtime.getRuntime().maxMemory()>5000)
                    runOutOfMemory(mContext);

                    int nextPage = (containerViewPager.getCurrentItem() + 1) % getPageCount();

                    containerViewPager.setCurrentItem(nextPage, 0 != nextPage || animateOnBoundary);
                   // Log.d(TAG, "run: ");
                }
            });
        }
    }
    private static final int MEGABYTE = (1024*1024* 40);
    public static void runOutOfMemory(Context mContext) {
        int size =MEGABYTE;//Integer.MAX_VALUE;
        int factor = 10;
//        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
//        for (int i=1; i <= 100; i++) {
        try {
            while (true) {
                try {
                  //  Log.d(TAG,"Trying to allocate " + getFileSize(size) );
                    byte[] bytes = new byte[size];
                   // Log.d(TAG,"Succeed! "+getFileSize(size));
                    Glide.get(mContext).clearMemory();
                    Glide.get(mContext).trimMemory(10);
                    Runtime.getRuntime().gc();
                //    Log.d(TAG,"Succeed! "+getFileSize(Runtime.getRuntime().freeMemory()));
                    break;
                } catch (Exception e) {
//                e.printStackTrace();
                  //  Log.d(TAG, "runOutOfMemory: "+e.toString());
                } catch (OutOfMemoryError e) {
                 //   Log.d(TAG, "runOutOfMemory: OOME .. Trying again with 10x less "+getFileSize(size /= factor));
                    size /= factor;
                       byte[] bytes = new byte[size];
                    Runtime.getRuntime().gc();
                //    Log.d(TAG, "runOutOfMemory: after "+getFileSize(size /= factor));
//                MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
//                long maxMemory = heapUsage.getMax() / MEGABYTE;
//                long usedMemory = heapUsage.getUsed() / MEGABYTE;
//                System.out.println(i + " : Memory Use :" + usedMemory + "M/" + maxMemory + "M");
                }
            }
        }catch (RuntimeException e){

//            Log.d(TAG, "runOutOfMemory: "+e.toString());
//            Log.d(TAG, "runOutOfMemory: ampun memory habis");
        }
//        }
    }
    public void setImageListener(ImageListener mImageListener) {
        this.mImageListener = mImageListener;
    }

    public void setViewListener(ViewListener mViewListener) {
        this.mViewListener = mViewListener;
    }

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
        containerViewPager.setImageClickListener(imageClickListener);
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setPageCount(int mPageCount) {
        this.mPageCount = mPageCount;

        setData();
    }

    public void addOnPageChangeListener(@NonNull ViewPager.OnPageChangeListener listener) {
        containerViewPager.addOnPageChangeListener(listener);
    }

    public void clearOnPageChangeListeners() {
        containerViewPager.clearOnPageChangeListeners();
    }

    public void setCurrentItem(int item) {
        containerViewPager.setCurrentItem(item);
    }

    public int getCurrentItem() {
        return containerViewPager.getCurrentItem();
    }

    public int getIndicatorMarginVertical() {
        return indicatorMarginVertical;
    }

    public void setIndicatorMarginVertical(int _indicatorMarginVertical) {
        indicatorMarginVertical = _indicatorMarginVertical;
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.topMargin = indicatorMarginVertical;
        params.bottomMargin = indicatorMarginVertical;
    }

    public int getIndicatorMarginHorizontal() {
        return indicatorMarginHorizontal;
    }
    
    public CarouselViewPager getContainerViewPager() {
        return containerViewPager;
    }

    public void setIndicatorMarginHorizontal(int _indicatorMarginHorizontal) {
        indicatorMarginHorizontal = _indicatorMarginHorizontal;
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.leftMargin = indicatorMarginHorizontal;
        params.rightMargin = indicatorMarginHorizontal;
    }

    public int getIndicatorGravity() {
        return mIndicatorGravity;
    }

    public void setIndicatorGravity(int gravity) {
        mIndicatorGravity = gravity;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = mIndicatorGravity;
        params.setMargins(indicatorMarginHorizontal, indicatorMarginVertical, indicatorMarginHorizontal, indicatorMarginVertical);
        mIndicator.setLayoutParams(params);
    }

    public int getOrientation() {
        return mIndicator.getOrientation();
    }

    public int getFillColor() {
        return mIndicator.getFillColor();
    }

    public int getStrokeColor() {
        return mIndicator.getStrokeColor();
    }

    public void setSnap(boolean snap) {
        mIndicator.setSnap(snap);
    }

    public void setRadius(float radius) {
        mIndicator.setRadius(radius);
    }

    public float getStrokeWidth() {
        return mIndicator.getStrokeWidth();
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);

    }

    public Drawable getIndicatorBackground() {
        return mIndicator.getBackground();
    }

    public void setFillColor(int fillColor) {
        mIndicator.setFillColor(fillColor);
    }

    public int getPageColor() {
        return mIndicator.getPageColor();
    }

    public void setOrientation(int orientation) {
        mIndicator.setOrientation(orientation);
    }

    public boolean isSnap() {
        return mIndicator.isSnap();
    }

    public void setStrokeColor(int strokeColor) {
        mIndicator.setStrokeColor(strokeColor);
    }

    public float getRadius() {
        return mIndicator.getRadius();
    }

    public void setPageColor(int pageColor) {
        mIndicator.setPageColor(pageColor);
    }

    public void setStrokeWidth(float strokeWidth) {
        mIndicator.setStrokeWidth(strokeWidth);
        int padding = (int) strokeWidth;
        mIndicator.setPadding(padding, padding, padding, padding);
    }

    @NonNull
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
