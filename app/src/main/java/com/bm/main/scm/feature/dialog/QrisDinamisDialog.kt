package com.bm.main.scm.feature.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bm.main.scm.R
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.utils.AppSession
import com.bm.main.scm.utils.FileUtils
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.utils.PermissionUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.samq.util.dp
import kotlinx.android.synthetic.main.fragment_qris_dinamis_2.*
import kotlinx.android.synthetic.main.fragment_qris_dinamis_2.view.*
import java.io.File


class QrisDinamisDialog : DialogFragment() {
    companion object {
        const val TAG = "QrisDinamisDialog"

        private const val KEY_NOMINAL = "KEY_NOMINAL"

        fun newInstance(nominal: String): QrisDinamisDialog {
            val args = Bundle()
            args.putString(KEY_NOMINAL, nominal)
            val fragment = QrisDinamisDialog()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var listener: SuccessDialogListener
    val appSession = AppSession()

    private var qrBitmap: Bitmap? = null
    private var qrFile: File? = null

    interface SuccessDialogListener {
        fun onPositiveButtonDialog()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        permissionUtil = PermissionUtil(context)
        if (context is SuccessDialogListener) {
            listener = context as SuccessDialogListener
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qris_dinamis_2, container, false)
        view.setPadding(20.dp, 10.dp, 20.dp, 10.dp)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            view.background = view.context.getDrawable(R.drawable.rounded_white_24dp)
        }
        (view as ViewGroup).clipToPadding = false
        //ImageView Setup
        val imageView = ImageView(requireContext())
        imageView.setImageResource(R.drawable.ic_close_scm)
        val param = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        imageView.layoutParams = param
        view.addView(imageView)
        imageView.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
        setupView(view)
    }

    private lateinit var permissionUtil: PermissionUtil
    private lateinit var shareStruk: PermissionCallback
    private lateinit var downloadStruk: PermissionCallback
    var ammount = ""
    var fastpayId = ""
    private fun setupView(view: View) {
        ammount = requireArguments().getString(KEY_NOMINAL, "")
        fastpayId = appSession.getSharedPreferenceString("FASTPAY_ID")!!
        view.tv_ammount.text = ammount
        Glide.with(view.iv_qris)
            .asBitmap()
            .load("https://mp.fastpay.co.id/qris/image_qris_dinamis_receiver?sc_id=$fastpayId&nominal=$ammount&bill_number=$ammount")
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    qrBitmap = resource
                    view.iv_qris.setImageBitmap(qrBitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })

        shareStruk = object : PermissionCallback {
            override fun onSuccess() {
                qrBitmap?.let {
                    Helper.getImageUriFromBitmap(
                        requireContext(), it, "qr-dinamis-$fastpayId-$ammount"
                    )
                }?.let { Helper.shareBitmapToApps(requireContext(), it) }
            }

            override fun onFailed() {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.reason_permission_write_external),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        downloadStruk = object : PermissionCallback {
            override fun onSuccess() {
                qrFile = qrBitmap?.let { it1 ->
                    FileUtils.bitmapToExternalFile(
                        requireContext(),
                        it1, "qr-dinamis-$fastpayId-$ammount.jpg"
                    )
                }
                qrFile?.let {
                    btn_download.isEnabled = false
                    Toast.makeText(
                        requireContext(),
                        "QR tersimpan di ${it.path}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } ?: run {
                    Toast.makeText(
                        requireContext(),
                        "Gagal menyimpan QR. Mohon ulangi lagi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                btn_download.isEnabled = true
            }

            override fun onFailed() {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.reason_permission_write_external),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupClickListeners(view: View) {
        view.btn_download.setOnClickListener {
            permissionUtil.checkWriteExternalPermission(downloadStruk)
        }
        view.btn_share.setOnClickListener {
            permissionUtil.checkWriteExternalPermission(shareStruk)
        }
    }
}