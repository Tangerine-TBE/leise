package cn.com.dihealth.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class test  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val mPath = Path()
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPathMeasure = PathMeasure()
    private val mDotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }
    init {
        mPaint.apply {
            strokeWidth = 10F
            style = Paint.Style.STROKE
            color = Color.BLACK
        }
        // 假设已有路径是一条曲线，这里只是举例，您需要根据实际路径修改
        mPath.moveTo(100F, 100F)
        mPath.quadTo(300F, 300F, 500F, 100F)
        // 获取路径的总长度
        mPathMeasure.setPath(mPath, false)
        val length = mPathMeasure.length
        // 根据路径的长度，计算出需要添加圆点的位置
        val pos = FloatArray(2)
        val tan = FloatArray(2)
        mPathMeasure.getPosTan(length, pos, tan)
        // 创建一个 Path，添加一个圆点
        val dotPath = Path().apply {
            addCircle(pos[0], pos[1], 10F, Path.Direction.CW)
        }
        // 将新的 Path 添加到原有路径的结尾
        mPath.addPath(dotPath)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制路径
        canvas.drawPath(mPath, mPaint)
        // 绘制实心圆点
        val pos = FloatArray(2)
        val length = mPathMeasure.length
        mPathMeasure.getPosTan(length, pos, null)
        canvas.drawCircle(pos[0], pos[1], 10F, mDotPaint)
    }

}