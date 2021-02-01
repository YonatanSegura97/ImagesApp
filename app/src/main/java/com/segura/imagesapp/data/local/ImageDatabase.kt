package com.segura.imagesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.segura.imagesapp.data.dataSource.ImagesDao
import com.segura.imagesapp.domain.model.ImageItem


@Database(entities = [ImageItem::class], version = 4, exportSchema = true)
@TypeConverters(RoomConverters::class)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao

}