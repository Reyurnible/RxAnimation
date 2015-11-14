package com.hosaka.rxanimation_kotlin.animation

import android.animation.Animator
import com.hosaka.rxanimation.animator.AnimatorEvent
import com.hosaka.rxanimation.animator.RxAnimator
import rx.Observable

public inline fun Animator.events(): Observable<AnimatorEvent> = RxAnimator.events(this)
