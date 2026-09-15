package com.ankitapatra.mobilitylens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ankitapatra.mobilitylens.ui.theme.MobilityLensTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.outlined.ScreenRotation
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Edit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobilityLensTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MobilityLensScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class MobilityDimension(
    val name: String,
    val constraint: String,
    val implication: String,
    val icon: ImageVector
)
@Composable
fun MobilityLensScreen(modifier: Modifier = Modifier) {

    var currentIndex by remember { mutableIntStateOf(0) }
    var applicationName by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val dimension = mobilityDimensions[currentIndex]
    val blankInputMessage = stringResource(R.string.blank_input_message)
    val analysisResult = stringResource(
        R.string.analysis_result,
        applicationName.trim().replaceFirstChar { it.uppercase() },
        dimension.name,
        dimension.implication
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    )
    {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF294983)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Smartphone,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.18f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.app_intro),
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dimension ${currentIndex + 1} of ${mobilityDimensions.size}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "${kotlin.math.round(((currentIndex + 1).toFloat() / mobilityDimensions.size) * 100).toInt()}% complete",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { (currentIndex + 1).toFloat() / mobilityDimensions.size },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        MobilityDimensionCard(dimension = dimension)

        Spacer(modifier = Modifier.height(32.dp))

        NavigationButtons(
            canGoPrevious = currentIndex > 0,
            canGoNext = currentIndex < mobilityDimensions.lastIndex,
            onPrevious = {
                currentIndex--
                resultMessage = ""
            },
            onNext = {
                currentIndex++
                resultMessage = ""
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(R.string.try_it_yourself),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF17233C)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        AnalyzePanel(
            applicationName = applicationName,
            resultMessage = resultMessage,
            showError = showError,
            onApplicationNameChange = {
                applicationName = it
                showError = false
            },
            onAnalyze = {
                if (applicationName.isBlank()) {
                    showError = true
                    resultMessage = blankInputMessage
                } else {
                    showError = false
                    resultMessage = analysisResult
                }
            }
        )
    }
}

@Composable
fun MobilityDimensionCard(dimension: MobilityDimension) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEAF2FF)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            color = Color(0xFFD7E4FF),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = dimension.icon,
                        contentDescription = null,
                        tint = Color(0xFF2457E6),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dimension.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF17233C)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Smartphone,
                    contentDescription = null,
                    tint = Color(0xFFE53935),
                    modifier = Modifier.width(22.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.mobile_constraint),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFE53935)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = dimension.constraint)

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lightbulb,
                    contentDescription = null,
                    tint = Color(0xFF16834B),
                    modifier = Modifier.width(22.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.developer_implication),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF16834B)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = dimension.implication)
        }
    }
}

@Composable
fun NavigationButtons(
    canGoPrevious: Boolean,
    canGoNext: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onPrevious,
            enabled = canGoPrevious
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(stringResource(R.string.previous))
        }

        Button(
            onClick = onNext,
            enabled = canGoNext
        ) {
            Text(stringResource(R.string.next))

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Composable
fun AnalyzePanel(
    applicationName: String,
    resultMessage: String,
    showError: Boolean,
    onApplicationNameChange: (String) -> Unit,
    onAnalyze: () -> Unit
) {
    OutlinedTextField(
        value = applicationName,
        onValueChange = onApplicationNameChange,
        label = {
            Text(stringResource(R.string.app_feature_label))
        },
        isError = showError,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onAnalyze,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(stringResource(R.string.analyze))
    }

    if (resultMessage.isNotEmpty()) {
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF1F3F6)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF16834B),
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Analysis Result",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF16834B)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = resultMessage,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MobilityLensPreview() {
    MobilityLensTheme {
        MobilityLensScreen()
    }
}
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