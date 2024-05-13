package com.abzer.weatherapp.ui.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class FontDimensions(
    val headingPrimary: TextUnit = TextUnit.Unspecified,
    val headingSecondary: TextUnit = TextUnit.Unspecified,
    val titlePrimary: TextUnit = TextUnit.Unspecified,
    val titleSecondary: TextUnit = TextUnit.Unspecified,
    val bodyPrimary: TextUnit = TextUnit.Unspecified,
    val bodySecondary: TextUnit = TextUnit.Unspecified,
)

val smallFontDimensions = FontDimensions(
    headingPrimary = 20.sp,
    headingSecondary = 18.sp,
    titlePrimary = 15.sp,
    titleSecondary = 13.sp,
    bodyPrimary = 12.sp,
    bodySecondary = 10.sp,
)

val sw360FontDimensions = FontDimensions(
    headingPrimary = 22.sp,
    headingSecondary = 20.sp,
    titlePrimary = 18.sp,
    titleSecondary = 16.sp,
    bodyPrimary = 15.sp,
    bodySecondary = 13.sp,
)