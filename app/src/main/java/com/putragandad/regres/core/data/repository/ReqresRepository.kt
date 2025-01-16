package com.putragandad.regres.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.putragandad.regres.core.data.source.remote.RemoteDataSource
import com.putragandad.regres.core.data.source.remote.paging.ReqresPagingSource
import com.putragandad.regres.core.data.source.remote.response.ListUserData
import com.putragandad.regres.core.data.source.remote.response.ListUserResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReqresRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getListUser() : Flow<PagingData<ListUserData>> = Pager(
        pagingSourceFactory = { ReqresPagingSource(remoteDataSource) },
        config = PagingConfig(
            pageSize = 3 // per_page size set to 3 (default from reqres api is 6), so we can get more than one page
        )
    ).flow
}