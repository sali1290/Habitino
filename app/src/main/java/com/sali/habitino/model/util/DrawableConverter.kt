package com.sali.habitino.model.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class DrawableConverter {

    @TypeConverter
    fun drawableToByteArray(drawable: Drawable): ByteArray {
        val bitmap = drawable.toBitmap()
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.PNG,
            100, outputStream
        )
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun byteArrayToDrawable(
        byteArray:
        ByteArray
    ): Drawable {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return BitmapDrawable(bitmap)
    }
}