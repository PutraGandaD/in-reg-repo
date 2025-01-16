package com.putragandad.regres.ui.thirdscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.putragandad.regres.core.data.repository.ReqresRepository
import com.putragandad.regres.core.data.source.remote.response.ListUserData
import com.putragandad.regres.core.utils.InternetCheckManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThirdScreenViewModel @Inject constructor(
    private val reqresRepository: ReqresRepository,
    private val internetCheckManager: InternetCheckManager
) : ViewModel() {

    private val _userResultState = MutableStateFlow(ThirdScreenUiState())
    val userResultState = _userResultState.asStateFlow()

    private var getListUserJob: Job? = null // for cancellable collector for flow since collecting flow is expensive

    init {
        getListUser()
    }

    fun getListUser() {
        getListUserJob?.cancel()
        getListUserJob = viewModelScope.launch {
            if(internetCheckManager.checkInternetConnection()) {
                reqresRepository.getListUser().cachedIn(viewModelScope)
                    .collect {
                        _userResultState.update { currentUiState ->
                            currentUiState.copy(data = it, isOffline = false)
                        }
                    }
            } else {
                _userResultState.update { currentUiState ->
                    currentUiState.copy(data = PagingData.empty(), isOffline = true)
                }
            }

        }
    }

    fun cleanUserList() {
        _userResultState.update { currentUiState ->
            currentUiState.copy(data = PagingData.empty(), isOffline = false)
        }
    }
}
