package com.bm.main.scm.feature.scan

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.ui.ext.toGone
import com.bm.main.scm.ui.ext.toVisible
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper
import kotlinx.android.synthetic.main.activity_scan_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.ArrayList

class ScanCodeActivity : BaseActivity<ScanCodePresenter, ScanCodeContract.View>(),
    ScanCodeContract.View,
    ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView
    private var mFlash: Boolean = false
    private var mAutoFocus: Boolean = true
    private var mSelectedIndices = arrayListOf<Int>()
    private var mCameraId = -1

    override fun createPresenter(): ScanCodePresenter {
        return ScanCodePresenter(this, this)
    }

    override fun createLayout(): Int = R.layout.activity_scan_code

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
        displayScanner()
        checkCameraPermission()
    }

    override fun renderView() {
        setupToolbar()
        permission_btn.setOnClickListener {
            openCameraPermission()
        }

        ll_search.setOnClickListener {
            val newintent: Intent = intent
            newintent.putExtra(AppConstant.SCAN.TYPE, AppConstant.SCAN.SELL_SEARCH)
            setResult(Activity.RESULT_OK, newintent)
            finish()
        }

        btn_manual.setOnClickListener {
            val newintent: Intent = intent
            newintent.putExtra(AppConstant.SCAN.TYPE, AppConstant.SCAN.SELL_ADD)
            setResult(Activity.RESULT_OK, newintent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (getCameraPermission()) {
            mScannerView.setResultHandler(this)
            mScannerView.startCamera(mCameraId)
            mScannerView.flash = mFlash
            mScannerView.setAutoFocus(mAutoFocus)
        }
        setupToolbar()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstant.REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permission_layout.toGone()
                    content_frame.toVisible()
                } else {
                    permission_layout.toVisible()
                    content_frame.toGone()
                }
                return
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun handleResult(rawResult: Result?) {
        val resultText = rawResult?.text
        if (rawResult == null || resultText.isNullOrEmpty()) {
            mScannerView.resumeCameraPreview(this)
            return
        }
        Log.d("scan", resultText)

        val newintent: Intent = intent
        newintent.putExtra(AppConstant.DATA, resultText)
        setResult(Activity.RESULT_OK, newintent)
        //EventBus.getDefault().post(onCode(resultText))
        finish()

    }

    override fun resumeCamera() {
        mScannerView.resumeCameraPreview(this)
    }

    override fun showMessage(code: Int, msg: String?) {

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            permission_layout.toVisible()
            content_frame.toGone()
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
                //                Util.openAppSettings(this);
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA), AppConstant.REQUEST_CAMERA_PERMISSION
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA), AppConstant.REQUEST_CAMERA_PERMISSION
                )
            }
            return
        }
        permission_layout.toGone()
        content_frame.toVisible()
    }

    private fun openCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            permission_layout.visibility = View.VISIBLE
            content_frame.visibility = View.GONE
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA), AppConstant.REQUEST_CAMERA_PERMISSION
                )
            } else {
                Helper.openAppSettings(this)
                //                ActivityCompat.requestPermissions(this,
                //                        new String[]{android.Manifest.permission.CAMERA}, AppConstant.REQUEST_ZXING_CAMERA_PERMISSION);
            }
            return
        }
        permission_layout.toGone()
        content_frame.toVisible()
    }

    private fun displayScanner() {
        mScannerView = ZXingScannerView(this)
        setupFormats()
        content_frame.addView(mScannerView)
    }

    private fun setupFormats() {
        val formats = ArrayList<BarcodeFormat>()
        if (mSelectedIndices.isEmpty()) {
            mSelectedIndices = ArrayList()
            for (i in ZXingScannerView.ALL_FORMATS.indices) {
                mSelectedIndices.add(i)
            }
        }

        for (index in mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS[index])
        }
        mScannerView.setFormats(formats)
    }

    private fun getCameraPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
                permission_layout.toVisible()
                content_frame.toGone()
            } else {
                permission_layout.toVisible()
                content_frame.toGone()
            }
//            Timber.d("camera permission is NOT granted")
            return false
        }
//        Timber.d("camera permission is granted")
        permission_layout.toGone()
        content_frame.toVisible()
        return true
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Scan"
            setDisplayHomeAsUpEnabled(true)


            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun onBackPressed() {
        mScannerView.stopCamera()
        finish()
    }

    override fun hideShowSearchHeader(visibility: Int) {
        ll_header.visibility = visibility
    }

}
