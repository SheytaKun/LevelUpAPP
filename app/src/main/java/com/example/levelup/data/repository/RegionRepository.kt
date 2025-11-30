package com.example.levelup.data.repository

import com.example.levelup.data.dao.RegionDao
import com.example.levelup.data.model.RegionEntity
import kotlinx.coroutines.flow.Flow

class RegionRepository(private val regionDao: RegionDao) {
    val allRegions: Flow<List<RegionEntity>> = regionDao.getAllRegions()
}
