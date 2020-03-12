package com.bm.main.fpl.templates.htmlspanner;

import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
//import android.util.Log;

import com.bm.main.fpl.templates.htmlspanner.exception.ParsingCancelledException;
import com.bm.main.fpl.templates.htmlspanner.handlers.FontHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.HeaderHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.ImageHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.LinkHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.ListItemHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.MonoSpaceHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.NewLineHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.PreHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.StyleNodeHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.StyledTextHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.SubScriptHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.SuperScriptHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.TableHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.UnderlineHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.attributes.AlignmentAttributeHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.attributes.BorderAttributeHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.attributes.HorizontalLineHandler;
import com.bm.main.fpl.templates.htmlspanner.handlers.attributes.StyleAttributeHandler;
import com.bm.main.fpl.templates.htmlspanner.style.Style;
import com.bm.main.fpl.templates.htmlspanner.style.StyleValue;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HtmlSpanner {

    public static final int HORIZONTAL_EM_WIDTH = 10;
    public static int NUMBER_WIDTH = 5;
    public static int BULLET_WIDTH = 3;
    public static int BLANK_WIDTH = 10;


    private Map<String, TagNodeHandler> handlers;

    private boolean stripExtraWhiteSpace = false;

    private HtmlCleaner htmlCleaner;

    private FontResolver fontResolver;

    private int backgroundColor;

    private int textColor;

    private float textSize;

    private boolean textAlignCenter;

    /**
     * Switch to determine if CSS is used
     */
    private boolean allowStyling = true;

    /**
     * If CSS colours are used
     */
    private boolean useColoursFromStyle = true;
    private static Map<String, String> htmlTagsDictionary;

    static {
        htmlTagsDictionary = new LinkedHashMap<>();
        htmlTagsDictionary.put("\r\n", "\n");
        htmlTagsDictionary.put("\r", "\n");
        htmlTagsDictionary.put("\n","<br>");
        htmlTagsDictionary.put("&gt;", ">");
        htmlTagsDictionary.put("&lt;", "<");
        htmlTagsDictionary.put("&bull;", "•");
        htmlTagsDictionary.put("&#39;", "'");
        htmlTagsDictionary.put("&euro;", "€");
        htmlTagsDictionary.put("&#36;", "$");
        htmlTagsDictionary.put("&nbsp;", " ");
        htmlTagsDictionary.put("&rsquo;", "'");
        htmlTagsDictionary.put("&lsquo;", "'");
        htmlTagsDictionary.put("&ldquo;", "\"");
        htmlTagsDictionary.put("&rdquo;", "\"");
        htmlTagsDictionary.put("&ndash;", "-");
        htmlTagsDictionary.put("&#95;", "_");
        htmlTagsDictionary.put("&copy;", "&#169;");
        htmlTagsDictionary.put("&divide;", "&#247;");
        htmlTagsDictionary.put("&micro;", "&#181;");
        htmlTagsDictionary.put("&middot;", "&#183;");
        htmlTagsDictionary.put("&para;", "&#182;");
        htmlTagsDictionary.put("&plusmn;", "&#177;");
        htmlTagsDictionary.put("&reg;", "&#174;");
        htmlTagsDictionary.put("&sect;", "&#167;");
        htmlTagsDictionary.put("&trade;", "&#153;");
        htmlTagsDictionary.put("&yen;", "&#165;");
        htmlTagsDictionary.put("&pound;", "£");
        htmlTagsDictionary.put("&raquo;", ">>");
        htmlTagsDictionary.put("&laquo;", "<<");
        htmlTagsDictionary.put("&hellip;", "...");
        htmlTagsDictionary.put("&agrave;", "à");
        htmlTagsDictionary.put("&egrave;", "è");
        htmlTagsDictionary.put("&igrave;", "ì");
        htmlTagsDictionary.put("&ograve;", "ò");
        htmlTagsDictionary.put("&ugrave;", "ù");
        htmlTagsDictionary.put("&aacute;", "á");
        htmlTagsDictionary.put("&eacute;", "é");
        htmlTagsDictionary.put("&iacute;", "í");
        htmlTagsDictionary.put("&oacute;", "ó");
        htmlTagsDictionary.put("&uacute;", "ú");
        htmlTagsDictionary.put("&Agrave;", "À");
        htmlTagsDictionary.put("&Egrave;", "È");
        htmlTagsDictionary.put("&Igrave;", "Ì");
        htmlTagsDictionary.put("&Ograve;", "Ò");
        htmlTagsDictionary.put("&Ugrave;", "Ù");
        htmlTagsDictionary.put("&Aacute;", "Á");
        htmlTagsDictionary.put("&Eacute;", "É");
        htmlTagsDictionary.put("&Iacute;", "Í");
        htmlTagsDictionary.put("&Oacute;", "Ó");
        htmlTagsDictionary.put("&Uacute;", "Ú");
        htmlTagsDictionary.put("<h1>","<h1 style=\"font-weight:bold\">");
        htmlTagsDictionary.put("<h2>","<h2 style=\"font-weight:bold\">");
    }


    /**
     * Creates a new HtmlSpanner using a default HtmlCleaner instance.
     */
    public HtmlSpanner(int textColor,float textSize) {
        this(createHtmlCleaner(), new SystemFontResolver(),textColor,textSize);
    }

    /**
     * Creates a new HtmlSpanner using the given HtmlCleaner instance.
     *
     * This allows for a custom-configured HtmlCleaner.
     *
     * @param cleaner
     */
    public HtmlSpanner(HtmlCleaner cleaner, FontResolver fontResolver,int textColor, float textSize) {
        this.htmlCleaner = cleaner;
        this.fontResolver = fontResolver;
        this.handlers = new HashMap<>();
        this.textColor=textColor;
        this.textSize=textSize;
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        NUMBER_WIDTH = Math.round(paint.measureText("4."));
       // if(NUMBER_WIDTH <= 0)
            BULLET_WIDTH = Math.round(paint.measureText("\u2022"));
        BLANK_WIDTH = Math.round(paint.measureText(" "));
        registerBuiltInHandlers();
    }

    public FontResolver getFontResolver() {
        return this.fontResolver;
    }

    public void setFontResolver( FontResolver fontResolver ) {
        this.fontResolver = fontResolver;
    }

    public FontFamily getFont( String name ) {
        return this.fontResolver.getFont(name);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getTextSize() {
        return textSize;
    }

    /**
     * Switch to specify whether excess whitespace should be stripped from the
     * input.
     *
     * @param stripExtraWhiteSpace
     */
    public void setStripExtraWhiteSpace(boolean stripExtraWhiteSpace) {
        this.stripExtraWhiteSpace = stripExtraWhiteSpace;
    }

    /**
     * Returns if whitespace is being stripped.
     *
     * @return
     */
    public boolean isStripExtraWhiteSpace() {
        return stripExtraWhiteSpace;
    }

    /**
     * Indicates whether the text style may be updated.
     *
     * If this is set to false, all CSS is ignored
     * and the basic built-in style is used.
     *
     * @return
     */
    public boolean isAllowStyling() {
        return allowStyling;
    }

    /**
     * Switch to specify is CSS style should be used.
     *
     * @param value
     */
    public void setAllowStyling( boolean value ) {
        this.allowStyling = value;
    }

    /**
     * Switch to specify if the colours from CSS
     * should override user-specified colours.
     *
     * @param value
     */
    public void setUseColoursFromStyle( boolean value ) {
        this.useColoursFromStyle = value;
    }


    public boolean isUseColoursFromStyle() {
        return this.useColoursFromStyle;
    }

    /**
     * Registers a new custom TagNodeHandler.
     *
     * If a TagNodeHandler was already registered for the specified tagName it
     * will be overwritten.
     *
     * @param tagName
     * @param handler
     */
    public void registerHandler(String tagName, TagNodeHandler handler) {
        this.handlers.put(tagName, handler);
        handler.setSpanner(this);
    }

    /**
     * Removes the handler for the given tag.
     *
     * @param tagName the tag to remove handlers for.
     */
    public void unregisterHandler(String tagName) {
        this.handlers.remove(tagName);
    }

    /**
     * Parses the text in the given String.
     *
     * @param html
     *
     * @return a Spanned version of the text.
     */
    public Spannable fromHtml(String html) {
        if(html!=null){
            if(!TextUtils.isEmpty(html)){
                html=replaceHtmlTags(html);
            }
        }
     //   Log.i("HTML_UPDATE_1",html);
        return fromTagNode(this.htmlCleaner.clean(html), null);
    }

    public Spannable fromHtml(String html, CancellationCallback cancellationCallback) {
        return fromTagNode(this.htmlCleaner.clean(html), cancellationCallback);
    }

    /**
     * Parses the text in the given Reader.
     *
     * @param reader
     * @return
     * @throws IOException
     */
    public Spannable fromHtml(Reader reader) throws IOException {
        return fromTagNode(this.htmlCleaner.clean(String.valueOf(reader)), null);
    }

    public Spannable fromHtml(Reader reader, CancellationCallback cancellationCallback) throws IOException {
        return fromTagNode(this.htmlCleaner.clean(String.valueOf(reader)), cancellationCallback);
    }

    /**
     * Parses the text in the given InputStream.
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public Spannable fromHtml(InputStream inputStream) throws IOException {
        return fromTagNode(this.htmlCleaner.clean(inputStream), null);
    }

    public Spannable fromHtml(InputStream inputStream, CancellationCallback cancellationCallback) throws IOException {
        return fromTagNode(this.htmlCleaner.clean(inputStream), cancellationCallback);
    }

    /**
     * Gets the currently registered handler for this tag.
     *
     * Used so it can be wrapped.
     *
     * @param tagName
     * @return the registed TagNodeHandler, or null if none is registered.
     */
    public TagNodeHandler getHandlerFor(String tagName) {
        return this.handlers.get(tagName);
    }

    /**
     * Creates spanned text from a TagNode.
     *
     * @param node
     * @return
     */
    public Spannable fromTagNode(TagNode node, CancellationCallback cancellationCallback) {
        SpannableStringBuilder result = new SpannableStringBuilder();
        SpanStack stack = new SpanStack();

        applySpan( result, node, stack, cancellationCallback );

        stack.applySpans(this, result);

        return result;
    }



    private static HtmlCleaner createHtmlCleaner() {
        HtmlCleaner result = new HtmlCleaner();
        CleanerProperties cleanerProperties = result.getProperties();

        cleanerProperties.setAdvancedXmlEscape(true);
        cleanerProperties.setCharset("UTF-8");
        cleanerProperties.setTranslateSpecialEntities(true);
        cleanerProperties.setTransResCharsToNCR(true);
        cleanerProperties.setOmitComments(true);

        cleanerProperties.setOmitXmlDeclaration(true);
        cleanerProperties.setOmitDoctypeDeclaration(true);

        cleanerProperties.setTranslateSpecialEntities(true);
        cleanerProperties.setTransResCharsToNCR(true);
        cleanerProperties.setRecognizeUnicodeChars(true);

        cleanerProperties.setIgnoreQuestAndExclam(true);
        cleanerProperties.setUseEmptyElementTags(true);

        cleanerProperties.setPruneTags("script,title");

        return result;
    }

    private void checkForCancellation( CancellationCallback cancellationCallback ) {
        if ( cancellationCallback != null && cancellationCallback.isCancelled() ) {
            throw new ParsingCancelledException();
        }
    }

    private void handleContent(SpannableStringBuilder builder, Object node,
                               SpanStack stack, CancellationCallback cancellationCallback ) {

        checkForCancellation(cancellationCallback);

        ContentNode contentNode = (ContentNode) node;

        String text = TextUtil.replaceHtmlEntities(
                contentNode.getContent().toString(), false);

        if ( isStripExtraWhiteSpace() ) {
            //Replace unicode non-breaking space with normal space.
            text = text.replace( '\u00A0', ' ' );
        }

        if ( text.trim().length() > 0 ) {
            builder.append(text);
        }
    }

    private void applySpan(SpannableStringBuilder builder, TagNode node, SpanStack stack,
                           CancellationCallback cancellationCallback) {

        checkForCancellation(cancellationCallback);

        TagNodeHandler handler = this.handlers.get(node.getName());

        if ( handler == null ) {
            handler = new StyledTextHandler();
            handler.setSpanner(this);
        }

        int lengthBefore = builder.length();

        handler.beforeChildren(node, builder, stack);

        if ( !handler.rendersContent() ) {

            for (Object childNode : node.getAllChildren()) {

                if ( childNode instanceof ContentNode ) {
                    handleContent( builder, childNode, stack, cancellationCallback );
                } else if ( childNode instanceof TagNode ) {
                    applySpan( builder, (TagNode) childNode, stack, cancellationCallback );
                }
            }
        }

        int lengthAfter = builder.length();
        handler.handleTagNode(node, builder, lengthBefore, lengthAfter, stack);
    }


    private static StyledTextHandler wrap( StyledTextHandler handler ) {
        return new StyleAttributeHandler(new AlignmentAttributeHandler(handler));
    }

    private void registerBuiltInHandlers() {

        TagNodeHandler italicHandler = new StyledTextHandler(
                new Style().setFontStyle(Style.FontStyle.ITALIC));

        registerHandler("i", italicHandler);
        registerHandler("em", italicHandler);
        registerHandler("cite", italicHandler);
        registerHandler("dfn", italicHandler);

        TagNodeHandler boldHandler = new StyledTextHandler(
                new Style().setFontWeight(Style.FontWeight.BOLD));

        registerHandler("b", boldHandler);
        registerHandler("bold", boldHandler);
        registerHandler("strong", boldHandler);
        //Underline added
        registerHandler("u",new UnderlineHandler());

        TagNodeHandler marginHandler = new StyledTextHandler(
                new Style().setMarginLeft(new StyleValue(2.0f, StyleValue.Unit.EM)));

        registerHandler("blockquote", marginHandler);

        TagNodeHandler listHandler = new StyledTextHandler(new Style()
                .setDisplayStyle(Style.DisplayStyle.BLOCK));

        registerHandler("ul", listHandler);
        registerHandler("ol", listHandler);

        TagNodeHandler monSpaceHandler = wrap(new MonoSpaceHandler());

        registerHandler("tt", monSpaceHandler);
        registerHandler("code", monSpaceHandler);

        registerHandler("style", new StyleNodeHandler() );

        //We wrap an alignment-handler to support
        //align attributes

        StyledTextHandler inlineAlignment = wrap(new StyledTextHandler());
        TagNodeHandler brHandler = new NewLineHandler(1, inlineAlignment);

        registerHandler("br", brHandler);
        registerHandler("br/", brHandler);

        Style.BorderStyle borderStyle = Style.BorderStyle.valueOf("solid".toUpperCase());

        //HR handler
        Style hrStyle = new Style()
                .setDisplayStyle(Style.DisplayStyle.BLOCK);

        TagNodeHandler hrHandler = new HorizontalLineHandler(wrap(new StyledTextHandler(hrStyle)));

        registerHandler("hr", hrHandler);

        Style paragraphStyle = new Style()
                .setDisplayStyle(Style.DisplayStyle.BLOCK)
                .setMarginBottom(
                        new StyleValue(1.0f, StyleValue.Unit.EM))
                .setBorderStyle(borderStyle).setBorderColor(backgroundColor);


        TagNodeHandler pHandler = new BorderAttributeHandler(wrap(new StyledTextHandler(paragraphStyle)));

        Style spanStyle = new Style()
                .setDisplayStyle(Style.DisplayStyle.INLINE)
                .setMarginBottom(
                        new StyleValue(1.0f, StyleValue.Unit.EM));


        TagNodeHandler spanHandler = new BorderAttributeHandler(wrap(new StyledTextHandler(spanStyle)));

        registerHandler("p", pHandler);
        registerHandler("div", pHandler);
        registerHandler("span",spanHandler);

        TableHandler tableHandler=new TableHandler();
        tableHandler.setTextSize(textSize * 0.83f);
        tableHandler.setTextColor(textColor);
        registerHandler("table",tableHandler);

        registerHandler("h1", wrap(new HeaderHandler(2f, 0.5f)));
        registerHandler("h2", wrap(new HeaderHandler(1.5f, 0.6f)));
        registerHandler("h3", wrap(new HeaderHandler(1.17f, 0.7f)));
        registerHandler("h4", wrap(new HeaderHandler(1.12f, 0.8f)));
        registerHandler("h5", wrap(new HeaderHandler(0.83f, 0.9f)));
        registerHandler("h6", wrap(new HeaderHandler(0.75f, 1f)));

        TagNodeHandler preHandler = new PreHandler();
        registerHandler("pre", preHandler);

        TagNodeHandler bigHandler = new StyledTextHandler(
                new Style().setFontSize(
                        new StyleValue(1.25f, StyleValue.Unit.EM)));

        registerHandler("big", bigHandler);

        TagNodeHandler smallHandler = new StyledTextHandler(
                new Style().setFontSize(
                        new StyleValue(0.8f, StyleValue.Unit.EM)));

        registerHandler("small", smallHandler);

        TagNodeHandler subHandler = new SubScriptHandler();
        registerHandler("sub", subHandler);

        TagNodeHandler superHandler = new SuperScriptHandler();
        registerHandler("sup", superHandler);

        TagNodeHandler centerHandler = new StyledTextHandler(new Style().setTextAlignment(Style.TextAlignment.CENTER));
        registerHandler("center", centerHandler);

        registerHandler("li", new ListItemHandler());

        registerHandler("a", new LinkHandler());
        registerHandler("img", new ImageHandler());

        registerHandler("font", new FontHandler() );

    }

    public static String replaceHtmlTags(String inputString) {
        if (inputString == null) {
            return null;
        }

        // before apply typefaces, replace all tags
        for (Map.Entry<String, String> entry : htmlTagsDictionary.entrySet()) {
            inputString = inputString.replace(entry.getKey(), entry.getValue());
            inputString = inputString.replace(entry.getKey().toUpperCase(), entry.getValue());
        }
        return inputString;
    }

    public static interface CancellationCallback {
        boolean isCancelled();
    }

}
