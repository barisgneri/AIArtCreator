package com.barisguneri.aiartcreator.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import com.barisguneri.aiartcreator.db.RoomEntity
import com.barisguneri.aiartcreator.data.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.barisguneri.aiartcreator.model.Result
import com.barisguneri.aiartcreator.data.local.ImageDao

class Repository(private val apiService: ApiService, private val imageDAO: ImageDao) {

    val allImages : Flow<List<RoomEntity>> = imageDAO.getAllImageList()

    @WorkerThread
    suspend fun insertImage(roomEntity: RoomEntity) {
        imageDAO.insertImage(roomEntity)
    }

    @WorkerThread
    suspend fun deleteImage(byteArray: ByteArray){
        imageDAO.deleteImage(byteArray)
    }

    suspend fun getTextToImage(text: String, category: String): Flow<Result<Bitmap>> = flow<Result<Bitmap>>{
        try {
            val response = apiService.getTextToImage(
                apiKey = "API-KEY-HERE",
                prompt = "$text, $category",
                guidance = 7,
                steps = 30,
                upscale = 1,
                width = 463,
                height = 686,
                sampler = "euler_a",
            )

            if (response.isSuccessful) {
                val byteArray = response.body()?.byteStream()?.readBytes()
                val bitmap = byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }
                emit(Result.Success(bitmap))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                emit(Result.Error(errorMessage, data = null))
            }
        } catch (e: Exception) {
            // emit(Result.Error("Network error: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}