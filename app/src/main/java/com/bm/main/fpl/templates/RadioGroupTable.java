package com.bm.main.fpl.templates;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by papahnakal on 08/11/17.
 */

public class RadioGroupTable extends TableLayout implements View.OnClickListener {

    private static final String TAG = RadioGroupTable.class.getSimpleName();
    private RadioButton activeRadioButton;

    public RadioGroupTable(Context context) {
        super(context);
    }

    public RadioGroupTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onClick(View v) {
        final RadioButton rb = (RadioButton) v;
        if ( activeRadioButton != null ) {
            activeRadioButton.setChecked(false);
        }
        rb.setChecked(true);
        activeRadioButton = rb;
    }

//    public void clearCheck(View v){
//
//        final RadioButton rb = (RadioButton) v;
//        if (rb.isChecked()) {
//            RadioGroup radioGroup =  v.findViewById(getCheckedRadioButtonId());
//            radioGroup.clearCheck();
//        }
//    }
    /* (non-Javadoc)
     * @see android.widget.TableLayout#addView(android.view.View, int, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        setChildrenOnClickListener((TableRow)child);
    }


    /* (non-Javadoc)
     * @see android.widget.TableLayout#addView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        setChildrenOnClickListener((TableRow)child);
    }


//    private void setChildrenOnClickListener(TableRow tr) {
//        final int c = tr.getChildCount();
//        for (int i=0; i < c; i++) {
//            final View v = tr.getChildAt(i);
//            if ( v instanceof RadioButton ) {
//                v.setOnClickListener(this);
//            }
//        }
//    }
private void setChildrenOnClickListener(@NonNull TableRow tr) {
    final int c = tr.getChildCount();
    for (int i=0; i < c; i++) {
        final View v = tr.getChildAt(i);
        if (v instanceof RadioButton) {
            v.setOnClickListener(this);
            if (((RadioButton) v).isChecked())
                activeRadioButton = (RadioButton) v;
        }
    }

}
    public int getCheckedRadioButtonId() {

        if ( activeRadioButton != null ) {
            return activeRadioButton.getId();
        }

        return -1;
    }
}
