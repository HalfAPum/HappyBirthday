package com.narvatov.happybirthday.ui.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.narvatov.happybirthday.R
import com.narvatov.happybirthday.ui.common.DatePickerFieldToModal
import com.narvatov.happybirthday.ui.common.PhotoBottomSheet
import com.narvatov.happybirthday.ui.viewmodel.BirthdayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: BirthdayViewModel,
    onShowModalDatePickerClicked: () -> Unit,
    onShowBirthdayButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            val birthdayUIState by viewModel.sharedUIStateFlow.collectAsState()

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

            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(color = Color.LightGray)
                    .clickable { showBottomSheet = true },
            ) {
                if (birthdayUIState.picture.isNotEmpty()) {
                    AsyncImage(
                        model = birthdayUIState.pictureUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth
                    )
                } else {
                    Text(
                        text = stringResource(R.string.picture),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
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

        if (showBottomSheet) {
            PhotoBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                onPhotoSelected = { viewModel.updatePicture(it) },
            )
        }
    }
}