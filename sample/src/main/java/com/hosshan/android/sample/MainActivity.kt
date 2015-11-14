package com.hosshan.android.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import com.hosaka.rxanimation.animation.AnimationEvent
import com.hosaka.rxanimation_kotlin.animation.bindView
import rx.Observable
import rx.Subscriber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.main_textview_hellow) as TextView

        val scaleAnimation = ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f)
        scaleAnimation.duration = 1000
        // Simple Use
        scaleAnimation.bindView(textView).subscribe(object : Subscriber<AnimationEvent>() {
            override fun onCompleted() {

            }

            override fun onNext(t: AnimationEvent?) {
                Log.d(MainActivity::class.java.simpleName, t.toString())
            }

            override fun onError(e: Throwable?) {

            }
        })

        /*val scaleAnimationObserver = scaleAnimation.bindView(textView).map { it.kind() == AnimationEvent.Kind.END }
        val alphaAnimationObserver = alphaAnimation.bindView(textView).map { it.kind() == AnimationEvent.Kind.END }

        Observable.combineLatest(scaleAnimationObserver, alphaAnimationObserver, { t1, t2 ->
            (t1 ?: false).and(t2 ?: false)
        }).subscribe { validate ->
            // post event
            textView.setTextColor(Color.RED)
        }*/




        val alphaAnimation = AlphaAnimation(0.0f, 1.0f)
        alphaAnimation.duration = 3000
    }
}
