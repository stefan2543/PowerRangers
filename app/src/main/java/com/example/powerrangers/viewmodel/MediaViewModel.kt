package com.example.powerrangers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.*
import com.example.powerrangers.data.Media
import com.example.powerrangers.data.MediaDao
import com.example.powerrangers.data.MediaRepository
import kotlinx.coroutines.launch

class MediaViewModel(
    // Pass dao here
    private val repository: MediaRepository?,
    private val mediaDao: MediaDao?
): ViewModel() {

    val allMedia: LiveData<List<Media>>? = repository?.allMedia?.asLiveData()

    fun insert(media: Media) = viewModelScope.launch {
        repository?.insert(media)
    }

    fun getMedia(id: Long) : LiveData<Media>? = mediaDao?.getMedia(id)?.asLiveData()

//    suspend fun pushCustomerData(columns:StringBuilder,values:StringBuilder) = withContext(
//        Dispatchers.IO){
//        val query = SimpleSQLiteQuery(
//            "INSERT INTO customer ($columns) values($values)",
//            arrayOf()
//        )
//        mediaDao.insertDataRawFormat(query)
//    }

}

class MediaViewModelFactory(private val repository: MediaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaViewModel(repository, null) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MediaViewModelFactory2(private val mediaDao: MediaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaViewModel(null, mediaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}