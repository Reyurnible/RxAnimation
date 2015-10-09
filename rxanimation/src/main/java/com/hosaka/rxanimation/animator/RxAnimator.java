package com.hosaka.rxanimation.animator;

import android.animation.Animator;
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

}
