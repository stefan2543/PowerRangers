package com.example.powerrangers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Media::class], version = 1)
abstract class MediaRoomDatabase : RoomDatabase() {

    abstract fun mediaDao(): MediaDao

    companion object {
        @Volatile
        private var INSTANCE: MediaRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MediaRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediaRoomDatabase::class.java,
                    "media_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(MediaDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class MediaDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.mediaDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(mediaDao: MediaDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mediaDao.deleteAll()

            val media = Media(0,"check","check", "check")
            mediaDao.insert(media)

            BufferedReader(FileReader("tv.csv")).use { br ->
                var line: String
                while (br.readLine().also { line = it } != null) {
                    val values: Array<String> =
                        line.split(",").toTypedArray()
                    val media = Media(0,values[0],values[1],values[2])
                    mediaDao.insert(media)
                }
            }

            BufferedReader(FileReader("movies.csv")).use { br ->
                var line: String
                while (br.readLine().also { line = it } != null) {
                    val values: Array<String> =
                        line.split(",").toTypedArray()
                    val media = Media(0, values[0],values[1],"N/A")
                    mediaDao.insert(media)
                }
            }



        }
    }
}