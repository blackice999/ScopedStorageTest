package com.example.scopedstoragetest

import java.io.File
import java.io.InputStream

fun InputStream.toFile(path: String) {
    File(path).outputStream().use {
        this.copyTo(it)
    }
}