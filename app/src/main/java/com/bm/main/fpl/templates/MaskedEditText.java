package com.bm.main.fpl.templates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.widget.AppCompatDrawableManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bm.main.pos.R;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.materialedittext.OnCutCopyPasteListener;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sarifhidayat on 2/18/19.
 **/
public class MaskedEditText extends MaterialEditText implements View.OnTouchListener, View.OnFocusChangeListener {

    private Context context;
    @Nullable
    private String mask;
    @Nullable
    private String notMaskedSymbol;

    @Nullable
    private String deleteChar;
    @Nullable
    private String replacementChar;
    @Nullable private String format;
    private boolean required;
    @Nullable private MaskIconCallback maskIconCallback;
    @Nullable private IconCallback iconCallback;
    private Drawable maskIcon;

    @NonNull
    private ArrayList<Integer> listValidCursorPositions = new ArrayList<>();
    private Integer firstAllowedPosition = 0;
    private Integer lastAllowedPosition = 0;
    private OnFocusChangeListener onFocusChangeListener;
    private String filteredMask;
    private MaskedInputFilter maskedInputFilter;

    public MaskedEditText(@NonNull Context context) {
        super(context);
        init(context, "", "", null, null, null, null, null);
    }

    private MaskedEditText(@NonNull Context context, String mask, String notMaskedSymbol, String format, @DrawableRes int maskIcon, IconCallback iconCallback) {
        super(context);
        Drawable drawable = null;
        if (maskIcon != -1)
            drawable = this.getResources().getDrawable(maskIcon);
        init(context, mask, notMaskedSymbol, null, format, drawable, null, iconCallback);
    }

