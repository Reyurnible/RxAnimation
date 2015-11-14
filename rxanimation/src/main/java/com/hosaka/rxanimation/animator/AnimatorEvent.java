package com.hosaka.rxanimation.animator;

import android.animation.Animator;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

/**
 * Created by shunhosaka on 15/10/09.
 */
public class AnimatorEvent {

    public enum Kind {
        START, END, CANCEL, REPEAT
    }

    @CheckResult @NonNull
    public static AnimatorEvent create(@NonNull Animator animation, @NonNull Kind kind) {
        return new AnimatorEvent(animation, kind);
    }

    private final Kind kind;
    private Animator animation;

    private AnimatorEvent(Animator animation, Kind kind) {
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
        if (!(object instanceof AnimatorEvent)) return false;
        AnimatorEvent other = (AnimatorEvent) object;
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
        return "AnimatorEvent{animation="
                + animation()
                + ", kind="
                + kind()
                + "}";
    }

}
