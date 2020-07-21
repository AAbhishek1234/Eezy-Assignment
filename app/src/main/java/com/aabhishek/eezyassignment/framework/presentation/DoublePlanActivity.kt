package com.aabhishek.eezyassignment.framework.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aabhishek.eezyassignment.R
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails
import com.aabhishek.eezyassignment.framework.DoublePlanApplication
import com.aabhishek.eezyassignment.framework.presentation.viewmodel.DoublePlanViewModel
import com.aabhishek.eezyassignment.utils.handleDoublePlanDetails
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendar
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.calendar_item_default.view.*
import kotlinx.android.synthetic.main.calendar_item_selected.view.*
import kotlinx.android.synthetic.main.single_time_slot.view.*
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class DoublePlanActivity : AppCompatActivity() {
    private val TAG = "DoublePlanActivity"
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: DoublePlanViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var singleRowCalendar: SingleRowCalendar
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as DoublePlanApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        back.setOnClickListener {
            finish()
        }
        setupObservers()
        setUpCalendar()
        setClickListeners()
        Log.i(TAG, "viewModel initialized : $viewModel")
    }

    private fun setupObservers() {
        viewModel.currentDate.observe(this, Observer {
            handleDateChange(it)
        })

        viewModel.doublePlanDetails.observe(this, Observer {
            handleDoublePlanDetails(it)
        })
    }


    private fun handleDateChange(date: Date) {
        current_date.text = DateUtils.getDay3LettersName(date) +
                " " +
                DateUtils.getDayNumber(date) +
                ", " +
                DateUtils.getMonth3LettersName(date) +
                " " +
                DateUtils.getYear(date)

        viewModel.fetchDoublePlan(calculateDayDiff(date))
    }

    //calculates number of days between the date passed and 1st jan 2010
    private fun calculateDayDiff(date: Date) : Long {
        val sdate1 = "01/01/2010"
        val date1 = SimpleDateFormat("dd/MM/yyyy").parse(sdate1)
        val duration = date.time - date1.time

        return TimeUnit.MILLISECONDS.toDays(duration)
    }

    private fun setUpCalendar() {
        val myCalendarViewManager = object : CalendarViewManager {
            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                if (isSelected) {
                    holder.itemView.day_of_week_selected.text = DateUtils.getDay1LetterName(date)
                    holder.itemView.date_selected.text = DateUtils.getDayNumber(date)
                } else {
                    holder.itemView.day_of_week.text = DateUtils.getDay1LetterName(date)
                    holder.itemView.date.text = DateUtils.getDayNumber(date)
                }
            }

            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                return when (isSelected) {
                    false -> R.layout.calendar_item_default
                    true -> R.layout.calendar_item_selected
                }
            }
        }

        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                return true
            }
        }

        val myCalendarChangesObserver = object : CalendarChangesObserver {
            override fun whenWeekMonthYearChanged(
                weekNumber: String,
                monthNumber: String,
                monthName: String,
                year: String,
                date: Date
            ) {
                super.whenWeekMonthYearChanged(weekNumber, monthNumber, monthName, year, date)
            }

            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                Log.i(TAG, "date selected : ${formatter.format(date)}")
                viewModel.setDate(date)
            }
        }
        singleRowCalendar = calendar_view.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            futureDaysCount = 60
            includeCurrentDate = true
            init()
        }
        singleRowCalendar.select(0)
