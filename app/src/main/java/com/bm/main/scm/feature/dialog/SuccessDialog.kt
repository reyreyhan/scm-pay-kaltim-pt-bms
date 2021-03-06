package com.bm.main.scm.feature.dialog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import com.bm.main.scm.R
import kotlinx.android.synthetic.main.dialog_scm_success.*
import kotlinx.android.synthetic.main.dialog_scm_success.view.*


class SuccessDialog : DialogFragment() {
    companion object{
        const val TAG = "SuccessDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): SuccessDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = SuccessDialog()
            fragment.arguments = args
            return fragment
        }
    }

    public lateinit var listener: SuccessDialogListener

    interface SuccessDialogListener {
        fun onPositiveButtonDialog()
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
        listener = context as SuccessDialogListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_scm_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
        setupView(view)
    }

    private fun setupView(view: View) {
        tv_title.text = requireArguments().getString(KEY_TITLE, "")
        tv_caption.text = requireArguments().getString(KEY_SUBTITLE, "")
        val iconAnimation: Animation = AnimationUtils.loadAnimation(
            view.context,
            R.anim.scale_check_icon_success
        )
        Handler().postDelayed({
            iv_check_circle_success.startAnimation(iconAnimation)
        }, 250)
    }

    private fun setupClickListeners(view: View) {
        view.btn_positive.setOnClickListener {
            if (listener != null) {
                listener.onPositiveButtonDialog()
            }
            dismiss()
        }
    }
}