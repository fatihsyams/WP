package com.example.wp.utils.datepicker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.wp.R
import kotlinx.android.synthetic.main.item_date.view.*
import java.util.*

class CalendarAdapter(
    context: Context,
    var datesInMonth: MutableList<Date> = mutableListOf(),
    val calendar: Calendar,
    private val dateAdapterListener: DateAdapterListener? = null
) : ArrayAdapter<Date>(context, R.layout.item_date, datesInMonth) {

    var currentSelectPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false)
        val currentDate = datesInMonth[position]
        val dateCal = Calendar.getInstance()
        dateCal.time = currentDate

        val dayValue = dateCal.get(Calendar.DAY_OF_MONTH)
        val displayMonth = dateCal.get(Calendar.MONTH) + 1
        val displayYear = dateCal.get(Calendar.YEAR)

        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)

        view.apply {
            tvDate.text = dayValue.toString()

            if (currentSelectPosition == position) {
                onDateSelected(view, tvDate)
            } else {
                onNormalDate(view, tvDate)
            }

            setOnClickListener {
                currentSelectPosition = position
                notifyDataSetChanged()
                dateCal.set(Calendar.MONTH, currentMonth - 1)
                dateCal.set(Calendar.YEAR, currentYear)
                dateAdapterListener?.onSelectDate(dateCal.timeInMillis)
            }

        }

        return view
    }

    private fun onNormalDate(view: View, tvDate: TextView) {
        view.setBackgroundResource(0)
        tvDate.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorPrimary
            )
        )
    }

    private fun onDateInAnotherMonth(view: View, tvDate: TextView) {
        view.setBackgroundResource(0)
        tvDate.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorGrey
            )
        )
    }

    private fun onDateSelected(view: View, tvDate: TextView) {
        view.setBackgroundResource(R.drawable.round_green)
        tvDate.setTextColor(
            ContextCompat.getColor(
                context,
                android.R.color.white
            )
        )
    }

    interface DateAdapterListener {
        fun onSelectDate(date: Long)
    }

}