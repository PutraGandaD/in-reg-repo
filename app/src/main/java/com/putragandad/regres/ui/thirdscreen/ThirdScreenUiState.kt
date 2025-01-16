package com.putragandad.regres.ui.thirdscreen

import androidx.paging.PagingData
import com.putragandad.regres.core.data.source.remote.response.ListUserData

data class ThirdScreenUiState(
    val data : PagingData<ListUserData> = PagingData.empty(),
    val isOffline : Boolean = false
)