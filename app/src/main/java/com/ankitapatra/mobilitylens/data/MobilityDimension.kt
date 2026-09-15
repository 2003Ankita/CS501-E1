package com.ankitapatra.mobilitylens.data

import androidx.compose.ui.graphics.vector.ImageVector

data class MobilityDimension(
    val name: String,
    val constraint: String,
    val implication: String,
    val icon: ImageVector
)