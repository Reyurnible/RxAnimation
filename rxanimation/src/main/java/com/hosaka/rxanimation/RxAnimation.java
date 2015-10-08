package com.hosaka.rxanimation;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.util.Observable;

/**
 * Created by shunhosaka on 15/10/08.
 */
public final class RxAnimation {

    public RxAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
