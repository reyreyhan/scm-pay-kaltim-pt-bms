package com.bm.main.scm.feature.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bm.main.scm.R
import com.bm.main.scm.utils.AppSession
import kotlinx.android.synthetic.main.dialog_scm_scm.view.*


class PinConfirmDialog : DialogFragment() {
    companion object{
        const val TAG = "PinConfirmDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): PinConfirmDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = PinConfirmDialog()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var listener: PinConfirmDialogListener
    private val appSession = AppSession()

    interface PinConfirmDialogListener {
        fun onPinConfirmSuccess()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as PinConfirmDialogListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_scm_scm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
        setupView(view)
    }

    private fun setupView(view: View) {
        view.tv_title.text = requireArguments().getString(KEY_TITLE, "")
        view.tv_caption.text = requireArguments().getString(KEY_SUBTITLE, "")
    }

    private fun setupClickListeners(view: View) {
        view.btn_positive.setOnClickListener {
            if (listener != null) {
                if (!appSession.getSharedPreferenceString("PIN").isNullOrEmpty() &&
                    appSession.getSharedPreferenceString("PIN") == view.et_pin.text.toString()) {
                    listener.onPinConfirmSuccess()
                }
                Toast.makeText(view.context, "PIN Anda Salah!", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            dismiss()
        }
        view.btn_close.setOnClickListener {
            dismiss()
        }
    }
}