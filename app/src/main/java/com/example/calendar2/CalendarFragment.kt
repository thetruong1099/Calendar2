package com.example.calendar2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendar2.adpater.DayInMonthAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*


class CalendarFragment(
    private var currentYear: Int,
    private var currentMonth: Int,
    private var currentDay: Int,
    private val dayStart: String
) : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var calendarItems: MutableList<MutableMap<String, Any>> = mutableListOf()
    private lateinit var dayInMonthAdapter: DayInMonthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_calendar, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        sharedPreferences = requireContext().getSharedPreferences("myData", Context.MODE_PRIVATE)
        var isOfMonth = sharedPreferences.getInt("isOfCurrentMonth", 0)
        var choseDay = sharedPreferences.getInt("chooseDay", 0)
        var chooseMonth = sharedPreferences.getInt("chooseMonth", 0)
        var chooseYear = sharedPreferences.getInt("chooseYear", 0)
        var checkCick = sharedPreferences.getInt("checked", 0)

        calendarItems.clear()

        dayInMonthAdapter = DayInMonthAdapter(calendarItems, requireContext())

        setCalendarItems(dayStart)
        dayInMonthAdapter.notifyDataSetChanged()

        dayInMonthAdapter.setCurrentDay(currentDay)
        dayInMonthAdapter.setCurrentMonthYear(currentMonth, currentYear)

        if (chooseMonth == currentMonth && chooseYear == currentYear) {
            dayInMonthAdapter.setChooseDay(getIndexDay(choseDay, isOfMonth), checkCick)
        }

        calendar_recycler_view.apply {
            layoutManager = GridLayoutManager(context, 7, GridLayoutManager.VERTICAL, false)
            adapter = dayInMonthAdapter
        }

        setMonthYear(currentYear, currentMonth)
    }

    private fun setCalendarItems(string: String) {
        var index = 0
        when (string) {
            "Monday" -> index = 1
            "Tuesday" -> index = 2
            "Wednesday" -> index = 3
            "Thursday" -> index = 4
            "Friday" -> index = 5
            "Saturday" -> index = 6
            "Sunday" -> index = 0
            else -> index = 0
        }

        //last day of Previous Month

        val calendarPrevious = Calendar.getInstance()
        if (currentMonth -1 == 1 || currentMonth -1 == 3 || currentMonth -1 == 5
            || currentMonth  -1== 7 || currentMonth -1 == 8
            || currentMonth -1 == 10 || currentMonth -1 == 12) calendarPrevious.add(Calendar.MONTH, -2)
        else calendarPrevious.add(Calendar.MONTH, -1)

        val max = calendarPrevious.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendarPrevious.set(Calendar.DAY_OF_MONTH, max)
        val dayOfPreviousMonth = calendarPrevious.get(Calendar.DATE)

        //last day of current month

        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.getActualMaximum(Calendar.DATE)

        //get day one of current month
        calendar.set(currentYear, currentMonth - 1, 1)
        val dayOneOfWeek = shiftIndexOfWeek(calendar.get(Calendar.DAY_OF_WEEK), index)

        //get Day Of Previous Month
        if (dayOneOfWeek > 1) {
            for (day in dayOfPreviousMonth - dayOneOfWeek + 2..dayOfPreviousMonth) {
                val map = mutableMapOf<String, Any>()
                map["day"] = day
                map["dayOfCurrentMonth"] = 0
                calendarItems.add(map)
            }
        }

        //get day  of current month
        var week: Int = 0
        for (day in 1..dayOfMonth) {
            val map = mutableMapOf<String, Any>()
            map["day"] = day
            calendar.set(currentYear, currentMonth - 1, day)
            week = shiftIndexOfWeek(calendar.get(Calendar.DAY_OF_WEEK), index)
            map["dayOfCurrentMonth"] = 1
            calendarItems.add(map)
        }

        //get day of next month
        var day = 1
        if (week < 7) {
            for (i in week + 1..7) {
                val map = mutableMapOf<String, Any>()
                map["day"] = day
                day++
                map["dayOfCurrentMonth"] = 0
                calendarItems.add(map)
            }
        }
        calendarPrevious.clear()
        calendar.clear()
    }

    private fun shiftIndexOfWeek(week: Int, index: Int): Int {
        var shiftWeek = week - index
        if (shiftWeek < 1) return 7 + shiftWeek
        return shiftWeek
    }

    private fun getIndexDay(date: Int, isOfMonth: Int): Int {
        for (i in 1..calendarItems.size) {
            if (calendarItems[i - 1]["dayOfCurrentMonth"] == isOfMonth && calendarItems[i - 1]["day"] == date) {
                return i - 1
            }
        }
        return -1
    }

    private fun setMonthYear(year: Int, month: Int) {
        var tvMonthYear = activity!!.findViewById<TextView>(R.id.tv_month_year)
        tvMonthYear.text = "$year ${getNameMonth(month)}"
    }
    private fun getNameMonth(i: Int): String {
        val list = listOf<String>(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        return list[i - 1]
    }

}