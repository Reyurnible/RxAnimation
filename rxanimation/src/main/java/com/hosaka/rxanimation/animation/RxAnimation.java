package com.hosaka.rxanimation.animation;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;

import rx.Observable;

/**
 * Static factory methods for animation event observable.
 */
public final class RxAnimation {

    /**
     * Create an animation event observable.
     */
    @CheckResult
    @NonNull
    public static Observable<AnimationEvent> events(@NonNull Animation animation, @NonNull View view) {
        return Observable.create(new AnimationEventOnSubscribe(animation, view));
    }
}
