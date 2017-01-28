package com.hosaka.rxanimation_kotlin.animation

import android.view.View
import android.view.animation.Animation
import com.hosaka.rxanimation.animation.AnimationEvent
import com.hosaka.rxanimation.animation.RxAnimation
import rx.Observable

fun Animation.events(view: View): Observable<AnimationEvent> = RxAnimation.events(this, view)
