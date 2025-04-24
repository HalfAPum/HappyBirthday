package com.narvatov.happybirthday.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.narvatov.happybirthday.R
import com.narvatov.happybirthday.model.ui.BirthdayAssetsVariants
import com.narvatov.happybirthday.model.ui.BirthdayScreenUIState
import com.narvatov.happybirthday.ui.common.NumberImage
import com.narvatov.happybirthday.ui.common.PhotoBottomSheet
import com.narvatov.happybirthday.ui.viewmodel.BirthdayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayScreen(
    birthdayAssetsVariants: BirthdayAssetsVariants,
    viewModel: BirthdayViewModel,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.birthdayScreenUiStateFlow.collectAsState(BirthdayScreenUIState("", null, 0))

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    ConstraintLayout (modifier = modifier
        .fillMaxSize()
        .background(color = birthdayAssetsVariants.backgroundColor)
    ) {
        val (topBox, pictureBox, appTitle, background) = createRefs()

        Image(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(R.string.back_button),
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp)
                .clickable { onBackButtonClicked.invoke() }
        )

        Box(modifier = Modifier
            .size(200.dp)
            .constrainAs(pictureBox) {
                bottom.linkTo(appTitle.top, margin = 15.dp)

                start.linkTo(parent.start, margin = 50.dp)
                end.linkTo(parent.end, margin = 50.dp)
            }
        ) {
            Image(
                painter = painterResource(birthdayAssetsVariants.placeholderImage),
                contentDescription = stringResource(R.string.picture_placeholder),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )

            if (uiState.pictureUri != null) {
                AsyncImage(
                    model = uiState.pictureUri,
                    contentDescription = stringResource(R.string.selected_picture),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            border = BorderStroke(6.dp, birthdayAssetsVariants.imageBorderColor),
                            shape = CircleShape,
                        )
                )
            }

            Image(
                painter = painterResource(birthdayAssetsVariants.cameraImage),
                contentDescription = stringResource(R.string.camera_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    // It looks like 45 degrees and is simple solution.
                    // I don't have time and desire for test task to do the math to calculate it's position
                    // But it's pretty simple you have hypotenuse 100.dp and 45 degree angle.
                    // from that you can find all you need.
                    .padding(12.dp)
                    .size(36.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .clickable { showBottomSheet = true }
            )
        }

        Image(
            painter = painterResource(birthdayAssetsVariants.backgroundImage),
            contentDescription = stringResource(R.string.background_image),
            contentScale = ContentScale.Fit,
            alignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize().constrainAs(background) {
                bottom.linkTo(parent.bottom)

                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.constrainAs(topBox) {
                top.linkTo(parent.top, margin = 20.dp)
                bottom.linkTo(pictureBox.top, margin = 15.dp)

                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                // It should be string resource with parameter
                text = stringResource(R.string.today) + " ${uiState.name} " + stringResource(R.string.is_),
                fontSize = 21.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                //Move color to resources or even define text style.
                //Not enough time to do that now
                color = Color(0xFF394562),
                modifier = Modifier.padding(horizontal = 50.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 13.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.left_swirls),
                    contentDescription = stringResource(R.string.left_swirl),
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(start = 22.dp)
                ) {
                    uiState.ageNumberList.forEach { NumberImage(it) }
                }

                Image(
                    painter = painterResource(R.drawable.right_swirls),
                    contentDescription = stringResource(R.string.right_swirl),
                    modifier = Modifier.padding(start = 22.dp)
                )
            }

            Text(
                text = if (uiState.isLessThanYearOld) stringResource(R.string.month_old)
                    else stringResource(R.string.years_old),
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                //Move color to resources or even define text style.
                //Not enough time to do that now
                color = Color(0xFF394562),
                modifier = Modifier.padding(top = 14.dp)
            )
        }

        Text(
            text = stringResource(R.string.app_title),
            fontSize = 22.sp,
            fontWeight = FontWeight.W500,
            color = Color(0xFF394562),
            // In design it's only mentioned to pin to the bottom edge.
            // Padding/ratio were not specified. Assume padding 100 dp from the design (98.37 px)
            modifier = Modifier.constrainAs(appTitle) {
                bottom.linkTo(parent.bottom, margin = 100.dp)

                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        if (showBottomSheet) {
            PhotoBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                onPhotoSelected = { viewModel.updatePicture(it) },
            )
        }
    }
}