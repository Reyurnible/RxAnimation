package com.reyurnible.rxanimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.reyurnible.rxanimation.animator.AnimatorEvent;
import com.reyurnible.rxanimation.animator.AnimatorUpdateEvent;
import com.reyurnible.rxanimation.animator.RxAnimator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public final class RxAnimatorTest {
    @Rule
    public final ActivityTestRule<TestActivity> activityRule =
            new ActivityTestRule<>(TestActivity.class);

    private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    private View child;

    @Before
    public void setUp() {
        TestActivity activity = activityRule.getActivity();
        child = activity.child;
    }

    @Test
    public void events() throws InterruptedException {
        RecordingObserver<AnimatorEvent> o = new RecordingObserver<>();

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(child, "alpha", 0f, 1f);
        alphaAnimator.setDuration(100);

        RxAnimator.events(alphaAnimator).subscribeOn(AndroidSchedulers.mainThread()).subscribe(o);
        {   // ロードが完了するまで待つ
            waitForLoadingFinished(500);
            // Stack Start event
            assertThat(o.takeNext().kind() == AnimatorEvent.Kind.START);
            // Stack End event
            assertThat(o.takeNext().kind() == AnimatorEvent.Kind.END);
        }
        // Checking complete
        {
            waitForLoadingFinished(2000);
            o.assertOnCompleted();
        }
        o.assertNoMoreEvents();
    }

    @Test
    public void updates() throws InterruptedException {
        RecordingObserver<AnimatorUpdateEvent> o = new RecordingObserver<>();

        ValueAnimator animator = ValueAnimator.ofInt(0, 3);
        animator.setDuration(100);

        RxAnimator.updateEvents(animator).subscribeOn(AndroidSchedulers.mainThread()).subscribe(o);
        {   // ロードが完了するまで待つ
            waitForLoadingFinished(500);
            // Event 0
            assertThat(o.takeNext() != null);
            // Event 1
            assertThat(o.takeNext() != null);
            // Event 2
            assertThat(o.takeNext() != null);
        }
    }


    public static void waitForLoadingFinished(int timeout) {
        long t = SystemClock.elapsedRealtime();
        do {
            SystemClock.sleep(100);
        } while (SystemClock.elapsedRealtime() - t < timeout);
    }


}
