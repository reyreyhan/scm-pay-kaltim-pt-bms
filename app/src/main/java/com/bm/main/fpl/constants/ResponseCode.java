package com.bm.main.fpl.constants;

public class ResponseCode
{
    public static final String SUCCESS = "00";
    public static final String NETWORK_ERROR = "01";
    public static final String RESPONSE_PARSE_ERROR = "02";
    public static final String RESPONSE_UNSUCCESSFUL = "03";

    private static final String ESC = "\u001B";
//    private static final String ESC1 = "\u0021";// !
    // - = "\u002D"
//@ = "\u0040"
    // B="\u0042"

    // 4 = "\u0034"

    // E = "\u0045"
    // M = "\u004D"

    // V = "\u0056"

    // t= "\u0074"

    // {= "\u007B"

    // }= "\u007D"

    // FS ="\u001C"

    // & = "\u0026"
    private static final String GS = "\u001D";
    public static final String InitializePrinter = ESC + "@";
    public static final String BoldOn = ESC + "E" + "\u0001";

    public static final String BoldOff = ESC + "E" + "\0";

    public static final String UpOn = ESC + "!" + "\u0010";
    public static final String UpOff = ESC + "!" + "\0";


    public static final String ItalicOn = ESC +  4 + "\u0001";
    public static final String ItalicOff = ESC  + 4  + "\0";
    public static final String UnderlineOn = ESC + "\u002D" + "\u0002";
    public static final String UnderlineOff = ESC + "\u002D" + "\0";


    public static final String DoubleOn = GS + "!" + "\u0011";  // 2x sized text (double-high + double-wide)
    public static final String DoubleOff = GS + "!" + "\0";

    public static final String BarcodeOn = "a" +"\u001C"+"}"+"%"+"\u001C";
    public static final String BarcodeOff = "a";
//    write("\x0a")           # Beginning line feed
//write("\x1c\x7d\x25")   # Start QR Code® command
//write("\x1C")           # Length of string to follow (28 bytes in this example)
//    write("https://pyramidacceptors.com")
//    write("\x0a")           # Ending line feed
//print()



    public static final String CenterOn =ESC+ "a" +"\u0001";
    public static final String CenterOff = ESC + "a" + "\0";
}
