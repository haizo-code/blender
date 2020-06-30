package com.haizo.poc.util

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

fun Context.toast(message: String?) {
    Toast.makeText(this, message ?: "", Toast.LENGTH_SHORT).show()
}

fun Context.showAlert(title: String = "Alert", message: String? = "", callback: (() -> Unit)? = null) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message ?: "")
        .setPositiveButton("Close") { _, _ -> callback?.invoke() }
        .show()
}