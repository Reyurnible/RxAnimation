package com.reyurnible.rxanimation.animator;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;

import com.reyurnible.rxanimation.internal.MainThreadSubscription;

import rx.Observable;
import rx.Subscriber;

import static com.reyurnible.rxanimation.internal.Preconditions.checkUiThread;

/**
 * A animation update event observer.
 */
final class AnimatorUpdateEventOnSubscribe implements Observable.OnSubscribe<AnimatorUpdateEvent> {
    private final ValueAnimator animation;

    AnimatorUpdateEventOnSubscribe(@NonNull ValueAnimator animation) {
        this.animation = animation;
    }

    @Override
    public void call(final Subscriber<? super AnimatorUpdateEvent> subscriber) {
        checkUiThread();

        final ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimatorUpdateEvent.create(animation));
                }
            }
        };
        animation.addUpdateListener(listener);
        animation.start();

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnSubscribe() {
                animation.removeUpdateListener(listener);
            }
        });
    }
}
