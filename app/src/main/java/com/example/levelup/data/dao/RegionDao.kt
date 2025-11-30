package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelup.data.model.RegionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {

    @Query("SELECT * FROM regions ORDER BY name ASC")
    fun getAllRegions(): Flow<List<RegionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegions(regions: List<RegionEntity>)

    @Query("SELECT COUNT(*) FROM regions")
    suspend fun getRegionCount(): Int
}
