package com.aabhishek.eezyassignment.utils

import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.*

abstract class DebouncedCalendarListener(
    private val delaySelection: Long,
    private val delayScroll: Long
): HorizontalCalendarListener() {

    private var lastAllowedScroll: Long = 0L
    private var lastAllowedSelection: Long = 0L

    override fun onDateSelected(date: Calendar?, position: Int) {
        val currTime = System.currentTimeMillis()
        if (currTime - lastAllowedSelection > delaySelection) {
            lastAllowedSelection = currTime
            onDebouncedDateSelected(date, position)
        }
    }

    override fun onCalendarScroll(calendarView: HorizontalCalendarView?, dx: Int, dy: Int) {
        val currTime = System.currentTimeMillis()
        if (currTime - lastAllowedScroll > delayScroll) {
            lastAllowedScroll = currTime
            onDebouncedScroll(calendarView, dx, dy)
        }
    }

    abstract fun onDebouncedDateSelected(date: Calendar?, position: Int)
    abstract fun onDebouncedScroll(calendarView: HorizontalCalendarView?, dx: Int, dy: Int)
}