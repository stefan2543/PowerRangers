package com.example.powerrangers

import android.app.Application
import com.example.powerrangers.data.MediaRoomDatabase
import kotlinx.coroutines.GlobalScope

class BaseApplication : Application() {

    // TODO: provide a ForageDatabase value by lazy here
    val database: MediaRoomDatabase by lazy { MediaRoomDatabase.getDatabase(this, GlobalScope) }
}

