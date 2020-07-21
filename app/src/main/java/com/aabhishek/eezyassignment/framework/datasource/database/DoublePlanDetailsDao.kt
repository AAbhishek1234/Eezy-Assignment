package com.aabhishek.eezyassignment.framework.datasource.database

import androidx.room.*
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails

@Dao
interface DoublePlanDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoublePlanDetails(
        doublePlanDetails: DoublePlanDetails
    ) : Long

    @Query("""
        SELECT * FROM DoublePlanDetails
        WHERE dateDiff = :dateDiff
    """)
    suspend fun getDoublePlanDetails(
        dateDiff: Long
    ): DoublePlanDetails
}