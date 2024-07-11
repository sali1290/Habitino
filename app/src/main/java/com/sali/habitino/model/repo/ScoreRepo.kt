package com.sali.habitino.model.repo

interface ScoreRepo {

    fun saveScore(score: Int)

    fun getScore(): Int

}