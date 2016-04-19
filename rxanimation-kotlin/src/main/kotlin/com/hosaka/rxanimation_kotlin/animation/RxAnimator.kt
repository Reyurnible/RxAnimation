package com.hosaka.rxanimation_kotlin.animation

import android.animation.Animator
import com.hosaka.rxanimation.animator.AnimatorEvent
import com.hosaka.rxanimation.animator.RxAnimator
import rx.Observable

fun Animator.bind(): Observable<AnimatorEvent> = RxAnimator.events(this)
