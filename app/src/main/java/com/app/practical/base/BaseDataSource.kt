package com.app.practical.base

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.app.practical.network.AppResponse
import com.app.practical.network.api.APILiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

open class BaseDataSource {
    private val TAG = BaseDataSource::class.java.simpleName
    fun <T> execute(
        V: BaseViewModel,
        callTagList: suspend () -> Response<T>?,
        loginObserver: APILiveData<T>
    ) {
        V.viewModelScope.launch {
            V.setIsLoading(true)

            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    callTagList.invoke()
                }
            }.onSuccess {

                Log.e("response", it?.raw().toString())
                Log.e("ur", it?.body().toString())

                if (it != null) {
                    if (it.isSuccessful) {
                        loginObserver.postValue(
                            AppResponse(
                                it.message(),
                                it.code(),
                                it.isSuccessful,
                                it.body(),
                                null
                            )
                        )
                    } else {
                        loginObserver.postValue(
                            AppResponse(
                                it.message(),

                                it.code(),
                                it.isSuccessful,
                                null,
                                it.errorBody()?.let { it1 ->
                                    Response.error<Any>(
                                        it.code(),
                                        it1
                                    )
                                }?.let { it2 ->
                                    HttpException(
                                        it2
                                    )
                                }
                            )
                        )
                    }
                } else {
                   Log.e(TAG, it.toString())
                }

                V.setIsLoading(false)
            }.onFailure {
                Log.e(TAG, "execute: error")
                loginObserver.postValue(AppResponse<T>("Something went wrong", 0, false, null, it))
                V.setIsLoading(false)
            }

        }
    }


}