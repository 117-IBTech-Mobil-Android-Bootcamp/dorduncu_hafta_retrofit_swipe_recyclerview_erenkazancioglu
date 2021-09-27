package com.erenkazancioglu.patikadevweek4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erenkazancioglu.patikadevweek4.base.BaseCallBackResponse
import com.erenkazancioglu.patikadevweek4.model.Data
import com.erenkazancioglu.patikadevweek4.model.response.TaskResponse
import com.erenkazancioglu.patikadevweek4.model.response.TasksResponse
import com.erenkazancioglu.patikadevweek4.service.ServiceConnector


class TaskViewModel : ViewModel() {

    private val currentTaskDataSet= MutableLiveData<List<Data>>()
    val currentLiveData: LiveData<List<Data>>
        get() = currentTaskDataSet
    val dataArray = arrayListOf<Data>()
    private lateinit var value : Data
    fun setTaskData(data : TasksResponse)
    {
        for (i in 0 until data.count){
            value = Data(data.dataList[i].id.toString()
                ,data.dataList[i].description.toString()
                ,data.dataList[i].completed
                ,data.dataList[i].createdAt)
            dataArray.add(value)
        }
        currentTaskDataSet.value =  dataArray
    }

    fun addTaskData(description: String){
        var params = mutableMapOf<String, Any>().apply {
            put("description", description)
        }

        ServiceConnector.restInterface.addTask(params).enqueue(object : BaseCallBackResponse<TaskResponse>(){
            override fun onSuccess(data: TaskResponse) {
                super.onSuccess(data)
                dataArray.add(currentLiveData.value!!.size,Data(data.data.id.toString(),data.data.description.toString(),data.data.completed,data.data.createdAt))
                currentTaskDataSet.value = dataArray
            }
            override fun onFailure() {
                super.onFailure()
            }
        })
    }

    /*
    fun deleteSelectedTask(position: Int) {
        ServiceConnector.restInterface.deleteTaskById(dataArray[position].id.toString()).enqueue(object : BaseCallBackResponse<Unit>(){
            override fun onSuccess(data: Unit) {
                super.onSuccess(data)
                dataArray.removeAt(position)
                currentTaskDataSet.value=dataArray
            }
            override fun onFailure() {
                super.onFailure()
            }
        })
    }
   */
}




