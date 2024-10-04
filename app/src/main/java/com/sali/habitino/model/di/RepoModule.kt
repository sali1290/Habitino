package com.sali.habitino.model.di

import com.sali.habitino.model.repo.AppsRepo
import com.sali.habitino.model.repo.AppsRepoImpl
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
    fun bindAppTrackRepo(appTrackRepoImpl: AppsRepoImpl): AppsRepo
}