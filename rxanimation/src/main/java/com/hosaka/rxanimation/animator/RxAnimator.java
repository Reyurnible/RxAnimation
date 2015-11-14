package com.hosaka.rxanimation.animator;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import rx.Observable;

/**
 * Created by shunhosaka on 15/10/09.
 */
public final class RxAnimator {

    @CheckResult @NonNull
    public static Observable<AnimatorEvent> events(@NonNull Animator animation) {
        return Observable.create(new AnimatorEventOnSubscribe(animation));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @CheckResult @NonNull
    public static Observable<AnimatorPauseEvent> pauseEvents(@NonNull Animator animation) {
        return Observable.create(new AnimatorPauseEventOnSubscribe(animation));
    }

}
