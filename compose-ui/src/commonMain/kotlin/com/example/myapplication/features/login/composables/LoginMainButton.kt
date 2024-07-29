package com.example.myapplication.features.login.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
internal fun LoginMainButton(
    modifier: Modifier,
    text: String,
    isLoading: Boolean,
    isClickable: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = {
            if (isClickable) onClick()
        },
        colors = ButtonDefaults.buttonColors(),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        if (isLoading) {
            PulsatingLoadingSpinner(
                modifier = Modifier.size(30.dp)
                    .padding(4.dp)
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun PulsatingLoadingSpinner(
    modifier: Modifier = Modifier,
) {
    val color = Color.White
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 270f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier) {
        drawArc(
            color = color,
            startAngle = angle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = 4.dp.toPx())
        )
    }
}
