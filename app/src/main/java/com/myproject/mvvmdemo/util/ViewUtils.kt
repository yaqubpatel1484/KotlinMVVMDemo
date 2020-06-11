package com.myproject.mvvmdemo.util

import android.content.Context
import android.opengl.Visibility
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


fun Context.toast(message: String) {

    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}

fun ProgressBar.show() {
    visibility = View.VISIBLE;
}

fun ProgressBar.hide() {
    visibility = View.GONE;
}

fun View.snakebar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}