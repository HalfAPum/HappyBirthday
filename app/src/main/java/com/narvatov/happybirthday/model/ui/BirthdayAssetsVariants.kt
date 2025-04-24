package com.narvatov.happybirthday.model.ui

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import com.narvatov.happybirthday.R

sealed class BirthdayAssetsVariants(
    // Should be android resource or compose defined color
    val backgroundColor: Color,
    val imageBorderColor: Color,
    @DrawableRes
    val backgroundImage: Int,
    @DrawableRes
    val cameraImage: Int,
    @DrawableRes
    val placeholderImage: Int,
) {

    data object ElephantBirthdayAssets : BirthdayAssetsVariants(
        backgroundColor = Color(0xFFFEEFCB),
        imageBorderColor = Color(0xFFFEBE21),
        backgroundImage = R.drawable.bg_elephant,
        cameraImage = R.drawable.camera_elephant,
        placeholderImage = R.drawable.placeholder_elephant,
    )

    data object FoxBirthdayAssets : BirthdayAssetsVariants(
        backgroundColor = Color(0xFFC5E8DF),
        imageBorderColor = Color(0xFF6FC5AF),
        backgroundImage = R.drawable.bg_fox,
        cameraImage = R.drawable.camera_fox,
        placeholderImage = R.drawable.placeholder_fox,
    )

    data object PelicanBirthdayAssets : BirthdayAssetsVariants(
        backgroundColor = Color(0xFFDAF1F6),
        imageBorderColor = Color(0xFF8BD3E4),
        backgroundImage = R.drawable.bg_pelican,
        cameraImage = R.drawable.camera_pelican,
        placeholderImage = R.drawable.placeholder_pelican,
    )

}

val assetVariantsList = listOf(
    BirthdayAssetsVariants.ElephantBirthdayAssets,
    BirthdayAssetsVariants.FoxBirthdayAssets,
    BirthdayAssetsVariants.PelicanBirthdayAssets,
)