//        singleRowCalendar.suppressLayout(true)

        singleRowCalendar.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
            }
        })
        calendar_view.setOnTouchListener(object : View.OnTouchListener {

            private var x1: Float = 0F

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        x1 = event.x
                    }

                    MotionEvent.ACTION_UP -> {
                        val x2 = event.x
                        val deltaX = x2 - x1
                        if (deltaX >= 50) {
                            moveOneWeekBackward()
                        } else if (deltaX <= -50){
                            moveOneWeekForward()
                        }
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        val x2 = event.x
                        val deltaX = x2 - x1
                        if (deltaX >= 50) {
                            moveOneWeekBackward()
                        } else if (deltaX <= -50){
                            moveOneWeekForward()
                        }
                    }
                }
                return false
            }
        })

        //Set todays date to current
        viewModel.setDate(Date())
    }

    private fun moveOneWeekForward() {
        val isItemSelected = singleRowCalendar.getSelectedIndexes().isNotEmpty()
        var currentIndex: Int = 0
        var newSelectedIndex: Int = 0
        if (isItemSelected) {
            currentIndex = singleRowCalendar.getSelectedIndexes().get(0)
            newSelectedIndex = min(currentIndex + 7,singleRowCalendar.getDates().size - 1)
        }
        val firstVisibleIndex = (singleRowCalendar.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val newFirstVisibleIndex = min(firstVisibleIndex + 7, singleRowCalendar.getDates().size - 1)

        singleRowCalendar.apply {
            //suppressLayout(false)
            clearSelection()
            if (isItemSelected) {
                setItemsSelected(listOf(newSelectedIndex), true)
            }
            //smoothScrollToPosition(newIndex)
            (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(newFirstVisibleIndex, 0)
            //suppressLayout(true)
        }
    }

    private fun moveOneWeekBackward() {
        val isItemSelected = singleRowCalendar.getSelectedIndexes().isNotEmpty()
        var currentIndex: Int = 0
        var newIndex: Int = 0
        if (isItemSelected) {
            currentIndex = singleRowCalendar.getSelectedIndexes().get(0)
            newIndex = max(currentIndex - 7, 0)
        }

        val firstVisibleIndex = (singleRowCalendar.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val newFirstVisibleIndex = max(firstVisibleIndex - 7, 0)

        singleRowCalendar.apply {
            //suppressLayout(false)
            clearSelection()
            if (isItemSelected) {
                setItemsSelected(listOf(newIndex), true)
            }
            //smoothScrollToPosition(newIndex)
            (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(newFirstVisibleIndex, 0)
            //suppressLayout(true)
        }
    }

    private fun setClickListeners() {

        // morning accept / decline buttons & expand buttons and setting text

        for (i in listOf(morninglayout, noonlayout, afternoonlayout, eveninglayout, nightlayout)) {
            i.apply {
                expand.setOnClickListener {
                    afternooncardheader.visibility = View.GONE
                    afternoonexpanded.visibility = View.VISIBLE
                }
                afternoonexapandedheader.setOnClickListener {
                    afternooncardheader.visibility = View.VISIBLE
                    afternoonexpanded.visibility = View.GONE
                }
            }
        }

        morninglayout.apply {
            timezone.text = "Morning"
            zone_image.setImageResource(R.drawable.ic_sun)
            zone_temp.text = "20 C"

            zone_time_expanded.text = "Morning"
            zone_image_expanded.setImageResource(R.drawable.ic_sun)
            zone_temp_expanded.text = "20 C"

            pub_accept.setOnClickListener {
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.MORNING, UserAction.ACCEPTED, OutingType.BAR)
            }

            pub_decline.setOnClickListener {
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.MORNING, UserAction.DECLINED, OutingType.BAR)

            }

            rest_accept.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.MORNING, UserAction.ACCEPTED, OutingType.RESTAURANT)

            }

            rest_decline.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
                viewModel.onUserAction(TimeOfDay.MORNING, UserAction.DECLINED, OutingType.RESTAURANT)

            }
        }

        noonlayout.apply {
            timezone.text = "Noon"
            zone_image.setImageResource(R.drawable.ic_rain___grey)
            zone_temp.text = "18 C"

            zone_time_expanded.text = "Noon"
            zone_image_expanded.setImageResource(R.drawable.ic_rain___grey)
            zone_temp_expanded.text = "18 C"

            pub_accept.setOnClickListener {
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.NOON, UserAction.ACCEPTED, OutingType.BAR)
            }

            pub_decline.setOnClickListener {
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.NOON, UserAction.DECLINED, OutingType.BAR)
            }

            rest_accept.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.NOON, UserAction.ACCEPTED, OutingType.RESTAURANT)
            }

            rest_decline.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
                viewModel.onUserAction(TimeOfDay.NOON, UserAction.DECLINED, OutingType.RESTAURANT)
            }
        }

        afternoonlayout.apply {
            timezone.text = "Afternoon"
            zone_image.setImageResource(R.drawable.ic_rain___grey)
            zone_temp.text = "18 C"

            zone_time_expanded.text = "Afternoon"
            zone_image_expanded.setImageResource(R.drawable.ic_rain___grey)
            zone_temp_expanded.text = "18 C"

            pub_accept.setOnClickListener {
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.AFTERNOON, UserAction.ACCEPTED, OutingType.BAR)
            }

            pub_decline.setOnClickListener {
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.AFTERNOON, UserAction.DECLINED, OutingType.BAR)
            }

            rest_accept.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.AFTERNOON, UserAction.ACCEPTED, OutingType.RESTAURANT)
            }

            rest_decline.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
                viewModel.onUserAction(TimeOfDay.AFTERNOON, UserAction.DECLINED, OutingType.RESTAURANT)
            }
        }

        nightlayout.apply {
            timezone.text = "Night"
            zone_image.setImageResource(R.drawable.ic_rain___grey)
            zone_temp.text = "12 C"

            zone_time_expanded.text = "Night"
            zone_image_expanded.setImageResource(R.drawable.ic_rain___grey)
            zone_temp_expanded.text = "12 C"

            pub_accept.setOnClickListener {
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.NIGHT, UserAction.ACCEPTED, OutingType.BAR)
            }

            pub_decline.setOnClickListener {
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.NIGHT, UserAction.DECLINED, OutingType.BAR)
            }

            rest_accept.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.NIGHT, UserAction.ACCEPTED, OutingType.RESTAURANT)

            }

            rest_decline.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
                viewModel.onUserAction(TimeOfDay.NIGHT, UserAction.DECLINED, OutingType.BAR)

            }
        }

        eveninglayout.apply {
            timezone.text = "Evening"
            zone_image.setImageResource(R.drawable.ic_rain___grey)
            zone_temp.text = "16 C"

            zone_time_expanded.text = "Evening"
            zone_image_expanded.setImageResource(R.drawable.ic_rain___grey)
            zone_temp_expanded.text = "16 C"

            pub_accept.setOnClickListener {
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.EVENING, UserAction.ACCEPTED, OutingType.BAR)

            }

            pub_decline.setOnClickListener {
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.EVENING, UserAction.DECLINED, OutingType.BAR)

            }

            rest_accept.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.GONE
                viewModel.onUserAction(TimeOfDay.EVENING, UserAction.ACCEPTED, OutingType.RESTAURANT)

            }

            rest_decline.setOnClickListener {
                rest_accept.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                rest_decline.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
                viewModel.onUserAction(TimeOfDay.EVENING, UserAction.DECLINED, OutingType.RESTAURANT)

            }
        }
    }

    companion object {
        enum class TimeOfDay {
            MORNING,
            NOON,
            AFTERNOON,
            EVENING,
            NIGHT
        }

        enum class UserAction {
            ACCEPTED,
            DECLINED
        }

        enum class OutingType {
            RESTAURANT,
            BAR
        }
    }

}