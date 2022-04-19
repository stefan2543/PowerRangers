package com.example.powerrangers.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_table")
data class Media(
    @PrimaryKey
    @ColumnInfo(name = "Title")
    val title: String,

    @ColumnInfo(name = "Date")
    var date: String,

    @ColumnInfo(name = "Network")
    var network: String
)