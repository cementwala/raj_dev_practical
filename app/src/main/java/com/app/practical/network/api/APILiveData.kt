package com.app.practical.network.api

import androidx.lifecycle.MutableLiveData
import com.app.practical.R
import com.app.practical.base.BaseActivity
import com.app.practical.exceptions.ServerError
import com.app.practical.network.AppResponse
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

class APILiveData<T> : MutableLiveData<AppResponse<T>>() {


    /**
     *  @param owner : Life Cycle Owner
     *  @param onChange : live data
     *  @param onError : Server and API error -> return true to handle error by base else return false to handle error by your self
     *
     */



    fun observe(
        owner: BaseActivity<*, *>,
        onChange: (T) -> Unit,
        onError:(code:Int,message:String)->Unit, liveData: Boolean = false
    ) {
        super.observe(owner) {
            var message="";
            if (it?.throwable != null) {
                if (it.throwable is HttpException) {
                    if (it.code == 401) {
                        message=it.throwable.message()
                    }
                    else if (it.code == 404)
                    {
                        message=owner.getString(R.string.something_went_wrong)
                    }
                    else if (it.code == 400) {
                        try {
                            val jObjError = JSONObject(it.throwable.response()?.errorBody()?.string())

                            if (jObjError.has("message")) {
                                message =jObjError.getString("message")
                            } else if (jObjError.has("errors")) {
                                message =jObjError.getString("errors")
                            }
                        } catch (e: Exception) {
                            message =it.throwable.localizedMessage ?: it.throwable.message()
                        }

                    }
                    else if (it.code == 405) {
                        message = it.throwable.localizedMessage ?: it.throwable.message()
                    }
                    else if (it.throwable.code() > 499) {

                        message="Server is in maintenance, please try again later"
                    }

                    onError(it.code,message)
                }
                else if (it.throwable is SocketTimeoutException)
                {
                    owner.networkError(it.throwable)
                }
                else if (it.throwable is ServerError) {
                    owner.showSnackBar(it.throwable.localizedMessage ?: it.throwable.message!!)
                }
                it.success = false
            } else if (it?.data != null) {
                onChange(it.data)
                it.success = liveData
            } else {
                // if I am getting 200 header code then We need to get callback in success of api to display error msg
                it.data?.let { it1 -> onChange(it1) }
                it.success = liveData
            }
        }
    }


}