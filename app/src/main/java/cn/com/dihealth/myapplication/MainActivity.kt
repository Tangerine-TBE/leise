package cn.com.dihealth.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import cn.com.dihealth.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val animator = ObjectAnimator.ofFloat(binding.customView, "offsetAngle", 360f)
        animator.interpolator = LinearInterpolator()
        animator.duration = 6 * 1000
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val n = binding.customView.offsetAngle / 360
                animator.setFloatValues(n * 360 + 360)
                animator.start()
            }
        })
        animator.start()
    }
}