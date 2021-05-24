package com.example.calendar2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.calendar2.adpater.CalenderViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var currentYear = 0
    private var currentMonth = 0
    private var currentDay = 0
    private lateinit var adapterViewPagers: CalenderViewPagerAdapter
    private val size = 1000
    private lateinit var sharedPreferences: SharedPreferences
    private var dayStart: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = Calendar.getInstance()
        currentYear = calendar.get(Calendar.YEAR)
        currentMonth = calendar.get(Calendar.MONTH) + 1
        currentDay = calendar.get(Calendar.DATE)


        btnSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE)
        dayStart = sharedPreferences.getString("dayStart", "").toString()
        if (dayStart != "") {
            setDayStartLayout(dayStart)
        }

        var fragmentList = MutableList<Fragment>(size) { index -> Fragment() }
        fragmentList.set(size / 2 - 1, CalendarFragment(currentYear, currentMonth - 1, 0, dayStart))
        fragmentList.set(
            size / 2,
            CalendarFragment(currentYear, currentMonth, currentDay, dayStart)
        )
        fragmentList.set(size / 2 + 1, CalendarFragment(currentYear, currentMonth + 1, 0, dayStart))

        adapterViewPagers = CalenderViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        calendar_view_papger.apply {
            adapter = adapterViewPagers
            setCurrentItem(size / 2, false)
        }

        calendar_view_papger.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            var countLeft = 2
            var countRight = 2
            var kYearLeft = 1
            var kMonthLeft = 12
            var kYearRight = 1
            var kMonthRight = 1
            var jump = size / 2
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position < jump) {
                    adapterViewPagers.reloadFragment(jump)
                    jump = position
                    if (currentMonth - countLeft > 0) {
                        adapterViewPagers.setFragmentAtPosition(
                            CalendarFragment(
                                currentYear,
                                currentMonth - countLeft,
                                0, dayStart
                            ), size / 2 - countLeft
                        )
                    } else {
                        adapterViewPagers.setFragmentAtPosition(
                            CalendarFragment(
                                currentYear - kYearLeft,
                                kMonthLeft,
                                0, dayStart
                            ), size / 2 - countLeft
                        )
                        kMonthLeft--
                        if (kMonthLeft == 0) {
                            kMonthLeft = 12
                            kYearLeft++
                        }
                    }
                    countLeft++
                }

                if (position > jump) {
                    adapterViewPagers.reloadFragment(jump)
                    jump = position
                    if (currentMonth + countRight <= 12) {
                        adapterViewPagers.setFragmentAtPosition(
                            CalendarFragment(
                                currentYear,
                                currentMonth + countRight,
                                0, dayStart
                            ), size / 2 + countRight
                        )

                    } else {
                        adapterViewPagers.setFragmentAtPosition(
                            CalendarFragment(
                                currentYear + kYearRight,
                                kMonthRight,
                                0, dayStart
                            ), size / 2 + countRight
                        )
                        kMonthRight++
                        if (kMonthRight > 12) {
                            kMonthRight = 1
                            kYearRight++
                        }
                    }
                    countRight++
                }

                if (position != size / 2) {
                    tv_current_day.visibility = View.VISIBLE
                    tv_current_day.text = currentDay.toString()
                } else {
                    tv_current_day.visibility = View.GONE
                }
            }
        })

        tv_current_day.setOnClickListener {
            tv_month_year.text = "$currentYear ${currentMonth}"
            calendar_view_papger.currentItem = size / 2 }
    }

    private fun setDayStartLayout(string: String) {
        when (string) {
            "Monday" -> {
                tv_day_first.text = "MON"
                tv_day_second.text = "TUE"
                tv_day_third.text = "WED"
                tv_day_fourth.text = "THUR"
                tv_day_fifth.text = "FRI"
                tv_day_sixth.text = "SAT"
                tv_day_seventh.text = "SUN"
            }
            "Tuesday" -> {
                tv_day_first.text = "TUE"
                tv_day_second.text = "WED"
                tv_day_third.text = "THUR"
                tv_day_fourth.text = "FRI"
                tv_day_fifth.text = "SAT"
                tv_day_sixth.text = "SUN"
                tv_day_seventh.text = "MON"
            }
            "Wednesday" -> {
                tv_day_first.text = "WED"
                tv_day_second.text = "THUR"
                tv_day_third.text = "FRI"
                tv_day_fourth.text = "SAT"
                tv_day_fifth.text = "SUN"
                tv_day_sixth.text = "MON"
                tv_day_seventh.text = "TUE"
            }
            "Thursday" -> {
                tv_day_first.text = "THUR"
                tv_day_second.text = "FRI"
                tv_day_third.text = "SAT"
                tv_day_fourth.text = "SUN"
                tv_day_fifth.text = "MON"
                tv_day_sixth.text = "TUE"
                tv_day_seventh.text = "WED"
            }
            "Friday" -> {
                tv_day_first.text = "FRI"
                tv_day_second.text = "SAT"
                tv_day_third.text = "SUN"
                tv_day_fourth.text = "MON"
                tv_day_fifth.text = "TUE"
                tv_day_sixth.text = "WED"
                tv_day_seventh.text = "THUR"
            }
            "Saturday" -> {
                tv_day_first.text = "SAT"
                tv_day_second.text = "SUN"
                tv_day_third.text = "MON"
                tv_day_fourth.text = "TUE"
                tv_day_fifth.text = "WED"
                tv_day_sixth.text = "THUR"
                tv_day_seventh.text = "FRI"
            }
            "Sunday" -> {
                tv_day_first.text = "SUN"
                tv_day_second.text = "MON"
                tv_day_third.text = "TUE"
                tv_day_fourth.text = "WED"
                tv_day_fifth.text = "THUR"
                tv_day_sixth.text = "FRI"
                tv_day_seventh.text = "SAT"
            }
        }
    }
}
