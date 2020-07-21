package com.aabhishek.eezyassignment.business.data.cache

import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails

interface DoublePlanCacheDataSource {

    suspend fun insertDoublePlanDetails(
        doublePlanDetails: DoublePlanDetails
    ): Long

    suspend fun getDoublePlanDetails(
        dateDiff: Long
    ): DoublePlanDetails?
}