package com.bm.main.fpl.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import androidx.annotation.NonNull;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.fpl.utils.FileUtils;
import com.bm.main.pos.R;

import java.io.File;

public class ShowPdfActivity extends BaseActivity {
    private static final String TAG = ShowPdfActivity.class.getSimpleName();
    Integer pageNumber = 0;
    String pdfFileName;
//    PDFView pdfView;
    private String nama_pdf, url_pdf;
    private Uri uri;
    private String finalproduk;
    private String finalnamaProduk;
    private String finalnama;
    private String finalkode;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);
        Intent intent = getIntent();
        if (intent != null)
            nama_pdf = intent.getStringExtra("namaPdf");
        url_pdf = intent.getStringExtra("urlPdf");
        finalproduk = intent.getStringExtra("produk");
        finalnamaProduk = intent.getStringExtra("namaProduk");

        finalnama = intent.getStringExtra("nama");
        finalkode = intent.getStringExtra("kode");
        FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, null);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Menampilkan E-Tiket sedang di proses");
        openProgressBarDialog(ShowPdfActivity.this, view);
        FileUtils.doCekPDF(nama_pdf + ".pdf", url_pdf);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("E-tiket");
        init(1);
//        pdfView = findViewById(R.id.pdfView);
        String path = Environment.getExternalStorageDirectory().toString();
        File dir = new File(path, "/Profit/struk/pdfs/");
        file = new File(dir, nama_pdf + ".pdf");
        //Log.d(TAG, "onCreate: "+file.toString());
        uri = Uri.fromFile(file);

        closeProgressBarDialog();
        FileUtils.openPdf(this, file);
//        displayFromUri(uri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.core_menu_rumah_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {
            openTopDialog(false);
        } else if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_share) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    DialogUtils.openSendVia(ShowPdfActivity.this, findViewById(R.id.action_share), uri, finalproduk, finalnamaProduk, finalkode, finalnama, file);
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

//    @Override
//    public void onPageChanged(int page, int pageCount) {
//        pageNumber = page;
//        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
//    }

//    private void displayFromUri(Uri uri) {
//        pdfView.fromUri(uri)
//                .defaultPage(pageNumber)
//                .onPageChange(this)
//                .enableAnnotationRendering(true)
//                .onLoad(this)
//                .scrollHandle(new DefaultScrollHandle(this)).onError(new OnErrorListener() {
//            @Override
//            public void onError(Throwable t) {
//
//                closeProgressBarDialog();
//
////                showToastCustom(ShowPdfActivity.this,1,"File not in PDF format or corrupted");
//                snackBarCustomAction(findViewById(R.id.coordinated_layout_pdf), 0, "File not in PDF format or corrupted", 1);
//            }
//        })
//                .load();
//    }

//    @Override
//    public void loadComplete(int nbPages) {
//        PdfDocument.Meta meta = pdfView.getDocumentMeta();
//        /*Log.e(TAG, "title = " + meta.getTitle());
//        Log.e(TAG, "author = " + meta.getAuthor());
//        Log.e(TAG, "subject = " + meta.getSubject());
//        Log.e(TAG, "keywords = " + meta.getKeywords());
//        Log.e(TAG, "creator = " + meta.getCreator());
//        Log.e(TAG, "producer = " + meta.getProducer());
//        Log.e(TAG, "creationDate = " + meta.getCreationDate());
//        Log.e(TAG, "modDate = " + meta.getModDate());*/
//        printBookmarksTree(pdfView.getTableOfContents(), "-");
//        closeProgressBarDialog();
//    }

//    public void printBookmarksTree(@NonNull List<PdfDocument.Bookmark> tree, String sep) {
//        for (PdfDocument.Bookmark b : tree) {
//            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
//            if (b.hasChildren()) {
//                printBookmarksTree(b.getChildren(), sep + "-");
//            }
//        }
//    }
}
