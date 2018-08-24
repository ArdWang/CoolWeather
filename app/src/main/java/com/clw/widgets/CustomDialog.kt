package com.clw.widgets

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.clw.R

/**
 * 自定义对话框
 */
class CustomDialog constructor(context: Context, themeResId: Int) : Dialog(context,themeResId){

    class Builder(private val context: Context) {
        private var positiveButtonClickListener: DialogInterface.OnClickListener? = null

        private var negativeButtonClickListener: DialogInterface.OnClickListener? = null

        private var positiveButtonText: String? = null

        private var negativeButtonText: String? = null

        private var mTips: TextView? = null

        private var mContent: TextView? = null

        private var tips: String? = null

        private var content: String? = null

        fun getTips(): String? {
            return tips
        }

        fun setTips(tips: String): Builder {
            this.tips = tips
            return this
        }

        fun getContent(): String? {
            return content
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        fun setPositiveButton(positiveButtonText: Int,
                              listener: DialogInterface.OnClickListener): Builder {
            this.positiveButtonText = context
                    .getText(positiveButtonText) as String
            this.positiveButtonClickListener = listener
            return this
        }


        fun setnegativeButton(negativeButtonText: Int,
                              listener: DialogInterface.OnClickListener): Builder {
            this.negativeButtonText = context.getText(negativeButtonText) as String
            this.negativeButtonClickListener = listener
            return this
        }


        fun create(): CustomDialog {
            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            // instantiate the dialog with the custom Theme
            val dialog = CustomDialog(context, R.style.Dialog)
            val layout = inflater.inflate(R.layout.dialog_custom, null)
            //设置布局
            dialog.addContentView(layout, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            mTips = layout.findViewById(R.id.mTips)

            mContent = layout.findViewById(R.id.mContent)

            if (!getTips()!!.isEmpty()) {
                mTips!!.text = getTips()
            }

            if (!getContent()!!.isEmpty()) {
                mContent!!.text = getContent()
            }

            // set the confirm button
            if (positiveButtonText != null) {
                (layout.findViewById<View>(R.id.positiveButton) as Button).text = positiveButtonText
                if (positiveButtonClickListener != null) {
                    layout.findViewById<View>(R.id.positiveButton)
                            .setOnClickListener {
                                positiveButtonClickListener!!.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE)
                            }
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById<View>(R.id.positiveButton).visibility = View.GONE
            }
            // set the cancel button
            if (negativeButtonText != null) {

                (layout.findViewById<View>(R.id.negativeButton) as Button).text = negativeButtonText

                if (negativeButtonClickListener != null) {
                    layout.findViewById<View>(R.id.negativeButton)
                            .setOnClickListener {
                                negativeButtonClickListener!!.onClick(dialog,
                                        DialogInterface.BUTTON_NEGATIVE)
                            }
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById<View>(R.id.negativeButton).visibility = View.GONE
            }
            dialog.setContentView(layout)
            return dialog
        }
    }
}