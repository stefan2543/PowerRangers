package com.example.powerrangers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.*
import com.example.powerrangers.data.Media
import com.example.powerrangers.data.MediaRepository
import kotlinx.coroutines.launch

class MediaViewModel(
    // Pass dao here
    private val repository: MediaRepository,

): ViewModel() {

    val allMedia: LiveData<List<Media>> = repository.allMedia.asLiveData()

    fun insert(media: Media) = viewModelScope.launch {
        repository.insert(media)
    }

    fun getMedia(id: Long) : LiveData<Media> = repository.getMedia(id).asLiveData()

    fun updateMedia(
        mediaId: Long,
        mediaName: String,
        mediaRelease: String,
        mediaPlatform: String,
        mediaFavorite: Boolean,
        mediaImage: String,
        mediaDescription: String
    ) {
        val updatedMedia = getUpdatedMediaEntry(mediaId, mediaName, mediaRelease, mediaPlatform, mediaFavorite, mediaImage, mediaDescription)
        updateMedia(updatedMedia)
    }

    fun updateMedia(media: Media) {
        viewModelScope.launch {
            repository.update(media)
        }
    }

    private fun getUpdatedMediaEntry(
        mediaId: Long,
        mediaName: String,
        mediaRelease: String,
        mediaPlatform: String,
        mediaFavorite: Boolean,
        mediaImage: String,
        mediaDescription: String
    ): Media {
        return Media(
            id = mediaId,
            name = mediaName,
            date = mediaRelease,
            network = mediaPlatform,
            favorite = mediaFavorite,
            image = mediaImage,
            description = mediaDescription
        )
    }

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
            return MediaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
