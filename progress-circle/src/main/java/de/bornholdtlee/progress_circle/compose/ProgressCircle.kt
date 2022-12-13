package de.bornholdtlee.progress_circle.compose

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

@Stable
data class CircleSpec(
    val color: Color,
    val width: Dp = 8.dp
)

private object ProgressCircleDefaults {

    @Composable
    fun defaultCircleSpecProgress() = CircleSpec(
        color = MaterialTheme.colors.primary,
        width = 8.dp
    )

    @Composable
    fun defaultCircleSpecBackground() = CircleSpec(
        color = MaterialTheme.colors.primary.copy(
            alpha = 0.2f
        ),
        width = 12.dp
    )

    val DefaultAnimationSpec: AnimationSpec<Float> = tween(
        durationMillis = 1000
    )
}

@Composable
fun ProgressCircleAnimated(
    modifier: Modifier = Modifier,
    progress: Float,
    progressCircleSpec: CircleSpec = ProgressCircleDefaults.defaultCircleSpecProgress(),
    backgroundCircleSpec: CircleSpec = ProgressCircleDefaults.defaultCircleSpecBackground(),
    animationSpec: AnimationSpec<Float> = ProgressCircleDefaults.DefaultAnimationSpec,
    onAnimationFinished: ((progress: Float) -> Unit)? = null
) {

    val progressGuarded = progress.coerceIn(0f, 100f)

    val animatedProgress by animateFloatAsState(
        targetValue = progressGuarded,
        animationSpec = animationSpec,
        finishedListener = onAnimationFinished
    )

    ProgressCircle(
        modifier = modifier,
        progress = animatedProgress,
        progressCircleSpec = progressCircleSpec,
        backgroundCircleSpec = backgroundCircleSpec
    )
}

@Composable
fun ProgressCircle(
    modifier: Modifier = Modifier,
    progress: Float,
    progressCircleSpec: CircleSpec = ProgressCircleDefaults.defaultCircleSpecProgress(),
    backgroundCircleSpec: CircleSpec = ProgressCircleDefaults.defaultCircleSpecBackground()
) {
    val progressGuarded = progress.coerceIn(0f, 100f)
    val innerPadding = max(progressCircleSpec.width, backgroundCircleSpec.width) / 2f
    Canvas(
        modifier = modifier.padding(innerPadding)
    ) {
        drawCircle(
            color = backgroundCircleSpec.color,
            style = Stroke(
                width = backgroundCircleSpec.width.toPx()
            )
        )
        drawArc(
            color = progressCircleSpec.color,
            startAngle = -90f,
            sweepAngle = 360f * (progressGuarded / 100f),
            useCenter = false,
            style = Stroke(
                width = progressCircleSpec.width.toPx(),
                cap = StrokeCap.Round,
            )
        )
    }
}

@Preview(name = "Default")
@Composable
private fun Preview_ProgressCircle_Default() {
    ProgressCircle(
        modifier = Modifier.size(100.dp),
        progress = 30f
    )
}

@Preview(name = "No Background")
@Composable
private fun Preview_ProgressCircle_NoBackground() {
    ProgressCircle(
        modifier = Modifier.size(100.dp),
        progress = 30f,
        backgroundCircleSpec = CircleSpec(
            color = Color.Transparent,
            width = 0.dp
        )
    )
}

@Preview(name = "Thicker Progress Stroke")
@Composable
private fun Preview_ProgressCircle_ThickerProgressStroke() {
    ProgressCircle(
        modifier = Modifier.size(100.dp),
        progress = 30f,
        progressCircleSpec = CircleSpec(
            color = MaterialTheme.colors.primary,
            width = 16.dp
        ),
        backgroundCircleSpec = CircleSpec(
            color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
            width = 8.dp
        )
    )
}
