package com.example.login.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.login.R
import com.example.login.databinding.ActivityHomeBinding
import com.example.login.presentation.Fragment.HomeFragment
import com.example.login.presentation.Fragment.QnAFragment
import com.example.login.presentation.Fragment.RoomFragment
import com.example.login.presentation.Fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    private var mbinding: ActivityHomeBinding ?= null
    private val binding get() = mbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //toolbar 초기화
        setSupportActionBar(binding.toolbar)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }

        val homeFragment = HomeFragment() // main
        val roomFragment = RoomFragment() // messageRoom
        val myPageFragment = SettingFragment() // myPage
        val qnAFragment = QnAFragment() //QnAPage


        replaceFragment(homeFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.QnA -> replaceFragment(roomFragment)
                R.id.myPage -> replaceFragment(myPageFragment)
            }
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when(item.itemId){
            R.drawable.message_black -> replaceFragment(RoomFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // 현 Activity 에 연결된 Fragment 관리하는 supportFragmentManager 를 통해 Fragment 전환
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportActionBar?.show()
        mbinding = null
    }
}