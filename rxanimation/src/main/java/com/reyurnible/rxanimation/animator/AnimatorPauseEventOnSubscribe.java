package com.reyurnible.rxanimation.animator;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

import static com.reyurnible.rxanimation.internal.Preconditions.checkUiThread;

/**
 * A animator pause event observer.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class AnimatorPauseEventOnSubscribe implements ObservableOnSubscribe<AnimatorPauseEvent> {
    private final Animator animation;

    AnimatorPauseEventOnSubscribe(Animator animation) {
        this.animation = animation;
    }

    @Override
    public void subscribe(final ObservableEmitter<AnimatorPauseEvent> emitter) throws Exception {
        checkUiThread();

        final Animator.AnimatorPauseListener listener = new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimatorPauseEvent.create(animation, AnimatorPauseEvent.Kind.PAUSE));
                }
            }

            @Override
            public void onAnimationResume(Animator animation) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(AnimatorPauseEvent.create(animation, AnimatorPauseEvent.Kind.RESUME));
                }
            }
        };
        animation.addPauseListener(listener);

        animation.start();

        emitter.setCancellable(new Cancellable() {
            @Override
            public void cancel() throws Exception {
                animation.removePauseListener(listener);
            }
        });
    }
}
