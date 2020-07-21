package com.aabhishek.eezyassignment.business.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DoublePlanDetails")
data class DoublePlanDetails(
    @PrimaryKey(autoGenerate = false)
    val dateDiff: Long,

    @ColumnInfo(name = "Morning Restaurant Status")
    var morningRestaurantStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Morning Bar Status")
    var morningBarStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Evening Restaurant Status")
    var eveningRestaurantStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Evening Bar Status")
    var eveningBarStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Night Restaurant Status")
    var nightRestaurantStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Night Bar Status")
    var nightBarStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Noon Restaurant Status")
    var noonRestaurantStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Noon Bar Status")
    var noonBarStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Afternoon Restaurant Status")
    var afternoonRestaurantStatus: String = STATUS_NONE,

    @ColumnInfo(name = "Afternoon Bar Status")
    var afternoonBarStatus: String = STATUS_NONE

) {

    companion object {
        const val STATUS_ACCEPTED = "accepted"
        const val STATUS_REJECTED = "rejected"
        const val STATUS_NONE = "none"
    }
}