package com.example.scopedstoragetest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scopedstoragetest.utils.IOUtils
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private var path: String = ""
    private lateinit var uri: Uri
    private lateinit var uriContentNameResolver: UriContentNameResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uriContentNameResolver = UriContentNameResolver(contentResolver)

        openGallery.setOnClickListener {
            openGallery()
        }

        addGrayScale.setOnClickListener {
            addFilter()
            IOUtils.saveBitmapToFile(image.gpuImage.bitmapWithFilterApplied, path)
        }
    }

    private fun addFilter() {
        image.filter = GPUImageGrayscaleFilter()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == Activity.RESULT_OK) {
            handleImage(data)
        }
    }

    private fun handleImage(data: Intent?) {
        uri = data?.data!!
        uri.let {
            val imageName = uriContentNameResolver.getFileName(uri)
            imagePath.text = imageName
            saveImage(it, imageName!!)
        }
    }

    private fun saveImage(uri: Uri, imageName: String) {
        val imagesFolder = File(cacheDir, "images")
        imagesFolder.mkdir()

        path = imagesFolder.absolutePath + PATH_SEPARATOR + imageName
        contentResolver.openInputStream(uri)?.toFile(path)

        image.setImage(File(path))
    }

    companion object {
        const val REQUEST_GALLERY_IMAGE = 1000
        const val PATH_SEPARATOR = "/"
    }
}

