package com.narvatov.happybirthday.model.ui

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class DetailsScreenUIState(
    val name: String,
    val birthday: Long?,
    val picture: String,
) {

    val pictureUri: Uri
        get() = Uri.parse(picture)

    val showBirthdayScreenButtonEnabled: Boolean by lazy {
        name.isNotBlank() && name.isNotEmpty() && birthday != null
    }

}