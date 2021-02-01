package com.segura.imagesapp.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.segura.imagesapp.domain.model.ImageItem
import kotlinx.coroutines.flow.Flow


@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPhotoToFavorite(imageItem: ImageItem)

    @Query("SELECT * FROM FavoritePhotos ")
    fun getFavoritePhotos(): LiveData<List<ImageItem>>

    @Query("SELECT * FROM FavoritePhotos ")
    fun getFavoriteImages(): Flow<List<ImageItem>>

    @Query("DELETE FROM FavoritePhotos WHERE id = :photoId")
    suspend fun deleteFavorite(photoId: String)


    @Query("SELECT * FROM FavoritePhotos WHERE user like '%' || :userName || '%'")
    suspend fun searchImages(userName: String): List<ImageItem>
}