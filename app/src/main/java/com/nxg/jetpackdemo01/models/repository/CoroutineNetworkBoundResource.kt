/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nxg.jetpackdemo01.models.repository

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.nxg.jetpackdemo01.models.source.remote.api.ApiEmptyResponse
import com.nxg.jetpackdemo01.models.source.remote.api.ApiErrorResponse
import com.nxg.jetpackdemo01.models.source.remote.api.ApiResponse
import com.nxg.jetpackdemo01.models.source.remote.api.ApiSuccessResponse
import com.nxg.jetpackdemo01.vo.Resource
import com.nxg.jetpackdemo01.vo.Status
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException

fun <ResultType, RequestType> networkBoundResource(
    saveCallResult: suspend (RequestType) -> Unit,
    shouldFetch: (ResultType) -> Boolean = { true },
    loadFromDb: () -> LiveData<ResultType>,
    fetch: suspend () -> ApiResponse<RequestType>,
    processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType) = { it.body },
    onFetchFailed: ((ApiErrorResponse<RequestType>) -> Unit)? = null
): LiveData<Resource<ResultType>> {
    return CoroutineNetworkBoundResource(
        saveCallResult = saveCallResult,
        shouldFetch = shouldFetch,
        loadFromDb = loadFromDb,
        fetch = fetch,
        processResponse = processResponse,
        onFetchFailed = onFetchFailed
    ).asLiveData().distinctUntilChanged() // not super happy about this as the data might be BIG
}

/**
 * A [NetworkBoundResource] implementation in corotuines
 */
private class CoroutineNetworkBoundResource<ResultType, RequestType>
@MainThread constructor(
    private val saveCallResult: suspend (RequestType) -> Unit,
    private val shouldFetch: (ResultType) -> Boolean = { true },
    private val loadFromDb: () -> LiveData<ResultType>,
    private val fetch: suspend () -> ApiResponse<RequestType>,
    private val processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType),
    private val onFetchFailed: ((ApiErrorResponse<RequestType>) -> Unit)?
) {
    @ExperimentalCoroutinesApi
    private val result = liveData<Resource<ResultType>> {
        if (latestValue?.status != Status.SUCCESS) {
            emit(Resource.loading(latestValue?.data))
        }
        val dbSource = loadFromDb()
        val initialValue = dbSource.await()
        val willFetch = initialValue == null || shouldFetch(initialValue)
        if (!willFetch) {
            // if we won't fetch, just emit existing db values as success
            emitSource(dbSource.map {
                Resource.success(it)
            })
        } else {
            doFetch(dbSource, this)
        }
    }

    private suspend fun doFetch(
        dbSource: LiveData<ResultType>,
        liveDataScope: LiveDataScope<Resource<ResultType>>
    ) {
        // emit existing values as loading while we fetch
        val initialSource = liveDataScope.emitSource(dbSource.map {
            Resource.loading(it)
        })
        val response = fetchCatching()
        when (response) {
            is ApiSuccessResponse, is ApiEmptyResponse -> {
                if (response is ApiSuccessResponse) {
                    val processed = processResponse(response)
                    initialSource.dispose()
                    // before saving it, disconnect it so that new values comes w/ success
                    saveCallResult(processed)
                }
                liveDataScope.emitSource(loadFromDb().map {
                    Resource.success(it)
                })
            }
            is ApiErrorResponse -> {
                onFetchFailed?.invoke(response)
                liveDataScope.emitSource(dbSource.map {
                    Resource.error(response.errorMessage, it)
                })
            }
        }
    }

    // temporary here during migration
    fun asLiveData() = result as LiveData<Resource<ResultType>>

    private suspend fun fetchCatching(): ApiResponse<RequestType> {
        return try {
            fetch()
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            ApiResponse.create(ex)
        }
    }

    private suspend fun <T> LiveData<T>.await() = withContext(Dispatchers.Main) {
        val receivedValue = CompletableDeferred<T?>()
        val observer = Observer<T> {
            if (receivedValue.isActive){
                receivedValue.complete(it)
            }
        }
        try {
            observeForever(observer)
            return@withContext receivedValue.await()
        } finally {
            removeObserver(observer)
        }
    }
}