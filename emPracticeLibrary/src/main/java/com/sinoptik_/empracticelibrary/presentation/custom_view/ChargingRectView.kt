package com.sinoptik_.empracticelibrary.presentation.custom_view

import android.R.attr.height
import android.R.attr.width
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class ChargingRectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0
    private var fillColor = Color.GREEN

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        isClickable = true
        setOnClickListener {
            updateProgress()
        }
    }

    private fun updateProgress() {
        if (progress >= 100) {
            progress = 0
        } else {
            progress += 10
        }
        fillColor = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = 200
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()
        val padding = 10f


        val fillWidth = (w - 2 * padding) * (progress / 100f)

        fillPaint.color = fillColor
        canvas.drawRect(
            padding,
            padding,
            padding + fillWidth,
            h - padding,
            fillPaint
        )
        canvas.drawRect(padding, padding, w - padding, h - padding, strokePaint)

    }
}
