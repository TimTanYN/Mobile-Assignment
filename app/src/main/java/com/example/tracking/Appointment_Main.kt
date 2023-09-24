package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp

class Appointment_Main: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appointment_main)

        // 获取查看医生列表按钮
        val doctorListButton = findViewById<Button>(R.id.doctorListButton)

        // 添加按钮点击事件监听器
        doctorListButton.setOnClickListener(View.OnClickListener {
            // 创建一个Intent来启动DoctorListActivity
            val intent = Intent(this@Appointment_Main, DoctorListActivity::class.java)

            // 启动DoctorListActivity
            startActivity(intent)
        })

        val myappointmentButton = findViewById<Button>(R.id.myAppointmentButton)

        myappointmentButton.setOnClickListener {
            val intent = Intent(this@Appointment_Main, MyAppointmentActivity::class.java)
            startActivity(intent)
        }

    }
}