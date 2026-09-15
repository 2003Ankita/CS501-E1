package com.ankitapatra.mobilitylens.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ankitapatra.mobilitylens.R

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