package com.example.halanchallenge.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ContextModule {
    @Singleton
    @Binds
    abstract fun provideContext(application: Application): Context

}