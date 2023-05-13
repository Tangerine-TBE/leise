package cn.com.dihealth.myapplication

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import cn.com.dihealth.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
     val animator =   ObjectAnimator.ofFloat(binding.customView,"offsetAngle",360f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10 * 1000
        animator.start()
    }
}