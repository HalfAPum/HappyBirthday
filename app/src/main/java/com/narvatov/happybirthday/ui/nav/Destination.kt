package com.narvatov.happybirthday.ui.nav

import kotlinx.serialization.Serializable

sealed class Destination {

    @Serializable
    data object DetailsScreen : Destination()

    @Serializable
    data object BirthdayScreen : Destination()

    @Serializable
    data object DatePickerDialog : Destination()

}