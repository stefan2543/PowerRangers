package com.example.powerrangers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.powerrangers.data.Media
import com.example.powerrangers.data.MediaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaViewModel(
    // Pass dao here
    private val mediaDao: MediaDao
): ViewModel() {

    val allMedia: LiveData<List<Media>> = mediaDao.getAlphabetizedWords().asLiveData()

    fun getMedia(id: Long): LiveData<Media> {
        return mediaDao.getMedia(id).asLiveData()
    }

    suspend fun pushCustomerData(columns:StringBuilder,values:StringBuilder) = withContext(
        Dispatchers.IO){
        val query = SimpleSQLiteQuery(
            "INSERT INTO customer ($columns) values($values)",
            arrayOf()
        )
        mediaDao.insertDataRawFormat(query)
    }

}

class MediaViewModelFactory(private val mediaDao: MediaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaViewModel(mediaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}