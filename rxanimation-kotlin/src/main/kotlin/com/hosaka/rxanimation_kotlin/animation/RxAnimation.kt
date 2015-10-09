package com.hosaka.rxanimation_kotlin.animation

import android.view.animation.Animation
import com.hosaka.rxanimation.animation.AnimationEvent
import com.hosaka.rxanimation.animation.RxAnimation
import rx.Observable

/**
 * Created by shunhosaka on 15/10/09.
 */
public inline fun Animation.events(): Observable<AnimationEvent> = RxAnimation.events(this)