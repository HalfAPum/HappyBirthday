package com.narvatov.happybirthday.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narvatov.happybirthday.data.datastore.BirthdayDataStore
import com.narvatov.happybirthday.model.ui.BirthdayAssetsVariants
import com.narvatov.happybirthday.model.ui.BirthdayScreenUIState
import com.narvatov.happybirthday.model.ui.DetailsScreenUIState
import com.narvatov.happybirthday.model.ui.assetVariantsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BirthdayViewModel(private val birthdayDataStore: BirthdayDataStore) : ViewModel() {

    private val _sharedUIStateFlow = MutableStateFlow(DetailsScreenUIState("", null, ""))
    val sharedUIStateFlow = _sharedUIStateFlow.asStateFlow()

    val birthdayScreenUiStateFlow = _sharedUIStateFlow.map {
        BirthdayScreenUIState(
            name = it.name,
            pictureUri = if (it.picture.isNotEmpty()) it.pictureUri else null,
            birthday = requireNotNull(it.birthday),
        )
    }

    init {
        viewModelScope.launch {
            _sharedUIStateFlow.emit(
                DetailsScreenUIState(
                    name = birthdayDataStore.getName(),
                    birthday = birthdayDataStore.getBirthday(),
                    picture = birthdayDataStore.getPicture(),
                )
            )
        }
    }

    fun updateName(name: String) {
        // Validation was not in the scope of the task

        _sharedUIStateFlow.update { it.copy(name = name) }
        viewModelScope.launch { birthdayDataStore.updateName(name) }
    }

    fun updateBirthday(birthday: Long?) {
        // Validation was not in the scope of the task

        if (birthday == null) return

        _sharedUIStateFlow.update { it.copy(birthday = birthday) }
        viewModelScope.launch { birthdayDataStore.updateBirthday(birthday) }
    }

    fun updatePicture(pictureUri: Uri?) {
        val picture = pictureUri?.toString() ?: return

        _sharedUIStateFlow.update { it.copy(picture = picture) }
        viewModelScope.launch { birthdayDataStore.updatePicture(picture) }
    }

    var birthdayAssetsVariant: BirthdayAssetsVariants = BirthdayAssetsVariants.ElephantBirthdayAssets
        private set

    fun randomizeBirthdayAssetsVariant() {
        birthdayAssetsVariant = assetVariantsList.random()
    }

}