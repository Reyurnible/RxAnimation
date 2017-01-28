package com.hosaka.rxanimation.internal;

import android.os.Looper;

/**
 * Check condition static function.
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
