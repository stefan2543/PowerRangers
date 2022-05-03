package powerrangers.movietracker.watchnext

import android.app.Application
import powerrangers.movietracker.watchnext.data.MediaRepository
import powerrangers.movietracker.watchnext.data.MediaRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BaseApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database: MediaRoomDatabase by lazy { MediaRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MediaRepository(database.mediaDao()) }
}

