package com.example.enpit_p31.hapverk

import android.app.Application
import io.realm.Realm

class MySchedulerApplicationAsa : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}