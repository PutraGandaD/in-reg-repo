package com.putragandad.regres.ui.thirdscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.putragandad.regres.core.data.repository.ReqresRepository
import com.putragandad.regres.core.data.source.remote.response.ListUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThirdScreenViewModel @Inject constructor(
    private val reqresRepository: ReqresRepository
) : ViewModel() {

    private val _userResultState: MutableStateFlow<PagingData<ListUserData>> = MutableStateFlow(PagingData.empty())
    val userResultState = _userResultState.asStateFlow()

    private var getListUserJob: Job? = null // for cancellable collector for flow since collecting flow is expensive

    init {
        getListUser()
    }

    fun getListUser() {
        getListUserJob?.cancel()
        getListUserJob = viewModelScope.launch {
            reqresRepository.getListUser().cachedIn(viewModelScope)
                .collect { _userResultState.value = it }
        }
    }
}
