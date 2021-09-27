package com.erenkazancioglu.patikadevweek4.ui

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import androidx.navigation.fragment.findNavController
import com.erenkazancioglu.patikadevweek4.R
import com.erenkazancioglu.patikadevweek4.base.BaseCallBackResponse
import com.erenkazancioglu.patikadevweek4.base.BaseFragment
import com.erenkazancioglu.patikadevweek4.model.response.LoginResponse
import com.erenkazancioglu.patikadevweek4.service.ServiceConnector
import com.erenkazancioglu.patikadevweek4.utils.*
import kotlinx.android.synthetic.main.fragment_login.*
import android.content.SharedPreferences




class LoginFragment : BaseFragment() {
    override fun getLayoutID() = R.layout.fragment_login

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backgroundColorChange(R.color.white)
    }

    override fun prepareView() {
        pb_login.gone()
        btn_login.setOnClickListener {
            sendLoginRequest()
        }
    }

    fun sendLoginRequest() {
        pb_login.visible()
        val email = txt_user_mail.getString()
        val password = txt_password.getString()

        if(isValidPassword(password) && isValidEmail(email))
        {
            var params = mutableMapOf<String, Any>().apply {
                put("email", email)
                put("password", password)
            }
            ServiceConnector.restInterface.login(params).enqueue(object : BaseCallBackResponse<LoginResponse>(){
                override fun onSuccess(loginResponse: LoginResponse) {
                    super.onSuccess(loginResponse)
                    pb_login.gone()
                    toast("login başarılı")
                    saveDataAsString(USER_TOKEN,loginResponse.token)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                }
                override fun onFailure() {
                    super.onFailure()
                    toast("hata oluştu ")
                }
            })
        }else{
            toast("Alanları kontrol ediniz")
        }
    }

    private fun isValidPassword(password : String) : Boolean{
       return if (password.isEmpty() || password.length < 7)  false else true
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}