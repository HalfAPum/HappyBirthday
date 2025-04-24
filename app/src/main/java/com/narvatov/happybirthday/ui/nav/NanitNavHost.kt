package com.narvatov.happybirthday.ui.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.narvatov.happybirthday.model.ui.assetVariantsList
import com.narvatov.happybirthday.ui.dialog.DatePickerModal
import com.narvatov.happybirthday.ui.screen.BirthdayScreen
import com.narvatov.happybirthday.ui.screen.DetailsScreen
import com.narvatov.happybirthday.ui.viewmodel.BirthdayViewModel

@Composable
fun NanitNavHost(birthdayViewModel: BirthdayViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.DetailsScreen,
    ) {
        composable<Destination.DetailsScreen> {
            DetailsScreen(
                viewModel = birthdayViewModel,
                onShowModalDatePickerClicked = {
                    navController.navigate(Destination.DatePickerDialog)
                },
                onShowBirthdayButtonClicked = {
                    // It's better to pass value as a param (or generate in dedicated viewModel to the Birthday screen)
                    // but I already spent enough time on implementation
                    birthdayViewModel.randomizeBirthdayAssetsVariant()
                    navController.navigate(Destination.BirthdayScreen)
                },
            )
        }

        composable<Destination.BirthdayScreen> {
            BirthdayScreen(
                birthdayAssetsVariants = birthdayViewModel.birthdayAssetsVariant,
                viewModel = birthdayViewModel,
                onBackButtonClicked = { navController.popBackStack() },
            )
        }

        dialog<Destination.DatePickerDialog> {
            DatePickerModal(
                onDateSelected = { birthdayViewModel.updateBirthday(it) },
                onDismiss = { navController.popBackStack() },
            )
        }
    }
}