package com.bm.main.scm.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtils {

    fun showDialog(context: Context,
                   title: String = "",
                   msg: String = "",
                   cancelable: Boolean = true,
                   showOkButton: Boolean = true,
                   buttonOkLabel: String = "Ok",
                   buttonOkAction: () -> Unit = {},
                   showCancelButton: Boolean = true,
                   buttonCancelLabel: String = "Batal",
                   buttonCancelAction: () -> Unit = {}) {
        AlertDialog.Builder(context).setTitle(title).setMessage(msg).setCancelable(cancelable)
                .apply {
                    if (showOkButton) setPositiveButton(buttonOkLabel) { d, _ ->
                        d.dismiss()
                        buttonOkAction()
                    }
                    if (showCancelButton) setNegativeButton(buttonCancelLabel) { d, _ ->
                        d.dismiss()
                        buttonCancelAction()
                    }
                }.show()
    }
}