package com.aabhishek.eezyassignment.utils

import java.lang.StringBuilder

fun getFormattedDate(day: Int, date: Int, month: Int, year: Int): String {
    val sb : StringBuilder = StringBuilder()
    sb.append(getDay(day))
    sb.append(" ")
    sb.append(date.toString())
    sb.append(", ")
    sb.append(getMonth(month))
    sb.append(" ")
    sb.append(year.toString())
    return sb.toString()
}

fun getMonth(month: Int) = when (month) {
    1 -> {
        "JAN"
    }

    2 -> {
        "FEB"
    }
    3 -> {
        "MAR"
    }
    4 -> {
        "APR"
    }
    5 -> {
        "MAY"
    }
    6 -> {
        "JUNE"
    }
    7 -> {
        "JULY"
    }
    8 -> {
        "AUG"
    }
    9 -> {
        "SEPT"
    }
    10 -> {
        "OCT"
    }
    11 -> {
        "NOV"
    }
    12 -> {
        "DEC"
    }
    else -> {
        ""
    }
}

fun getDay(day: Int) =
    when (day) {
        0 -> {
            "SUN"
        }

        1 -> {
            "MON"
        }

        2 -> {
            "TUE"
        }

        3 -> {
            "WED"
        }

        4 -> {
            "THU"
        }

        5 -> {
            "FRI"
        }

        6 -> {
            "SAT"
        }

        else -> {
            ""
        }

}

