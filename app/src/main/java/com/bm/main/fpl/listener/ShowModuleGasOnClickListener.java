package com.bm.main.fpl.listener;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bm.main.fpl.activities.PertagasActivity;
import com.bm.main.fpl.activities.PgnActivity;
import com.bm.main.fpl.constants.MenuActionCode;

public class ShowModuleGasOnClickListener implements View.OnClickListener {
    private AppCompatActivity context;
    private int actionCode;
    @NonNull
    Handler handler = new Handler(Looper.getMainLooper());

    public ShowModuleGasOnClickListener(AppCompatActivity context, int actionCode) {
        this.context = context;
        this.actionCode = actionCode;

    }

    @Override
    public void onClick(View view) {
        if (actionCode == MenuActionCode.PGN) {
            handler.post(() -> context.startActivity(new Intent(context, PgnActivity.class)));
        } else if (actionCode == MenuActionCode.PERTAGAS) {
            handler.post(() -> context.startActivity(new Intent(context, PertagasActivity.class)));
        }
    }
}
