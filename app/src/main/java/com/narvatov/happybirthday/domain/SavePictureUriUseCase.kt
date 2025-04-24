package com.narvatov.happybirthday.domain

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.narvatov.happybirthday.data.datastore.BirthdayDataStore

class SavePictureUriUseCase(
    private val context: Context,
    private val birthdayDataStore: BirthdayDataStore,
) {

    suspend operator fun invoke(picture: Uri) {
        try {
            context.contentResolver.takePersistableUriPermission(
                picture,
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        } catch (e: SecurityException) {
            println(e)
        }

        birthdayDataStore.updatePicture(picture.toString())
    }

}