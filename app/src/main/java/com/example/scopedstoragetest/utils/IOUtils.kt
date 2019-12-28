package com.example.scopedstoragetest.utils

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class IOUtils {
    companion object {
        fun saveBitmapToFile(bitmap: Bitmap, path: String) {
            try {
                val stream: OutputStream = FileOutputStream(File(path))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}