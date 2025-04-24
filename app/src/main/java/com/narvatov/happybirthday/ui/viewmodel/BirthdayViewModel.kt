package com.narvatov.happybirthday.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narvatov.happybirthday.data.datastore.BirthdayDataStore
import com.narvatov.happybirthday.model.ui.BirthdayUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BirthdayViewModel(private val birthdayDataStore: BirthdayDataStore) : ViewModel() {

    private val _birthdayUIStateFlow = MutableStateFlow(BirthdayUIState("", null, ""))
    val birthdayUIStateFlow = _birthdayUIStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _birthdayUIStateFlow.emit(
                BirthdayUIState(
                    name = birthdayDataStore.getName(),
                    birthday = birthdayDataStore.getBirthday(),
                    picture = birthdayDataStore.getPicture(),
                )
            )
        }
    }

    fun updateName(name: String) {
        _birthdayUIStateFlow.update { it.copy(name = name) }
        viewModelScope.launch { birthdayDataStore.updateName(name) }
    }

    fun updateBirthday(birthday: Long?) {
        if (birthday == null) return

        _birthdayUIStateFlow.update { it.copy(birthday = birthday) }
        viewModelScope.launch { birthdayDataStore.updateBirthday(birthday) }
    }

    fun updatePicture(pictureUri: Uri?) {
        val picture = pictureUri?.toString() ?: return

        _birthdayUIStateFlow.update { it.copy(picture = picture) }
        viewModelScope.launch { birthdayDataStore.updatePicture(picture) }
    }

}