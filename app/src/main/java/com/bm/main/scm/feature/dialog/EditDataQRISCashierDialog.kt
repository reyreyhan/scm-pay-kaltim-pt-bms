package com.bm.main.scm.feature.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.bm.main.scm.R
import com.bm.main.scm.utils.AppSession
import kotlinx.android.synthetic.main.dialog_scm_cashier_edit.view.*


class EditDataQRISCashierDialog : DialogFragment() {
    companion object {
        const val TAG = "EditDataQRISCashierDialog"

        private const val KEY_NAME = "KEY_NAME"
        private const val KEY_PHONE = "KEY_PHONE"
        private const val KEY_ID = "KEY_ID"

        fun newInstance(name: String, phone: String, id:String): EditDataQRISCashierDialog {
            val args = Bundle()
            args.putString(KEY_NAME, name)
            args.putString(KEY_PHONE, phone)
            args.putString(KEY_ID, id)
            val fragment = EditDataQRISCashierDialog()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var listener: EditDataQRISCashierDialogListener
    private val appSession = AppSession()

    interface EditDataQRISCashierDialogListener {
        fun onEditButtonSubmit(name: String, phone: String, id:String)
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
        listener = context as EditDataQRISCashierDialogListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_scm_cashier_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
        setupView(view)
    }

    private fun setupView(view: View) {
        view.et_cashier_name.setText(requireArguments().getString(KEY_NAME, ""))
        view.et_cashier_num_hp.setText(requireArguments().getString(KEY_PHONE, ""))
    }

    private fun setupClickListeners(view: View) {
        view.btn_positive.setOnClickListener {
            if (listener != null) {
                listener.onEditButtonSubmit(
                    view.et_cashier_name.text.toString(),
                    view.et_cashier_num_hp.text.toString(),
                    requireArguments().getString(KEY_ID, "0")
                )
                dismiss()
            }
            dismiss()
        }
        view.btn_close.setOnClickListener {
            dismiss()
        }
    }
}