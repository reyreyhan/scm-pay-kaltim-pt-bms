package com.bm.main.fpl.templates;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sarifhidayat on 1/9/19.
 **/
public class TextJustification {
    public static void justify(@NonNull final TextView textView) {

        final AtomicBoolean isJustify = new AtomicBoolean(false);

        final String textString = textView.getText().toString();

        final TextPaint textPaint = textView.getPaint();

        final SpannableStringBuilder builder = new SpannableStringBuilder();

        textView.post(new Runnable() {
            @Override
            public void run() {

                if (!isJustify.get()) {

                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);

                        String lineString = textString.substring(lineStart, lineEnd);

                        if (i == lineCount - 1) {
                            builder.append(new SpannableString(lineString));
                            break;
                        }

                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        SpannableString spannableString = new SpannableString(lineString);
                        for (int j = 0; j < trimSpaceText.length(); j++) {
                            char c = trimSpaceText.charAt(j);
                            if (c == ' ') {
                                Drawable drawable = new ColorDrawable(0x00ffffff);
                                drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                ImageSpan span = new ImageSpan(drawable);
                                spannableString.setSpan(span, j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        builder.append(spannableString);
                    }

                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }

//    /**
//     * " abc  "[0, 5, 6]
//     */
//    private static Set<Integer> spacePositionInEnds(String string) {
//        Set<Integer> result = new HashSet<>();
//        for (int i = 0; i < string.length(); i++) {
//            char c = string.charAt(i);
//            if (c == ' ') {
//                result.add(i);
//            } else {
//                break;
//            }
//        }
//
//        if (result.size() == string.length()) {
//            return result;
//        }
//
//        for (int i = string.length() - 1; i > 0; i--) {
//            char c = string.charAt(i);
//            if (c == ' ') {
//                result.add(i);
//            } else {
//                break;
//            }
//        }
//
//        return result;
//    }
//
//    public static void justify(TextView textView,float contentWidth) {
//        String text=textView.getText().toString();
//        Paint paint=textView.getPaint();
//
//        ArrayList<String> lineList=lineBreak(text,paint,contentWidth);
//
//        textView.setText(TextUtils.join(" ", lineList).replaceFirst("\\s", ""));
//    }
//
//
//    private static ArrayList<String> lineBreak(String text,Paint paint,float contentWidth){
//        String [] wordArray=text.split("\\s");
//        ArrayList<String> lineList = new ArrayList<String>();
//        String myText="";
//
//        for(String word:wordArray){
//            if(paint.measureText(myText+" "+word)<=contentWidth)
//                myText=myText+" "+word;
//            else{
//                int totalSpacesToInsert=(int)((contentWidth-paint.measureText(myText))/paint.measureText(" "));
//                lineList.add(justifyLine(myText,totalSpacesToInsert));
//                myText=word;
//            }
//        }
//        lineList.add(myText);
//        return lineList;
//    }
//
//    private static String justifyLine(String text,int totalSpacesToInsert){
//        String[] wordArray=text.split("\\s");
//        String toAppend=" ";
//
//        while((totalSpacesToInsert)>=(wordArray.length-1)){
//            toAppend=toAppend+" ";
//            totalSpacesToInsert=totalSpacesToInsert-(wordArray.length-1);
//        }
//        int i=0;
//        String justifiedText="";
//        for(String word:wordArray){
//            if(i<totalSpacesToInsert)
//                justifiedText=justifiedText+word+" "+toAppend;
//
//            else
//                justifiedText=justifiedText+word+toAppend;
//
//            i++;
//        }
//
//        return justifiedText;
//    }
}
