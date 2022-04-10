package com.example.powerrangers.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Room Magic is in this file, where you map a method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
interface MediaDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM media_table ORDER BY name ASC")
    fun getAlphabetizedWords(): Flow<List<Media>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(media: Media)

    @Query("DELETE FROM media_table")
    suspend fun deleteAll()
}