package com.segura.imagesapp.data.paging

import androidx.paging.PagingSource
import com.segura.imagesapp.domain.useCase.GetImagesUseCase
import com.segura.imagesapp.domain.model.ImageItem

class ImagePagingSource(
    private val useCase: GetImagesUseCase
) : PagingSource<Int, ImageItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        return try {
            val nextPage = params.key ?: 1
            val response = useCase.execute(nextPage, 10)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}