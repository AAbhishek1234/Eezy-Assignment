package com.aabhishek.eezyassignment.utils

import com.aabhishek.eezyassignment.business.data.cache.CacheResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext

//Cache error

const val CACHE_ERROR_TIMEOUT = "Cache timeout"

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            CacheResult.Success(cacheCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    CacheResult.GenericError("Unknown error")
                }
            }
        }
    }
}