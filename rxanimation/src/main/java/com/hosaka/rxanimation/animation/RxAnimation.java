package com.hosaka.rxanimation.animation;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;

import rx.Observable;

/**
 * Created by shunhosaka on 15/10/08.
 */
public final class RxAnimation {

    @CheckResult @NonNull
    public static Observable<AnimationEvent> events(@NonNull Animation animation, @NonNull View view) {
        return Observable.create(new AnimationEventOnSubscribe(animation, view));
    }
}
