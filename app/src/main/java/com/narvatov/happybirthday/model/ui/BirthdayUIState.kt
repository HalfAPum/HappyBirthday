package com.narvatov.happybirthday.model.ui

import androidx.compose.runtime.Immutable

@Immutable
data class BirthdayUIState(
    val name: String,
    val birthday: Long?,
    val picture: String,
) {

    val showBirthdayScreenButtonEnabled: Boolean by lazy {
        name.isNotBlank() && name.isNotEmpty() && birthday != null
    }

}