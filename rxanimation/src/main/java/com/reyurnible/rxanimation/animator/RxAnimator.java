package com.reyurnible.rxanimation.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import rx.Observable;

/**
 * Static factory methods for animator event observable.
 */
public final class RxAnimator {

    @CheckResult
    @NonNull
    public static Observable<AnimatorEvent> events(@NonNull Animator animation) {
        return Observable.create(new AnimatorEventOnSubscribe(animation));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @CheckResult
    @NonNull
    public static Observable<AnimatorPauseEvent> pauseEvents(@NonNull Animator animation) {
        return Observable.create(new AnimatorPauseEventOnSubscribe(animation));
    }

    @CheckResult
    @NonNull
    public static Observable<AnimatorUpdateEvent> updateEvents(@NonNull ValueAnimator animation) {
        return Observable.create(new AnimatorUpdateEventOnSubscribe(animation));
    }

}
