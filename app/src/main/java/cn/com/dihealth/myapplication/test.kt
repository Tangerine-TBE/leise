package cn.com.dihealth.myapplication

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class test  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    // 声明路径、路径测量工具、描边画笔、边框偏移量、路径终点坐标数组和路径样式
    private val path = Path()
    private val pathMeasure = PathMeasure()
    private val paint = Paint().apply {
        color = Color.RED
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }
    private val item = PathItem(0f, 200f, 300f, 200f)
    private val pos = FloatArray(2)
    private val pathDst = Path()
    private var phase = 0f // 用于动画的属性

    init {
        // 构造函数中初始化路径
        path.addCircle(item.width / 2.0f, item.height / 2.0f, (item.width.toFloat() - 2 * item.offset) / 2, Path.Direction.CCW)
        pathMeasure.setPath(path, false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 在onDraw方法中计算并绘制路径
        canvas?.drawColor(Color.WHITE) // 清空背景色
        val distance = pathMeasure.length * phase
        pathMeasure.getSegment(0f, distance, pathDst, true)
        canvas?.drawPath(pathDst, paint)
    }

    // 开始动画
    fun startAnimation() {
        val animator = ValueAnimator.ofFloat(1f, 2f)
        animator.duration = 2000L
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            phase = it.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    // 声明路径对象的参数，包括边框宽度、偏移量和宽高
    data class PathItem(
        val offset: Float, // 边框偏移量
        val startX: Float,
        val endX: Float,
        val height: Float
    ) {
        val width: Float = endX - startX
    }

}