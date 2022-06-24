package com.app.practical.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.practical.network.AppResponse
import com.app.practical.network.api.APILiveData
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    val baseDataSource = BaseDataSource()

    val loadingObserver = MutableLiveData<Boolean>()

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val msgObserver = MutableLiveData<String>()

    val error = MutableLiveData<Throwable>()

    val handleClickListener: MutableLiveData<Int> = MutableLiveData()


    protected fun <T> withLiveData(liveData: APILiveData<T>): SingleObserver<AppResponse<T>> {
        loadingObserver.value = true
        return object : SingleObserver<AppResponse<T>> {
            override fun onError(e: Throwable) {

            }
            override fun onSuccess(t: AppResponse<T>) {
                loadingObserver.value = false
                liveData.postValue(t)
//
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }
        }
    }


    fun setIsLoading(isLoading: Boolean) {
        this.loadingObserver.postValue(isLoading)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


}