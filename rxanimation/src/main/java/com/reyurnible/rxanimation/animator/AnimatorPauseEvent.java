package com.reyurnible.rxanimation.animator;

import android.animation.Animator;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

/**
 * A android animator pause event occurred.
 */
public class AnimatorPauseEvent {

    public enum Kind {
        PAUSE, RESUME
    }

    @CheckResult @NonNull
    public static AnimatorPauseEvent create(@NonNull Animator animation, @NonNull Kind kind) {
        return new AnimatorPauseEvent(animation, kind);
    }

    private final Kind kind;
    private Animator animation;

    private AnimatorPauseEvent(Animator animation, Kind kind) {
        this.animation = animation;
        this.kind = kind;
    }

    @NonNull
    public Animator animation() {
        return animation;
    }

    @NonNull
    public Kind kind() {
        return kind;
    }

    @Override public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof AnimatorPauseEvent)) return false;
        AnimatorPauseEvent other = (AnimatorPauseEvent) object;
        return other.animation() == animation()
                && other.kind() == kind();
    }

    @Override public int hashCode() {
        int result = 17;
        result = result * 37 + animation().hashCode();
        result = result * 37 + kind().hashCode();
        return result;
    }

    @Override public String toString() {
        return "AnimatorPauseEvent{animation="
                + animation()
                + ", kind="
                + kind()
                + "}";
    }

}
