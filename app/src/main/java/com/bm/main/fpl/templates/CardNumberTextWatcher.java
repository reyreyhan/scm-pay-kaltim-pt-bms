package com.bm.main.fpl.templates;

import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by sarifhidayat on 2/18/19.
 **/
public class CardNumberTextWatcher implements TextWatcher {

    private static final String TAG = CardNumberTextWatcher.class
            .getSimpleName();
    private EditText edTxt;
    private boolean isDelete;
    private static final char space = ' ';
    private int count=0;

    public CardNumberTextWatcher(EditText edTxtPhone) {
        this.edTxt = edTxtPhone;
        edTxt.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    isDelete = true;
                }
                return false;
            }
        });
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    public void afterTextChanged(@NonNull Editable s) {

        if (isDelete) {
            isDelete = false;
            return;
        }
      String val = s.toString();
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        if (val != null && val.length() > 0) {
            val = val.replace("-", "");
            if (val.length() >= 4) {
                a = val.substring(0, 4);
            } else if (val.length() < 4) {
                a = val.substring(0, val.length());
            }
            if (val.length() >= 8) {
                b = val.substring(4, 8);
                c = val.substring(8, val.length());
            } else if (val.length() > 4 && val.length() < 8) {
                b = val.substring(4, val.length());
            }

            if (val.length() >= 12) {
                c = val.substring(8, 12);
                d = val.substring(12, val.length());
            } else if (val.length() > 8 && val.length() < 12) {
                c = val.substring(8, val.length());
            }

            if (val.length() >= 16) {
                d = val.substring(12, 16);
                e = val.substring(16, val.length());
            } else if (val.length() > 12 && val.length() < 16) {
                d = val.substring(12, val.length());
            }

            StringBuffer stringBuffer = new StringBuffer();
            if (a != null && a.length() > 0) {
                stringBuffer.append(a);
                if (a.length() == 4) {
                    stringBuffer.append("-");
                }
            }
            if (b != null && b.length() > 0) {
                stringBuffer.append(b);
                if (b.length() == 4) {
                    stringBuffer.append("-");
                }
            }
            if (c != null && c.length() > 0) {
                stringBuffer.append(c);
                if (c.length() == 4) {
                    stringBuffer.append("-");
                }
            }
            if (d != null && d.length() > 0) {
                stringBuffer.append(d);
                if (d.length() == 4) {
                    stringBuffer.append("-");
                }
            }
            if (e != null && e.length() > 0) {
                stringBuffer.append(e);
            }
            edTxt.removeTextChangedListener(this);
            edTxt.setText(stringBuffer.toString());
            edTxt.setSelection(edTxt.getText().toString().length());
            edTxt.addTextChangedListener(this);
        } else {
            edTxt.removeTextChangedListener(this);
            edTxt.setText("");
            edTxt.addTextChangedListener(this);
        }

//        if (s.length() > 0) {
////            if (s.length() % 5 == 0) {
////                final char c = s.charAt(s.length() - 1);
////                if (space == c) {
////                    s.delete(s.length() - 1, s.length());
////                }
////            }
////            // Insert char where needed.
////            if (s.length() % 5 == 0) {
////                char c = s.charAt(s.length() - 1);
////                // Only if its a digit where there should be a space we insert a space
////                if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
////                    s.insert(s.length() - 1, String.valueOf(space));
////                }
////            }
////            edTxt.removeTextChangedListener(this);
////            edTxt.setText(s.toString());
////            edTxt.setSelection(edTxt.getText().toString().length());
////            edTxt.addTextChangedListener(this);
////
////        } else {
////            edTxt.removeTextChangedListener(this);
////            edTxt.setText("");
////            edTxt.addTextChangedListener(this);
////        }

//        if (count <= edTxt.getText().toString().length()
//                &&(edTxt.getText().toString().length()==4
//                ||edTxt.getText().toString().length()==9
//                ||edTxt.getText().toString().length()==14||edTxt.getText().toString().length()==19)){
//            edTxt.setText(edTxt.getText().toString()+"-");
//            int pos = edTxt.getText().length();
//            edTxt.setSelection(pos);
//        }else if (count >= edTxt.getText().toString().length()
//                &&(edTxt.getText().toString().length()==4
//                ||edTxt.getText().toString().length()==9
//                ||edTxt.getText().toString().length()==14||edTxt.getText().toString().length()==19)){
//            edTxt.setText(edTxt.getText().toString().substring(0,edTxt.getText().toString().length()-1));
//            int pos = edTxt.getText().length();
//            edTxt.setSelection(pos);
//        }
//        count = edTxt.getText().toString().length();


    }
}
