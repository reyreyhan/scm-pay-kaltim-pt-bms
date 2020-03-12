package com.bm.main.fpl.listener;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.bm.main.fpl.activities.PerbankanBNIActivity;
import com.bm.main.fpl.constants.MenuActionCode;

//import static com.bm.main.fpl.activities.BaseActivity.preferenceClass;

//import com.bm.main.single.ftl.travelbus.activities.StartTravelBusActivity;

public class ShowModulePerbankanOnClickListener implements View.OnClickListener {
    private AppCompatActivity context;
    private int actionCode;
    @NonNull
    Handler handler = new Handler(Looper.getMainLooper());

    public ShowModulePerbankanOnClickListener(AppCompatActivity context, int actionCode) {
        this.context = context;
        this.actionCode = actionCode;

    }

    @Override
    public void onClick(View view) {
        if (actionCode == MenuActionCode.BNI) {
//            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
//            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(new Intent(context, PerbankanBNIActivity.class));
                }
            });
        }
//        else if (actionCode == MenuActionCode.MANDIRI) {
//
//
//        }

    }
}
