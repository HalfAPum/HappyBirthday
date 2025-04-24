package com.narvatov.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.narvatov.happybirthday.ui.nav.NanitNavHost
import com.narvatov.happybirthday.ui.theme.HappyBirthdayTheme
import com.narvatov.happybirthday.ui.viewmodel.BirthdayViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    // I would just waste time setting up koin for compose to initialize view model there
    private val birthdayViewModel: BirthdayViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HappyBirthdayTheme {
                NanitNavHost(birthdayViewModel)
            }
        }
    }
}
