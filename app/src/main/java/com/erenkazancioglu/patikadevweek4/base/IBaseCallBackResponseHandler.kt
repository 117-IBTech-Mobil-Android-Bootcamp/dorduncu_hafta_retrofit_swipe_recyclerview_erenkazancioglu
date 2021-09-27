package com.erenkazancioglu.patikadevweek4.base

interface IBaseCallBackResponseHandler<T> {

    fun onSuccess(data :  T)
    fun onFailure()

}