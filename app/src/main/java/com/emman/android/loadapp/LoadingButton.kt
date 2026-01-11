package com.emman.android.loadapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.properties.Delegates


class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private val valueAnimator = ValueAnimator()
    private var progressWidth = 0f
    private var circleAngle = 0f

    private var buttonText = resources.getString(R.string.button_download)

    var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old, new ->
        if (new == old) return@observable
        when (new) {
            ButtonState.Loading -> {
                buttonText = resources.getString(R.string.button_loading)
                valueAnimator.apply {
                    cancel()
                    removeAllListeners()
                    setFloatValues(0f, widthSize.toFloat())
                    duration = 2000
                    repeatCount = ValueAnimator.INFINITE
                    repeatMode = ValueAnimator.RESTART
                    start()
                }
            }
            ButtonState.Completed -> {
                buttonText = resources.getString(R.string.button_download)
                valueAnimator.apply {
                    cancel()
                    removeAllListeners()
                    setFloatValues(progressWidth, widthSize.toFloat())
                    duration = 500
                    repeatCount = 0
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            progressWidth = 0f
                            circleAngle = 0f
                            invalidate()
                        }
                    })
                    start()
                }
            }
            ButtonState.Clicked -> {

            }
        }
        invalidate()
    }

    private val buttonColorPaint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        color = ContextCompat.getColor(context, R.color.colorPrimary)
        style = Paint.Style.FILL
    }

    private val paintText = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.white)
        textSize = resources.getDimension(R.dimen.default_text_size)
        textAlign = Paint.Align.CENTER
    }

    private val loadingPaint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        style = Paint.Style.FILL
    }

    private val circlePaint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        color = ContextCompat.getColor(context, R.color.colorAccent)
        style = Paint.Style.FILL
    }

    init {
        isClickable = true
        valueAnimator.addUpdateListener { animator ->
            progressWidth = animator.animatedValue as Float
            circleAngle = if (widthSize > 0) (progressWidth / widthSize) * 360f else 0f
            invalidate()
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /**
         * Draw the button
         */
        canvas.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), buttonColorPaint)

        if (progressWidth > 0f) {
            canvas.drawRect(0f, 0f, progressWidth, heightSize.toFloat(), loadingPaint)
        }


        /**
         * Draw the text
         */
        canvas.drawText(
            buttonText,
            (widthSize / 2f),
            (heightSize / 2f) - ((paintText.descent() + paintText.ascent()) / 2f),
            paintText
        )

        /**
         * Draw the circle
         */
        if (progressWidth > 0f) {
            canvas.drawArc(
                widthSize - 150f,
                heightSize / 2f - 25f,
                widthSize - 100f,
                heightSize / 2f + 25f,
                0f,
                circleAngle,
                true,
                circlePaint
            )
        }


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}