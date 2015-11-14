package com.hosaka.rxanimation.internal;

import android.os.Looper;

/**
 * Created by shunhosaka on 15/10/08.
 */
public final class Preconditions {
    public static void checkArgument(boolean assertion, String message) {
        if (!assertion) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkUiThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException(
                    "Must be called from the main thread. Was: " + Thread.currentThread());
        }
    }

    private Preconditions() {
        throw new AssertionError("No instance");
    }

}
