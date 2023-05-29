package com.barisguneri.aiartcreator.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barisguneri.aiartcreator.data.local.ImageDao

@Database(entities = [RoomEntity::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}