    public MaskedEditText(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaskedEditText(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, AttributeSet attrs) {
        init(context, "", "", attrs, null, null, null, null);
    }

    private void init(@NonNull Context context, String mask, String notMaskedSymbol, AttributeSet attrs, String format, Drawable maskIcon, MaskIconCallback maskIconCallback, IconCallback iconCallback) {
        this.context = context;
        this.mask = mask;
        this.notMaskedSymbol = notMaskedSymbol;
        this.maskIcon = maskIcon;
        this.maskIconCallback = maskIconCallback;
        this.iconCallback = iconCallback;
        this.format = format;

        initByAttributes(context, attrs);
        initMaskIcon();

        this.setLongClickable(false);
        this.setSingleLine(true);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    @SuppressLint("RestrictedApi")
    private void initByAttributes(@NonNull Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaskedEditText, 0, 0);

        if (TextUtils.isEmpty(mask) && TextUtils.isEmpty(notMaskedSymbol)) {
            notMaskedSymbol = a.getString(R.styleable.MaskedEditText_notMaskedSymbol);
            mask = a.getString(R.styleable.MaskedEditText_mask);

            int maskedIconRes = a.getResourceId(R.styleable.MaskedEditText_maskIcon, 0);

            if (maskedIconRes > 0) {
                AppCompatDrawableManager dm = AppCompatDrawableManager.get();
                Drawable drawableIcon = dm.getDrawable(context, maskedIconRes);
                if (drawableIcon != null) {
                    final Drawable wrappedDrawable = DrawableCompat.wrap(drawableIcon);
                    int drawableIconColor = a.getColor(R.styleable.MaskedEditText_maskIconColor, getCurrentHintTextColor());
                    DrawableCompat.setTint(wrappedDrawable, drawableIconColor);
                    maskIcon = wrappedDrawable;
                }
            }
        }

        if (!TextUtils.isEmpty(mask) && !TextUtils.isEmpty(notMaskedSymbol)) {
            deleteChar = a.getString(R.styleable.MaskedEditText_deleteChar);
            if (deleteChar == null) deleteChar = " ";

            replacementChar = a.getString(R.styleable.MaskedEditText_replacementChar);
            if (replacementChar == null) replacementChar = " ";

            String format = a.getString(R.styleable.MaskedEditText_format);
            if (format == null && this.format == null) this.format = "";
            else if (!TextUtils.isEmpty(format) && this.format == null) this.format = format;

            initListValidCursorPositions(mask, notMaskedSymbol);

            filteredMask = this.mask.replace(this.notMaskedSymbol, replacementChar);
            this.setText(filteredMask, BufferType.NORMAL);

            maskedInputFilter = new MaskedInputFilter();
            this.setFilters(new InputFilter[]{maskedInputFilter});
        }

        int inputType = a.getInteger(R.styleable.MaskedEditText_android_inputType, -1);
        this.setInputType(inputType);
    }

    private void initMaskIcon() {
        if (maskIcon != null) {
            maskIcon.setBounds(0, 0, maskIcon.getIntrinsicHeight(), maskIcon.getIntrinsicHeight());
            final Drawable[] compoundDrawables = getCompoundDrawables();
            setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], maskIcon, compoundDrawables[3]);
        }
        super.setOnFocusChangeListener(this);
        super.setOnTouchListener(this);
    }

    private void initListValidCursorPositions(@NonNull String mask, @NonNull String charSequence) {
        char[] chars = mask.toCharArray();
        char maskedSymbol = charSequence.charAt(0);
        for (int i = 0; i < mask.length(); i++) {
            if (chars[i] == maskedSymbol) {
                listValidCursorPositions.add(i);
            }
        }
        firstAllowedPosition = listValidCursorPositions.get(0);
        lastAllowedPosition = listValidCursorPositions.get(listValidCursorPositions.size() - 1);
    }

    @Override
    public void setInputType(int type) {
        if (type == -1) {
            type = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }

        if (type == InputType.TYPE_CLASS_NUMBER ||
                type == InputType.TYPE_NUMBER_FLAG_SIGNED ||
                type == InputType.TYPE_NUMBER_FLAG_DECIMAL ||
                type == InputType.TYPE_CLASS_PHONE) {
            final String symbolExceptions = getSymbolExceptions();
            this.setKeyListener(DigitsKeyListener.getInstance("0123456789." + symbolExceptions));
        } else {
            super.setInputType(type);
        }
    }

    /**
     * Generate symbol exception for inputType = number
     */
    @NonNull
    private String getSymbolExceptions() {
        if (TextUtils.isEmpty(filteredMask)) return "";

        StringBuilder maskSymbolException = new StringBuilder();
        for (char c : filteredMask.toCharArray()) {
            if (!Character.isDigit(c) && maskSymbolException.indexOf(String.valueOf(c)) == -1) {
                maskSymbolException.append(c);
            }
        }
        maskSymbolException.append(replacementChar);
        return maskSymbolException.toString();
    }

    @NonNull
    public String getUnmaskedText() {
        Editable text = super.getEditableText();
        if (mask != null && !mask.isEmpty()) {
            Editable unMaskedText = new SpannableStringBuilder();
            for (Integer index : listValidCursorPositions) {
                if (text != null) {
                    unMaskedText.append(text.charAt(index));
                }
            }
            if (format != null && !format.isEmpty())
                return formatText(unMaskedText.toString(), format);
            else
                return unMaskedText.toString().trim();
        }

        return text.toString().trim();
    }

    public void setMaskedText(@Nullable String input) {
        if (input != null) {
            StringBuilder filteredInputBuilder = new StringBuilder(input);
            if (filteredInputBuilder.length() < listValidCursorPositions.size()) {
                while (filteredInputBuilder.length() < listValidCursorPositions.size()) {
                    filteredInputBuilder.append(deleteChar);
                }
            } else if (filteredInputBuilder.length() > listValidCursorPositions.size()) {
                filteredInputBuilder.replace(listValidCursorPositions.size(), filteredInputBuilder.length(), "");
            }

            StringBuilder buffer = new StringBuilder(filteredInputBuilder);
            Editable text = this.getEditableText();
            if (text != null) {
                for (int i = 0; i < mask.length(); i++) {
                    if (!listValidCursorPositions.contains(i)) {
                        buffer.insert(i, String.valueOf(mask.charAt(i)));
                    }

                }
                maskedInputFilter.setTextSetup(true);
                this.setText(buffer.toString());
                maskedInputFilter.setTextSetup(false);
            }
        }
    }

    @NonNull
    private String formatText(@NonNull String input, String pattern) {
        Pattern p = Pattern.compile("(\\[[\\d]+])");
        Matcher m = p.matcher(pattern);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, getSymbol(input, m.group()));
        }
        return sb.toString();
    }

    @NonNull
    private String getSymbol(@NonNull String input, @NonNull String group) {
        int i = Integer.valueOf(group.replace("[", "").replace("]", ""));
        return String.valueOf(input.toCharArray()[i - 1]);
    }

    /**
     * Use client
     */
    @Deprecated
    public void setFormat(@Nullable String format) {
        this.format = format;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Use client
     */
    @Deprecated
    public void setMask(String mask) {
        this.mask = mask;
    }

    @Override
    public void setOnFocusChangeListener(final OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(final OnTouchListener onTouchListener) {
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(view, hasFocus);
        }
        if (hasFocus) {
            this.setSelection(firstAllowedPosition);
            this.requestFocus();
        }
    }

    @Override
    public boolean onTouch(View view, @NonNull MotionEvent event) {
        final int x = (int) event.getX();
        if (maskIcon != null && maskIcon.isVisible() && x > getWidth() - getPaddingRight() - maskIcon.getIntrinsicWidth()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (maskIconCallback != null)
                    maskIconCallback.onIconPushed();
                if (iconCallback != null)
                    iconCallback.onIconPushed(getUnmaskedText());
            }
            return true;
        }
        if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN) && TextUtils.isEmpty(this.getUnmaskedText())) {
            this.setSelection(firstAllowedPosition);
            this.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) return false;
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }
        return false;
    }

    /**
     * Use setIconCallback method
     */
    @Deprecated
    public void setMaskIconCallback(@Nullable MaskIconCallback maskIconCallback) {
        this.maskIconCallback = maskIconCallback;
    }

    /**
     * Use IconCallback interface
     */
    @Deprecated
    public interface MaskIconCallback {
        void onIconPushed();
    }

    public void setIconCallback(@Nullable IconCallback iconCallback) {
        this.iconCallback = iconCallback;
    }

    public interface IconCallback {
        void onIconPushed(String unmaskedText);
    }

    private class MaskedInputFilter implements InputFilter {
        private boolean isUserInput = true;
        private boolean textSetup = false;

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (textSetup) return source;

            if (!(source instanceof SpannableStringBuilder)) {
                StringBuilder filteredStringBuilder = new StringBuilder();
                // defaultMaskedSymbols == array that tells us which symbols should be replaced by default
                // and which symbols are part of mask
                boolean defaultMaskedSymbols[] = new boolean[dend - dstart + 1];
                for (int i = 0; i <= dend - dstart; i++) {
                    defaultMaskedSymbols[i] = isCharAllowed(dstart + i);
                }
                for (int i = start; i < end; i++) {
                    char currentChar = source.charAt(i);

                    if (defaultMaskedSymbols[0]) {
                        isUserInput = false;
                        MaskedEditText.this.getEditableText().replace(dstart, dstart + 1, "");
                      //  getEditableText().replace(dstart, dstart + 1, "");
                        isUserInput = true;
                        filteredStringBuilder.append(currentChar);
                        int index;
                        if (!isCharAllowed(dstart + 1))
                            index = dstart + 1;
                        else
                            index = dstart;

                        skipSymbol(index);

                    } else {
                        if (dstart != mask.length()) {
                            int index;
                            if (!isCharAllowed(dstart))
                                index = dstart + 1;
                            else
                                index = dstart;
                            int position = skipSymbol(index);
                            MaskedEditText.this.getEditableText().replace(position, position, Character.toString(currentChar));
                        }
                    }
                }
                if (isUserInput && TextUtils.isEmpty(source)) {//deletion detection
                    if (dend != 0) {
                        for (int i = 0; i < dend - dstart; i++) {
                            if (defaultMaskedSymbols[i]) {
                                filteredStringBuilder.append(deleteChar);
                            } else {
                                filteredStringBuilder.append(mask.charAt(dstart + i));
                            }
                        }
                        skipSymbolAfterDeletion(dstart);
                    }

                }

                return filteredStringBuilder.toString();
            }
            return source;
        }

        private int skipSymbol(int index) {
            int position = getNextAvailablePosition(index, false);
            if (position > lastAllowedPosition)
                position = lastAllowedPosition;
            setSelection(position);
            return position;
        }


        private void skipSymbolAfterDeletion(int index) {
            final int position = getNextAvailablePosition(index, true);
            setSelection(position);
        }

        private int getNextAvailablePosition(int index, boolean isDeletion) {
            if (listValidCursorPositions.contains(index)) {
                final int i = listValidCursorPositions.indexOf(index);
                final ListIterator<Integer> iterator = listValidCursorPositions.listIterator(i);
                if (isDeletion) {

                    if (iterator.hasPrevious()) return iterator.previous() + 1;

                } else {

                    if (iterator.hasNext()) return iterator.next();

                }
                return index;
            } else {
                return findCloserIndex(index, isDeletion);
            }
        }

        private int findCloserIndex(int index, boolean isDeletion) {
            ListIterator<Integer> iterator;
            if (isDeletion) {
                iterator = listValidCursorPositions.listIterator(listValidCursorPositions.size() - 1);
                while (iterator.hasPrevious()) {
                    final Integer previous = iterator.previous();
                    if (previous <= index)
                        return previous + 1;
                }
                return firstAllowedPosition;

            } else {
                if (index > firstAllowedPosition) {
                    iterator = listValidCursorPositions.listIterator();
                    while (iterator.hasNext()) {
                        final Integer next = iterator.next();
                        if (next >= index)
                            return next - 1;
                    }
                    return lastAllowedPosition;
                } else {
                    return firstAllowedPosition;
                }
            }
        }

        private boolean isCharAllowed(int index) {
            return index < mask.length() && mask.charAt(index) == notMaskedSymbol.toCharArray()[0];
        }

        private void setTextSetup(boolean textSetup) {
            this.textSetup = textSetup;
        }
    }

    public static class Builder {
        private Context context;
        @Nullable
        private String mask = null;
        @Nullable
        private String notMaskedSymbol = null;
        private int icon = -1;
        @Nullable
        private IconCallback iconCallback = null;
        @Nullable
        private String format = null;

        public Builder(Context context) {
            this.context = context;
        }

        @NonNull
        public Builder mask(String mask) {
            this.mask = mask;
            return this;
        }

        @NonNull
        public Builder notMaskedSymbol(String notMaskedSymbol) {
            this.notMaskedSymbol = notMaskedSymbol;
            return this;
        }

        @NonNull
        public Builder icon(@DrawableRes int maskIcon) {
            this.icon = maskIcon;
            return this;
        }

        @NonNull
        public Builder iconCallback(IconCallback maskIconCallback) {
            this.iconCallback = maskIconCallback;
            return this;
        }

        @NonNull
        public Builder format(String format) {
            this.format = format;
            return this;
        }

        @Nullable
        public MaskedEditText build() {
            return new MaskedEditText(context, mask, notMaskedSymbol, format, icon, iconCallback);
        }
    }

    private OnCutCopyPasteListener mOnCutCopyPasteListener;
    /**
     * Set a OnCutCopyPasteListener.
     * @param listener
     */
    public void setOnCutCopyPasteListener(OnCutCopyPasteListener listener) {
        mOnCutCopyPasteListener = listener;
    }


    @Override
    public boolean onTextContextMenuItem(int id) {
        // Do your thing:
        boolean consumed = super.onTextContextMenuItem(id);
        // React:
        switch (id){
            case android.R.id.cut:
                onCut();
                break;
            case android.R.id.copy:
                onCopy();
                break;
            case android.R.id.paste:
                onPaste();
        }
        return consumed;
    }

    /**
     * Text was cut from this EditText.
     */
    public void onCut(){
        if(mOnCutCopyPasteListener!=null)
            mOnCutCopyPasteListener.onCut();
    }

    /**
     * Text was copied from this EditText.
     */
    public void onCopy(){
        if(mOnCutCopyPasteListener!=null)
            mOnCutCopyPasteListener.onCopy();
    }

    /**
     * Text was pasted into the EditText.
     */
    public void onPaste(){
        if(mOnCutCopyPasteListener!=null)
            mOnCutCopyPasteListener.onPaste();
    }

}
