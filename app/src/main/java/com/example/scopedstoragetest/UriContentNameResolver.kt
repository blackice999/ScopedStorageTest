package com.example.scopedstoragetest

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns

class UriContentNameResolver(private val contentResolver: ContentResolver) {

    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme.equals("content")) {

            val selection = MediaStore.Images.Media.DISPLAY_NAME
            val cursor: Cursor? = contentResolver.query(uri, null, selection, null, null)
            cursor.use {
                if (it != null && it.moveToFirst()) {
                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }

        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                if (cut != null) {
                    result = result?.substring(cut + 1)
                }
            }
        }

        return result
    }
}