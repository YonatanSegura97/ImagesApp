package com.segura.imagesapp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.segura.imagesapp.model.ImageItem


@Database(entities = [ImageItem::class], version = 4, exportSchema = true)
@TypeConverters(RoomConverters::class)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao

}