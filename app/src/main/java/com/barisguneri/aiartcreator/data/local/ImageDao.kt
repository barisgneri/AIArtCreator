package com.barisguneri.aiartcreator.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barisguneri.aiartcreator.db.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Query("SELECT * FROM image_table")
    fun getAllImageList(): Flow<List<RoomEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(userEntity: RoomEntity)

    @Query("DELETE FROM image_table WHERE image = :image")
    suspend fun deleteImage(image: ByteArray): Int

}