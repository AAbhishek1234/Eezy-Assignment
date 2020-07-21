package com.aabhishek.eezyassignment.business.data.cache

import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails
import com.aabhishek.eezyassignment.framework.datasource.cache.DoublePlanDetailsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoublePlanCacheDataSourceImpl
@Inject
constructor(
    private val doublePlanDetailsService: DoublePlanDetailsService
) : DoublePlanCacheDataSource {
    override suspend fun insertDoublePlanDetails(doublePlanDetails: DoublePlanDetails) =
        doublePlanDetailsService.insertDoublePlanDetails(doublePlanDetails)

    override suspend fun getDoublePlanDetails(dateDiff: Long) =
        doublePlanDetailsService.getDoublePlanDetails(dateDiff)
}