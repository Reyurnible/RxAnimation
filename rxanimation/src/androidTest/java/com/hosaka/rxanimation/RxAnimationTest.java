package com.hosaka.rxanimation;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.hosaka.rxanimation.animation.AnimationEvent;
import com.hosaka.rxanimation.animation.RxAnimation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Subscription;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static com.google.common.truth.Truth.assertThat;

/**
 * Created by shunhosaka on 2015/12/08.
 */
@RunWith(AndroidJUnit4.class)
public final class RxAnimationTest {
    @Rule public final UiThreadTestRule uiThread = new UiThreadTestRule();

    private final Context context = InstrumentationRegistry.getContext();
    private final View view = new View(context);

    @Test
    @UiThreadTest
    public void cycle() throws InterruptedException {
        RecordingObserver<Object> o = new RecordingObserver<>();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000 * 3);

        Subscription subscription =  RxAnimation.events(alphaAnimation, view).subscribe(o);
        o.assertNoMoreEvents();

        // checking Start
        {   // ロードが完了するまで待つ
            waitForLoadingFinished(1000);
            assertThat(((AnimationEvent) o.takeNext()).kind() == AnimationEvent.Kind.START).isNull();
        }
        // checking end
        {
            waitForLoadingFinished(4000);
            o.assertOnCompleted();
        }

        subscription.unsubscribe();
        o.assertNoMoreEvents();
    }

    public static void waitForLoadingFinished(int timeout) {
        long t = SystemClock.elapsedRealtime();
        do {
            SystemClock.sleep(100);
        } while (SystemClock.elapsedRealtime() - t < timeout);
    }


}
