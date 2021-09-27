package com.erenkazancioglu.patikadevweek4.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.erenkazancioglu.patikadevweek4.R
import com.erenkazancioglu.patikadevweek4.utils.USER_TOKEN

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val type = intent.getStringExtra("TYPE")

        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.home_nav_fragment) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_main)

        when (type){
            "0" ->graph.startDestination = R.id.loginFragment
            "1" ->graph.startDestination = R.id.homeFragment
        }

        navHostFragment.navController.graph = graph
    }

}