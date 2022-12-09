package de.bornholdtlee.progresscirclelib

import android.os.Bundle
import androidx.activity.ComponentActivity
import de.bornholdtlee.progress_circle.view.ProgressCircleView
import de.bornholdtlee.progresscirclelib.databinding.ActivityMainBinding
import de.bornholdtlee.progresscirclelib.databinding.ActivityMainBinding.inflate
import de.bornholdtlee.progresscirclelib.utils.viewBinding

class MainActivity : ComponentActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.buttonProgress.setOnClickListener {
            binding.progressCircleView.setProgressWithAnimation(50f)
        }
    }
}
