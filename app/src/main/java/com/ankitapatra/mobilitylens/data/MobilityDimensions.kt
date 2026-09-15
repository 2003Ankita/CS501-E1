package com.ankitapatra.mobilitylens.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.ScreenRotation
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.TouchApp

val mobilityDimensions = listOf(
    MobilityDimension(
        name = "Input and Interaction",
        constraint = "Mobile users mainly interact through touch, gestures, and on-screen keyboards.",
        implication = "Developers should use clear controls, comfortable touch targets, and avoid relying on hover.",
        icon = Icons.Outlined.TouchApp
    ),
    MobilityDimension(
        name = "Screen Size, Orientation, and Density",
        constraint = "Phones have limited screen space and can vary in orientation and pixel density.",
        implication = "Developers should create responsive layouts that remain readable in different screen conditions.",
        icon = Icons.Outlined.ScreenRotation
    ),
    MobilityDimension(
        name = "Lifecycle and Resource Constraints",
        constraint = "Mobile apps can be paused, stopped, recreated, or limited by memory and battery conditions.",
        implication = "Developers should manage state carefully and avoid unnecessary background work.",
        icon = Icons.Outlined.BatteryChargingFull
    ),
    MobilityDimension(
        name = "Context Awareness",
        constraint = "Mobile devices may be used in changing locations, environments, and connectivity conditions.",
        implication = "Developers should design features that adapt appropriately to the user's current context.",
        icon = Icons.Outlined.LocationOn
    ),
    MobilityDimension(
        name = "Usage Patterns",
        constraint = "Mobile users often interact with apps in short sessions and may switch tasks frequently.",
        implication = "Developers should make important actions quick to access and easy to resume.",
        icon = Icons.Outlined.Schedule
    ),
    MobilityDimension(
        name = "Security and Privacy Expectations",
        constraint = "Mobile devices often contain sensitive personal information and private user data.",
        implication = "Developers should collect only necessary data and handle permissions and user information carefully.",
        icon = Icons.Outlined.Security
    )
)