package com.example.appfinal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PostEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
