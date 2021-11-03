package com.tentwenty.assignment.utils

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.tentwenty.assignment.R
import kotlinx.android.synthetic.main.dialog_loading.*
import javax.inject.Inject

class DialogUtilImpl @Inject constructor(private val context: FragmentActivity): DialogUtil{

    private var dialog: Dialog? = null
    override fun showDialog() {
        if (!isDialogShowing()) {
            dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog!!.setContentView(R.layout.dialog_loading)
            dialog!!.getWindow()?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        R.color.black_transparent
                    )
                )
            )
            dialog!!.setCancelable(false)
            dialog!!.show()
        }
    }

    override fun hideDialog() {
        dialog?.dismiss()
    }

    //*************************Dialogs********************************//
    private fun isDialogShowing(): Boolean {
//        var dialog: Dialog? = null
        return dialog?.isShowing ?: false
    }

}