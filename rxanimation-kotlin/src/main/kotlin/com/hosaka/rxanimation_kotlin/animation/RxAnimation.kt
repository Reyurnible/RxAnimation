package com.hosaka.rxanimation_kotlin.animation

import android.view.animation.Animation
import com.hosaka.rxanimation.animation.AnimationEvent
import com.hosaka.rxanimation.animation.RxAnimation
import rx.Observable

public inline fun Animation.events(): Observable<AnimationEvent> = RxAnimation.events(this)