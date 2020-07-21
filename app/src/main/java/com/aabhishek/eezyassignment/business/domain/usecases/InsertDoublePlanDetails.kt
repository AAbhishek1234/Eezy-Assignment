package com.aabhishek.eezyassignment.business.domain.usecases

import com.aabhishek.eezyassignment.business.data.cache.DoublePlanCacheDataSource
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails
import com.aabhishek.eezyassignment.utils.safeCacheCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertDoublePlanDetails
@Inject
constructor(
    private val doublePlanCacheDataSource: DoublePlanCacheDataSource
) {

    operator fun invoke(doublePlanDetails: DoublePlanDetails): Flow<Unit> = flow {
        safeCacheCall(Dispatchers.IO) {
            doublePlanCacheDataSource.insertDoublePlanDetails(doublePlanDetails)
        }
    }
}