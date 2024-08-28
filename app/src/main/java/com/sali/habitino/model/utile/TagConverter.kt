package com.sali.habitino.model.utile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sali.habitino.model.dto.Tags

class TagConverter {
    @TypeConverter
    fun convertTagsToJSONString(tags: Tags): String = Gson().toJson(tags)

    @TypeConverter
    fun convertJSONStringToInvoiceList(jsonString: String): Tags =
        Gson().fromJson(jsonString, Tags::class.java)
}