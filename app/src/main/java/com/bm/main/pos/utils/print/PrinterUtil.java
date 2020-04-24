package com.bm.main.pos.utils.print;

/**
 * Created by Richie on 08/03/18.
 */

import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bm.main.pos.models.store.Store;
import com.bm.main.pos.models.transaction.DetailTransaction;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class PrinterUtil {

    static ArrayList<Integer> grandQty = new ArrayList<>();
    private OutputStreamWriter mWriter = null;
    private OutputStream mOutputStream = null;

    private final static int WIDTH_PIXEL = 384;
    //    public final static int IMAGE_SIZE = 320;
    private int MAX_CHAR;
    private int nominalLength;
    public static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 33, -128, 0};

    /**
     * Inisialisasi
     *
     * @param encoding Pengodean
     * @throws IOException
     */
    public PrinterUtil(OutputStream outputStream, String encoding, int maxChar, int nominalLength) throws IOException {
        mWriter = new OutputStreamWriter(outputStream, encoding);
        mOutputStream = outputStream;
        MAX_CHAR = maxChar;
        this.nominalLength = nominalLength;
        Timber.e("init max char: %s", maxChar);
    }

    public PrinterUtil(OutputStream outputStream, String encoding) throws IOException {
        this(outputStream, encoding, 42, 10);
    }

    public void print(byte[] bs) throws IOException {
        mOutputStream.write(Formatter.leftAlign());
        mOutputStream.write(bs);
    }

    public void printImageMode(byte[] img) throws IOException {
        mOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
        mOutputStream.write(img);
    }

    public void printImageModeLeft(byte[] img) throws IOException {
        mOutputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
        mOutputStream.write(img);
    }

    /**
     * Ganti baris
     *
     * @return length
     * @throws IOException
     */
    public void printLine(int lineNum) throws IOException {
        for (int i = 0; i < lineNum; i++) {
            mWriter.write("\n");
        }
        mWriter.flush();
    }

    /**
     * ganti baris 1
     *
     * @throws IOException
     */
    public void printLine() throws IOException {
        printLine(1);
    }

    /**
     * Set lokasi
     *
     * @return
     * @throws IOException
     */
    public byte[] setLocation(int offset) throws IOException {
        byte[] bs = new byte[4];
        bs[0] = 0x1B;
        bs[1] = 0x24;
        bs[2] = (byte) (offset % 256);
        bs[3] = (byte) (offset / 256);
        return bs;
    }

    public byte[] getGbk(String stText) throws IOException {
        byte[] returnText = stText.getBytes("GBK"); // 必须放在try内才可以
        return returnText;
    }

    private int getStringPixLength(String str) {
        int pixLength = 0;
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            pixLength += 12;
        }
        return pixLength;
    }

    public int getOffset(String str) {
        return WIDTH_PIXEL - getStringPixLength(str);
    }

    /**
     * Print text
     *
     * @param text
     * @throws IOException
     */
    public void printText(String text) throws IOException {
        mWriter.write(text);
        mWriter.flush();
    }

    public void writeWithFormat(byte[] buffer, final byte[] pFormat, final byte[] pAlignment) {
        try {
            mOutputStream.write(pAlignment);
            mOutputStream.write(pFormat);
            // Write the actual data:
            mOutputStream.write(buffer, 0, buffer.length);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Notify printer it should be printed in the given format:

    }

    public void writeWithFormat(byte[] buffer, final byte[] pAlignment) {
        try {
            mOutputStream.write(pAlignment);
            mOutputStream.write(buffer);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Notify printer it should be printed in the given format:
    }

    /**
     * Alignment 0: Left-aligned, 1: Centered, 2: Right-aligned
     */
    public void printAlignment(int alignment) throws IOException {
        mWriter.write(0x1b);
        mWriter.write(0x61);
        mWriter.write(alignment);
    }

    public void printTwoColumn(String title, String content) throws IOException {
        int iNum = 0;
        byte[] byteBuffer = new byte[100];
        byte[] tmp;

        tmp = getGbk(title);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = setLocation(getOffset(content));
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(content);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);

        print(byteBuffer);
    }

    public void printDashLine() throws IOException {
        StringBuilder sb = new StringBuilder("");
        Timber.e("max char: %s", MAX_CHAR);
        for (int i = 0; i < MAX_CHAR; i++) {
            sb.append("-");
        }
        writeWithFormat(sb.toString().getBytes(), Formatter.centerAlign());
    }

    public static void printTest(BluetoothSocket bluetoothSocket, Drawable drawable) {
        try {
            PrinterUtil pUtil = new PrinterUtil(bluetoothSocket.getOutputStream(), "GBK");
            pUtil.writeWithFormat("\n".getBytes(), Formatter.centerAlign());
            pUtil.writeWithFormat("\n".getBytes(), Formatter.centerAlign());

            pUtil.writeWithFormat(getLogo(drawable), Formatter.rightAlign());

            pUtil.writeWithFormat("\n".getBytes(), Formatter.centerAlign());
            pUtil.writeWithFormat("\n".getBytes(), Formatter.centerAlign());

            pUtil.printLine();

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void print(BluetoothSocket bluetoothSocket, DetailTransaction data, Store store, String footer, Drawable drawable) {
        try {
            PrinterUtil pUtil = new PrinterUtil(bluetoothSocket.getOutputStream(), "GBK");
            printHeader(pUtil, data, store);
            printItem(pUtil, data);
            printInfo(pUtil, data);
            printFooter(pUtil, drawable);
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(BluetoothSocket bluetoothSocket, DetailTransaction data, Store store, String footer, Drawable drawable, String printerName) {
        try {
            PrinterUtil pUtil = new PrinterUtil(bluetoothSocket.getOutputStream(), "GBK"
                    , printerName.equalsIgnoreCase("InnerPrinter") ? 32 : 42
                    , printerName.equalsIgnoreCase("InnerPrinter") ? 8 : 10
            );
            printHeader(pUtil, data, store);
            printItem(pUtil, data);
            printInfo(pUtil, data);
            printFooter(pUtil, drawable);
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface PrintListener {
        void afterPrint(Boolean success);
    }

    public static void printText(BluetoothSocket bluetoothSocket, String text, Drawable drawable, String footerTxt, PrintListener listener) {
        try {
            PrinterUtil pUtil = new PrinterUtil(bluetoothSocket.getOutputStream(), "GBK");

            if (text != null && !text.equals("")) {
                pUtil.writeWithFormat(text.getBytes(), Formatter.leftAlign());
            }
            printFooter(pUtil, drawable, footerTxt);
            bluetoothSocket.close();
            if (listener != null)
                listener.afterPrint(true);
        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null)
                listener.afterPrint(false);
        }
    }

    public static void printImg(BluetoothSocket bluetoothSocket, Drawable drawable, Drawable footer, String footerTxt, PrintListener listener) {
        try {
            PrinterUtil pUtil = new PrinterUtil(bluetoothSocket.getOutputStream(), "GBK");

            byte[] img = getLogo(drawable);
            pUtil.printImageMode(img);
            printFooter(pUtil, footer, footerTxt);
            bluetoothSocket.close();
            if (listener != null)
                listener.afterPrint(true);
        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null)
                listener.afterPrint(false);
        }
    }

    public static void printImgLeft(BluetoothSocket bluetoothSocket, Drawable drawable, Drawable footer, String footerTxt, PrintListener listener) {
        try {
            PrinterUtil pUtil = new PrinterUtil(bluetoothSocket.getOutputStream(), "GBK");

            byte[] img = getLogo(drawable);
            pUtil.printImageModeLeft(img);
            printFooter(pUtil, footer);
            bluetoothSocket.close();
            if (listener != null)
                listener.afterPrint(true);
        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null)
                listener.afterPrint(false);
        }
    }

    static void printHeader(PrinterUtil pUtil, DetailTransaction data, Store store) {
        try {
            if (data.getStruk().getPembayaran().equals("tunai")) {
//                String newLine = "\n";
                String no = "ID Transaksi:";
                DetailTransaction.Struk struk = data.getStruk();
                Store toko = getEmptyStore(struk);
                if (store != null) {
                    if (store.getNama_toko() != null) {
                        toko = store;
                    }
                }

//                pUtil.writeWithFormat(newLine.getBytes(), new Formatter().medium().get(), Formatter.centerAlign());
//                pUtil.printLine();
                pUtil.writeWithFormat(toko.getNama_toko().getBytes(), Formatter.centerAlign());
                pUtil.printLine();
                if (toko.getAlamat() != null && toko.getAlamat().length() > 0) {
                    pUtil.writeWithFormat(toko.getAlamat().getBytes(), new Formatter().small().get(), Formatter.centerAlign());
                    pUtil.printLine();
                }
                if (toko.getNohp() != null && toko.getNohp().length() > 0) {
                    pUtil.writeWithFormat(toko.getNohp().getBytes(), new Formatter().small().get(), Formatter.centerAlign());
                    pUtil.printLine();
                }

                pUtil.printLine();
                pUtil.printDashLine();
                String invoice = struk.getNo_invoice();
                if (invoice != null) {
                    if (invoice.length() + no.length() > pUtil.MAX_CHAR) {
                        pUtil.printLine();
                        pUtil.writeWithFormat(no.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                        pUtil.printLine();
                        pUtil.writeWithFormat(invoice.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                    } else {
                        pUtil.printLine();
                        String text = no + getWhiteSpace(pUtil.MAX_CHAR - no.length() - invoice.length()) + invoice;
                        pUtil.writeWithFormat(text.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                    }

                }

                pUtil.printLine();
                pUtil.printDashLine();


            } else {
                String newLine = "\n";
                String no = "ID Transaksi:";
                String method = "Metode Pembayaran:";
                String jatuhTempo = "Jatuh Tempo:";

                DetailTransaction.Struk struk = data.getStruk();
                Store toko = getEmptyStore(struk);
                if (store != null) {
                    if (store.getNama_toko() != null) {
                        toko = store;
                    }
                }

                pUtil.writeWithFormat(newLine.getBytes(), new Formatter().medium().get(), Formatter.centerAlign());
                pUtil.printLine();
                pUtil.writeWithFormat(toko.getNama_toko().getBytes(), Formatter.centerAlign());
                pUtil.printLine();
                if (toko.getAlamat() != null && toko.getAlamat().length() > 0) {
                    pUtil.writeWithFormat(toko.getAlamat().getBytes(), new Formatter().small().get(), Formatter.centerAlign());
                    pUtil.printLine();
                }
                if (toko.getNohp() != null && toko.getNohp().length() > 0) {
                    pUtil.writeWithFormat(toko.getNohp().getBytes(), new Formatter().small().get(), Formatter.centerAlign());
                    pUtil.printLine();
                }
                pUtil.printLine();
                pUtil.printDashLine();
                String invoice = struk.getNo_invoice();
                if (invoice != null) {
                    if (invoice.length() + no.length() > pUtil.MAX_CHAR) {
                        pUtil.printLine();
                        pUtil.writeWithFormat(no.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                        pUtil.printLine();
                        pUtil.writeWithFormat(invoice.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                    } else {
                        pUtil.printLine();
                        String text = no + getWhiteSpace(pUtil.MAX_CHAR - no.length() - invoice.length()) + invoice;
                        pUtil.writeWithFormat(text.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                    }

                }

                String methodValue = struk.getPembayaran();
                if (methodValue != null) {
                    if (methodValue.length() + method.length() > pUtil.MAX_CHAR) {
                        pUtil.printLine();
                        pUtil.writeWithFormat(method.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                        pUtil.printLine();
                        pUtil.writeWithFormat(methodValue.getBytes(), new Formatter().small().get(), Formatter.rightAlign());
                    } else {
                        pUtil.printLine();
                        String text = method + getWhiteSpace(pUtil.MAX_CHAR - method.length() - methodValue.length()) + methodValue;
                        pUtil.writeWithFormat(text.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                    }
                }

//
                String jatuhTempoValue = struk.getJatuh_tempo();
                if (jatuhTempoValue != null && jatuhTempoValue.length() > 0 && !jatuhTempoValue.equals("0000-00-00")) {
                    try {
                        jatuhTempoValue = getDateFormat(jatuhTempoValue, "yyyy-MM-dd", "dd MMMM yyyy");
                        if (jatuhTempoValue.length() + jatuhTempo.length() > pUtil.MAX_CHAR) {
                            pUtil.printLine();
                            pUtil.writeWithFormat(jatuhTempo.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                            pUtil.printLine();
                            pUtil.writeWithFormat(jatuhTempoValue.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                        } else {
                            pUtil.printLine();
                            String text = jatuhTempo + getWhiteSpace(pUtil.MAX_CHAR - jatuhTempo.length() - jatuhTempoValue.length()) + jatuhTempoValue;
                            pUtil.writeWithFormat(text.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


                pUtil.printLine();
                pUtil.printDashLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printItem(PrinterUtil pUtil, DetailTransaction data) throws IOException {
        List<DetailTransaction.Data> items = data.getData();
        grandQty.clear();
        if (items == null) {
            return;
        }
        if (items.size() == 0) {
            return;
        }

        for (DetailTransaction.Data model : items) {
            try {
                pUtil.printLine();
                String nama;
                String sisaNama = "";
                int maxNameLenght = pUtil.MAX_CHAR - (pUtil.nominalLength * 2) - 2;
                if (model.getNama_barang().length() > maxNameLenght) {
                    nama = model.getNama_barang().substring(0, maxNameLenght).toUpperCase();
                    sisaNama = model.getNama_barang().substring(maxNameLenght).toUpperCase();
                } else {
                    nama = model.getNama_barang().toUpperCase();
                }
                String qty = convertToCurrency(model.getJumlah());
                String harga_dasar = convertToCurrency(model.getHarga());
                String total = convertToCurrency(model.getTotalharga());
                grandQty.add(Integer.parseInt(model.getJumlah()));

                int harga_lenght = (pUtil.nominalLength - harga_dasar.length());
                int total_lenght = (pUtil.nominalLength - total.length());
                String value = qty + getWhiteSpace(harga_lenght) + harga_dasar + getWhiteSpace(total_lenght) + total;
                int size = pUtil.MAX_CHAR - nama.length() - value.length();
                String text = nama + getWhiteSpace(size) + value;
                pUtil.writeWithFormat(text.getBytes(), new Formatter().small().get(), Formatter.leftAlign());

                if (!sisaNama.equals("")) {
                    pUtil.writeWithFormat(sisaNama.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        pUtil.printLine();
        pUtil.printDashLine();
    }

    private static void printInfo(PrinterUtil pUtil, DetailTransaction data) {
        try {
            int total_qty = 0;
            DetailTransaction.Struk struk = data.getStruk();
            String total = "Total Item:";
            String tanggal = "Tanggal:";


            String bayar = "Tunai:";
            String kembalian = "Kembalian";
            for (Integer element : grandQty) {
                total_qty += element;
            }
            String totalOrder = "Rp " + convertToCurrency(struk.getTotalorder());
            int totalOder_lenght = (pUtil.nominalLength * 2) - totalOrder.length();
            String value = total_qty + getWhiteSpace(totalOder_lenght) + totalOrder;
            if (value.length() + total.length() > pUtil.MAX_CHAR) {
                pUtil.printLine();
                pUtil.writeWithFormat(total.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                pUtil.printLine();
                pUtil.writeWithFormat(value.getBytes(), new Formatter().small().get(), Formatter.rightAlign());
            } else {
                pUtil.printLine();
                int size = pUtil.MAX_CHAR - total.length() - value.length();
                String totalValue = total + getWhiteSpace(size) + value;
                pUtil.writeWithFormat(totalValue.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            }
            if (data.getStruk().getPembayaran().equals("tunai")) {
                if (struk.getId_pelanggan() != null && struk.getId_pelanggan().length() > 0) {
                    if (struk.getTotalbayar() != null) {
                        double bayarDouble = Double.parseDouble(struk.getTotalbayar());

                        if (bayarDouble > 0) {
                            String bayarValue = "Rp " + convertToCurrency(struk.getTotalbayar());
                            if (bayarValue.length() + bayar.length() > 20) {
                                pUtil.printLine();
                                pUtil.writeWithFormat(bayar.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                                pUtil.printLine();
                                pUtil.writeWithFormat(bayarValue.getBytes(), new Formatter().small().get(), Formatter.rightAlign());
                            } else {
                                pUtil.printLine();
                                int size = pUtil.MAX_CHAR - bayar.length() - bayarValue.length();
                                String text = bayar + getWhiteSpace(size) + bayarValue;
                                pUtil.writeWithFormat(text.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                            }
                        }
                    }

                    if (struk.getKembalian() != null) {
                        double kembalianDouble = Double.parseDouble(struk.getKembalian());
                        if (kembalianDouble > 0) {
                            String kembalianValue = "Rp " + convertToCurrency(struk.getKembalian());
                            if (kembalianValue.length() + kembalian.length() > 20) {
                                pUtil.printLine();
                                pUtil.writeWithFormat(kembalian.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                                pUtil.printLine();
                                pUtil.writeWithFormat(kembalianValue.getBytes(), new Formatter().small().get(), Formatter.rightAlign());
                            } else {
                                pUtil.printLine();
                                int size = pUtil.MAX_CHAR - kembalian.length() - kembalianValue.length();
                                String text = kembalian + getWhiteSpace(size) + kembalianValue;
                                pUtil.writeWithFormat(text.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                            }
                        }
                    }
                }
            }
            pUtil.printLine();
            pUtil.printDashLine();
            String tanggalValue = struk.getTanggal();
            if (tanggalValue != null && tanggalValue.length() > 0) {
                try {
                    tanggalValue = getDateFormat(tanggalValue, "yyyy-MM-dd", "dd MMMM yyyy");
                    if (tanggalValue.length() + tanggal.length() > pUtil.MAX_CHAR) {
                        pUtil.printLine();
                        pUtil.writeWithFormat(tanggal.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                        pUtil.printLine();
                        pUtil.writeWithFormat(tanggalValue.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                    } else {
                        pUtil.printLine();
                        String text = tanggal + getWhiteSpace(pUtil.MAX_CHAR - tanggal.length() - tanggalValue.length()) + tanggalValue;
                        pUtil.writeWithFormat(text.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            pUtil.printLine();
            pUtil.printDashLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printFooter(PrinterUtil pUtil, Drawable drawable) {
        printFooter(pUtil, drawable, "https://profit.fastpay.co.id");
    }

    private static void printFooter(PrinterUtil pUtil, Drawable drawable, String footerTxt) {
        try {
            String info = "Powered By";
//            String url_profit = "https://profit.fastpay.co.id";
            pUtil.printLine(1);
            pUtil.writeWithFormat(info.getBytes(), Formatter.centerAlign());
//            pUtil.writeWithFormat(info.getBytes(), new Formatter().small().get(), Formatter.centerAlign());
            pUtil.printLine();
            pUtil.printLine();
            byte[] img = getLogo(drawable);
            pUtil.printImageMode(img);

            if (!footerTxt.equals("")) {
                pUtil.printLine(1);
                pUtil.writeWithFormat(footerTxt.getBytes(), Formatter.centerAlign());
//            pUtil.writeWithFormat(url_profit.getBytes(), new Formatter().small().get(), Formatter.centerAlign());
                pUtil.printLine();
            }

            pUtil.printLine(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getWhiteSpace(int size) {
        if (size < 0) return "";

        StringBuilder builder = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }

    private static byte[] getLogo(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        com.bm.main.fpl.utils.PrintPic pg = com.bm.main.fpl.utils.PrintPic.getInstance();
        pg.initCanvas(380);
        pg.initPaint();
        pg.drawImage(90, 0, bitmap);
        return pg.printDraw();
    }

    public static String convertToCurrency(int value, String format) {
        double currentValue;
        try {
            currentValue = (double) value;
        } catch (NumberFormatException nfe) {
            currentValue = 0.0;
        }
        DecimalFormat formatter = new DecimalFormat(format);
        return formatter.format(currentValue).replace(",", ".");
    }

    public static String convertToCurrency(String value) {
        return convertToCurrency(Integer.parseInt(value), "#,###,###");
    }

    private static Store getEmptyStore(DetailTransaction.Struk struk) {
        Store store = new Store();
        store.setNama_toko("Nama Toko");
        store.setAlamat("Alamat Toko");
        store.setNohp("No. Telepon Toko");
        if (struk == null) {
            return store;
        }
        if (struk.getNama_toko() != null) {
            store.setNama_toko(struk.getNama_toko());
        }
        if (struk.getAlamat() != null) {
            store.setAlamat(struk.getAlamat());
        }
        if (struk.getNohp() != null) {
            store.setNohp(struk.getNohp());
        }
        return store;
    }

    public static String getDateFormat(String tanggal, String formatFrom, String formatTo) throws ParseException {
        Locale locale = new Locale("in", "IN");
        SimpleDateFormat sdfBefore = new SimpleDateFormat(formatFrom, locale);
        Date dateBefore = sdfBefore.parse(tanggal);

        SimpleDateFormat sdfAfter = new SimpleDateFormat(formatTo, locale);
        if (dateBefore == null) {
            return "";
        }
        return sdfAfter.format(dateBefore);
    }
}