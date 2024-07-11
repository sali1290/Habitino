package com.sali.habitino.model.repo

import android.content.Context
import com.sali.habitino.model.utils.Keys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ScoreRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ScoreRepo {
    override fun saveScore(score: Int) {
        context.getSharedPreferences(Keys.HABITINO_SHARED, Context.MODE_PRIVATE)
            .edit().putInt(Keys.SCORE, score).apply()
    }

    override fun getScore(): Int {
        return context.getSharedPreferences(Keys.HABITINO_SHARED, Context.MODE_PRIVATE)
            .getInt(Keys.SCORE, 0)
    }
}