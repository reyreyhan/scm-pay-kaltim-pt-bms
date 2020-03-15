package com.bm.main.pos.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.bm.main.pos.R

class CheckOutView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr){

    companion object{
        private val DEFAULT_RADIUS = 9f
    }

    private val eraser = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circlesPath = Path()
    private var circleRadius: Float = 0f
    private var circleSpace: Float = 0f
    private var dashColor: Int = 0
    private var dashSize: Float = 0f
    private val dashPath = Path()
    private val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init{
        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        val a = context.obtainStyledAttributes(attrs, R.styleable.CheckOutView)
        try {
            circleRadius = a.getDimension(R.styleable.CheckOutView_cov_circleRadius, getDp(DEFAULT_RADIUS).toFloat())
            circleSpace = a.getDimension(R.styleable.CheckOutView_cov_circleSpace, getDp(15f).toFloat())
            dashColor = a.getColor(R.styleable.CheckOutView_cov_dashColor, Color.parseColor("#0085be"))
            dashSize = a.getDimension(R.styleable.CheckOutView_cov_dashSize, getDp(1.5f).toFloat())
        } finally {
            a.recycle()
        }

        eraser.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        dashPaint.color = dashColor
        dashPaint.style = Paint.Style.STROKE
        dashPaint.strokeWidth = dashSize
        dashPaint.pathEffect = DashPathEffect(floatArrayOf(getDp(3f).toFloat(), getDp(3f).toFloat()), 0f)
    }

    fun setRadius(radius: Float) {
        this.circleRadius = radius
        postInvalidate()
    }

    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        val drawChild = super.drawChild(canvas, child, drawingTime)
        drawHoles(canvas!!)
        return drawChild
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.save()
        super.dispatchDraw(canvas)
        canvas?.restore()
    }


    override fun onDraw(canvas: Canvas) {
        drawHoles(canvas)
        super.onDraw(canvas)
    }

    private fun drawHoles(canvas: Canvas) {
        circlesPath = Path()
        val w = width
        val radius = circleRadius
        val space = circleSpace
        val circleWidth = radius * 2

        var leftMargin = 0
        if (layoutParams is MarginLayoutParams) {
            val lp = layoutParams as MarginLayoutParams
            leftMargin = lp.leftMargin
        }

        val left = left - leftMargin
        val circleSpace = circleWidth + space
        val count = (w / circleSpace).toInt()
        val offset = w - circleSpace * count
        val sideOffset = offset / 2
        val halfCircleSpace = circleSpace / 2

        for (i in 0 until count) {
            var positionCircle = i * circleSpace + sideOffset + left.toFloat() - radius
            if (i == 0) {
                positionCircle = left + sideOffset - radius
            }
            this.circlesPath.addCircle(positionCircle + halfCircleSpace, -circleRadius / 4, radius, Path.Direction.CW)
        }

        with(canvas) {
            if (dashSize > 0)
                drawPath(dashPath, dashPaint)
            drawPath(circlesPath, eraser)
        }
    }

    private fun getDp(value: Float): Int {
        return when (value) {
            0f -> 0
            else -> {
                val density = resources.displayMetrics.density
                Math.ceil((density * value).toDouble()).toInt()
            }
        }
    }
}