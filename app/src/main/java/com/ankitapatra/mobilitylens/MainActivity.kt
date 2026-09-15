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
    val implication: String
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
        applicationName,
        dimension.name,
        dimension.implication
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    )
    {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.app_intro)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Dimension ${currentIndex + 1} of ${mobilityDimensions.size}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { (currentIndex + 1).toFloat() / mobilityDimensions.size },
            modifier = Modifier.fillMaxWidth()
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = dimension.name,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.mobile_constraint),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = dimension.constraint)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.developer_implication),
                style = MaterialTheme.typography.titleMedium
            )

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
            Text(stringResource(R.string.previous))
        }

        Button(
            onClick = onNext,
            enabled = canGoNext
        ) {
            Text(stringResource(R.string.next))
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
        Text(stringResource(R.string.analyze))
    }

    if (resultMessage.isNotEmpty()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = resultMessage)
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
        "Input and Interaction",
        "Mobile users mainly interact through touch, gestures, and on-screen keyboards.",
        "Developers should use clear controls, comfortable touch targets, and avoid relying on hover."
    ),
    MobilityDimension(
        "Screen Size, Orientation, and Density",
        "Phones have limited screen space and can vary in orientation and pixel density.",
        "Developers should create responsive layouts that remain readable in different screen conditions."
    ),
    MobilityDimension(
        "Lifecycle and Resource Constraints",
        "Mobile apps can be paused, stopped, recreated, or limited by memory and battery conditions.",
        "Developers should manage state carefully and avoid unnecessary background work."
    ),
    MobilityDimension(
        "Context Awareness",
        "Mobile devices may be used in changing locations, environments, and connectivity conditions.",
        "Developers should design features that adapt appropriately to the user's current context."
    ),
    MobilityDimension(
        "Usage Patterns",
        "Mobile users often interact with apps in short sessions and may switch tasks frequently.",
        "Developers should make important actions quick to access and easy to resume."
    ),
    MobilityDimension(
        "Security and Privacy Expectations",
        "Mobile devices often contain sensitive personal information and private user data.",
        "Developers should collect only necessary data and handle permissions and user information carefully."
    )
)