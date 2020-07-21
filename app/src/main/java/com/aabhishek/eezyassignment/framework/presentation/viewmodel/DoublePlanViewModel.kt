package com.aabhishek.eezyassignment.framework.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails.Companion.STATUS_ACCEPTED
import com.aabhishek.eezyassignment.business.domain.entity.DoublePlanDetails.Companion.STATUS_REJECTED
import com.aabhishek.eezyassignment.business.domain.usecases.FetchDoublePlanDetails
import com.aabhishek.eezyassignment.business.domain.usecases.InsertDoublePlanDetails
import com.aabhishek.eezyassignment.framework.presentation.DoublePlanActivity
import com.aabhishek.eezyassignment.framework.presentation.DoublePlanActivity.Companion
import com.aabhishek.eezyassignment.framework.presentation.DoublePlanActivity.Companion.OutingType
import com.aabhishek.eezyassignment.framework.presentation.DoublePlanActivity.Companion.TimeOfDay
import com.aabhishek.eezyassignment.framework.presentation.DoublePlanActivity.Companion.UserAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoublePlanViewModel
@Inject
constructor(
    private val fetchDoublePlanDetails: FetchDoublePlanDetails,
    private val insertDoublePlanDetails: InsertDoublePlanDetails
) : ViewModel() {
    private val TAG = "DoublePlanViewModel"

    private val _currentDate: MutableLiveData<Date> = MutableLiveData()

    val currentDate: LiveData<Date>
        get() = _currentDate

    private val _doublePlanDetails: MutableLiveData<DoublePlanDetails> = MutableLiveData()

    val doublePlanDetails: LiveData<DoublePlanDetails>
        get() = _doublePlanDetails


    fun fetchDoublePlan(dateDiff: Long) {
        val job: Flow<DoublePlanDetails?> = fetchDoublePlanDetails(dateDiff)
        job.onEach { doublePlanDetails ->
            withContext(Dispatchers.Main) {
                doublePlanDetails?.let {
                    _doublePlanDetails.value = it
                }
            }
        }.launchIn(
            CoroutineScope(Dispatchers.IO)
        )
    }

    private fun insertDoublePlan(doublePlanDetails: DoublePlanDetails) {
        Log.i(TAG,"insertDoublePlan : $doublePlanDetails")
        _doublePlanDetails.value = doublePlanDetails
        insertDoublePlanDetails(doublePlanDetails).launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun setDate(date: Date) {
        _currentDate.value = date
    }

    fun onUserAction(timeOfDay: TimeOfDay, action: UserAction, outingType: OutingType) {
        when (timeOfDay) {
            TimeOfDay.MORNING -> {
                onMorningAction(action, outingType)
            }

            TimeOfDay.NOON -> {
                onNoonAction(action, outingType)
            }

            TimeOfDay.AFTERNOON -> {
                onAfterNoonAction(action, outingType)
            }

            TimeOfDay.EVENING -> {
                onEveningAction(action, outingType)
            }

            TimeOfDay.NIGHT -> {
                onNightAction(action, outingType)
            }
        }
    }

    private fun onMorningAction(action: UserAction, outingType: OutingType) {
        val doublePlanDetails = _doublePlanDetails.value
        when(action) {
            UserAction.ACCEPTED -> {
                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.morningRestaurantStatus = STATUS_ACCEPTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.morningBarStatus = STATUS_ACCEPTED
                    }
                }
            }

            UserAction.DECLINED -> {
                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.morningRestaurantStatus = STATUS_REJECTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.morningBarStatus = STATUS_REJECTED
                    }
                }
            }
        }
        insertDoublePlan(doublePlanDetails!!)
    }

    private fun onNoonAction(action: UserAction, outingType: OutingType) {
        val doublePlanDetails = _doublePlanDetails.value
        when(action) {
            UserAction.ACCEPTED -> {
                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.noonRestaurantStatus = STATUS_ACCEPTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.noonBarStatus = STATUS_ACCEPTED
                    }
                }
            }

            UserAction.DECLINED -> {
                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.noonRestaurantStatus = STATUS_REJECTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.noonBarStatus = STATUS_REJECTED
                    }
                }
            }
        }
        insertDoublePlan(doublePlanDetails!!)
    }

    private fun onAfterNoonAction(action: UserAction, outingType: OutingType) {
        val doublePlanDetails = _doublePlanDetails.value
        when(action) {
            UserAction.ACCEPTED -> {

                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.afternoonRestaurantStatus = STATUS_ACCEPTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.afternoonBarStatus = STATUS_ACCEPTED
                    }
                }
            }

            UserAction.DECLINED -> {
                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.afternoonRestaurantStatus = STATUS_REJECTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.afternoonBarStatus = STATUS_REJECTED
                    }
                }
            }
        }
        insertDoublePlan(doublePlanDetails!!)
    }

    private fun onEveningAction(action: UserAction, outingType: OutingType) {
        val doublePlanDetails = _doublePlanDetails.value
        when(action) {
            UserAction.ACCEPTED -> {

                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.eveningRestaurantStatus = STATUS_ACCEPTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.eveningBarStatus = STATUS_ACCEPTED
                    }
                }
            }

            UserAction.DECLINED -> {
                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.eveningRestaurantStatus = STATUS_REJECTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.eveningBarStatus = STATUS_REJECTED
                    }
                }
            }
        }
        insertDoublePlan(doublePlanDetails!!)
    }

    private fun onNightAction(action: UserAction, outingType: OutingType) {
        val doublePlanDetails = _doublePlanDetails.value
        when(action) {
            UserAction.ACCEPTED -> {

                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.nightRestaurantStatus = STATUS_ACCEPTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.nightBarStatus = STATUS_ACCEPTED
                    }
                }
            }

            UserAction.DECLINED -> {
                when(outingType) {
                    OutingType.RESTAURANT -> {
                        doublePlanDetails?.nightRestaurantStatus = STATUS_REJECTED
                    }

                    OutingType.BAR -> {
                        doublePlanDetails?.nightBarStatus = STATUS_REJECTED
                    }
                }
            }
        }
        insertDoublePlan(doublePlanDetails!!)
    }

}