package com.aabhishek.eezyassignment.business.domain.usecases

import android.util.Log
import com.aabhishek.eezyassignment.business.data.cache.CacheResult
import com.aabhishek.eezyassignment.business.data.cache.DoublePlanCacheDataSource
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails
import com.aabhishek.eezyassignment.utils.safeCacheCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchDoublePlanDetails
@Inject constructor(
    private val doublePlanCacheDataSource: DoublePlanCacheDataSource
) {
    private val TAG = "FetchDoublePlanDetails"
    operator fun invoke(
        dateDiff: Long
    ): Flow<DoublePlanDetails?> = flow {
        val cacheResult =
            safeCacheCall(Dispatchers.IO) { doublePlanCacheDataSource.getDoublePlanDetails(dateDiff) }
        when (cacheResult) {
            is CacheResult.Success -> {
                if (cacheResult.value == null) {
                    //There is no entry , create one and pass
                    Log.i(TAG, "no entry for diff : $dateDiff, creating one")
                    val doublePlanDetails = DoublePlanDetails(dateDiff)
                    safeCacheCall(Dispatchers.IO) {
                        doublePlanCacheDataSource.insertDoublePlanDetails(
                            doublePlanDetails
                        )
                    }
                    emit(doublePlanDetails)
                } else {
                    Log.i(TAG, "got entry : ${cacheResult.value}")
                    emit(cacheResult.value)
                }
            }

            is CacheResult.GenericError -> {
                // some error , just return empty
                val doublePlanDetails = DoublePlanDetails(dateDiff)
                emit(doublePlanDetails)
            }
        }
    }
}