package com.bm.main.fpl.activities;

import android.content.Intent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bm.main.scm.R;

public class KeyboardListenerActivity extends BaseActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    public ViewGroup rootLayout;
    public boolean keyboardListenersAttached = false;

    public void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = findViewById(R.id.rootLayout);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);

        keyboardListenersAttached = true;
    }

    @Override
    public void onGlobalLayout() {
        int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
        int contentViewTop = getWindow().findViewById(android.R.id.content).getTop();
        contentViewTop = contentViewTop + 350;
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        if (heightDiff <= contentViewTop) {
            onHideKeyboard();

            Intent intent = new Intent("KeyboardWillHide");
            broadcastManager.sendBroadcast(intent);
        } else {
            int keyboardHeight = heightDiff - contentViewTop;
            onShowKeyboard(keyboardHeight);

            Intent intent = new Intent("KeyboardWillShow");
            intent.putExtra("KeyboardHeight", keyboardHeight);
            broadcastManager.sendBroadcast(intent);
        }
    }

    protected void onShowKeyboard(int keyboardHeight) {
    }

    protected void onHideKeyboard() {
    }
}
