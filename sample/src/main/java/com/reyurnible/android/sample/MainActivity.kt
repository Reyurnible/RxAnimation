package com.reyurnible.android.sample

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import com.reyurnible.rxanimation.animation.AnimationEvent
import com.reyurnible.rxanimation.animator.AnimatorEvent
import com.reyurnible.rxanimation_kotlin.animation.events
import rx.Observable

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    val loginButton: Button by bindView(R.id.main_button_login)
    val formLayout: RelativeLayout by bindView(R.id.main_layout_form)
    val mailEditText: EditText by bindView(R.id.main_edittext_mail)
    val passEditText: EditText by bindView(R.id.main_edittext_pass)
    val signupTextView: TextView by bindView(R.id.main_textview_signup)
    val forgotTextView: TextView by bindView(R.id.main_textview_forgot)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize invisible
        formLayout.visibility = View.INVISIBLE
        loginButton.setOnClickListener { startAnimations() }
    }

    private fun startAnimations() {
        formLayout.visibility = View.VISIBLE
        mailEditText.visibility = View.INVISIBLE
        passEditText.visibility = View.INVISIBLE
        signupTextView.visibility = View.INVISIBLE
        forgotTextView.visibility = View.INVISIBLE

        val alphaAnimation: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        alphaAnimation.duration = 3000
        val alphaAnimationObserver = alphaAnimation.events(formLayout).map {
            Log.d(TAG, it.toString())
            it.kind() == AnimationEvent.Kind.END
        }

        val translationAnimator: ObjectAnimator = ObjectAnimator.ofFloat(formLayout, "translationY", -1000f, 0f)
        translationAnimator.duration = 3000
        val translateAnimatorObserver = translationAnimator.events().map {
            Log.d(TAG, it.toString())
            it.kind() == AnimatorEvent.Kind.END
        }

        Observable.combineLatest(translateAnimatorObserver, alphaAnimationObserver, { t1, t2 ->
            Log.d(TAG, "T1:" + t1.toString())
            Log.d(TAG, "T2:" + t2.toString())
            t1.and(t2)
        }).filter { it }.subscribe { validate ->
            // post event
            Log.d(TAG, "End All Animation")
            mailEditText.visibility = View.VISIBLE
            passEditText.visibility = View.VISIBLE
            signupTextView.visibility = View.VISIBLE
            forgotTextView.visibility = View.VISIBLE
        }
    }

}
