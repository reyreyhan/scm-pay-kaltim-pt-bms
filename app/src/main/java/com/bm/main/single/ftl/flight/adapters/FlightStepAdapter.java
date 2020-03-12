package com.bm.main.single.ftl.flight.adapters;

import android.content.Context;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bm.main.pos.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Created by sarifhidayat on 2019-04-26.
 **/
public class FlightStepAdapter extends AbstractStepAdapter {
    private static final String TAG = FlightStepAdapter.class.getSimpleName();
    private LayoutInflater layoutInflater;
    Context mContext;
    @NonNull
    int layouts[] = new int[]{
            R.layout.flight_layout_step_book,
            R.layout.flight_layout_step_reviewbook,

    };
    @NonNull
    private final SparseArray pages;// = new SparseArray<Step>();

    public FlightStepAdapter(@NonNull Context context) {
        super(context);
        this.pages = new SparseArray();
    }

    @NonNull
    @Override
    public Step createStep(int position) {
        return (Step)this.createStep(position);
    }

    @Nullable
    @Override
    public Step findStep(int position) {
        return this.pages.size() > 0 ? (Step)this.pages.get(position) : null;
    }

//    @Override
//    public int getCount() {
//        return 3;
//
//    }

    @Override
    public int getCount() {
        return 2;//layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0L) int position) {
        StepViewModel var10000 = new StepViewModel.Builder(this.context).setTitle("").create();
        //return super.getViewModel(position);
if(position==0){
    var10000= new StepViewModel.Builder(this.context).setTitle("Book").create();
}else if(position==1){
    var10000= new StepViewModel.Builder(this.context).setTitle("Review Book").create();
}

        return var10000;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Step step = (Step) this.pages.get(position);
        if (step == null) {
            step = (Step) this.createStep(position);
            this.pages.put(position, step);
        }

        if (step == null) {
            Log.d(TAG, "instantiateItem: null cannot be cast to non-null type android.view.View");
            throw new ClassCastException("null cannot be cast to non-null type android.view.View");

        } else {
            View stepView = (View) step;
            container.addView(stepView);
            return stepView;
        }
//        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        layouts = new int[]{
////                R.layout.layout_bl_langsung,
////                R.layout.layout_bl_tidak_langsung,
////
////        };
//        View view = layoutInflater.inflate(layouts[position], container, false);
//
//        container.addView(view);
//
//        return view;

    }
}
