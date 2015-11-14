package com.hosshan.android.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import com.hosaka.rxanimation.animation.AnimationEvent
import com.hosaka.rxanimation.animator.AnimatorEvent
import com.hosaka.rxanimation_kotlin.animation.bindView
import rx.Observable
import rx.Subscriber

class MainActivity : AppCompatActivity() {

    val loginButton: Button by bindView(R.id.main_button_login)
    val formLayout: RelativeLayout by bindView(R.id.main_layout_form)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize invisible
        formLayout.visibility = View.INVISIBLE

        loginButton.setOnClickListener { startAnimations() }
    }

    private fun startAnimations() {

        val alphaAnimation: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        alphaAnimation.duration = 1200

        // Simple Use
        alphaAnimation.bindView(formLayout).subscribe(object : Subscriber<AnimationEvent>() {
            override fun onCompleted() {

            }

            override fun onNext(t: AnimationEvent) {
                when(t.kind()){
                    AnimationEvent.Kind.START -> {
                        formLayout.visibility = View.VISIBLE
                    }
                    AnimationEvent.Kind.REPEAT -> {

                    }
                    AnimationEvent.Kind.END -> {

                    }
                }
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
    }

}
