package com.putragandad.regres.core.data.source.remote.paging

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.putragandad.regres.core.data.source.remote.RemoteDataSource
import com.putragandad.regres.core.data.source.remote.response.ListUserData
import javax.inject.Inject

private const val INITIAL_PAGE = 1

class ReqresPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, ListUserData>() {
    override fun getRefreshKey(state: PagingState<Int, ListUserData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListUserData> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = remoteDataSource.getListUser(page, params.loadSize)
            LoadResult.Page(
                data = response.data ?: emptyList(), // null check if data list is empty
                prevKey = if(page == INITIAL_PAGE) null else page - 1,
                nextKey = if (response.data.isNullOrEmpty()) null else page + 1 // if data is empty on nex key, go to the next page
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

}