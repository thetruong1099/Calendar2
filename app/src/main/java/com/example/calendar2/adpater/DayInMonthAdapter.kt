package com.example.calendar2.adpater

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar2.R
import com.example.calendar2.util.OnDoubleClickListener
import kotlinx.android.synthetic.main.day_in_month_item.view.*

class DayInMonthAdapter(
    private val items: MutableList<MutableMap<String, Any>>,
    private val context: Context,
) :
    RecyclerView.Adapter<DayInMonthAdapter.ViewHolder>() {

    private var row_index = -1
    private var checkClick = 0
    private var currentDay: Int = 0
    private var currentMonth: Int = 0
    private var currentYear: Int = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var date = view.tv_cell_day
        val layoutDayInMonth = view.layout_day_in_month
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.day_in_month_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = items[position]["day"].toString()
        if (currentDay.toString() == items[position]["day"].toString()) {
            holder.date.setTextColor(Color.parseColor("#ff7f50"))
        } else {
            if (items[position]["dayOfCurrentMonth"] == 0) {
                holder.date.setTextColor(Color.parseColor("#80000000"))
            } else {
                holder.date.setTextColor(Color.parseColor("#000000"))
            }
        }

        var sharedPreferences = context.getSharedPreferences("myData", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()


        holder.itemView.setOnClickListener(object : OnDoubleClickListener() {
            override fun onSingleClick(v: View?) {
                row_index = position;
                checkClick = 1
                notifyDataSetChanged()
                editor.putInt("chooseDay", items[position]["day"] as Int)
                editor.putInt("chooseMonth", currentMonth)
                editor.putInt("chooseYear", currentYear)
                editor.putInt("isOfCurrentMonth", items[position]["dayOfCurrentMonth"] as Int)
                editor.putInt("checked", checkClick)
                editor.commit()
            }

            override fun onDoubleClick(v: View?) {
                row_index = position;
                checkClick = 2
                notifyDataSetChanged()
                editor.putInt("chooseDay", items[position]["day"] as Int)
                editor.putInt("chooseMonth", currentMonth)
                editor.putInt("chooseYear", currentYear)
                editor.putInt("isOfCurrentMonth", items[position]["dayOfCurrentMonth"] as Int)
                editor.putInt("checked", checkClick)
                editor.commit()
            }

        })

        if (row_index == position) {
            if (checkClick == 1) {
                row_index = -1
                checkClick = 0
                holder.layoutDayInMonth.setBackgroundResource(R.drawable.back_ground_item_day_in_month)
                holder.date.setTextColor(Color.parseColor("#ffffff"))
            } else if (checkClick == 2) {
                row_index = -1
                checkClick = 0
                holder.layoutDayInMonth.setBackgroundResource(R.drawable.background_item_day_in_mounth2)
                holder.date.setTextColor(Color.parseColor("#ffffff"))
            }
        } else {
            holder.layoutDayInMonth.setBackgroundResource(R.color.white)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setCurrentDay(currentDay: Int) {
        this.currentDay = currentDay
        notifyDataSetChanged()
    }

    fun setCurrentMonthYear(currentMonth: Int, currentYear: Int) {
        this.currentMonth = currentMonth
        this.currentYear = currentYear
        notifyDataSetChanged()
    }

    fun setChooseDay(index: Int, checked: Int) {
        row_index = index
        checkClick = checked
        notifyDataSetChanged()
    }
}

