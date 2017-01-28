package com.hosaka.rxanimation.internal;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Keep;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import rx.Subscription;

/**
 * Checking main thread subscription helper.
 */
public abstract class MainThreadSubscription implements Subscription, Runnable {
    private static final Handler mainThread = new Handler(Looper.getMainLooper());

    @Keep
    @SuppressWarnings("unused") // Updated by 'unsubscribedUpdater' object.
    private volatile int unsubscribed;
    private static final AtomicIntegerFieldUpdater<MainThreadSubscription> unsubscribedUpdater =
            AtomicIntegerFieldUpdater.newUpdater(MainThreadSubscription.class, "unsubscribed");

    @Override
    public final boolean isUnsubscribed() {
        return unsubscribed != 0;
    }

    @Override
    public final void unsubscribe() {
        if (unsubscribedUpdater.compareAndSet(this, 0, 1)) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                onUnSubscribe();
            } else {
                mainThread.post(this);
            }
        }
    }

    @Override
    public final void run() {
        onUnSubscribe();
    }

    protected abstract void onUnSubscribe();
}
