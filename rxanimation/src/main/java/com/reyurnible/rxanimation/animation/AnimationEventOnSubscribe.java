package com.reyurnible.rxanimation.animation;

import android.view.View;
import android.view.animation.Animation;

import com.reyurnible.rxanimation.internal.MainThreadSubscription;

import rx.Observable;
import rx.Subscriber;

import static com.reyurnible.rxanimation.internal.Preconditions.checkUiThread;

/**
 * A animation event observer.
 */
final class AnimationEventOnSubscribe implements Observable.OnSubscribe<AnimationEvent> {
    private final Animation animation;
    private final View view;

    AnimationEventOnSubscribe(Animation animation, View view) {
        this.animation = animation;
        this.view = view;
    }

    @Override
    public void call(final Subscriber<? super AnimationEvent> subscriber) {
        checkUiThread();

        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimationEvent.create(animation, AnimationEvent.Kind.START));
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimationEvent.create(animation, AnimationEvent.Kind.END));
                    subscriber.onCompleted();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AnimationEvent.create(animation, AnimationEvent.Kind.REPEAT));
                }
            }
        };
        animation.start();
        animation.setAnimationListener(listener);
        // subscribeするタイミングはユーザーが決められるのでsetではなくstartにする
        view.startAnimation(animation);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnSubscribe() {
                animation.setAnimationListener(null);
            }
        });
    }


}
