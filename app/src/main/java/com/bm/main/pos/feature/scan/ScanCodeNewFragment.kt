package com.bm.main.pos.feature.scan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseFragment
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.ui.ext.htmlText
import com.bm.main.pos.ui.ext.toGone
import com.bm.main.pos.ui.ext.toVisible
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_penjualan_barcode_scan.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanCodeNewFragment : BaseFragment<ScanCodePresenter, ScanCodeContract.View>(), ScanCodeContract.View,  ZXingScannerView.ResultHandler {
    private var productListener: OnProductSelectedListener? = null

    interface OnProductSelectedListener {
        fun onProductSelected(data: String)
    }

    private lateinit var mScannerView: ZXingScannerView
    private var mFlash: Boolean = false
    private var mAutoFocus: Boolean = true
    private var mSelectedIndices = arrayListOf<Int>()
    private var mCameraId = -1

    private lateinit var _view: View

    companion object {

        @JvmStatic
        fun newInstance() =
            ScanCodeNewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun createPresenter(): ScanCodePresenter {
        return ScanCodePresenter(activity as Context, this)
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_penjualan_barcode_scan, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initAction(view: View) {
        _view = view
        renderView()
        arguments?.let { getPresenter()?.onViewFragmentCreated(it) }
        displayScanner()
        checkCameraPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun renderView() {
        _view.tv_message.htmlText("Arahkan kamera handphone Anda ke <b>Barcode</b> dari produk yang Anda ingin tambahkan")
        _view.permission_btn.setOnClickListener {
            openCameraPermission()
        }
        _view.content_frame.setOnClickListener {
           if (mFlash){
               mFlash = false
               mScannerView.flash = mFlash
           }else{
               mFlash = true
               mScannerView.flash = mFlash
           }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        if (getCameraPermission()) {
            mScannerView.setResultHandler(this)
            mScannerView.startCamera(mCameraId)
            mScannerView.flash = mFlash
            mScannerView.setAutoFocus(mAutoFocus)
        }
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
                    _view.permission_layout.toGone()
                    _view.content_frame.toVisible()
                } else {
                    _view.permission_layout.toVisible()
                    _view.content_frame.toGone()
                }
                return
            }
        }
    }


    override fun handleResult(rawResult: Result?) {
        val resultText = rawResult?.text
        if (rawResult == null || resultText.isNullOrEmpty()) {
            mScannerView.resumeCameraPreview(this)
            return
        }
        Log.d("scan", resultText)
        if (productListener != null){
            productListener!!.onProductSelected(resultText)
            mScannerView.stopCamera()
        }
    }

    override fun resumeCamera() {
        mScannerView.resumeCameraPreview(this)
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
    }

    override fun hideShowSearchHeader(visibility: Int) {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            _view.permission_layout.toVisible()
            _view.content_frame.toGone()
            if (requireActivity().shouldShowRequestPermissionRationale( android.Manifest.permission.CAMERA)) {
                //                Util.openAppSettings(this);
                requireActivity().requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA), AppConstant.REQUEST_CAMERA_PERMISSION
                )
            } else {
                requireActivity().requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA), AppConstant.REQUEST_CAMERA_PERMISSION
                )
            }
            return
        }
        _view.permission_layout.toGone()
        _view.content_frame.toVisible()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            _view.permission_layout.visibility = View.VISIBLE
            _view.content_frame.visibility = View.GONE
            if (requireActivity().shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                requireActivity().requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA), AppConstant.REQUEST_CAMERA_PERMISSION
                )
            } else {
                Helper.openAppSettings(requireActivity())
                //                ActivityCompat.requestPermissions(this,
                //                        new String[]{android.Manifest.permission.CAMERA}, AppConstant.REQUEST_ZXING_CAMERA_PERMISSION);
            }
            return
        }
        _view.permission_layout.toGone()
        _view.content_frame.toVisible()
    }

    private fun displayScanner() {
        mScannerView = ZXingScannerView(requireContext())
        setupFormats()
        _view.content_frame.addView(mScannerView)
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCameraPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (requireActivity().shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                _view.permission_layout.toVisible()
                _view.content_frame.toGone()
            } else {
                _view.permission_layout.toVisible()
                _view.content_frame.toGone()
            }
//            Timber.d("camera permission is NOT granted")
            return false
        }
//        Timber.d("camera permission is granted")
        _view.permission_layout.toGone()
        _view.content_frame.toVisible()
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProductSelectedListener) {
            productListener = context
        } else {
            throw RuntimeException("$context must implement OnProductSelectedListener")
        }
    }

    override fun onDetach() {
        mScannerView.stopCamera()
        productListener = null
        super.onDetach()
    }
}