package com.bm.main.fpl.templates.slider.library.Tricks;

import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bm.main.fpl.templates.slider.library.SliderAdapter;

/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging wrap-around.
 * Thanks to: https://github.com/antonyt/InfiniteViewPager
 */
public class InfinitePagerAdapter extends PagerAdapter {

    private static final String TAG = "InfinitePagerAdapter";
    private static final boolean DEBUG = false;

    private SliderAdapter adapter;

    public InfinitePagerAdapter(SliderAdapter adapter) {
        this.adapter = adapter;
    }

    public SliderAdapter getRealAdapter(){
        return this.adapter;
    }

    @Override
    public int getCount() {
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        return Integer.MAX_VALUE;
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getRealCount() {
        return adapter.getCount();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if(getRealCount() == 0){
            return null;
        }
        int virtualPosition = position % getRealCount();
        debug("instantiateItem: real position: " + position);
        debug("instantiateItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        return adapter.instantiateItem(container, virtualPosition);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if(getRealCount() == 0){
            return;
        }
        int virtualPosition = position % getRealCount();
        debug("destroyItem: real position: " + position);
        debug("destroyItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        adapter.destroyItem(container, virtualPosition, object);
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        adapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        adapter.startUpdate(container);
    }

    /*
     * End delegation
     */

    private void debug(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }
}