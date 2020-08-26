package com.bm.main.scm.feature.webpage

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bm.main.scm.R
import kotlinx.android.synthetic.main.activity_pos_webview.*

class WebViewActivity : AppCompatActivity() {

    private var url: String = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pos_webview)

        setSupportActionBar(toolbarx)
        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbarx.setNavigationOnClickListener { v: View? -> finish() }
        }

        title = ""
        toolbarx.title = ""

        toolbar_title.text =
            if (intent.hasExtra("title"))
                intent.getStringExtra("title") ?: getString(R.string.app_name)
            else
                getString(R.string.app_name)

        url = intent.getStringExtra("url") ?: run {
            Toast.makeText(this, "Halaman tidak tersedia", Toast.LENGTH_SHORT).show()
            finish()
            ""
        }

        webView.apply {
            settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                defaultTextEncodingName = "utf-8"
            }

            webViewClient = MyWebViewClient()
            webChromeClient = MyWebChromeClient()

            loadUrl(this@WebViewActivity.url)
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean = false

        override fun onPageFinished(view: WebView, url: String) =
            progressBar.setVisibility(View.GONE)

        override fun onPageStarted(
            view: WebView,
            url: String,
            favicon: Bitmap?
        ) = progressBar.setVisibility(View.VISIBLE)

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            AlertDialog.Builder(this@WebViewActivity)
                .setTitle("Perhatian")
                .setMessage("Gagal memuat halaman")
                .setPositiveButton(
                    "Muat ulang"
                ) { dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                    webView.loadUrl(url)
                }
                .show()
        }
    }

    inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE)
            } else {
                progressBar.setVisibility(View.VISIBLE)
                progressBar.setIndeterminate(true)
            }
            if (newProgress > 0) {
                progressBar.setIndeterminate(false)
                progressBar.setProgress(newProgress)
            }
        }

        override fun onJsAlert(
            view: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            AlertDialog.Builder(this@WebViewActivity)
                .setTitle("Perhatian")
                .setMessage(message)
                .setPositiveButton(
                    "Ok"
                ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                .show()
            return true
        }
    }
}