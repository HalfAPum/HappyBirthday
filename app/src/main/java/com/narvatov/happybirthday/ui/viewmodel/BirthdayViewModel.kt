package com.narvatov.happybirthday.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narvatov.happybirthday.model.ui.BirthdayUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BirthdayViewModel : ViewModel() {

    private val _birthdayUIStateFlow = MutableStateFlow(BirthdayUIState("", null, ""))
    val birthdayUIStateFlow = _birthdayUIStateFlow.asStateFlow()

    fun updateName(name: String) {
        updateUIState { it.copy(name = name) }
    }

    fun updateBirthday(birthday: Long?) {
        if (birthday == null) return

        updateUIState { it.copy(birthday = birthday) }
    }

    fun updatePicture(picture: String) {
        updateUIState { it.copy(picture = picture) }
    }

    private fun updateUIState(updateStateCallback: (BirthdayUIState) -> BirthdayUIState) {
        viewModelScope.launch {
            _birthdayUIStateFlow.update {
                updateStateCallback.invoke(it)
            }
        }
    }

}