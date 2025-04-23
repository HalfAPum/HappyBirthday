package com.narvatov.happybirthday.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.happybirthday.R
import com.narvatov.happybirthday.ui.common.DatePickerFieldToModal
import com.narvatov.happybirthday.ui.viewmodel.BirthdayViewModel

@Composable
fun DetailsScreen(
    viewModel: BirthdayViewModel,
    onShowModalDatePickerClicked: () -> Unit,
    onShowBirthdayButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp),
    ) {
        val birthdayUIState by viewModel.birthdayUIStateFlow.collectAsState()

        Text(
            text = stringResource(R.string.app_title),
            style = MaterialTheme.typography.headlineMedium,
        )

        OutlinedTextField(
            value = birthdayUIState.name,
            onValueChange = { viewModel.updateName(it) },
            modifier = Modifier.padding(top = 40.dp)
        )

        DatePickerFieldToModal(
            selectedDate = birthdayUIState.birthday,
            onShowModalDatePickerClicked = onShowModalDatePickerClicked,
            modifier = Modifier.padding(top = 20.dp)
        )

        Box(modifier = Modifier
            .padding(top = 20.dp)
            .size(200.dp)
            .clip(CircleShape)
            .background(color = Color.LightGray)
            .clickable {  },
        ) {
            Text(
                text = stringResource(R.string.picture),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Button(
            onClick = { onShowBirthdayButtonClicked.invoke() },
            enabled = birthdayUIState.showBirthdayScreenButtonEnabled,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.show_birthday_screen),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}