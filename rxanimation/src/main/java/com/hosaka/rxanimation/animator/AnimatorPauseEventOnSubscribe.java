package com.hosaka.rxanimation.animator;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;

import com.hosaka.rxanimation.internal.MainThreadSubscription;

import rx.Observable;
import rx.Subscriber;

import static com.hosaka.rxanimation.internal.Preconditions.checkUiThread;

/**
 * Created by shunhosaka on 15/10/10.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class AnimatorPauseEventOnSubscribe implements Observable.OnSubscribe<AnimatorPauseEvent> {
    private final Animator animation;

    AnimatorPauseEventOnSubscribe(Animator animation) {
        this.animation = animation;
    }

    @Override public void call(final Subscriber<? super AnimatorPauseEvent> subscriber) {
        checkUiThread();

        final Animator.AnimatorPauseListener listener = new Animator.AnimatorPauseListener() {
            @Override public void onAnimationPause(Animator animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimatorPauseEvent.create(animation, AnimatorPauseEvent.Kind.PAUSE));
                }
            }

            @Override public void onAnimationResume(Animator animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimatorPauseEvent.create(animation, AnimatorPauseEvent.Kind.RESUME));
                }
            }
        };
        animation.addPauseListener(listener);

        animation.start();

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnSubscribe() {
                animation.removePauseListener(listener);
            }
        });
    }
}
