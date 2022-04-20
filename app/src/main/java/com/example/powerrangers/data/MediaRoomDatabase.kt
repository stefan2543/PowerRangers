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
import java.io.File
import java.io.FileReader
import java.io.InputStream
import java.util.*


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
         */
        suspend fun populateDatabase(mediaDao: MediaDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mediaDao.deleteAll()

            var media = Media(0,"notcheck","25", "check")
            mediaDao.insert(media)
            media = Media(2,"2heck","45", "check")
            mediaDao.insert(media)

            var check = 3

            val file = File("C:\\Users\\rvark\\Downloads\\movies.txt")

          val read = CSVReader(FileReader(file))
            do{
                val values = read.readNext()
                if(values != null) {
                    media = Media(check++.toLong(), values[0], values[1], values[2])
                    mediaDao.insert(media)
                }
            }while(values != null)


/*
            BufferedReader(FileReader(file)).use { br ->
                var line: String
                while (br.readLine().also { line = it } != null) {
                    val values: Array<String> =
                        line.split(",").toTypedArray()
                    val media = Media(check++.toLong(), values[0],values[1],"N/A")
                    mediaDao.insert(media)
                }


            }

 */



        }
    }
}