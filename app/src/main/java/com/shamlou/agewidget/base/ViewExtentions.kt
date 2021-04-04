package com.shamlou.agewidget.base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

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