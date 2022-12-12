package de.bornholdtlee.progresscirclelib

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import de.bornholdtlee.progresscirclelib.databinding.ActivityMainBinding
import de.bornholdtlee.progresscirclelib.utils.viewBinding

class MainActivity : ComponentActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.buttonProgressAdd.setOnClickListener {
            binding.progressCircleView.setProgressWithAnimation(
                progress = binding.progressCircleView.progress + 20f,
                animationInterpolator = OvershootInterpolator(1f)
            )
        }
        binding.buttonProgressSubstract.setOnClickListener {
            binding.progressCircleView.setProgressWithAnimation(
                progress = binding.progressCircleView.progress - 20f,
                animationInterpolator = OvershootInterpolator(1f)
            )
        }
    }
}
