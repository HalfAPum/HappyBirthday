package com.narvatov.happybirthday.utils

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir,
    )
    return image
}

fun getAgeYears(date: Date): Int {
    val dob: Calendar = Calendar.getInstance()
    val today: Calendar = Calendar.getInstance()

    dob.time = date

    val year: Int = dob.get(Calendar.YEAR)
    val month: Int = dob.get(Calendar.MONTH)
    val day: Int = dob.get(Calendar.DAY_OF_MONTH)

    dob.set(year, month + 1, day)

    var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    return age
}

fun getAgeMonths(date: Date): Int {
    val dob: Calendar = Calendar.getInstance()
    val today: Calendar = Calendar.getInstance()

    dob.time = date

    val dobYear: Int = dob.get(Calendar.YEAR)
    val dobMonth: Int = dob.get(Calendar.MONTH)
    val todayYear: Int = today.get(Calendar.YEAR)
    val todayMonth: Int = today.get(Calendar.MONTH)

    var monthDifference: Int = (todayYear - dobYear) * 12 + (todayMonth - dobMonth)

    if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
        monthDifference--
    }

    return monthDifference
}