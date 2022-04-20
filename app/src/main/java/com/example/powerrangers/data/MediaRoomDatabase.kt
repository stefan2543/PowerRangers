package com.example.powerrangers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*
import javax.xml.parsers.DocumentBuilderFactory
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.*
import java.nio.channels.AsynchronousFileChannel.open


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
            val inputStream: InputStream = context.assets.open("Movies.csv")
            val inputStream2: InputStream = context.assets.open("TV.csv")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediaRoomDatabase::class.java,
                    "media_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(MediaDatabaseCallback(scope, inputStream, inputStream2))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class MediaDatabaseCallback(
            private val scope: CoroutineScope,
            private val inputStream: InputStream,
            private val inputStream2: InputStream
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
                        populateDatabase(database.mediaDao(), inputStream, inputStream2)
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         */
        suspend fun populateDatabase(mediaDao: MediaDao, inputStream: InputStream, inputStream2: InputStream) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mediaDao.deleteAll()

            inputStream.bufferedReader().use { br ->
                var line = br.readLine()
                while (line != null) {
                    val values: Array<String> =
                        line.split(",").toTypedArray()
                    val media = Media(0, values[0], values[1], "Theaters", false)
                    mediaDao.insert(media)
                    line = br.readLine()
                }

            }
            inputStream2.bufferedReader().use { br ->
                var line = br.readLine()
                while (line != null) {
                    val values: Array<String> =
                        line.split(",").toTypedArray()
                    val media = Media(0, values[0], values[1], values[2], false)
                    mediaDao.insert(media)
                    line = br.readLine()
                }
            }








        }
    }
}