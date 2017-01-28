package com.reyurnible.rxanimation_kotlin.animation

import android.animation.Animator
import android.animation.ValueAnimator
import com.reyurnible.rxanimation.animator.AnimatorEvent
import com.reyurnible.rxanimation.animator.AnimatorPauseEvent
import com.reyurnible.rxanimation.animator.AnimatorUpdateEvent
import com.reyurnible.rxanimation.animator.RxAnimator
import rx.Observable

fun Animator.events(): Observable<AnimatorEvent> = RxAnimator.events(this)

fun Animator.pauseEvents(): Observable<AnimatorPauseEvent> = RxAnimator.pauseEvents(this)

fun ValueAnimator.updateEvents(): Observable<AnimatorUpdateEvent> = RxAnimator.updateEvents(this)
