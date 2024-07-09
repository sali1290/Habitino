package com.sali.habitino.model.di

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

}