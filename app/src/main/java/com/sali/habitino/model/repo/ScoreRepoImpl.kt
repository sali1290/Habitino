package com.sali.habitino.model.repo

import com.sali.habitino.model.utile.SharedPrefUtils
import javax.inject.Inject

class ScoreRepoImpl @Inject constructor(
    private val sharedPrefUtils: SharedPrefUtils
) : ScoreRepo {
    override fun saveScore(score: Int) {
        sharedPrefUtils.saveScore(score)
    }

    override fun getScore(): Int {
        return sharedPrefUtils.getScore()
    }
}