//package com.google.mediapipe.examples.facelandmarker
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.graphics.RectF
//import android.util.AttributeSet
//import android.view.View
//
//class MultiLevelCircularProgressBar @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : View(context, attrs, defStyleAttr) {
//
//    private val paints = arrayOf(Paint(), Paint(), Paint(), Paint(),Paint())
//    private val progressValues = arrayOf(0f, 0f, 0f, 0f)
//    private val rect = RectF()
//    private var gap = 20f
//    private var strokeWidth = 20f
//
//    init {
//        // Initialize paint objects
//        for (paint in paints) {
//            paint.apply {
//                style = Paint.Style.STROKE
//                isAntiAlias = true
//                strokeCap = Paint.Cap.ROUND
//            }
//        }
//    }
//
//    var colorLeft: Int
//        get() = paints[0].color
//        set(value) {
//            paints[0].color = value
//            invalidate()
//        }
//
//    var colorRight: Int
//        get() = paints[1].color
//        set(value) {
//            paints[1].color = value
//            invalidate()
//        }
//
//    var colorUp: Int
//        get() = paints[2].color
//        set(value) {
//            paints[2].color = value
//            invalidate()
//        }
//
//    var colorDown: Int
//        get() = paints[3].color
//        set(value) {
//            paints[3].color = value
//            invalidate()
//        }
//    var EmptyStroke: Int
//        get() = paints[4].color
//        set(value) {
//            paints[4].color = value
//            invalidate()
//        }
//
//    var progressLeft: Float
//        get() = progressValues[0]
//        set(value) {
//            if (value in 0f..100f) {
//                progressValues[0] = value
//                invalidate()
//            } else {
//                throw IllegalArgumentException("Progress value must be between 0 and 100")
//            }
//        }
//
//    var progressRight: Float
//        get() = progressValues[1]
//        set(value) {
//            if (value in 0f..100f) {
//                progressValues[1] = value
//                invalidate()
//            } else {
//                throw IllegalArgumentException("Progress value must be between 0 and 100")
//            }
//        }
//
//    var progressUp: Float
//        get() = progressValues[2]
//        set(value) {
//            if (value in 0f..100f) {
//                progressValues[2] = value
//                invalidate()
//            } else {
//                throw IllegalArgumentException("Progress value must be between 0 and 100")
//            }
//        }
//
//    var progressDown: Float
//        get() = progressValues[3]
//        set(value) {
//            if (value in 0f..100f) {
//                progressValues[3] = value
//                invalidate()
//            } else {
//                throw IllegalArgumentException("Progress value must be between 0 and 100")
//            }
//        }
//
//    fun setGap(gap: Float) {
//        this.gap = gap
//        invalidate()
//    }
//
//    fun setStrokeWidth(strokeWidth: Float) {
//        this.strokeWidth = strokeWidth
//        for (paint in paints) {
//            paint.strokeWidth = strokeWidth
//        }
//        invalidate()
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//
//        val centerX = width / 2f
//        val centerY = height / 2f
//        val radius = Math.min(centerX, centerY) - strokeWidth / 2
//
//        val angles = arrayOf(135f, 45f, -45f, -135f)  // left, right, up, down
//
//        for (i in 0 until 4) {
//            val startAngle = angles[i]
//            rect.set(
//                centerX - radius,
//                centerY - radius,
//                centerX + radius,
//                centerY + radius
//            )
//            val sweepAngle = (90f - gap) * (progressValues[i] / 100)
//            val emptySweepAngle = (90f - gap)
//            canvas.drawArc(rect, startAngle + gap / 2, emptySweepAngle, false, paints[4])
//            canvas.drawArc(rect, startAngle + gap / 2, sweepAngle, false, paints[i])
//        }
//    }
//}
package com.google.mediapipe.examples.facelandmarker

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.google.mediapipe.examples.facelandmarker.R

class MultiLevelCircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paints = Array(5) { Paint() }
    private val progressValues = FloatArray(4)
    private val rect = RectF()
    private var gap = 20f
    private var strokeWidth = 20f

    init {
        initPaints()
        loadAttributes(context, attrs)
    }

    private fun initPaints() {
        for (paint in paints) {
            paint.apply {
                style = Paint.Style.STROKE
                isAntiAlias = true
                strokeCap = Paint.Cap.ROUND
            }
        }
    }

    private fun loadAttributes(context: Context, attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiLevelCircularProgressBar)
        try {
            paints[0].color = typedArray.getColor(R.styleable.MultiLevelCircularProgressBar_colorLeft, DEFAULT_COLOR)
            paints[1].color = typedArray.getColor(R.styleable.MultiLevelCircularProgressBar_colorRight, DEFAULT_COLOR)
            paints[2].color = typedArray.getColor(R.styleable.MultiLevelCircularProgressBar_colorUp, DEFAULT_COLOR)
            paints[3].color = typedArray.getColor(R.styleable.MultiLevelCircularProgressBar_colorDown, DEFAULT_COLOR)
            paints[4].color = typedArray.getColor(R.styleable.MultiLevelCircularProgressBar_colorEmpty, DEFAULT_COLOR)
            strokeWidth = typedArray.getDimension(R.styleable.MultiLevelCircularProgressBar_strokeWidth, DEFAULT_STROKE_WIDTH)
            gap = typedArray.getDimension(R.styleable.MultiLevelCircularProgressBar_gap, DEFAULT_GAP)
            setStrokeWidth(strokeWidth)
        } finally {
            typedArray.recycle()
        }
    }

    var colorLeft: Int
        get() = paints[0].color
        set(value) {
            paints[0].color = value
            invalidate()
        }

    var colorRight: Int
        get() = paints[1].color
        set(value) {
            paints[1].color = value
            invalidate()
        }

    var colorUp: Int
        get() = paints[2].color
        set(value) {
            paints[2].color = value
            invalidate()
        }

    var colorDown: Int
        get() = paints[3].color
        set(value) {
            paints[3].color = value
            invalidate()
        }

    var colorEmpty: Int
        get() = paints[4].color
        set(value) {
            paints[4].color = value
            invalidate()
        }

    var progressLeft: Float
        get() = progressValues[0]
        set(value) {
            if (value in 0f..100f) {
                progressValues[0] = value
                invalidate()
            }
        }

    var progressRight: Float
        get() = progressValues[1]
        set(value) {
            if (value in 0f..100f) {
                progressValues[1] = value
                invalidate()
            }
        }

    var progressUp: Float
        get() = progressValues[2]
        set(value) {
            if (value in 0f..100f) {
                progressValues[2] = value
                invalidate()
            }
        }

    var progressDown: Float
        get() = progressValues[3]
        set(value) {
            if (value in 0f..100f) {
                progressValues[3] = value
                invalidate()
            }
        }

    fun setGap(gap: Float) {
        this.gap = gap
        invalidate()
    }

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        for (paint in paints) {
            paint.strokeWidth = strokeWidth
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = Math.min(centerX, centerY) - strokeWidth / 2

        val angles = arrayOf(135f, -135f, 45f,-45f)  // left, right, up, down

        for (i in 0 until 4) {
            val startAngle = angles[i]
            rect.set(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
            )
            val sweepAngle = (90f - gap) * (progressValues[i] / 100)
            val emptySweepAngle = (90f - gap)
            canvas.drawArc(rect, startAngle + gap / 2, emptySweepAngle, false, paints[4])
            canvas.drawArc(rect, startAngle + gap / 2, sweepAngle, false, paints[i])
        }
    }

    companion object {
        private const val DEFAULT_COLOR = 0xD3D3D3.toInt() // Black
        private const val DEFAULT_STROKE_WIDTH = 20f
        private const val DEFAULT_GAP = 20f
    }
}
