package com.punkmic.businesscard.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessCardDao {

    @Query("SELECT * FROM BusinessCard")
    fun getAll(): Flow<List<BusinessCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(businessCard: BusinessCard)

    @Delete
    fun delete(vararg businessCard: BusinessCard)
}