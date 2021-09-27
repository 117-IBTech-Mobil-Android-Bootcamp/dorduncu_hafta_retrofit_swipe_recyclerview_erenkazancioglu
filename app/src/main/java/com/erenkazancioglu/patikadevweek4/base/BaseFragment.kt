package com.erenkazancioglu.patikadevweek4.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.erenkazancioglu.patikadevweek4.R


abstract class BaseFragment : Fragment(),IBaseFragment {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutID(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView()
    }

    abstract fun getLayoutID() : Int

    open fun prepareView(){}

    override fun backgroundColorChange(id: Int) {
        activity?.window?.statusBarColor = resources.getColor(id)
    }

    override fun exitDialogShow() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val alert= AlertDialog.Builder(requireContext())
                alert.setTitle(R.string.Warning_Title_Text)
                alert.setMessage(R.string.Warning_Text)
                alert.setCancelable(false)
                alert.setNegativeButton(R.string.Warning_No_Text){ dialog, which ->
                    dialog.cancel()
                }
                alert.setPositiveButton(R.string.Warning_Yes_Text){dialog,which ->
                    activity?.finish()
                }.show()
            }
        })
    }

}