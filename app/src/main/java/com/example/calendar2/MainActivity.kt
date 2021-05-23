package com.example.calendar2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        fragmentList.set(size / 2 - 2, CalendarFragment(currentYear, currentMonth - 2, 0, dayStart))
        fragmentList.set(size / 2 - 1, CalendarFragment(currentYear, currentMonth - 1, 0, dayStart))
        fragmentList.set(
            size / 2,
            CalendarFragment(currentYear, currentMonth, currentDay, dayStart)
        )
        fragmentList.set(size / 2 + 1, CalendarFragment(currentYear, currentMonth + 1, 0, dayStart))
        fragmentList.set(size / 2 + 2, CalendarFragment(currentYear, currentMonth + 2, 0, dayStart))

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
            var countLeft = 3
            var countRight = 3
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
                        adapterViewPagers.addToLeftList(
                            CalendarFragment(
                                currentYear,
                                currentMonth - countLeft,
                                0, dayStart
                            ), size / 2 - countLeft
                        )
                    } else {
                        adapterViewPagers.addToLeftList(
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
                        adapterViewPagers.addtoRightList(
                            CalendarFragment(
                                currentYear,
                                currentMonth + countRight,
                                0, dayStart
                            ), size / 2 + countRight
                        )

                    } else {
                        adapterViewPagers.addtoRightList(
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

        tv_current_day.setOnClickListener { calendar_view_papger.currentItem = size / 2 }
    }

    private fun setDayStartLayout(string: String) {
        when (string) {
            "Monday" -> {
                day_start_mon.visibility = View.VISIBLE
                day_start_tue.visibility = View.GONE
                day_start_wed.visibility = View.GONE
                day_start_thur.visibility = View.GONE
                day_start_fri.visibility = View.GONE
                day_start_sat.visibility = View.GONE
                day_start_sun.visibility = View.GONE
            }
            "Tuesday" -> {
                day_start_mon.visibility = View.GONE
                day_start_tue.visibility = View.VISIBLE
                day_start_wed.visibility = View.GONE
                day_start_thur.visibility = View.GONE
                day_start_fri.visibility = View.GONE
                day_start_sat.visibility = View.GONE
                day_start_sun.visibility = View.GONE
            }
            "Wednesday" -> {
                day_start_mon.visibility = View.GONE
                day_start_tue.visibility = View.GONE
                day_start_wed.visibility = View.VISIBLE
                day_start_thur.visibility = View.GONE
                day_start_fri.visibility = View.GONE
                day_start_sat.visibility = View.GONE
                day_start_sun.visibility = View.GONE
            }
            "Thursday" -> {
                day_start_mon.visibility = View.GONE
                day_start_tue.visibility = View.GONE
                day_start_wed.visibility = View.GONE
                day_start_thur.visibility = View.VISIBLE
                day_start_fri.visibility = View.GONE
                day_start_sat.visibility = View.GONE
                day_start_sun.visibility = View.GONE
            }
            "Friday" -> {
                day_start_mon.visibility = View.GONE
                day_start_tue.visibility = View.GONE
                day_start_wed.visibility = View.GONE
                day_start_thur.visibility = View.GONE
                day_start_fri.visibility = View.VISIBLE
                day_start_sat.visibility = View.GONE
                day_start_sun.visibility = View.GONE
            }
            "Saturday" -> {
                day_start_mon.visibility = View.GONE
                day_start_tue.visibility = View.GONE
                day_start_wed.visibility = View.GONE
                day_start_thur.visibility = View.GONE
                day_start_fri.visibility = View.GONE
                day_start_sat.visibility = View.VISIBLE
                day_start_sun.visibility = View.GONE
            }
            "Sunday" -> {
                day_start_mon.visibility = View.GONE
                day_start_tue.visibility = View.GONE
                day_start_wed.visibility = View.GONE
                day_start_thur.visibility = View.GONE
                day_start_fri.visibility = View.GONE
                day_start_sat.visibility = View.GONE
                day_start_sun.visibility = View.VISIBLE
            }
        }
    }

}
