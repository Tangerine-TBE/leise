package cn.com.dihealth.myapplication

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import cn.com.dihealth.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//     val animator =   ObjectAnimator.ofFloat(binding.customView,"offsetAngle",360f)
//        animator.repeatCount = ValueAnimator.INFINITE
//        animator.interpolator = LinearInterpolator()
//        animator.duration = 10 * 1000
//        animator.start()

        val anim: Animation = RotateAnimation(360f,
            0f,Animation.RELATIVE_TO_SELF,
            0.5f,Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.fillAfter = true // 设置保持动画最后的状态
        anim.duration = 10 * 1000 // 设置动画时间
        anim.repeatCount = ValueAnimator.INFINITE
        anim.interpolator = LinearInterpolator() // 设置插入器
        binding.customView.startAnimation(anim)

    }
}