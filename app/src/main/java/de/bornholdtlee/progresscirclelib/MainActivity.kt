package de.bornholdtlee.progresscirclelib

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import de.bornholdtlee.progress_circle.compose.CircleSpec
import de.bornholdtlee.progress_circle.compose.ProgressCircle
import de.bornholdtlee.progress_circle.compose.ProgressCircleAnimated
import de.bornholdtlee.progresscirclelib.databinding.ActivityMainBinding
import de.bornholdtlee.progresscirclelib.utils.viewBinding

class MainActivity : ComponentActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.composeContainer.setContent {
            MainActivityComposePart()
        }

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.buttonProgressAdd.setOnClickListener {
            binding.progressCircleView.setProgressWithAnimation(
                progress = binding.progressCircleView.progress + 20f,
                animationDuration = 2000L,
                animationInterpolator = OvershootInterpolator(1f)
            ) { newProgress ->
                // React to animation completed event
            }
        }
        binding.buttonProgressSubstract.setOnClickListener {
            binding.progressCircleView.setProgressWithAnimation(
                progress = binding.progressCircleView.progress - 20f,
                animationInterpolator = OvershootInterpolator(1f)
            )
        }
    }
}

@Composable
private fun MainActivityComposePart() {

    var progress: Float by remember {
        mutableStateOf(0f)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProgressCircle(
            modifier = Modifier.size(100.dp),
            progress = progress,
            progressCircleSpec = CircleSpec(
                color = colorResource(id = R.color.primary),
                width = 12.dp
            ),
            backgroundCircleSpec = CircleSpec(
                color = colorResource(id = R.color.primaryVariant),
                width = 14.dp
            )
        )

        ProgressCircleAnimated(
            modifier = Modifier.size(100.dp),
            progress = progress,
            progressCircleSpec = CircleSpec(
                color = colorResource(id = R.color.primary),
                width = 12.dp
            ),
            backgroundCircleSpec = CircleSpec(
                color = colorResource(id = R.color.primaryVariant),
                width = 14.dp
            ),
            animationSpec = tween(
                durationMillis = 500
            ),
            onAnimationFinished = { progress ->
                // React to animation completed event
            }
        )

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                progress += 20f
            }
        ) {
            Text(text = "Progress +")
        }

        Button(
            onClick = {
                progress -= 20f
            }
        ) {
            Text(text = "Progress -")
        }
    }
}
