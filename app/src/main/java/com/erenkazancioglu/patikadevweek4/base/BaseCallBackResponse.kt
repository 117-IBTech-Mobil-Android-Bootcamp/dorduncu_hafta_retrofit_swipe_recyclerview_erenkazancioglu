package com.erenkazancioglu.patikadevweek4.base

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseCallBackResponse<T> : Callback<T> ,IBaseCallBackResponseHandler<T>  {
    override fun onResponse(call: Call<T>, response: Response<T>) {
            if(response.isSuccessful){
                response.body()?.let {
                    onSuccess(it)
                }?:
                run {
                    onFailure()
                }
            }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailure()
    }

    override fun onSuccess(data: T) {
        Log.d("success ", "success response ")
    }

    override fun onFailure() {
        Log.d("failed ", "failed response")
    }
}