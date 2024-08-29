package com.sali.habitino.model.di

import com.sali.habitino.model.repo.AppTrackRepo
import com.sali.habitino.model.repo.AppTrackRepoImpl
import com.sali.habitino.model.repo.HabitStateRepo
import com.sali.habitino.model.repo.HabitStateRepoImpl
import com.sali.habitino.model.repo.CommonHabitRepo
import com.sali.habitino.model.repo.CommonHabitRepoImpl
import com.sali.habitino.model.repo.ScoreRepo
import com.sali.habitino.model.repo.ScoreRepoImpl
import com.sali.habitino.model.repo.SelfAddedHabitRepo
import com.sali.habitino.model.repo.SelfAddedHabitRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    @Singleton
    fun bindSelfAddedHabitRepo(selfAddedHabitRepoImpl: SelfAddedHabitRepoImpl): SelfAddedHabitRepo

    @Binds
    @Singleton
    fun bindHabitStateRepo(habitStateRepoImpl: HabitStateRepoImpl): HabitStateRepo

    @Binds
    @Singleton
    fun bindScoreRepo(scoreRepoImpl: ScoreRepoImpl): ScoreRepo

    @Binds
    @Singleton
    fun bindCommonHabitRepo(remoteHabitRepoImpl: CommonHabitRepoImpl): CommonHabitRepo

    @Binds
    @Singleton
    fun bindAppTrackRepo(appTrackRepoImpl: AppTrackRepoImpl): AppTrackRepo
}