package com.reyurnible.rxanimation.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;

/**
 * A android animation update event occurred.
 */
public class AnimatorUpdateEvent {

    public static AnimatorUpdateEvent create(@NonNull ValueAnimator animation) {
        return new AnimatorUpdateEvent(animation);
    }

    private ValueAnimator animation;

    public AnimatorUpdateEvent(ValueAnimator animation) {
        this.animation = animation;
    }

    @NonNull
    public Animator animation() {
        return animation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimatorUpdateEvent that = (AnimatorUpdateEvent) o;
        return animation != null ? animation.equals(that.animation) : that.animation == null;
    }

    @Override
    public int hashCode() {
        return animation != null ? animation.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AnimatorUpdateEvent{" +
                "animation=" + animation +
                '}';
    }
}
