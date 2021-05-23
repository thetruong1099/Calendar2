package com.example.calendar2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calendar2.viewmodel.ShareDataViewModel
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        var day = sharedPreferences.getString("dayStart", "")
        if (day == "") {
            tv_set_day_start.text = "Sunday"
        } else {
            tv_set_day_start.text = sharedPreferences.getString("dayStart", "")
        }

        val model = ViewModelProvider(this).get(ShareDataViewModel::class.java)
        model.getText().observe(this, Observer {
            tv_set_day_start.text = it
        })

        layout_day_start.setOnClickListener { opentBottomSheetDialog() }

        btnBack.setOnClickListener { onBackPressed() }
    }

    fun opentBottomSheetDialog() {
        var bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, "TAG")
    }
}