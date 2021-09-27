package com.erenkazancioglu.patikadevweek4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.erenkazancioglu.patikadevweek4.R
import com.erenkazancioglu.patikadevweek4.base.BaseCallBackResponse
import com.erenkazancioglu.patikadevweek4.model.Data
import com.erenkazancioglu.patikadevweek4.model.response.TaskResponse
import com.erenkazancioglu.patikadevweek4.service.ServiceConnector
import com.erenkazancioglu.patikadevweek4.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.row_todo.view.*
import kotlin.collections.ArrayList

class MySwipeAdapter(private val taskList: ArrayList<Data>, private val taskViewModel: TaskViewModel) : RecyclerView.Adapter<MySwipeAdapter.SwipeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.row_todo,parent,false)
        return SwipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskStatus.setImageResource(if(task.completed) R.drawable.ic_marked else R.drawable.ic_mask )
        holder.description.text = task.description
    }

    override fun getItemViewType(position: Int) = position
    override fun getItemCount(): Int = taskViewModel.dataArray.size

    inner class SwipeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var taskStatus : AppCompatImageView = itemView.findViewById(R.id.img_task_status)
        var description : AppCompatTextView = itemView.findViewById(R.id.txt_description)

      init {
             itemView.btn_complete.setOnClickListener {
                 //listener?.onRecyclerViewItemClickListener(it.btn_complete,taskList[adapterPosition],adapterPosition)
                updateTask(taskViewModel.dataArray[adapterPosition].id.toString(),adapterPosition)
             }
            itemView.btn_delete.setOnClickListener {
                //listener!!.onRecyclerViewItemClickListener(adapterPosition)
                deleteSelectedTask(taskViewModel.dataArray[adapterPosition].id.toString(),adapterPosition)
            }
         }

       fun unselected() {
           taskViewModel.dataArray[adapterPosition].completed=false
           taskStatus.setImageResource(R.drawable.ic_mask)
           notifyItemChanged(adapterPosition)
           notifyDataSetChanged()
       }

       fun selected() {
           taskViewModel.dataArray[adapterPosition].completed = true
           taskStatus.setImageResource(R.drawable.ic_marked)
           notifyItemChanged(adapterPosition)
           notifyDataSetChanged()
       }

       private fun updateTask(id : String, position: Int) {
           var params = mutableMapOf<String, Any>()
           params["completed"] = !taskViewModel.dataArray[adapterPosition].completed
           ServiceConnector.restInterface.updateTaskById(id,params).enqueue(object : BaseCallBackResponse<TaskResponse>(){
               override fun onSuccess(data: TaskResponse) {
                   super.onSuccess(data)
                   if (taskViewModel.dataArray[adapterPosition].completed) unselected() else selected()
                   notifyItemChanged(adapterPosition)
               }
           })
       }

       private fun deleteSelectedTask(id : String, position: Int) {
           ServiceConnector.restInterface.deleteTaskById(id).enqueue(object : BaseCallBackResponse<Unit>(){
               override fun onSuccess(data: Unit) {
                   super.onSuccess(data)
                    taskViewModel.dataArray.removeAt(position)
                    taskList.removeAt(position)
                    notifyItemRemoved(position)
               }
           })
       }
    }

    fun invokeTaskList(newTaskList: List<Data>) {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }

}