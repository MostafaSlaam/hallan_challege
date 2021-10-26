package com.example.halanchallenge.util

import dagger.hilt.android.HiltAndroidApp

import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.multidex.MultiDex
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }


}