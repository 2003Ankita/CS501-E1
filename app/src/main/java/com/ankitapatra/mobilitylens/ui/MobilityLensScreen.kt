package com.ankitapatra.mobilitylens.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ankitapatra.mobilitylens.R
import com.ankitapatra.mobilitylens.data.mobilityDimensions
import com.ankitapatra.mobilitylens.ui.components.AnalyzePanel
import com.ankitapatra.mobilitylens.ui.components.MobilityDimensionCard
import com.ankitapatra.mobilitylens.ui.components.NavigationButtons
import com.ankitapatra.mobilitylens.ui.theme.MobilityLensTheme
import kotlin.math.round
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

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
    ) {
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
                text = "${
                    round(
                        ((currentIndex + 1).toFloat() / mobilityDimensions.size) * 100
                    ).toInt()
                }% complete",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = {
                (currentIndex + 1).toFloat() / mobilityDimensions.size
            },
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

@Preview(showBackground = true)
@Composable
fun MobilityLensPreview() {
    MobilityLensTheme {
        MobilityLensScreen()
    }
}