package cn.com.dihealth.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val pathMeasure = PathMeasure()

    private val endPos = FloatArray(2)
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
    var offsetAngle = 0f
        set(value) {
            field = value
            invalidate()
        }
    private lateinit var mEffects: List<Effect>

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(widthSize, widthSize)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        init()
    }

    public fun reverser() {
        for (item in mEffects) {
            item.setCounterClockwise()
        }
        invalidate()
    }

    private fun init() {
        mEffects = listOf(
            StandardArcEffect(
                -270f,
                180f,
                0f,
                "#D6E8FD",
                1f.dp,
                width.toFloat(),
                height.toFloat(),
                false, 0.2f,
                DashPathEffect(floatArrayOf(8.dp, 4.dp), 0f)

            ),
            StandardArcEffect(
                0f,
                180f,
                8.dp,
                "#D6E8FD",
                1.dp,
                width.toFloat(),
                height.toFloat(),
                true, 0.3f,
                DashPathEffect(floatArrayOf(8.dp, 4.dp), 0f)
            ),
            CircleArcEffect(
                270f,
                90f,
                24.dp,
                "#318BF6",
                1.dp,
                width.toFloat(),
                height.toFloat(),
                true, 0.65f,
                listOf(
                    CircleDash(5.dp, 1f, "#318BF6")
                )
            ),
            CircleArcEffect(
                -270f,
                90f,
                24.dp,
                "#318BF6",
                2.dp, width.toFloat(),
                height.toFloat(),
                true, 0.65f,
                listOf(
                    CircleDash(5.dp, 1f, "#318BF6")
                )
            ),

            StandardArcEffect(
                270f,
                180f,
                32.dp,
                "#D6E8FD",
                1.dp, width.toFloat(),
                height.toFloat(),
                false, 0.2f,
                DashPathEffect(floatArrayOf(8.dp, 4.dp), 0f)
            ),
            StandardArcEffect(
                0f,
                360f,
                48.dp,
                "#D6E8FD",
                1.dp, width.toFloat(),
                height.toFloat(),
                true, 0.4f,
                DashPathEffect(floatArrayOf(25.dp, 15.dp), 0f)
            ),
            CircleArcEffect(
                0f,
                360f,
                40.dp,
                "#318BF6",
                2.dp, width.toFloat(),
                height.toFloat(),
                false, 1f,
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
                1.dp, width.toFloat(),
                height.toFloat(),
                true, 1.2f,
                listOf(
                    CircleDash(4.5f.dp, 0.49f, "#318BF6")
                )
            ),
            CircleArcEffect(
                0f,
                360f,
                69.dp,
                "#D6E8FD",
                1.dp, width.toFloat(),
                height.toFloat(),
                false, 1.5f,
                listOf(
                    CircleDash(3.5f.dp, 0.2f, "#318BF6")
                )
            ),
            CircleArcEffect(
                0f,
                360f,
                83.dp,
                "#D6E8FD",
                1.dp, width.toFloat(),
                height.toFloat(),
                true, 2f,
                listOf(
                    CircleDash(3.5f.dp, 0.9f, "#318BF6"),
                    CircleDash(2.5f.dp, 0.3f, "#318BF6")

                )
            ),
            CircleArcEffect(
                0f,
                360f,
                97.dp,
                "#D6E8FD",
                1.dp, width.toFloat(),
                height.toFloat(),
                false, 2.5f,
                listOf()
            ),
            CircleArcEffect(
                0f,
                360f,
                111.dp,
                "#D6E8FD",
                1.dp, width.toFloat(),
                height.toFloat(),
                true, 3f,
                listOf()
            )
        )

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (item in mEffects) {
            paint.color = Color.parseColor(item.color)
            paint.strokeWidth = item.strokeWidth
            if (item is StandardArcEffect) {
                paint.pathEffect = item.pathEffect
                item.matrixRotate(offsetAngle * item.scale)
                val rotatedPath = Path()
                item.path.transform(item.matrix, rotatedPath)
                canvas.drawPath(rotatedPath, paint)
            } else if (item is CircleArcEffect) {
                item.matrixRotate(offsetAngle * item.scale)
                paint.pathEffect = null
                val rotatedPath = Path()
                item.path.transform(item.matrix, rotatedPath)
                canvas.drawPath(rotatedPath, paint) // 添加此行以绘制圆形路径
                if (item.circles.isNotEmpty()) {
                    pathMeasure.setPath(rotatedPath, true)
                    val mPathLength = pathMeasure.length
                    dotPaint.color = Color.parseColor(item.color)
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

    open class Effect(
        startAngle: Float,//开始角度
        sweepAngle: Float,//偏移角度
        offset: Float,//内部偏移量
        val color: String,//画笔的颜色设置
        val strokeWidth: Float,//线条的宽度
        width: Float,
        height: Float,
        private var counterclockwise: Boolean = false,
        val scale: Float,//放大速度

    ) {
        val path = Path()
        val matrix = Matrix()
        private val centerX = ((width - offset) + offset) / 2
        private val centerY = ((height - offset) + offset) / 2

        init {
            path.addArc(
                offset,
                offset,
                width - offset,
                height - offset,
                startAngle,
                sweepAngle
            )
            matrix.setTranslate(centerX, centerY)
        }

        fun matrixRotate(rotateDeg: Float) {
            matrix.reset()
            if (!counterclockwise) {
                matrix.postRotate(rotateDeg, centerX, centerY)
            } else {
                matrix.postRotate(-rotateDeg, centerX, centerY)
            }
        }

        fun setCounterClockwise() {
            counterclockwise = !counterclockwise
        }
    }

    data class CircleArcEffect(
        val q: Float,//开始角度
        val w: Float,//偏移角度
        val e: Float,//内部偏移量
        val r: String,//画笔的颜色设置
        val t: Float,
        val a: Float,
        val s: Float,
        val d: Boolean,
        val f: Float,
        val circles: List<CircleDash>,//路径特效

    ) : Effect(q, w, t / 2 + e, r, t, a, s, d, f)

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
        val a: Float,
        val s: Float,
        val d: Boolean,
        val f: Float,//放大速度
        val pathEffect: PathEffect //路径特效
    ) : Effect(q, w, t / 2 + e, r, t, a, s, d, f)

}
