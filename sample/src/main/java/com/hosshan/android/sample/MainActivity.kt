package com.hosshan.android.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AlphaAnimation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import com.hosaka.rxanimation.animation.AnimationEvent
import com.hosaka.rxanimation_kotlin.animation.bindView
import rx.Observable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.main_textview_hellow) as TextView

        val scaleAnimation = ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f)
        val alphaAnimation = AlphaAnimation(0.0f, 1.0f)

        val scaleAnimationObserver = scaleAnimation.bindView(textView).map { it.kind() == AnimationEvent.Kind.END }
        val alphaAnimationObserver = alphaAnimation.bindView(textView).map { it.kind() == AnimationEvent.Kind.END }

        Observable.combineLatest(scaleAnimationObserver, alphaAnimationObserver, { t1, t2 ->
            (t1 ?: false).and(t2 ?: false)
        }
        ).subscribe { validate ->
            // post event
            textView.setTextColor(Color.RED)
        }
    }
}
