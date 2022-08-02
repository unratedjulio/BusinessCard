package com.punkmic.businesscard.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.punkmic.businesscard.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object Image {
    fun share(context: Context, view: View) {
        val bitmap: Bitmap? = getScreenShotFromView(view)

        bitmap?.let {
            saveMediaToStorage(context, bitmap)
        }
    }

    private fun saveMediaToStorage(context: Context, bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            with(context.contentResolver) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val mediaUri: Uri? =
                    insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = mediaUri?.let {
                    shareIntent(context, mediaUri)
                    openOutputStream(it)
                }
            }
        } else {
            val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            shareIntent(context, Uri.fromFile(image))
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context, "Image saved.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareIntent(context: Context, mediaUri: Uri) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, mediaUri)
            type = "image/jpeg"
        }
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.resources.getString(R.string.label_share)
            )
        )
    }

    private fun getScreenShotFromView(card: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            screenshot = Bitmap.createBitmap(
                card.measuredWidth,
                card.measuredHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(screenshot)
            card.draw(canvas)
        } catch (e: Exception) {
            Log.e("Error =>", "Error to create bitmap", e)
        }
        return screenshot
    }
}