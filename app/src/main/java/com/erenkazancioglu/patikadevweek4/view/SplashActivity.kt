package com.erenkazancioglu.patikadevweek4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erenkazancioglu.patikadevweek4.R
import com.erenkazancioglu.patikadevweek4.base.BaseCallBackResponse
import com.erenkazancioglu.patikadevweek4.model.User
import com.erenkazancioglu.patikadevweek4.service.ServiceConnector
import com.erenkazancioglu.patikadevweek4.utils.*
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    private var token : String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.statusBarColor = resources.getColor(R.color.light_blue)
        setContentView(R.layout.activity_splash)

        if(isLoggedIn()){
            User.getCurrentInstance().token = token
            ServiceConnector.restInterface.getLoggedInUser().enqueue(object : BaseCallBackResponse<User>(){
                override fun onSuccess(data: User) {
                    super.onSuccess(data)
                    progressBar.gone()
                    User.getCurrentInstance().setUser(data)
                    //go to 4B
                    startActivity<MainActivity> {
                        this.putExtra("TYPE", HOME)
                    }
                }
                override fun onFailure() {
                    super.onFailure()
                }
            })
        }else{
            progressBar.gone()
           //go to 4A
            startActivity<MainActivity> {
                this.putExtra("TYPE", LOGIN_SCREEN)
            }
        }
    }

    private fun isLoggedIn() : Boolean{
        val token = getToken()
        return token.isNotEmpty()
    }

    private fun getToken() : String{
        val sh = getSharedPreferences("Bag", MODE_PRIVATE)
        token = sh.getString(USER_TOKEN, "")!!
        return token!!
    }
}