package net.servoper.flickrimagefilterdemo.util

import android.R.attr
import android.graphics.*
import android.graphics.Bitmap

import android.R.attr.src


class BitmapFilterUntil {
    companion object {
        /**
         *
         * @param bitmap input bitmap
         * @param contrast 0..10 1 is default
         * @param brightness -255..255 0 is default
         * @param saturation -0...1
         * @return new bitmap
         */
        suspend fun filter(
            bitmap: Bitmap,
            contrast: Float,
            brightness: Float,
            saturation: Float
        ): Bitmap {
            val cm = ColorMatrix(
                floatArrayOf(
                    contrast, 0f, 0f, 0f, brightness, 0f, contrast, 0f, 0f, brightness,
                    0f, 0f, contrast, 0f, brightness, 0f, 0f, 0f, 1f, 0f
                )
            )

            val result = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
                .copy(Bitmap.Config.ARGB_8888, true)

            val canvas = Canvas(result)

            val paint = Paint()
            paint.colorFilter = ColorMatrixColorFilter(cm)
            canvas.drawBitmap(bitmap, 0F, 0F, paint)

            val pixels = IntArray(bitmap.width * bitmap.height)
            result.getPixels(pixels, 0, 0, 0, 0, bitmap.width, bitmap.height)

            pixels.forEachIndexed { color, index ->
                val hsv = FloatArray(3)
                Color.colorToHSV(color, hsv)
                hsv[1] += saturation
                pixels[index] = Color.HSVToColor(Color.alpha(color), hsv)
            }

            return result
        }
    }
}