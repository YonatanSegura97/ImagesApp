package com.segura.imagesapp.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.segura.imagesapp.model.ImageItem


@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPhotoToFavorite(imageItem: ImageItem)

    @Query("SELECT * FROM FavoritePhotos ")
    fun getFavoritePhotos(): LiveData<List<ImageItem>>

    @Query("DELETE FROM FavoritePhotos WHERE id = :photoId")
    suspend fun deleteFavorite(photoId: String)
}