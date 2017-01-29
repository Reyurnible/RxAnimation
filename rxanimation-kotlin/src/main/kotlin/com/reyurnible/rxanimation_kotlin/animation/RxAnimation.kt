package com.reyurnible.rxanimation_kotlin.animation

import android.view.View
import android.view.animation.Animation
import com.reyurnible.rxanimation.animation.AnimationEvent
import com.reyurnible.rxanimation.animation.RxAnimation
import io.reactivex.Observable

fun Animation.events(view: View): Observable<AnimationEvent> = RxAnimation.events(this, view)
