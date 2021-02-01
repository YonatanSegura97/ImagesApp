package com.segura.imagesapp.data.paging

import androidx.paging.PagingSource
import com.segura.imagesapp.domain.useCase.SearchImagesUseCase
import com.segura.imagesapp.domain.model.ImageItem
import com.segura.imagesapp.utils.ConstantsUtils

class SearchImagePagingSource(
    private val useCase: SearchImagesUseCase,
    private val query: String

) : PagingSource<Int, ImageItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        return try {
            val nextPage = params.key ?: 1
            val response = useCase.execute(query, nextPage, ConstantsUtils.ITEMS_BY_PAGE)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}