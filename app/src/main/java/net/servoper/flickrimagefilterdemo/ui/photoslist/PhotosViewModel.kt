package net.servoper.flickrimagefilterdemo.ui.photoslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.servoper.flickrimagefilterdemo.data.model.Photo
import net.servoper.flickrimagefilterdemo.repository.PhotosRepository

class PhotosViewModel : ViewModel() {

    private val mRepository = PhotosRepository()

    val photosLiveData = MutableLiveData<ArrayList<Photo>>()
    val errorLiveData = MutableLiveData<String>()

    fun requestPhotos(page: Int? = null) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    mRepository.getPhotosList(page)
                }
            }.onSuccess {
                photosLiveData.postValue(it?.data?.photosList)
            }.onFailure {
                errorLiveData.postValue(it.message)
            }
        }
    }
}