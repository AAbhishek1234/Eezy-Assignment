package com.aabhishek.eezyassignment.utils

import android.util.Log
import android.view.View
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails
import com.aabhishek.eezyassignment.framework.presentation.DoublePlanActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.single_time_slot.view.*

fun DoublePlanActivity.handleDoublePlanDetails(doublePlanDetails: DoublePlanDetails) {
    Log.i("DoublePlanActivity","DoublePlan incoming : $doublePlanDetails")
    morninglayout.apply {
        when (doublePlanDetails.morningRestaurantStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                rest_accept.visibility = View.VISIBLE
                rest_decline.visibility = View.VISIBLE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
            }
        }

        when (doublePlanDetails.morningBarStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                pub_accept.visibility = View.VISIBLE
                pub_decline.visibility = View.VISIBLE
                accepted.visibility = View.GONE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
            }
        }
    }

    noonlayout.apply {
        when (doublePlanDetails.noonRestaurantStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                rest_accept.visibility = View.VISIBLE
                rest_decline.visibility = View.VISIBLE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
            }
        }

        when (doublePlanDetails.noonBarStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                pub_accept.visibility = View.VISIBLE
                pub_decline.visibility = View.VISIBLE
                accepted.visibility = View.GONE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
            }
        }
    }

    afternoonlayout.apply {
        when (doublePlanDetails.afternoonRestaurantStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                rest_accept.visibility = View.VISIBLE
                rest_decline.visibility = View.VISIBLE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
            }
        }

        when (doublePlanDetails.afternoonBarStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                pub_accept.visibility = View.VISIBLE
                pub_decline.visibility = View.VISIBLE
                accepted.visibility = View.GONE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
            }
        }
    }

    eveninglayout.apply {
        when (doublePlanDetails.eveningRestaurantStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                rest_accept.visibility = View.VISIBLE
                rest_decline.visibility = View.VISIBLE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
            }
        }

        when (doublePlanDetails.eveningBarStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                pub_accept.visibility = View.VISIBLE
                pub_decline.visibility = View.VISIBLE
                accepted.visibility = View.GONE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
            }
        }
    }

    nightlayout.apply {
        when (doublePlanDetails.nightRestaurantStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                rest_accept.visibility = View.VISIBLE
                rest_decline.visibility = View.VISIBLE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.VISIBLE
                declined_rest.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                rest_accept.visibility = View.GONE
                rest_decline.visibility = View.GONE
                accepted_rest.visibility = View.GONE
                declined_rest.visibility = View.VISIBLE
            }
        }

        when (doublePlanDetails.nightBarStatus) {
            DoublePlanDetails.STATUS_NONE -> {
                pub_accept.visibility = View.VISIBLE
                pub_decline.visibility = View.VISIBLE
                accepted.visibility = View.GONE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_ACCEPTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.VISIBLE
                declined.visibility = View.GONE
            }

            DoublePlanDetails.STATUS_REJECTED -> {
                pub_accept.visibility = View.GONE
                pub_decline.visibility = View.GONE
                accepted.visibility = View.GONE
                declined.visibility = View.VISIBLE
            }
        }
    }
}