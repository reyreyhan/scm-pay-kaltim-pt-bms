package com.bm.main.fpl.templates.htmltextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.text.Spannable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bm.main.fpl.templates.htmlspanner.HtmlSpanner;

@SuppressLint("AppCompatCustomView")
public class HtmlTextView extends TextView {
  //  private final LinkMovementMethod movementMethod;
  private HtmlSpanner htmlSpanner;
  Spannable htmlText;
    public HtmlTextView(Context context) {
        super(context);
        htmlSpanner  =new HtmlSpanner(this.getCurrentTextColor(), this.getTextSize());
        htmlSpanner.setBackgroundColor(this.getSolidColor());
    }

    public HtmlTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        htmlSpanner  =new HtmlSpanner(this.getCurrentTextColor(), this.getTextSize());
        htmlSpanner.setBackgroundColor(this.getSolidColor());
    }

    public HtmlTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // this.movementMethod =  new LinkMovementMethod.getInstance();
        htmlSpanner  =new HtmlSpanner(this.getCurrentTextColor(), this.getTextSize());
        htmlSpanner.setBackgroundColor(this.getSolidColor());
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

//    public Spannable getHtmlText() {
//        return htmlText;
//    }

    public void setHtmlText(String htmlText) {
        super.setText(htmlSpanner.fromHtml(htmlText));
    }

//    public String setHtml(String string){
//        return htmlSpanner.fromHtml(string);
//    }
}
