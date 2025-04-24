package com.narvatov.happybirthday.model.ui

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.narvatov.happybirthday.utils.getAgeMonths
import com.narvatov.happybirthday.utils.getAgeYears
import java.util.Date

@Immutable
data class BirthdayScreenUIState(
    val name: String,
    val pictureUri: Uri?,
    val birthday: Long,
) {

    private val birthdayDate = Date(birthday)

    val isLessThanYearOld: Boolean = getAgeYears(birthdayDate) == 0

    // Can either indicate months or years
    val ageNumberList: List<Int> by lazy {
        val ageNumber = if (isLessThanYearOld) getAgeMonths(birthdayDate) else getAgeYears(birthdayDate)
        ageNumber.toString().map { it.digitToInt() }
    }

}