package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.model.RegionEntity
import com.example.levelup.data.repository.RegionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RegionViewModel(
    repository: RegionRepository
) : ViewModel() {
    val regions: StateFlow<List<RegionEntity>> = repository.allRegions
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
