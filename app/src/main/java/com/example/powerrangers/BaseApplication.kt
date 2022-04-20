package com.example.powerrangers

import android.app.Application
import com.example.powerrangers.data.MediaRepository
import com.example.powerrangers.data.MediaRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob

class BaseApplication : Application() {

    // TODO: provide a ForageDatabase value by lazy here
    val applicationScope = CoroutineScope(SupervisorJob())
    val database: MediaRoomDatabase by lazy { MediaRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MediaRepository(database.mediaDao())}
}

