package com.erenkazancioglu.patikadevweek4.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.erenkazancioglu.patikadevweek4.R
import com.erenkazancioglu.patikadevweek4.adapter.MySwipeAdapter
import com.erenkazancioglu.patikadevweek4.base.BaseCallBackResponse
import com.erenkazancioglu.patikadevweek4.base.NewBaseFragment
import com.erenkazancioglu.patikadevweek4.model.response.TasksResponse
import com.erenkazancioglu.patikadevweek4.service.ServiceConnector
import com.erenkazancioglu.patikadevweek4.utils.USER_TOKEN
import com.erenkazancioglu.patikadevweek4.utils.gone
import com.erenkazancioglu.patikadevweek4.utils.visible
import com.erenkazancioglu.patikadevweek4.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import android.content.SharedPreferences
import com.erenkazancioglu.patikadevweek4.model.User


class HomeFragment : NewBaseFragment()
{
    val limit :Int = 10
    var skip :Int = 0
    private lateinit var taskViewModel: TaskViewModel
    private var swipeAdapter :MySwipeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("Bag", Context.MODE_PRIVATE)
        val userToken = preferences.getString(USER_TOKEN,null)
        User.getCurrentInstance().token = userToken

        exitDialogShow()
        backgroundColorChange(R.color.homepage_bg)
        getDataByPagination(limit, skip)
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_show_no_data.gone()
        recyclerview_homepage.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_homepage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE ) {
                    skip += limit
                    getDataByPagination(limit, skip)
                }
            }
        })

        fab_button.setOnClickListener{
            showAddTaskDialog()
        }
    }

    private fun getDataByPagination(limit: Int, skip: Int){
        ServiceConnector.restInterface.getTaskWithPagination(limit, skip).enqueue(object: BaseCallBackResponse<TasksResponse>(){
            override fun onSuccess(data: TasksResponse) {
                super.onSuccess(data)

                taskViewModel= ViewModelProviders.of(requireActivity()).get(TaskViewModel::class.java)
                taskViewModel.setTaskData(data)

                if(taskViewModel.dataArray.isEmpty())
                    txt_show_no_data.visible()

                if (swipeAdapter == null){
                    swipeAdapter = MySwipeAdapter(data.dataList,taskViewModel)
                    recyclerview_homepage.adapter = swipeAdapter
                    observeLiveData()
                }
                else{
                    recyclerview_homepage.adapter?.notifyDataSetChanged()
                }
            }
            override fun onFailure() {
                super.onFailure()
                Toast.makeText(requireActivity(), "Something went wrong, no tasks have been fetched", Toast.LENGTH_SHORT).show()
            }

        })
    }

    @SuppressLint("CheckResult")
    private fun showAddTaskDialog() {
        var etDesc : String? = null
        MaterialDialog(requireActivity())
            .show {
                title(R.string.Add_Task_Title)
                    .input { _, newTaskDesc ->
                    etDesc = newTaskDesc.toString()
                }
            }
            .positiveButton(R.string.Add_Task_Positive_Button_Text){
                taskViewModel.addTaskData(etDesc!!)
                txt_show_no_data.gone()
            }
            .negativeButton(R.string.Add_Task_Negative_Button_Text)
    }

    private fun observeLiveData() {
        taskViewModel.currentLiveData.observe(viewLifecycleOwner, { item->
            item?.let {
                swipeAdapter?.invokeTaskList(it)
            }
        })
    }

}