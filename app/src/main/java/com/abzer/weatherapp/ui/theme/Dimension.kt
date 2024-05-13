package com.abzer.weatherapp.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val screen_padding: Dp = Dp.Unspecified,
    val inner_padding: Dp = Dp.Unspecified,
    val button_inner_padding: Dp = Dp.Unspecified,
    val textfield_radius: Dp = Dp.Unspecified,
    val button_primary_radius: Dp = Dp.Unspecified,
    val progressbar_stroke: Dp = Dp.Unspecified,
    val progressbar_size: Dp = Dp.Unspecified,
    val navigation_bar_height: Dp = Dp.Unspecified,
    val navigation_bar_indicator_thickness: Dp = Dp.Unspecified,
    val navigation_bar_item_space: Dp = Dp.Unspecified,
    val navigation_bar_divider_width: Dp = Dp.Unspecified,
    val cardPadding: Dp = Dp.Unspecified,
    val cardMargin: Dp = Dp.Unspecified,
    val cardRadius: Dp = Dp.Unspecified,

    val itemUserImage: Dp = Dp.Unspecified,
)

val smallDimensions = Dimensions(
    screen_padding = 10.dp,
    inner_padding = 6.dp,
    button_inner_padding = 10.dp,
    textfield_radius = 10.dp,
    button_primary_radius = 10.dp,
    progressbar_stroke = 4.dp,
    progressbar_size = 40.dp,
    navigation_bar_height= 60.dp,
    navigation_bar_indicator_thickness = 3.dp,
    navigation_bar_item_space = 10.dp,
    navigation_bar_divider_width = 2.dp,
    cardPadding = 6.dp,
    cardMargin = 6.dp,
    cardRadius = 10.dp,
    itemUserImage = 30.dp,
)

val sw360Dimensions = Dimensions(
    screen_padding = 20.dp,
    inner_padding = 10.dp,
    button_inner_padding = 15.dp,
    textfield_radius = 20.dp,
    button_primary_radius = 20.dp,
    progressbar_stroke = 6.dp,
    progressbar_size = 60.dp,
    navigation_bar_height= 70.dp,
    navigation_bar_indicator_thickness = 5.dp,
    navigation_bar_item_space = 20.dp,
    navigation_bar_divider_width = 3.dp,
    cardPadding = 10.dp,
    cardMargin = 10.dp,
    cardRadius = 20.dp,
    itemUserImage = 45.dp,
)