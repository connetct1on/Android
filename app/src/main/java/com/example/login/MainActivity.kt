package com.example.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.login.presentation.Home
import com.example.login.databinding.ActivityMainBinding
import com.example.login.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.LoginRequset
import com.example.login.network.retrofit.response.LoginResponse
import com.example.login.db.sharedPreFerences.SharedPreFerences
import com.example.login.presentation.Singup
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"

    private var mbinding: ActivityMainBinding ?= null
    private val binding get() = mbinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Realm.init(this)

        // 로그인 버튼
        binding.btnLogin.setOnClickListener {
            //editText로부터 입력된 값을 받아온다
            var id = binding.editId.text.toString()
            var pw = binding.editPw.text.toString()

            val call = RetrofitClient.api.login(LoginRequset(email = id, password = pw))
            call.enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.code() == 200){
                        SharedPreFerences(this@MainActivity).dataAccessToken = response.body()?.accessToken
                        SharedPreFerences(this@MainActivity).dataRefreshToken = response.body()?.refreshToken
                        SharedPreFerences(this@MainActivity).dataBearerToken = "Bearer " + response.body()?.accessToken
                        SharedPreFerences(this@MainActivity).dataUserMail = id
                        val intent = Intent(this@MainActivity,Home::class.java)
                        finishAffinity()
                        startActivity(intent)
                    } else{
                        Log.d("상태","실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("상태", t.message.toString())
                }

            })
       }

        // 회원가입 버튼
            binding.btnRegister.setOnClickListener {
            val intent = Intent(this, Singup::class.java)
            startActivity(intent)
        }

    }

    // 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
    fun dialog(type: String){
        var dialog = AlertDialog.Builder(this)

        if(type.equals("success")){
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 성공!")
        }
        else if(type.equals("fail")){
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인해주세요")
        }

        var dialog_listener = DialogInterface.OnClickListener { dialog, which ->
            val alertDialog = (dialog as AlertDialog)
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    when(type) {
                        "success" -> {
                            startActivity(Intent(this@MainActivity, Home::class.java))
                        }
                        else -> { }
                    }
                    Log.d(TAG, "")
                }
            }
        }

        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }
}