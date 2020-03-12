package com.bm.main.fpl.templates;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.annotation.NonNull;

public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
    @NonNull
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        PasswordCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }

        public char charAt(int index) {
            return '\u25CF'; // This is the important part
        }

        public int length() {
            return mSource.length(); // Return default
        }

        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }
    }
}
