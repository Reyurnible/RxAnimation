package com.hosaka.rxanimation.animation;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.animation.Animation;

/**
 * A android animation event occurred.
 */
public class AnimationEvent {

    /**
     * Animation event kinds.
     */
    public enum Kind {
        START, END, REPEAT
    }

    @CheckResult
    @NonNull
    public static AnimationEvent create(@NonNull Animation animation, @NonNull Kind kind) {
        return new AnimationEvent(animation, kind);
    }

    private final Kind kind;
    private Animation animation;

    private AnimationEvent(Animation animation, Kind kind) {
        this.animation = animation;
        this.kind = kind;
    }

    @NonNull
    public Animation animation() {
        return animation;
    }

    @NonNull
    public Kind kind() {
        return kind;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof AnimationEvent)) return false;
        AnimationEvent other = (AnimationEvent) object;
        return other.animation() == animation()
                && other.kind() == kind();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 37 + animation().hashCode();
        result = result * 37 + kind().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AnimationEvent{animation="
                + animation()
                + ", kind="
                + kind()
                + "}";
    }


}
