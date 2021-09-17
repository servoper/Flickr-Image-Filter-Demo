package net.servoper.flickrimagefilterdemo.repository

import android.graphics.Bitmap
import android.net.Uri
import net.servoper.flickrimagefilterdemo.util.BitmapFilterUntil
import android.content.ContentValues
import android.provider.MediaStore
import net.servoper.flickrimagefilterdemo.MyApplication
import java.io.OutputStream
import java.lang.Exception


class EditPhotoRepository {
    fun getFilteredImageDestination(
        bitmap: Bitmap,
        contrast: Float,
        saturation: Float
    ): Uri {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "title")
        MyApplication.applicationContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )?.let {
            var outstream: OutputStream? = null
            try {
                outstream = MyApplication.applicationContext().contentResolver.openOutputStream(it)
                BitmapFilterUntil.filter(bitmap, contrast, saturation)
                    .compress(Bitmap.CompressFormat.JPEG, 100, outstream)
                outstream?.flush()
            } finally {
                outstream?.close()
            }
            return it
        }

        throw Exception("Failed to create URI")
    }
}