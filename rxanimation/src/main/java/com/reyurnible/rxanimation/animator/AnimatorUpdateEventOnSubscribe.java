package com.reyurnible.rxanimation.animator;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

import static com.reyurnible.rxanimation.internal.Preconditions.checkUiThread;

/**
 * A animation update event observer.
 */
final class AnimatorUpdateEventOnSubscribe implements ObservableOnSubscribe<AnimatorUpdateEvent> {
    private final ValueAnimator animation;

    AnimatorUpdateEventOnSubscribe(@NonNull ValueAnimator animation) {
        this.animation = animation;
    }

    @Override
    public void subscribe(final ObservableEmitter<AnimatorUpdateEvent> emitter) throws Exception {
        checkUiThread();

        final ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimatorUpdateEvent.create(animation));
                }
            }
        };
        animation.addUpdateListener(listener);
        animation.start();

        emitter.setCancellable(new Cancellable() {
            @Override
            public void cancel() throws Exception {
                animation.removeUpdateListener(listener);
            }
        });
    }
}
