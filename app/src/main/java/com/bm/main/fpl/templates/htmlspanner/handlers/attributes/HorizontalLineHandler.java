package com.bm.main.fpl.templates.htmlspanner.handlers.attributes;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.bm.main.fpl.templates.htmlspanner.SpanStack;
import com.bm.main.fpl.templates.htmlspanner.handlers.StyledTextHandler;
import com.bm.main.fpl.templates.htmlspanner.spans.HorizontalLineSpan;
import com.bm.main.fpl.templates.htmlspanner.style.Style;

import org.htmlcleaner.TagNode;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/23/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class HorizontalLineHandler extends WrappingStyleHandler {

    public HorizontalLineHandler(StyledTextHandler handler) {
        super(handler);
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end,
                              Style useStyle, SpanStack spanStack) {

        end+=1;
        Log.d("HorizontalLineHandler", "Draw hr from " + start + " to " + end);
        spanStack.pushSpan(new HorizontalLineSpan(useStyle, start, end), start, end);
        appendNewLine(builder);

        super.handleTagNode(node, builder, start, end, useStyle, spanStack);

    }


}
