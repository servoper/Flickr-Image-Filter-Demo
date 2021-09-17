package net.servoper.flickrimagefilterdemo.ui.editphoto

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.servoper.flickrimagefilterdemo.repository.EditPhotoRepository
import net.servoper.flickrimagefilterdemo.ui.custom.Event

class EditPhotoViewModel : ViewModel() {
    private val mEditPhotoRepository = EditPhotoRepository()

    val sharingFileLiveData = MutableLiveData<Event<Uri>>()
    val errorLiveData = MutableLiveData<String>()

    fun prepareImageForSharing(
        bitmap: Bitmap,
        contrast: Float,
        saturation: Float
    ) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    mEditPhotoRepository.getFilteredImageDestination(
                        bitmap,
                        contrast,
                        saturation
                    )
                }
            }.onSuccess {
                sharingFileLiveData.postValue(Event(it))
            }.onFailure {
                errorLiveData.postValue(it.message)
            }
        }
    }
}