package com.example.halanchallenge.di

import com.example.halanchallenge.repository.AppRepository
import com.example.halanchallenge.repository.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideAppRepo(impl: AppRepositoryImpl): AppRepository
}