package com.example.wp.utils.datePicker

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.LinearLayout
import com.example.wp.R
import com.example.wp.utils.changeFormatLongDate
import com.example.wp.utils.constants.DateFormat
import com.example.wp.utils.datePicker.adapter.CalendarAdapter
import com.example.wp.utils.emptyString
import com.example.wp.utils.getCurrentDate
import kotlinx.android.synthetic.main.dialog_date_picker.*
import java.text.SimpleDateFormat
import java.util.*

class
DialogDatePicker(context: Context, private var dateDialogListener: DateDialogListener? = null) : Dialog(context), CalendarAdapter.DateAdapterListener {

    private val dateAdapter: CalendarAdapter by lazy {
        CalendarAdapter(
            context = context,
            datesInMonth = datesInMonth,
            calendar = cal,
            dateAdapterListener = this
        )
    }

    private var datesInMonth: MutableList<Date> = mutableListOf()

    private val maxColumn = 42

    private var cal = Calendar.getInstance(Locale("in", "ID"))

    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale("in", "ID"))

    private var currentSelectedDate = emptyString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_date_picker)
        val width = (context.resources.displayMetrics.widthPixels * 0.80).toInt()
        window?.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        gvCalendar.adapter = dateAdapter

        setupCalendar()

        ivLeft.setOnClickListener {
            datesInMonth = mutableListOf()
            cal.add(Calendar.MONTH, -1)
            setupCalendar()
        }

        ivRight.setOnClickListener {
            datesInMonth = mutableListOf()
            cal.add(Calendar.MONTH, 1)
            setupCalendar()
        }

    }

    private fun setupCalendar() {
        gvCalendar.verticalSpacing = 2
        gvCalendar.horizontalSpacing = 2

        val mCal = cal.clone() as Calendar
        mCal.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth)

        while (datesInMonth.size < maxColumn) {
            datesInMonth.add(mCal.time)
            mCal.add(Calendar.DAY_OF_MONTH, 1)
        }

        val month = dateFormat.format(cal.time)

        if (month != getCurrentDate("MMMMM")) dateAdapter.currentSelectPosition = -1

        tvMonth.text = month

        dateAdapter.datesInMonth = datesInMonth
        dateAdapter.notifyDataSetChanged()
    }

    override fun onSelectDate(date: Long) {
        currentSelectedDate =  changeFormatLongDate(date, DateFormat.UI_DATE_FORMAT_FULL_MONTH)
        dateDialogListener?.onSelectDate(currentSelectedDate)
        dismiss()
    }

    interface DateDialogListener {
        fun onSelectDate(date:String)
    }


}