package com.reyurnible.rxanimation.animation;

import android.view.View;
import android.view.animation.Animation;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

import static com.reyurnible.rxanimation.internal.Preconditions.checkUiThread;

/**
 * A animation event observer.
 */
final class AnimationEventOnSubscribe implements ObservableOnSubscribe<AnimationEvent> {
    private final Animation animation;
    private final View view;

    AnimationEventOnSubscribe(Animation animation, View view) {
        this.animation = animation;
        this.view = view;
    }

    @Override
    public void subscribe(final ObservableEmitter<AnimationEvent> emitter) throws Exception {
        checkUiThread();

        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimationEvent.create(animation, AnimationEvent.Kind.START));
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimationEvent.create(animation, AnimationEvent.Kind.END));
                    emitter.onComplete();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimationEvent.create(animation, AnimationEvent.Kind.REPEAT));
                }
            }
        };
        animation.start();
        animation.setAnimationListener(listener);
        // subscribeするタイミングはユーザーが決められるのでsetではなくstartにする
        view.startAnimation(animation);

        emitter.setCancellable(new Cancellable() {
            @Override
            public void cancel() throws Exception {
                animation.setAnimationListener(null);
            }
        });
    }
}
