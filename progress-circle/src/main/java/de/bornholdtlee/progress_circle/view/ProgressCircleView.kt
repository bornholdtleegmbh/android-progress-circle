package de.bornholdtlee.progress_circle.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.Keep
import de.bornholdtlee.progress_circle.R
import de.bornholdtlee.progress_circle.utils.toPx

class ProgressCircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private companion object {
        const val DEFAULT_ANIMATION_DURATION_MS: Long = 1000L
        const val DEFAULT_STROKE_WIDTH_DP: Float = 8f
    }

    private var animationDuration: Long = DEFAULT_ANIMATION_DURATION_MS
    private var strokeWidthPb: Float = DEFAULT_STROKE_WIDTH_DP
    private var isRound = true
    private var progress = 0f
    private val rectF = RectF()

    private val backgroundPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = strokeWidthPb
            strokeCap = Paint.Cap.ROUND
        }
    }

    private val foregroundPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = strokeWidthPb
            strokeCap = Paint.Cap.ROUND
        }
    }

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressCircleView)
        strokeWidthPb = attributes.getFloat(R.styleable.ProgressCircleView_pcv_strokeWidth, 8F).toInt().toPx().toFloat()
        isRound = attributes.getBoolean(R.styleable.ProgressCircleView_pcv_isRound, true)
        backgroundPaint.color = attributes.getColor(R.styleable.ProgressCircleView_pcv_colorBackground, 0)
        foregroundPaint.color = attributes.getColor(R.styleable.ProgressCircleView_pcv_colorProgress, 0)
        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = width.coerceAtMost(height)
        setMeasuredDimension(min, min)
        rectF[0 + strokeWidthPb / 2, 0 + strokeWidthPb / 2, min - strokeWidthPb / 2] = min - strokeWidthPb / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val start = if (isRound) -105f else -220f
        val end = if (isRound) 360f else 260f
        canvas.drawArc(rectF, start, end, false, backgroundPaint)
        canvas.drawArc(rectF, start, end * progress / 100, false, foregroundPaint)
    }

    @Keep
    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun setProgressWithAnimation(progress: Float) {
        ObjectAnimator.ofFloat(this, "progress", this.progress, progress).apply {
            duration = animationDuration
            interpolator = DecelerateInterpolator()
        }.start()
    }
}
