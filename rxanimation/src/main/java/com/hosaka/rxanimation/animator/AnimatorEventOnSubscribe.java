package com.hosaka.rxanimation.animator;

import android.animation.Animator;
import android.support.annotation.NonNull;

import com.hosaka.rxanimation.internal.MainThreadSubscription;

import rx.Observable;
import rx.Subscriber;

import static com.hosaka.rxanimation.internal.Preconditions.checkUiThread;

/**
 * A animator event observer.
 */
final class AnimatorEventOnSubscribe implements Observable.OnSubscribe<AnimatorEvent> {
    private final Animator animation;

    AnimatorEventOnSubscribe(@NonNull Animator animation) {
        this.animation = animation;
    }

    @Override
    public void call(final Subscriber<? super AnimatorEvent> subscriber) {
        checkUiThread();

        final Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimatorEvent.create(animation, AnimatorEvent.Kind.START));
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimatorEvent.create(animation, AnimatorEvent.Kind.END));
                    subscriber.onCompleted();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimatorEvent.create(animation, AnimatorEvent.Kind.CANCEL));
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimatorEvent.create(animation, AnimatorEvent.Kind.REPEAT));
                }
            }
        };
        animation.addListener(listener);

        animation.start();

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnSubscribe() {
                animation.removeListener(listener);
            }
        });
    }
}
