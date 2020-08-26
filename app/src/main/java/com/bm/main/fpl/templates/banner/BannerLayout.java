package com.bm.main.fpl.templates.banner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.OrientationHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bm.main.scm.R;
import com.bm.main.fpl.templates.banner.layoutmanager.BannerLayoutManager;
import com.bm.main.fpl.templates.banner.layoutmanager.CenterSnapHelper;
import com.bumptech.glide.Glide;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class BannerLayout extends FrameLayout {
@NonNull
static String TAG=BannerLayout.class.getSimpleName();
    private int autoPlayDuration;//刷新间隔时间

    private boolean showIndicator;//是否显示指示器
    private RecyclerView indicatorContainer;
    private Drawable mSelectedDrawable;
    private Drawable mUnselectedDrawable;
    private IndicatorAdapter indicatorAdapter;
    private int indicatorMargin;//指示器间距
    private RecyclerView mRecyclerView;

    private BannerLayoutManager mLayoutManager;

    private int WHAT_AUTO_PLAY = 1000;

    private boolean hasInit;
    private int bannerSize = 1;
    private int currentIndex;
    private boolean isPlaying = false;

    private boolean isAutoPlaying = true;
    int itemSpace;
    float centerScale;
    float moveSpeed;
    @NonNull
    protected Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == WHAT_AUTO_PLAY) {
                if (currentIndex == mLayoutManager.getCurrentPosition()) {
                    ++currentIndex;
                    mRecyclerView.smoothScrollToPosition(currentIndex);
                    mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration);
                    refreshIndicator();
                }
            }
            return false;
        }
    });

    public BannerLayout(@NonNull Context context) {
        this(context, null);

    }

    public BannerLayout(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    protected void initView(@NonNull Context context, AttributeSet attrs) {
        runOutOfMemory(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout);
        showIndicator = a.getBoolean(R.styleable.BannerLayout_showIndicatorBanner, true);
        autoPlayDuration = a.getInt(R.styleable.BannerLayout_intervalBanner, 4000);
        isAutoPlaying = a.getBoolean(R.styleable.BannerLayout_autoPlayingBanner, true);
        itemSpace = a.getInt(R.styleable.BannerLayout_itemSpaceBanner, 20);
        centerScale = a.getFloat(R.styleable.BannerLayout_centerScaleBanner, 1.2f);
        moveSpeed = a.getFloat(R.styleable.BannerLayout_moveSpeedBanner, 1.0f);
        if (mSelectedDrawable == null) {
            //绘制默认选中状态图形
            GradientDrawable selectedGradientDrawable = new GradientDrawable();
            selectedGradientDrawable.setShape(GradientDrawable.OVAL);
            selectedGradientDrawable.setColor(Color.WHITE);
            selectedGradientDrawable.setSize(dp2px(5), dp2px(5));
            selectedGradientDrawable.setCornerRadius((float)(dp2px(5) / 2));
            mSelectedDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
        }
        if (mUnselectedDrawable == null) {
            //绘制默认未选中状态图形
            GradientDrawable unSelectedGradientDrawable = new GradientDrawable();
            unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
            unSelectedGradientDrawable.setColor(Color.GRAY);
            unSelectedGradientDrawable.setSize(dp2px(5), dp2px(5));
            unSelectedGradientDrawable.setCornerRadius((float)(dp2px(5) / 2));
            mUnselectedDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
        }

        indicatorMargin = dp2px(4);
        int marginLeft = dp2px(16);
        int marginRight = dp2px(0);
        int marginBottom = dp2px(3);
        int gravity = GravityCompat.START;
        int o = a.getInt(R.styleable.BannerLayout_orientationBanner, 0);
        int orientation = 0;
        if (o == 0) {
            orientation = OrientationHelper.HORIZONTAL;
        } else if (o == 1) {
            orientation = OrientationHelper.VERTICAL;
        }
        a.recycle();
        //轮播图部分
        mRecyclerView = new RecyclerView(context);

        LayoutParams vpLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        addView(mRecyclerView, vpLayoutParams);
        mLayoutManager = new BannerLayoutManager(getContext(), orientation);
        mLayoutManager.setItemSpace(itemSpace);
        mLayoutManager.setCenterScale(centerScale);
        mLayoutManager.setMoveSpeed(moveSpeed);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new CenterSnapHelper().attachToRecyclerView(mRecyclerView);


        //指示器部分
        indicatorContainer = new RecyclerView(context);
        LinearLayoutManager indicatorLayoutManager = new LinearLayoutManager(context, orientation, false);
        indicatorContainer.setLayoutManager(indicatorLayoutManager);
        indicatorAdapter = new IndicatorAdapter();
        indicatorContainer.setAdapter(indicatorAdapter);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | gravity;
        params.setMargins(marginLeft, 0, marginRight, marginBottom);
        addView(indicatorContainer, params);
        if (!showIndicator) {
            indicatorContainer.setVisibility(GONE);
        }
    }

    // 设置是否禁止滚动播放
    public void setAutoPlaying(boolean isAutoPlaying) {
        this.isAutoPlaying = isAutoPlaying;
        setPlaying(this.isAutoPlaying);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    //设置是否显示指示器
    public void setShowIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
        indicatorContainer.setVisibility(showIndicator ? VISIBLE : GONE);
    }

    //设置当前图片缩放系数
    public void setCenterScale(float centerScale) {
        this.centerScale = centerScale;
        mLayoutManager.setCenterScale(centerScale);
    }

    //设置跟随手指的移动速度
    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
        mLayoutManager.setMoveSpeed(moveSpeed);
    }

    //设置图片间距
    public void setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
        mLayoutManager.setItemSpace(itemSpace);
    }

    /**
     * 设置轮播间隔时间
     *
     * @param autoPlayDuration 时间毫秒
     */
    public void setAutoPlayDuration(int autoPlayDuration) {
        this.autoPlayDuration = autoPlayDuration;
    }

    public void setOrientation(int orientation) {
        mLayoutManager.setOrientation(orientation);
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    public synchronized void setPlaying(boolean playing) {
        if (isAutoPlaying && hasInit) {
            if (!isPlaying && playing) {
                mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration);
                isPlaying = true;
//                Log.d(TAG, "setPlaying: "+isPlaying);
            } else if (isPlaying && !playing) {
                mHandler.removeMessages(WHAT_AUTO_PLAY);
                isPlaying = false;
//                Log.d(TAG, "setPlaying: "+isPlaying);
            }
        }
    }


    /**
     * 设置轮播数据集
     */
    public void setAdapter(@NonNull final RecyclerView.Adapter adapter, @NonNull final OnBannerScrollListener listener) {
        this.listener=listener;
        hasInit = false;
        mRecyclerView.setAdapter(adapter);
        bannerSize = adapter.getItemCount();
        mLayoutManager.setInfinite(bannerSize >= 3);
        setPlaying(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dx != 0) {
                    setPlaying(false);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int first = mLayoutManager.getCurrentPosition();

                if (currentIndex != first) {
                    currentIndex = first;

                }

                if (newState == SCROLL_STATE_IDLE) {
                    setPlaying(true);
                    listener.onBannerScrollListener(currentIndex);
                }
                refreshIndicator();

               // Log.d("xxx", "onScrollStateChanged "+currentIndex);
            }
        });
        hasInit = true;
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPlaying(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setPlaying(true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            setPlaying(true);
        } else {
            setPlaying(false);
        }
    }

    /**
     * 标示点适配器
     */
    protected class IndicatorAdapter extends RecyclerView.Adapter {

        int currentPosition = 0;

        public void setPosition(int currentPosition) {
            this.currentPosition = currentPosition;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            ImageView bannerPoint = new ImageView(getContext());
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
            bannerPoint.setLayoutParams(lp);
            return new RecyclerView.ViewHolder(bannerPoint) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ImageView bannerPoint = (ImageView) holder.itemView;
            bannerPoint.setImageDrawable(currentPosition == position ? mSelectedDrawable : mUnselectedDrawable);

        }

        @Override
        public int getItemCount() {
            return bannerSize;
        }
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 改变导航的指示点
     */
    protected synchronized void refreshIndicator() {
        if (showIndicator && bannerSize > 1) {
            indicatorAdapter.setPosition(currentIndex % bannerSize);
            indicatorAdapter.notifyDataSetChanged();
        }
    }

    private OnBannerScrollListener listener;
    public void setOnBannerScrollListener(OnBannerScrollListener onBannerScrollListener) {
        this.listener = onBannerScrollListener;
    }

    public interface OnBannerItemClickListener {
        void onItemClick(int position);
    }

    public interface OnBannerScrollListener {
        void onBannerScrollListener(int currentIndex);
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
}