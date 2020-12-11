package com.eneserdogan.hizligeliyocase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eneserdogan.hizligeliyocase.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButtonListener()

    }

    private fun loginButtonListener() {
        login_button.setOnClickListener {
            val intent=Intent(applicationContext,MenuActivity::class.java)
            startActivity(intent)
        }
    }
}