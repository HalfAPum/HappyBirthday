package com.narvatov.happybirthday.ui.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.narvatov.happybirthday.R

@Composable
fun NumberImage(number: Int) {
    val imageRes = when (number) {
        0 -> R.drawable.number0
        1 -> R.drawable.number1
        2 -> R.drawable.number2
        3 -> R.drawable.number3
        4 -> R.drawable.number4
        5 -> R.drawable.number5
        6 -> R.drawable.number6
        7 -> R.drawable.number7
        8 -> R.drawable.number8
        9 -> R.drawable.number9
        else -> null
    }

    imageRes?.let {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
        )
    }
}