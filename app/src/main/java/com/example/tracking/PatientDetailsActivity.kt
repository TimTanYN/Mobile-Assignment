package com.example.tracking

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.tracking.BaseNavigationActivity
import com.example.tracking.databinding.ActivityPatientDetailsBinding  // 请确保导入正确的数据绑定类

class PatientDetailsActivity : BaseNavigationActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用数据绑定来设置布局
        val binding: ActivityPatientDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_patient_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Patient Details"
        initToolbarAndNavigation()


        // 不再需要使用 findViewById 来查找视图元素，可以直接通过绑定对象来访问它们
        val editTextName = binding.editTextName
        val editTextAge = binding.editTextAge
        val editTextCondition = binding.editTextCondition
        val genderSpinner = binding.genderSpinner
        val buttonNext = binding.buttonNext

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences("PatientPrefs", MODE_PRIVATE)

        // 创建一个 ArrayAdapter 以提供选项
        val genderOptions = arrayOf("Female", "Male")

        // 创建适配器并设置它
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // 设置默认性别选项为 "Female"
        genderSpinner.setSelection(0)

        buttonNext.setOnClickListener {
            // 获取用户输入的信息
            val name = editTextName.text.toString().trim()
            val age = editTextAge.text.toString().trim()
            val condition = editTextCondition.text.toString().trim()
            val selectedGender = genderSpinner.selectedItem.toString()

            // 进行表单验证
            if (name.isEmpty()) {
                showToast("Please enter your name")
                return@setOnClickListener
            }

            if (age.isEmpty()) {
                showToast("Please enter your age")
                return@setOnClickListener
            }

            if (condition.isEmpty()) {
                showToast("Please describe your problem")
                return@setOnClickListener
            }

            // 使用SharedPreferences保存用户输入的信息
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.putString("age", age)
            editor.putString("condition", condition)
            editor.putString("gender", selectedGender)
            editor.apply()

            // 如果表单验证通过，将用户输入的信息传递给下一个活动
            val intent = Intent(this@PatientDetailsActivity, PaymentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}