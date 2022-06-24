package com.app.practical.utils.networkutils


import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ProgressBar
import com.app.practical.R


import java.util.*

class AlertUtils {
    internal var callback: AlertUtilsListener? = null

    interface AlertUtilsListener

    companion object {
        var dialog: Dialog? = null
        private lateinit var rotateLoading: ProgressBar

        private val max = 28
        private val min = 0
        fun showCustomProgressDialog(con: Context) {
            // create a Dialog component

            //  DebugLog.e("this is called from home ");

            try {
                val random = Random()
                random.nextInt(max - min)
                dialog = Dialog(con)
                dialog!!.setCancelable(false)
                // this line removes title
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                //tell the Dialog to use the dialog.xml as it's layout description
                dialog!!.setContentView(R.layout.custom_loader)
                rotateLoading = dialog!!.findViewById(R.id.rotateloading)

               /* rotateLoading.setIndicator(Common.INDICATORS[18])*/
                dialog!!.show()
                //rotateLoading.show()
            } catch (e: Exception) {
                e.printStackTrace()
                dismissDialog()
            }

        }// end of showCustomProgressDialog

        fun dismissDialog() {
            try {
                if (dialog != null && dialog!!.isShowing) {
                    dialog!!.dismiss()
                   // rotateLoading.hide()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}
