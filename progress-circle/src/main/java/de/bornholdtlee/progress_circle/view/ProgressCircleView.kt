package de.bornholdtlee.progress_circle.view

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.withStyledAttributes
import de.bornholdtlee.progress_circle.R
import de.bornholdtlee.progress_circle.utils.toPx
import kotlin.math.max

class ProgressCircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private companion object {
        const val DEFAULT_ANIMATION_DURATION_MS: Long = 1000L
        const val DEFAULT_STROKE_WIDTH_DP: Float = 8f
    }

    private var strokeWidthProgress: Float = DEFAULT_STROKE_WIDTH_DP
    private var strokeWidthBackground: Float = DEFAULT_STROKE_WIDTH_DP

    var progress: Float = 0f
        set(value) {
            val progressGuarded = value.coerceIn(0f, 100f)
            field = progressGuarded
            invalidate()
            requestLayout()
        }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = strokeWidthBackground
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = strokeWidthProgress
        strokeCap = Paint.Cap.ROUND
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.ProgressCircleView) {
            strokeWidthProgress = getFloat(
                R.styleable.ProgressCircleView_pcv_strokeWidthProgress,
                strokeWidthProgress
            ).toInt().toPx().toFloat()
            progressPaint.strokeWidth = strokeWidthProgress
            strokeWidthBackground = getFloat(
                R.styleable.ProgressCircleView_pcv_strokeWidthBackground,
                strokeWidthBackground
            ).toInt().toPx().toFloat()
            backgroundPaint.strokeWidth = strokeWidthBackground
            backgroundPaint.color = getColor(
                R.styleable.ProgressCircleView_pcv_colorBackground,
                Color.LTGRAY
            )
            progressPaint.color = getColor(
                R.styleable.ProgressCircleView_pcv_colorProgress,
                Color.GREEN
            )
            progress = getFloat(
                R.styleable.ProgressCircleView_pcv_progress,
                progress
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val size = width.coerceAtMost(height)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackgroundCircle(canvas)
        drawProgressCircle(canvas)
    }

    private fun drawBackgroundCircle(canvas: Canvas) {
        val radius: Float = (height / 2f) - (max(strokeWidthBackground, strokeWidthProgress) / 2f)
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            radius,
            backgroundPaint
        )
    }

    private fun drawProgressCircle(canvas: Canvas) {
        val maxStrokeWidth = max(strokeWidthBackground, strokeWidthProgress)
        canvas.drawArc(
            maxStrokeWidth / 2f,
            maxStrokeWidth / 2f,
            width - (maxStrokeWidth / 2),
            height - (maxStrokeWidth / 2),
            -90f,
            360f * (progress / 100f),
            false,
            progressPaint
        )
    }

    fun setProgressWithAnimation(
        progress: Float,
        animationDuration: Long = DEFAULT_ANIMATION_DURATION_MS,
        animationInterpolator: TimeInterpolator = DecelerateInterpolator(),
        onAnimationFinished: (progress: Float) -> Unit = {}
    ) {
        ObjectAnimator.ofFloat(
            this,
            "progress",
            this.progress,
            progress
        ).apply {
            duration = animationDuration
            interpolator = animationInterpolator
            doOnEnd {
                onAnimationFinished(progress)
            }
        }.start()
    }
}
