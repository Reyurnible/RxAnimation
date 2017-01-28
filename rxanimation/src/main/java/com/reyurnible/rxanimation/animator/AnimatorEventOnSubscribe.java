package com.reyurnible.rxanimation.animator;

import android.animation.Animator;
import android.support.annotation.NonNull;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

import static com.reyurnible.rxanimation.internal.Preconditions.checkUiThread;

/**
 * A animator event observer.
 */
final class AnimatorEventOnSubscribe implements ObservableOnSubscribe<AnimatorEvent> {
    private final Animator animation;

    AnimatorEventOnSubscribe(@NonNull Animator animation) {
        this.animation = animation;
    }

    @Override
    public void subscribe(final ObservableEmitter<AnimatorEvent> emitter) throws Exception {
        checkUiThread();

        final Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimatorEvent.create(animation, AnimatorEvent.Kind.START));
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimatorEvent.create(animation, AnimatorEvent.Kind.END));
                    emitter.onComplete();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimatorEvent.create(animation, AnimatorEvent.Kind.CANCEL));
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimatorEvent.create(animation, AnimatorEvent.Kind.REPEAT));
                }
            }
        };
        animation.addListener(listener);

        animation.start();

        emitter.setCancellable(new Cancellable() {
            @Override
            public void cancel() throws Exception {
                animation.removeListener(listener);
            }
        });
    }
}
