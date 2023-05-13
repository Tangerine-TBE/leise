package cn.com.dihealth.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsetsAnimation.Bounds
import android.view.WindowManager

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        Paint.ANTI_ALIAS_FLAG
    }
    private val dotPaint = Paint().apply {
        style = Paint.Style.FILL
        Paint.ANTI_ALIAS_FLAG
    }
    private val pathMeasure = PathMeasure()
     var offsetAngle = 0f
        set(value) {
            field = value
            invalidate()
        }
    open class Effect(
        val startAngle: Float,//开始角度
        val sweepAngle: Float,//偏移角度
        val offset: Float,//内部偏移量
        val color: String,//画笔的颜色设置
        val strokeWidth: Float
    )
    private val mEffects by lazy {
        listOf(
            StandardArcEffect(
                -270f,
                180f,
                0f,
                "#D6E8FD",
                1f.dp,
                DashPathEffect(floatArrayOf(8.dp, 4.dp), 0f)
            ),
            StandardArcEffect(
                0f,
                180f,
                8.dp,
                "#D6E8FD",
                1.dp,
                DashPathEffect(floatArrayOf(8.dp, 4.dp), 0f)
            ),
            CircleArcEffect(
                270f,
                90f,
                24.dp,
                "#318BF6",
                1.dp,
                listOf(
                    CircleDash(5.dp, 1f, "#318BF6")
                )
            ),
            CircleArcEffect(
                -270f,
                90f,
                24.dp,
                "#318BF6",
                3.dp,
                listOf(
                    CircleDash(5.dp, 1f, "#318BF6")
                )
            ),

            StandardArcEffect(
                270f,
                180f,
                32.dp,
                "#D6E8FD",
                1.dp,
                DashPathEffect(floatArrayOf(8.dp, 4.dp), 0f)
            ),
            StandardArcEffect(
                0f,
                360f,
                48.dp,
                "#D6E8FD",
                1.dp,
                DashPathEffect(floatArrayOf(25.dp, 15.dp), 0f)
            ),
            CircleArcEffect(
                0f,
                360f,
                40.dp,
                "#318BF6",
                2.dp,
                listOf(
                    CircleDash(5.dp, 0.7f, "#318BF6"),
                    CircleDash(9.dp, 0.15f, "#318BF6")
                )
            ),

            CircleArcEffect(
                0f,
                360f,
                55.dp,
                "#D6E8FD",
                1.dp,
                listOf(
                    CircleDash(4.5f.dp, 0.49f, "#318BF6")
                )
            ),
            CircleArcEffect(
                0f,
                360f,
                69.dp,
                "#D6E8FD",
                1.dp,
                listOf(
                    CircleDash(3.5f.dp, 0.2f, "#318BF6")
                )
            ),
            CircleArcEffect(
                0f,
                360f,
                83.dp,
                "#D6E8FD",
                1.dp,
                listOf(
                    CircleDash(3.5f.dp, 0.9f, "#318BF6"),
                    CircleDash(2.5f.dp,  0.3f,"#318BF6")

                )
            ),
            CircleArcEffect(
                0f,
                360f,
                97.dp,
                "#D6E8FD",
                1.dp,
                listOf()
            ),
            CircleArcEffect(
                0f,
                360f,
                111.dp,
                "#D6E8FD",
                1.dp,
                listOf()
            )
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(widthSize,widthSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for ((index, item) in mEffects.withIndex()) {
            if (item is StandardArcEffect) {
                paint.color = Color.parseColor(item.color)
                paint.pathEffect = item.pathEffect
                paint.strokeWidth = item.strokeWidth
                val path = Path()
                path.addArc(
                    0f + item.offset,
                    0f + item.offset,
                    width.toFloat() - item.offset,
                    height.toFloat() - item.offset,
                    item.startAngle-offsetAngle,
                    item.sweepAngle
                )
                canvas.drawPath(path, paint)
            } else if (item is CircleArcEffect) {
                paint.color = Color.parseColor(item.color)
                paint.pathEffect = null
                paint.strokeWidth = item.strokeWidth
                dotPaint.color = Color.parseColor(item.color)
                val path = Path()
                path.addArc(
                    0f + item.offset,
                    0f + item.offset,
                    width.toFloat() - item.offset,
                    height.toFloat() - item.offset,
                    item.startAngle-offsetAngle,
                    item.sweepAngle
                )
                canvas.drawPath(path, paint)
                if (item.circles.isNotEmpty()) {
                    pathMeasure.setPath(path, false)
                    val mPathLength = pathMeasure.length
                    val endPos = FloatArray(2)
                    for (circle in item.circles) {
                        val radius = circle.size
                        dotPaint.color = Color.parseColor(circle.color)
                        pathMeasure.getPosTan(mPathLength * circle.position, endPos, null)
                        canvas.drawCircle(endPos[0], endPos[1], radius, dotPaint)
                    }
                }
            }
        }

    }



    data class CircleArcEffect(
        val q: Float,//开始角度
        val w: Float,//偏移角度
        val e: Float,//内部偏移量
        val r: String,//画笔的颜色设置
        val t: Float,
        val circles: List<CircleDash>,//路径特效

    ) : Effect(q, w, t / 2 + e, r, t)

    data class CircleDash(
        val size: Float, //添加圆点的大小
        val position: Float,//在哪里添加圆点 0.0 ~ 1f
        val color: String
    )

    data class StandardArcEffect(
        val q: Float,//开始角度
        val w: Float,//偏移角度
        val e: Float,//内部偏移量
        val r: String,//画笔的颜色设置
        val t: Float,
        val pathEffect: PathEffect //路径特效
    ) : Effect(q, w, t / 2 + e, r, t)

}
