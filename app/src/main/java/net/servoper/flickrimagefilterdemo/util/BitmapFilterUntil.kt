package net.servoper.flickrimagefilterdemo.util

import android.graphics.*
import android.graphics.Bitmap


class BitmapFilterUntil {
    companion object {
        /**
         *
         * @param bitmap input bitmap
         * @param contrast - 0..2 default 1
         * @param saturation - o..2 default 1
         * @return new bitmap
         */
        fun filter(
            bitmap: Bitmap,
            contrast: Float,
            saturation: Float
        ): Bitmap {
            val colorMatrix = ColorMatrix()

            colorMatrix.setSaturation(saturation)

            val m: FloatArray = colorMatrix.array

            colorMatrix.set(
                floatArrayOf(
                    m[0] * contrast, m[1] * contrast, m[2] * contrast, m[3] * contrast,
                    m[4] * contrast, m[5] * contrast, m[6] * contrast, m[7] * contrast,
                    m[8] * contrast, m[9] * contrast, m[10] * contrast, m[11] * contrast,
                    m[12] * contrast, m[13] * contrast, m[14] * contrast, m[15], m[16], m[17],
                    m[18], m[19]
                )
            )

            val result = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)

            val canvas = Canvas(result)

            val paint = Paint()
            paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
            canvas.drawBitmap(bitmap, 0F, 0F, paint)
/*
            val pixels = IntArray(bitmap.width * bitmap.height)
            result.getPixels(pixels, 0, 0, 0, 0, bitmap.width, bitmap.height)

            pixels.forEachIndexed { color, index ->
                val hsv = FloatArray(3)
                Color.colorToHSV(color, hsv)
                hsv[1] += saturation
                pixels[index] = Color.HSVToColor(Color.alpha(color), hsv)
            }
            */

            return result
        }
    }
}