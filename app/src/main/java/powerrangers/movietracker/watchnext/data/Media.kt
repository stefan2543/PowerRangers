package powerrangers.movietracker.watchnext.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */

@Entity(tableName = "media_table")
data class Media(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "network")
    var network: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "description")
    var description: String

)