package com.aabhishek.eezyassignment.framework.datasource.cache

import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails
import com.aabhishek.eezyassignment.framework.datasource.database.DoublePlanDetailsDao
import javax.inject.Singleton

@Singleton
class DoublePlanDetailsService(
    val doublePlanDetailsDao: DoublePlanDetailsDao
) {

    suspend fun getDoublePlanDetails(
        dateDiff: Long
    ) = doublePlanDetailsDao.getDoublePlanDetails(dateDiff)

    suspend fun insertDoublePlanDetails(
        doublePlanDetails: DoublePlanDetails
    ) = doublePlanDetailsDao.insertDoublePlanDetails(doublePlanDetails)
}