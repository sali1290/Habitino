package com.sali.habitino.model.utile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


class DrawableConverter {
    @TypeConverter
    fun drawableString(drawable: Drawable): String? {
        val byteArrayOutPutStream = ByteArrayOutputStream()
        drawable.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutPutStream)
        val byteArray = byteArrayOutPutStream.toByteArray()
        val temp = Base64.encodeToString(byteArray, Base64.DEFAULT)
        return temp
    }

    @TypeConverter
    fun StringToBitMap(encodedString: String): Bitmap? {
        try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            return bitmap
        } catch (exception: Exception) {
            exception.message
            return null
        }
    }
}