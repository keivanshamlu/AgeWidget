package com.shamlou.agewidget.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.shamlou.agewidget.R
import kotlinx.android.synthetic.main.custom_snackbar.*
import kotlinx.android.synthetic.main.custom_snackbar.view.*

fun View.closeSoftKeyboard() {
    try {
        val imm: InputMethodManager? = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

@SuppressLint("RestrictedApi")
fun Activity.showSnackBarTop(snackBarText: String, timeLength: Int, view: View? = null, cancelText: String? = null) {
    this.let {
        Snackbar.make(
            view ?: it.findViewById(android.R.id.content),
            snackBarText,
            timeLength
        )
            .apply {

                val snackBarView = getView() as Snackbar.SnackbarLayout

                snackBarView.background = ContextCompat.getDrawable(context, R.drawable.snackbar_container)
                (snackBarView.findViewById<View>(R.id.snackbar_text) as TextView).visibility = View.INVISIBLE
                val snackView: View =
                    View.inflate(context, R.layout.custom_snackbar, custom_snackbar_root)
                snackView.text_view_description.text = snackBarText

                cancelText?.let {

                    snackView.button_dismiss_snackbar.text = it
                    snackView.button_dismiss_snackbar.setOnClickListener {

                        dismiss()
                    }
                } ?: kotlin.run {

                    snackView.button_dismiss_snackbar.visibility = View.GONE
                }

                snackBarView.setPadding(0, 0, 0, 0)
                snackBarView.addView(snackView, 0)
                snackBarView.visibility = View.INVISIBLE
            }.show()
    }
}