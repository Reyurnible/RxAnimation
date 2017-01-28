package com.hosaka.rxanimation_kotlin.animation

import android.animation.Animator
import android.animation.ValueAnimator
import com.hosaka.rxanimation.animator.AnimatorEvent
import com.hosaka.rxanimation.animator.AnimatorPauseEvent
import com.hosaka.rxanimation.animator.AnimatorUpdateEvent
import com.hosaka.rxanimation.animator.RxAnimator
import rx.Observable

fun Animator.events(): Observable<AnimatorEvent> = RxAnimator.events(this)

fun Animator.pauseEvents(): Observable<AnimatorPauseEvent> = RxAnimator.pauseEvents(this)

fun ValueAnimator.updateEvents(): Observable<AnimatorUpdateEvent> = RxAnimator.updateEvents(this)
