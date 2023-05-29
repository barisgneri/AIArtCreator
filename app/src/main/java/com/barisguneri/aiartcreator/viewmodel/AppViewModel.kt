package com.barisguneri.aiartcreator.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.barisguneri.aiartcreator.repository.Repository
import com.barisguneri.aiartcreator.db.RoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import com.barisguneri.aiartcreator.model.Result

class AppViewModel(private val repository: Repository) : ViewModel() {

    val text2imageState = MutableStateFlow<Result<Bitmap>>(Result.Idle("asdas"))

    suspend fun getTextToImage(text: String, category: String) {
        repository.getTextToImage(text, category)
            .collectLatest {
                text2imageState.emit(it)
            }
    }

    val allImageList: Flow<List<RoomEntity>> = repository.allImages

    suspend fun insertImage(roomEntity: RoomEntity) {
        repository.insertImage(roomEntity)
    }

    suspend fun deleteImage(byteArray: ByteArray){
        repository.deleteImage(byteArray)
    }

}
