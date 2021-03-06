package powerrangers.movietracker.watchnext.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class MediaRepository(private val mediaDao: MediaDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allMedia: Flow<List<Media>> = mediaDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(media: Media) {
        mediaDao.insert(media)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getMedia(id: Long) : Flow<Media> {
        return mediaDao.getMedia(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(media: Media) {
        return mediaDao.update(media)
    }
}