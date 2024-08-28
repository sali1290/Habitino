package com.sali.habitino.model.utile

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import javax.inject.Inject

class SharedPrefUtils @Inject constructor(@ApplicationContext private val context: Context) {

    fun saveHabitsFetchDate() {
        context.getSharedPreferences(Keys.HABITINO_SHARED, Context.MODE_PRIVATE).edit()
            .putInt(Keys.FETCH_DATE, LocalDateTime.now().dayOfYear).apply()
    }

    fun getHabitsFetchDate(): Int {
        return context.getSharedPreferences(Keys.HABITINO_SHARED, Context.MODE_PRIVATE)
            .getInt(Keys.FETCH_DATE, -1)
    }

    fun saveScore(score: Int) {
        context.getSharedPreferences(Keys.HABITINO_SHARED, Context.MODE_PRIVATE)
            .edit().putInt(Keys.SCORE, score).apply()
    }

    fun getScore(): Int {
        return context.getSharedPreferences(Keys.HABITINO_SHARED, Context.MODE_PRIVATE)
            .getInt(Keys.SCORE, 0)
    }

}