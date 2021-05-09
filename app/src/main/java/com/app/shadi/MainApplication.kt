package com.app.shadi

import android.app.Application
import com.app.shadi.database.AppDatabase

class MainApplication : Application() {

    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.getInstance(this)
    }
}